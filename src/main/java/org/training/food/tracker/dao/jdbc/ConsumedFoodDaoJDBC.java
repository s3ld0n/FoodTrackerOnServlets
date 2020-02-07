package org.training.food.tracker.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.ConsumedFood;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsumedFoodDaoJDBC implements ConsumedFoodDao {

    public static final String FIND_ALL_BY_DAY_ID_QUERY = "SELECT id AS consumed_foods_id, "
                                                              + "amount AS consumed_foods_amount, "
                                                              + "name AS consumed_foods_name, "
                                                              + "time AS consumed_foods_time, "
                                                              + "total_calories AS consumed_foods_total_calories, "
                                                              + "day_id AS consumed_foods_day_id "
                                                          + "FROM consumed_foods "
                                                          + "WHERE day_id = ?";

    private static final Logger LOG = LogManager.getLogger(ConsumedFoodDaoJDBC.class.getName());

    public List<ConsumedFood> findAllByDayId(Long dayId) throws DaoException {
        LOG.debug("Finding all consumed foods");
        List<ConsumedFood> foods = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_DAY_ID_QUERY)) {

            statement.setLong(1, dayId);

            LOG.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foods.add(extractConsumedFood(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error("Get consumed food has failed", e);
            throw new DaoException("Get consumed food has failed", e);
        }

        LOG.debug("{} consumed foods were found.", foods.size());
        return foods;
    }

    public ConsumedFood extractConsumedFood(ResultSet resultSet) throws SQLException {
        return ConsumedFood.builder()
                            .id(resultSet.getLong("consumed_foods_id"))
                            .amount(resultSet.getBigDecimal("consumed_foods_amount"))
                            .name(resultSet.getString("consumed_foods_name"))
                            .time(resultSet.getTime("consumed_foods_time").toLocalTime())
                            .totalCalories(resultSet.getBigDecimal("consumed_foods_total_calories"))
                            .build();
    }

    @Override public ConsumedFood create(ConsumedFood consumedFood) throws DaoException {
        return null;
    }

    @Override public ConsumedFood findById(Long id) throws DaoException {
        return null;
    }

    @Override public ConsumedFood update(ConsumedFood consumedFood) {
        return null;
    }

    @Override public List<ConsumedFood> findAll() throws DaoException {
        return null;
    }

    @Override public void deleteById(Long id) {

    }
}
