package org.training.food.tracker.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.DayDao;
import org.training.food.tracker.dao.util.AutoRollbacker;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayDaoJDBC implements DayDao {
    public static final String FIND_BY_USER_AND_DATE_QUERY = "SELECT id AS days_id, "
                                                                     + "date AS days_date, "
                                                                     + "total_calories AS days_total_calories, "
                                                                     + "user_id AS days_user_id "
                                                                     + "FROM days WHERE days_user_id = ? AND days_date = ?";

    public static final String FIND_ALL_BY_USER_ORDERED_BY_DATE_DESC = "SELECT id, date, total_calories, user_id "
                                                                     + "FROM days WHERE user_id = ? ORDER BY date DESC";

    public static final String FIND_ALL_CONSUMED_FOOD_BY_DAY_ID = "SELECT id, amount, name, time, total_calories, day_id FROM "
                                                                  + "consumed_foods WHERE day_id = ?";

    public static final String CREATE_QUERY = "INSERT INTO days (date, total_calories, user_id) VALUES (?,?,?)";

    public static final String FIND_DAYS_WITH_CONSUMED_FOODS_BY_USER_ID_ORDERED_BY_DATE_DESC =
                                                  "SELECT days.id AS days_id, "
                                                        + "days.date AS days_date, "
                                                        + "days.total_calories AS days_total_calories, "
                                                        + "days.user_id AS days_user_id, "
                                                        + "consumed_foods.id AS consumed_foods_id, "
                                                        + "consumed_foods.amount AS consumed_foods_amount, "
                                                        + "consumed_foods.name AS consumed_foods_name, "
                                                        + "consumed_foods.time AS consumed_foods_time, "
                                                        + "consumed_foods.total_calories AS consumed_foods_total_calories, "
                                                        + "consumed_foods.day_id AS consumed_foods_day_id "
                                                + "FROM days "
                                                + "LEFT JOIN consumed_foods ON consumed_foods.day_id = days.id "
                                                + "WHERE user_id = ? ORDER BY days_date DESC";

    private static final Logger LOG = LogManager.getLogger(DayDaoJDBC.class.getName());

    private static ConsumedFoodDao consumedFoodDao = new ConsumedFoodDaoJDBC();

    public Day create(Day day) throws DaoException {

        LOG.debug("making connection and prepared statement");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            LOG.debug("setting date, totalCalories, user_id");
            statement.setDate(1, Date.valueOf(day.getDate()));
            statement.setBigDecimal(2, day.getTotalCalories());
            statement.setLong(3, day.getUser().getId());

            LOG.debug("executing update");
            statement.executeUpdate();


            try (ResultSet resultSet = statement.getGeneratedKeys()){
                LOG.debug("setting day id from generated keys of result set");
                resultSet.next();
                day.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoException("Creation of day has failed", e);
        }
        return day;
    }

    @Override public Day findById(Long id) throws DaoException {
        return null;
    }

    @Override public Day update(Day day) {
        return null;
    }

    @Override public List<Day> findAll() throws DaoException {
        return null;
    }

    @Override public void deleteById(Long id) {

    }

    public Day findByUserAndDate(User user, LocalDate date) throws DaoException {
        Day day;

        try (Connection connection = ConnectionFactory.getConnection();
                AutoRollbacker autoRollbacker = new AutoRollbacker(connection);
                PreparedStatement dayStatement = connection.prepareStatement(FIND_BY_USER_AND_DATE_QUERY);
                PreparedStatement consumedFoodStatement = connection.prepareStatement(FIND_ALL_CONSUMED_FOOD_BY_DAY_ID);
        ) {
            connection.setAutoCommit(false);

            day = getDay(user, date, dayStatement);
            day.setConsumedFoods(getConsumedFoods(day, consumedFoodStatement));

            autoRollbacker.commit();
        } catch (SQLException e) {
            throw new DaoException("Finding day failed of date " + date, e);
        }
        return day;
    }

    private Day getDay(User user, LocalDate date, PreparedStatement dayStatement) throws SQLException, DaoException {
        Day day;
        dayStatement.setLong(1, user.getId());
        dayStatement.setDate(2, Date.valueOf(date));

        try (ResultSet resultSet = dayStatement.executeQuery()){
            if (!resultSet.next()) {
                throw new DaoException("No such day of " + date);
            }
            day = extractDay(user, resultSet);
        }
        return day;
    }

    private Day extractDay(User user, ResultSet resultSet) throws SQLException {
        Day day;
        day = Day.builder()
                      .id(resultSet.getLong("days_id"))
                      .date(resultSet.getDate("days_date").toLocalDate())
                      .totalCalories(resultSet.getBigDecimal("days_total_calories"))
                      .user(user)
                      .build();
        return day;
    }

    private List<ConsumedFood> getConsumedFoods(Day day, PreparedStatement consumedFoodStatement) throws SQLException {
        consumedFoodStatement.setLong(1, day.getId());

        List<ConsumedFood> consumedFoods = new ArrayList<>();
        try (ResultSet resultSet = consumedFoodStatement.executeQuery()) {
            while (resultSet.next()) {
                consumedFoods.add(consumedFoodDao.extractConsumedFood(resultSet));
            }

        }
        return consumedFoods;
    }

    public List<Day> findAllByUserOrderByDateDesc(User user) throws DaoException {
        List<Day> days = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(FIND_DAYS_WITH_CONSUMED_FOODS_BY_USER_ID_ORDERED_BY_DATE_DESC)) {

            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()){
                extractDaysWithConsumedFoods(user, days, resultSet);
            }

        } catch (SQLException e) {
            throw new DaoException("Days selection failed");
        }
        return days;
    }

    private void extractDaysWithConsumedFoods(User user, List<Day> days, ResultSet resultSet) throws SQLException {
        resultSet.next();

        Day day = extractDay(user, resultSet);

        days.add(day);
        long previousDayId = day.getId();

        while (resultSet.next()) {
            long currentDayId = resultSet.getLong("days_id");

            if (previousDayId != currentDayId) {
                day = extractDay(user, resultSet);
                days.add(day);
            }

            ConsumedFood consumedFood = consumedFoodDao.extractConsumedFood(resultSet);
            consumedFood.setDay(day);

            day.getConsumedFoods().add(consumedFood);
            previousDayId = currentDayId;
        }
    }
}
