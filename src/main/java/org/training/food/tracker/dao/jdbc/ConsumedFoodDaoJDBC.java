package org.training.food.tracker.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.builder.ConsumedFoodBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsumedFoodDaoJDBC implements ConsumedFoodDao {

    private static final String CREATE_QUERY = "INSERT INTO consumed_foods (name, amount, total_calories, time, day_id) "
                                                       + " VALUES (?,?,?,?, (SELECT id from days WHERE date = ?))";

    private static final String FIND_ALL_QUERY = "SELECT id AS consumed_foods_id, "
                                                      + "amount AS consumed_foods_amount, "
                                                      + "name AS consumed_foods_name, "
                                                      + "time AS consumed_foods_time, "
                                                      + "total_calories AS consumed_foods_total_calories, "
                                                      + "day_id AS consumed_foods_day_id "
                                                + "FROM consumed_foods";

    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE consumed_foods.id = ?";

    private static final String FIND_ALL_BY_DAY_ID_QUERY = FIND_ALL_QUERY + " WHERE day_id = ?";

    private static final String UPDATE_QUERY = "UPDATE consumed_foods "
                                                       + "SET name = ?, "
                                                           + "amount = ?, "
                                                           + "total_calories = ?, "
                                                           + "time = ?, "
                                                           + "day_id = ? "
                                                       + "WHERE id = ?";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM consumed_foods WHERE id = ?";

    private static final Logger LOG = LoggerFactory.getLogger(ConsumedFoodDaoJDBC.class.getName());

    @Override public ConsumedFood create(ConsumedFood consumedFood) throws DaoException {
        LOG.debug("create() :: establishing connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            LOG.debug("create() :: prepared statement was created.");

            setPreparedStatementParams(consumedFood, statement);
            statement.setDate(5, Date.valueOf(LocalDate.now()));

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
    }

    @Override public ConsumedFood findById(Long id) throws DaoException {
        LOG.debug("findById() :: finding consumed food by id");
        ConsumedFood consumedFood;
        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            statement.setLong(1, id);
            LOG.debug("findById() :: executing query");
            try (ResultSet resultSet = statement.executeQuery()){
                consumedFood = buildConsumedFood(resultSet);
            }
        } catch (SQLException e) {
            LOG.debug("findById() :: error occurred", e);
            throw new DaoException("findById() :: error occurred", e);
        }
        return consumedFood;
    }

    public List<ConsumedFood> findAllByDayId(Long dayId) throws DaoException {
        LOG.debug("findAllByDayId() :: finding all consumed foods by day id");
        List<ConsumedFood> foods = new ArrayList<>();

        LOG.debug("findAllByDayId() :: getting connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_DAY_ID_QUERY)) {

            statement.setLong(1, dayId);

            getStatemetExecutionResults(foods, statement);
        } catch (SQLException e) {
            LOG.error("findAllByDayId() :: Get consumed food has failed", e);
            throw new DaoException("Get consumed food has failed", e);
        }

        LOG.debug("findAllByDayId() :: {} consumed foods were found.", foods.size());
        return foods;
    }

    private void getStatemetExecutionResults(List<ConsumedFood> foods, PreparedStatement statement) throws SQLException {
        LOG.debug("getConsumedFoods() :: Creating result set");
        try (ResultSet resultSet = statement.executeQuery()) {
            LOG.debug("getConsumedFoods() :: extracting foods from result set");
            while (resultSet.next()) {
                foods.add(buildConsumedFood(resultSet));
            }
        }
    }

    @Override public List<ConsumedFood> findAll() throws DaoException {
        LOG.debug("findAll() :: finding all consumed foods");
        List<ConsumedFood> foods = new ArrayList<>();

        LOG.debug("findAll() :: getting connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_DAY_ID_QUERY)) {

            LOG.debug("findAll() :: executing statement and getting results");
            getStatemetExecutionResults(foods, statement);
        } catch (SQLException e) {
            LOG.error("findAll() :: Get all consumed foods has failed", e);
            throw new DaoException("Get all consumed foods has failed", e);
        }

        LOG.debug("findAll() :: {} consumed foods were found.", foods.size());
        return foods;
    }

    @Override public ConsumedFood update(ConsumedFood consumedFood) throws DaoException {
        LOG.debug("update() :: updating consumed food");

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            setPreparedStatementParams(consumedFood, statement);
            statement.setLong(5, consumedFood.getDay().getId());
            statement.setLong(6, consumedFood.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("update() :: error occurred", e);
            throw new DaoException("update() :: error occurred", e);
        }
        return consumedFood;
    }

    public ConsumedFood buildConsumedFood(ResultSet resultSet) throws SQLException {
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

    private void setGeneratedId(ConsumedFood consumedFood, PreparedStatement statement) throws SQLException {
        LOG.debug("setGeneratedId() :: creating result set");
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            LOG.debug("setGeneratedId() :: result set was created. Setting id from DB to user object to return");
            resultSet.next();
            consumedFood.setId(resultSet.getLong(1));
        }
    }

    @Override public void deleteById(Long id) throws DaoException {
        LOG.debug("deleteById()");
        int affectedRows = 0;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)){

            LOG.debug("deleteById() :: setting id and executing statement");
            statement.setLong(1, id);
            affectedRows = statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("deleteById() :: error occurred", e);
            throw new DaoException("deleteById() :: error occurred", e);
        }

        if (affectedRows != 0) {
            LOG.debug("deleteById() :: {} rows were successfully deleted", affectedRows);
        } else {
            LOG.debug("deleteById() :: 0 rows were deleted. Must be no such id: {}", id);
        }
    }
}
