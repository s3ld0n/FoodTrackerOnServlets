package org.training.food.tracker.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayDao {
    public static final String FIND_BY_USER_AND_DATE_QUERY = "SELECT id, date, total_calories, user_id "
                                                                     + "FROM days WHERE user_id = ? AND date = ?";

    public static final String FIND_ALL_BY_USER_ORDERED_BY_DATE_DESC = "SELECT id, date, total_calories, user_id "
                                                                     + "FROM days WHERE user_id = ? ORDER BY date DESC";


    public static final String CREATE_QUERY = "INSERT INTO days (date, total_calories, user_id) VALUES (?,?,?)";

    private static final Logger log = LogManager.getLogger(DayDao.class.getName());

    public Day create(Day day) throws DaoException {

        log.debug("making connection and prepared statement");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            log.debug("setting date, totalCalories, user_id");
            statement.setDate(1, Date.valueOf(day.getDate()));
            statement.setBigDecimal(2, day.getTotalCalories());
            statement.setLong(3, day.getUser().getId());

            log.debug("executing update");
            statement.executeUpdate();


            try (ResultSet resultSet = statement.getGeneratedKeys()){
                log.debug("setting day id from generated keys of result set");
                resultSet.next();
                day.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoException("Creation of day has failed", e);
        }
        return day;
    }

    public Day findByUserAndDate(User user, LocalDate date) throws DaoException {
        Day day;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_AND_DATE_QUERY)) {

            statement.setLong(1, user.getId());
            statement.setDate(2, Date.valueOf(date));

            try (ResultSet resultSet = statement.executeQuery()){
                if (!resultSet.next()) {
                    throw new DaoException("No such day of " + date);
                }
                day = extractDay(user, resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Finding day failed of date " + date, e);
        }
        return day;
    }

    private Day extractDay(User user, ResultSet resultSet) throws SQLException, DaoException {
        Day day;
        day = Day.builder()
                      .id(resultSet.getLong("id"))
                      .date(resultSet.getDate("date").toLocalDate())
                      .totalCalories(resultSet.getBigDecimal("total_calories"))
                      .user(user)
                      .build();

        List<ConsumedFood> consumedFoods = new ConsumedFoodDao().findAllByDayId(day.getId());
        day.setConsumedFoods(consumedFoods);
        return day;
    }

    public List<Day> findAllByUserOrderByDateDesc(User user) throws DaoException {
        List<Day> days= new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ORDERED_BY_DATE_DESC)) {

            statement.setLong(1, user.getId());
            ConsumedFoodDao consumedFoodDao = new ConsumedFoodDao();
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    Day day = Day.builder()
                                  .id(resultSet.getLong("id"))
                                  .totalCalories(resultSet.getBigDecimal(""))
                                  .date(resultSet.getDate("date").toLocalDate())
                                  .user(user)
                                  .build();

                    /*TODO REWRITE QUERY WITHOUT ACCESSING DB IN LOOP*/
                    day.setConsumedFoods(consumedFoodDao.findAllByDayId(day.getId()));
                    days.add(day);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Days selection failed");
        }
        return days;
    }
}
