package org.training.food.tracker.dao;

import org.training.food.tracker.model.Food;

import java.util.List;

public interface FoodDao extends CrudDao<Food> {
    public List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException;
    public List<Food> findAllByUserIdOrderByIdDesc(Long userId) throws DaoException;
}
