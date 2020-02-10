package org.training.food.tracker.dao.jdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                                                                     + "FROM days WHERE user_id = ? AND date = ?";

    public static final String FIND_ALL_BY_USER_ORDERED_BY_DATE_DESC = "SELECT id, date, total_calories, user_id "
                                                                     + "FROM days WHERE user_id = ? ORDER BY date DESC";

    public static final String FIND_ALL_CONSUMED_FOOD_BY_DAY_ID_ORDER_BY_TIME_DESC =
            "SELECT id, amount, name, time, total_calories, day_id FROM consumed_foods WHERE day_id = ? ORDER BY time DESC";

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

    private static final Logger LOG = LoggerFactory.getLogger(DayDaoJDBC.class.getName());

    private static ConsumedFoodDao consumedFoodDao = new ConsumedFoodDaoJDBC();

    public Day create(Day day) throws DaoException {
        LOG.debug("create()");
        LOG.debug("create() :: making connection and prepared statement");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            LOG.debug("create() :: setting date, totalCalories, user_id");
            statement.setDate(1, Date.valueOf(day.getDate()));
            statement.setBigDecimal(2, day.getTotalCalories());
            statement.setLong(3, day.getUser().getId());

            LOG.debug("create() :: executing update");
            statement.executeUpdate();


            try (ResultSet resultSet = statement.getGeneratedKeys()){
                LOG.debug("create() :: setting day id from generated keys of result set");
                resultSet.next();
                day.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoException("create() :: Creation of day has failed", e);
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
        LOG.debug("findByUserAndDate()");
        Day day;

        LOG.debug("findByUserAndDate() ::  getting connection, preparing statements, creating autoRollbacker");
        try (Connection connection = ConnectionFactory.getConnection();
                AutoRollbacker autoRollbacker = new AutoRollbacker(connection);
                PreparedStatement dayStatement = connection.prepareStatement(FIND_BY_USER_AND_DATE_QUERY);
                PreparedStatement consumedFoodStatement = connection.prepareStatement(
                        FIND_ALL_CONSUMED_FOOD_BY_DAY_ID_ORDER_BY_TIME_DESC);
        ) {
            LOG.debug("findByUserAndDate() ::  setting autocommit false");
            connection.setAutoCommit(false);

            LOG.debug("findByUserAndDate() ::  getting day");
            day = getDay(user, date, dayStatement);

            LOG.debug("findByUserAndDate() ::  getting and setting consumed food");
            day.setConsumedFoods(getConsumedFoods(day, consumedFoodStatement));

            LOG.debug("findByUserAndDate() ::  making commit");
            autoRollbacker.commit();
        } catch (SQLException e) {
            LOG.error("Finding day failed of date {}, {}", date, e);
            throw new DaoException("Finding day failed of date " + date, e);
        }
        return day;
    }

    private Day getDay(User user, LocalDate date, PreparedStatement dayStatement) throws SQLException, DaoException {
        Day day;
        LOG.debug("getDay() ::  setting params of statement");
        dayStatement.setLong(1, user.getId());
        dayStatement.setDate(2, Date.valueOf(date));

        LOG.debug("params have been set - user_id: {}, date: {}", user.getId(), Date.valueOf(date));

        LOG.debug("getDay() ::  executing query");
        try (ResultSet resultSet = dayStatement.executeQuery()){
            if (!resultSet.next()) {
                LOG.warn("getDay() ::  query returned nothing");
                throw new DaoException("No such day of " + date);
            }
            LOG.debug("getDay() ::  extracting day from result set");
            day = extractDay(user, resultSet);
        }
        return day;
    }

    private Day extractDay(User user, ResultSet resultSet) throws SQLException {
        LOG.debug("extractDay()");
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
        LOG.debug("getConsumedFoods()");
        consumedFoodStatement.setLong(1, day.getId());

        List<ConsumedFood> consumedFoods = new ArrayList<>();
        LOG.debug("getConsumedFoods() :: executing query");
        try (ResultSet resultSet = consumedFoodStatement.executeQuery()) {
            LOG.debug("getConsumedFoods() :: looping result set extracting foods");
            while (resultSet.next()) {
                consumedFoods.add(consumedFoodDao.extractConsumedFood(resultSet));
            }

        }
        LOG.debug("extracted {} consumed foods", consumedFoods.size());
        return consumedFoods;
    }

    public List<Day> findAllByUserOrderByDateDesc(User user) throws DaoException {
        LOG.debug("findAllByUserOrderByDateDesc()");
        List<Day> days = new ArrayList<>();

        LOG.debug("findAllByUserOrderByDateDesc() ::  making connection, prepared statement");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(FIND_DAYS_WITH_CONSUMED_FOODS_BY_USER_ID_ORDERED_BY_DATE_DESC)) {

            statement.setLong(1, user.getId());
            LOG.debug("findAllByUserOrderByDateDesc() ::  executing query");
            try (ResultSet resultSet = statement.executeQuery()){
                extractDaysWithConsumedFoods(user, days, resultSet);
            }

        } catch (SQLException e) {
            LOG.error("Days selection failed");
            throw new DaoException("Days selection failed");
        }
        return days;
    }

    private void extractDaysWithConsumedFoods(User user, List<Day> days, ResultSet resultSet) throws SQLException {
        LOG.debug("extractDaysWithConsumedFoods()");
        resultSet.next();

        Day day = extractDay(user, resultSet);

        days.add(day);
        long previousDayId = day.getId();

        LOG.debug("extractDaysWithConsumedFoods() :: looping throw result set");
        while (resultSet.next()) {
            long currentDayId = resultSet.getLong("days_id");

            if (previousDayId != currentDayId) {
                day = extractDay(user, resultSet);
                days.add(day);
            }

            ConsumedFood consumedFood = consumedFoodDao.extractConsumedFood(resultSet);

            day.getConsumedFoods().add(consumedFood);
            previousDayId = currentDayId;
        }
    }
}
