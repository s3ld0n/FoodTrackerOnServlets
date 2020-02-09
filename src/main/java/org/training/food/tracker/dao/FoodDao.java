package org.training.food.tracker.dao;

import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;

import java.util.List;

public interface FoodDao extends CrudDao<Food> {
    List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException;

    List<Food> findAllByUserIdOrderByIdDesc(Long userId) throws DaoException;

    void removeByNameAndOwner(String foodName, User user);

    List<Food> findAllCommon() throws DaoException;
}
