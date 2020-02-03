package org.training.food_tracker.dao;

import org.training.food_tracker.dao.impl.ConnectionFactory;
import org.training.food_tracker.model.ConsumedFood;
import org.training.food_tracker.model.Day;
import org.training.food_tracker.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class DayDao {
    public static final String FIND_BY_USER_AND_DATE_QUERY = "SELECT id, date, total_calories, user_id "
                                                                     + "FROM days WHERE user_id = ? AND date = ?";

    public static final String CREATE_QUERY = "INSERT INTO days (date, total_calories, user_id) VALUES (?,?,?)";

    public Day create(Day day) throws DaoException {
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, Date.valueOf(day.getDate()));
            statement.setBigDecimal(2, day.getTotalCalories());
            statement.setLong(3, day.getUser().getId());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()){
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
                day = Day.builder()
                              .id(resultSet.getLong("id"))
                              .date(resultSet.getDate("date").toLocalDate())
                              .totalCalories(resultSet.getBigDecimal("total_calories"))
                              .user(user)
                              .build();

                List<ConsumedFood> consumedFoods = new ConsumedFoodDao().findAllByDayId(day.getId());
                day.setConsumedFoods(consumedFoods);
            }
        } catch (SQLException e) {
            throw new DaoException("Finding day failed of date " + date, e);
        }
        return day;
    }

}
