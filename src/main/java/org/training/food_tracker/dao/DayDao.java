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
