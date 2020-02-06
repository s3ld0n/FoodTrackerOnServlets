package org.training.food_tracker.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.dao.util.ConnectionFactory;
import org.training.food_tracker.model.ConsumedFood;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsumedFoodDao {

    public static final String FIND_ALL_BY_DAY_ID_QUERY = "SELECT id, amount, name, time, total_calories, day_id FROM "
                                                               + "consumed_foods WHERE day_id = ?";

    private static final Logger log = LogManager.getLogger(ConsumedFoodDao.class.getName());

    public List<ConsumedFood> findAllByDayId(Long dayId) throws DaoException {
        log.debug("Finding all consumed foods");
        List<ConsumedFood> foods = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_DAY_ID_QUERY)) {

            statement.setLong(1, dayId);

            log.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foods.add(extractConsumedFood(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Get consumed food has failed", e);
            throw new DaoException("Get consumed food has failed", e);
        }

        log.debug("{} consumed foods were found.", foods.size());
        return foods;
    }

    private ConsumedFood extractConsumedFood(ResultSet resultSet) throws SQLException {
        return ConsumedFood.builder()
                            .id(resultSet.getLong("id"))
                            .amount(resultSet.getBigDecimal("amount"))
                            .name(resultSet.getString("name"))
                            .time(resultSet.getTime("time").toLocalTime())
                            .totalCalories(resultSet.getBigDecimal("total_calories"))
                            .build();
    }
}
