package org.training.food.tracker.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.builder.ConsumedFoodBuilder;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsumedFoodDaoJDBC implements ConsumedFoodDao {

    private static final String CREATE_QUERY = "INSERT INTO consumed_foods (name, amount, total_calories, time, day_id) "
                                                       + " VALUES (?,?,?,?, (SELECT id from days WHERE date = ?))";

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
        LOG.debug("extractConsumedFood :: consumedFood time : {}",
                resultSet.getTime("consumed_foods_time").toLocalTime());

        return ConsumedFoodBuilder.instance()
                            .id(resultSet.getLong("consumed_foods_id"))
                            .amount(resultSet.getBigDecimal("consumed_foods_amount"))
                            .name(resultSet.getString("consumed_foods_name"))
                            .time(resultSet.getTime("consumed_foods_time").toLocalTime())
                            .totalCalories(resultSet.getBigDecimal("consumed_foods_total_calories"))
                            .build();
    }

    @Override public ConsumedFood create(ConsumedFood consumedFood) throws DaoException {
        LOG.debug("create() :: establishing connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            LOG.debug("create() :: prepared statement was created.");

            setPreparedStatementParams(consumedFood, statement);

            LOG.debug("create() :: executing prepared statement");
            statement.executeUpdate();

            setGeneratedId(consumedFood, statement);
        } catch (SQLException e) {
            LOG.error("Creation of food has failed.", e);
            throw new DaoException("Creation of food has failed.", e);
        }

        LOG.debug("create() :: food {} was successfully created", consumedFood.getName());

        return consumedFood;
    }

    private void setPreparedStatementParams(ConsumedFood consumedFood, PreparedStatement statement)
            throws SQLException {
        statement.setString(1, consumedFood.getName());
        statement.setBigDecimal(2, consumedFood.getAmount());
        statement.setBigDecimal(3, consumedFood.getTotalCalories());
        statement.setTime(4, Time.valueOf(consumedFood.getTime()));
        statement.setDate(5, Date.valueOf(LocalDate.now()));
    }

    private void setGeneratedId(ConsumedFood consumedFood, PreparedStatement statement) throws SQLException {
        LOG.debug("setGeneratedId() :: creating result set");
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            LOG.debug("setGeneratedId() :: result set was created. Setting id from DB to user object to return");
            resultSet.next();
            consumedFood.setId(resultSet.getLong(1));
        }
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
