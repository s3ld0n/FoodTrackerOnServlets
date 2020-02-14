package org.training.food.tracker.dao.jdbc;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.FoodDao;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;
import org.training.food.tracker.model.builder.FoodBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoJDBC implements FoodDao {

    public static final String CREATE_QUERY = "INSERT INTO foods (name, calories, user_id) VALUES (?,?,?)";

    public static final String FIND_ALL_COMMON_EXCLUDING_PERSONAL_BY_USER_ID =
            "SELECT id, name, calories, user_id FROM foods WHERE user_id IS NULL AND name NOT IN "
                                                         + "(SELECT name FROM foods WHERE user_id = ?) ORDER BY id DESC";

    public static final String FIND_ALL_BY_OWNER_ORDERED_BY_ID_DESC =
            "SELECT id, name, calories, user_id FROM foods WHERE user_id = ? ORDER BY id DESC";

    private static final String FIND_ALL_COMMON = "SELECT id, calories, name FROM foods WHERE id IS NULL";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM foods WHERE id = ?";

    private static final String DELETE_BY_NAME_AND_USER_ID = "DELETE FROM foods WHERE name = ? AND user_id = ?";

    private static final Logger LOG = LoggerFactory.getLogger(FoodDaoJDBC.class.getName());

    @Override public Food create(Food food) throws DaoException {
        LOG.debug("create() :: establishing connection");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            LOG.debug("create() :: prepared statement was created.");

            setPreparedStatementParams(food, statement);

            LOG.debug("create() :: executing prepared statement");
            statement.executeUpdate();

            setGeneratedId(food, statement);
        } catch (SQLException e) {
            LOG.error("Creation of food has failed.", e);
            throw new DaoException("Creation of food has failed.", e);
        }

        LOG.debug("create() :: food {} was successfully created", food.getName());

        return food;
    }

    private void setGeneratedId(Food food, PreparedStatement statement) throws SQLException {
        LOG.debug("setGeneratedId() :: creating result set");
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            LOG.debug("setGeneratedId() :: result set was created. Setting id from DB to user object to return");
            resultSet.next();
            food.setId(resultSet.getLong(1));
        }
    }

    private void setPreparedStatementParams(Food food, PreparedStatement statement) throws SQLException {
        statement.setString(1, food.getName());
        statement.setBigDecimal(2, food.getCalories());
        statement.setLong(3, food.getOwner().getId());
    }

    public List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException {
        return findFoodsByUserIdUsingQuery(userId, FIND_ALL_COMMON_EXCLUDING_PERSONAL_BY_USER_ID);
    }

    private List<Food> findFoodsByUserIdUsingQuery(Long userId, String query) throws DaoException {
        LOG.debug("findFoodsByUserIdUsingQuery()");
        List<Food> foods = new ArrayList<>();
        LOG.debug("findFoodsByUserIdUsingQuery() :: creating connection, making prepared statement");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);

            getFoods(foods, statement);

        } catch (SQLException e) {
            LOG.debug("findFoodsByUserIdUsingQuery() :: selection of common food that does not belong to user has failed");
            throw new DaoException("selection of common food that does not belong to user has failed", e);
        }
        LOG.debug("findFoodsByUserIdUsingQuery() :: {} foods were found", foods.size());
        return foods;
    }

    private void getFoods(List<Food> foods, PreparedStatement statement) throws SQLException {
        LOG.debug("getFoods() :: executing statement, getting result set and extracting results");
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                LOG.debug("getFoods() :: creating food");
                foods.add(extractFood(resultSet));
            }
        }
    }

    private Food extractFood(ResultSet resultSet) throws SQLException {
        return FoodBuilder.instance()
                            .id(resultSet.getLong("id"))
                            .name(resultSet.getString("name"))
                            .calories(resultSet.getBigDecimal("calories"))
                            .build();
    }

    public List<Food> findAllByUserIdOrderByIdDesc(Long userId) throws DaoException {
        return findFoodsByUserIdUsingQuery(userId, FIND_ALL_BY_OWNER_ORDERED_BY_ID_DESC);
    }

    @Override public Food findById(Long id) throws DaoException {
        return null;
    }

    @Override public Food update(Food food) {
        return null;
    }

    @Override public List<Food> findAll() throws DaoException {
        return null;
    }

    @Override public void deleteById(Long id) throws DaoException {
        LOG.debug("deleteById()");
        LOG.debug("deleteById() :: establishing connection");
        int affectedRows = 0;
        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {

            statement.setLong(1, id);
            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Deletion has failed", e);
            throw new DaoException("Deletion has failed", e);
        }

        if (affectedRows == 0) {
            LOG.debug("No food with id: {} in database", id);
        } else {
            LOG.debug("Food was successfully deleted");
        }
    }

    public void deleteByNameAndOwner(String foodName, User user) throws DaoException {
        LOG.debug("deleteByNameAndOwner()");
        LOG.debug("deleteByNameAndOwner() :: establishing connection");

        int affectedRows;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_NAME_AND_USER_ID)) {

            statement.setString(1, foodName);
            statement.setLong(2, user.getId());
            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Deletion has failed", e);
            throw new DaoException("Deletion has failed", e);
        }

        if (affectedRows == 0) {
            LOG.debug("No food with name: {} and user id: {} in database", foodName, user.getId());
        } else {
            LOG.debug("Food was successfully deleted");
        }
    }
}
