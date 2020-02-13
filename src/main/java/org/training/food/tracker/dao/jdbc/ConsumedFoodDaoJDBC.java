package org.training.food.tracker.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.builder.ConsumedFoodBuilder;

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

    private static final Logger LOG = LoggerFactory.getLogger(ConsumedFoodDaoJDBC.class.getName());

    public List<ConsumedFood> findAllByDayId(Long dayId) throws DaoException {
        LOG.debug("findAllByDayId()");
        LOG.debug("Finding all consumed foods");
        List<ConsumedFood> foods = new ArrayList<>();

        LOG.debug("findAllByDayId() :: getting connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_DAY_ID_QUERY)) {

            statement.setLong(1, dayId);

            LOG.debug("findAllByDayId() :: Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                LOG.debug("findAllByDayId() :: extracting foods from result set");
                while (resultSet.next()) {
                    foods.add(extractConsumedFood(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error("findAllByDayId() :: Get consumed food has failed", e);
            throw new DaoException("Get consumed food has failed", e);
        }

        LOG.debug("findAllByDayId() :: {} consumed foods were found.", foods.size());
        return foods;
    }

    public ConsumedFood extractConsumedFood(ResultSet resultSet) throws SQLException {
        return ConsumedFoodBuilder.instance()
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
