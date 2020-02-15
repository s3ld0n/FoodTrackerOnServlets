package org.training.food.tracker.service;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;

import java.util.List;

public interface FoodService {

    void addForOwner(FoodDTO foodDTO, User owner) throws DaoException;

    void registerConsumption(ConsumedFood food) throws DaoException;

    List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException;

    List<Food> findAllByOwner(User user) throws DaoException;

    void deleteByNameAndUserId(String foodName, User user) throws DaoException;
}
