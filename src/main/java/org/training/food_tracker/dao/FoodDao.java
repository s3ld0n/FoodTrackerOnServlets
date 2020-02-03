package org.training.food_tracker.dao;

import org.training.food_tracker.dao.impl.ConnectionFactory;
import org.training.food_tracker.model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDao implements CrudDao<Food> {

    public static final String FIND_ALL_COMMON_EXCLUDING_PERSONAL_BY_USER_ID_QUERY =
            "SELECT id, name, calories, user_id from food WHERE user_id IS NULL AND name NOT IN "
                                                         + "(SELECT name FROM food WHERE user_id = ?) ORDER BY id DESC";

    @Override public Food create(Food food) throws DaoException {
        return null;
    }

    public List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException {
        List<Food> foods = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(FIND_ALL_COMMON_EXCLUDING_PERSONAL_BY_USER_ID_QUERY)) {
            statement.setLong(1, userId);
            statement.executeUpdate();

            try (ResultSet resultSet = statement.executeQuery()) {
                Food food = Food.builder()
                                    .id(resultSet.getLong("id"))
                                    .name(resultSet.getString("name"))
                                    .calories(resultSet.getBigDecimal("calories"))
                                    .build();
                foods.add(food);
            }
        } catch (SQLException e) {
            throw new DaoException("selection of common food that does not belong to user has failed", e);
        }
        return foods;
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
}
