package org.training.food.tracker.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.FoodDao;
import org.training.food.tracker.dao.util.ConnectionFactory;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoJDBC implements FoodDao {

    public static final String FIND_ALL_COMMON_EXCLUDING_PERSONAL_BY_USER_ID =
            "SELECT id, name, calories, user_id FROM food WHERE user_id IS NULL AND name NOT IN "
                                                         + "(SELECT name FROM food WHERE user_id = ?) ORDER BY id DESC";

    public static final String FIND_ALL_BY_OWNER_ORDERED_BY_ID_DESC =
            "SELECT id, name, calories, user_id FROM food WHERE user_id = ? ORDER BY id DESC";

    private static final Logger LOG = LogManager.getLogger(FoodDaoJDBC.class.getName());

    @Override public Food create(Food food) throws DaoException {
        return null;
    }

    public List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException {
        return findFoodsByUserIdUsingQuery(userId, FIND_ALL_BY_OWNER_ORDERED_BY_ID_DESC);
    }

    private List<Food> findFoodsByUserIdUsingQuery(Long userId, String query) throws DaoException {
        List<Food> foods = new ArrayList<>();
        LOG.debug("creating connection, making prepared statement");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query)) {
            statement.setLong(1, userId);

            LOG.debug("executing statement, getting result set and extracting results");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LOG.debug("creating food");
                    Food food = Food.builder()
                                        .id(resultSet.getLong("id"))
                                        .name(resultSet.getString("name"))
                                        .calories(resultSet.getBigDecimal("calories"))
                                        .build();
                    foods.add(food);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("selection of common food that does not belong to user has failed", e);
        }
        LOG.debug("{} foods were found", foods.size());
        return foods;
    }

    public List<Food> findAllByUserIdOrderByIdDesc(Long userId) throws DaoException {
        return findFoodsByUserIdUsingQuery(userId, FIND_ALL_COMMON_EXCLUDING_PERSONAL_BY_USER_ID);
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

    @Override public void deleteById(Long id) {

    }

    public void removeByNameAndOwner(String foodName, User user) {

    }

    public List<Food> findAllCommon() {
        return null;
    }
}
