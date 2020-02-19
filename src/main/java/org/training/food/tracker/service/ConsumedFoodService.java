package org.training.food.tracker.service;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;

import java.util.List;

public interface ConsumedFoodService {
    void registerConsumption(ConsumedFood food) throws DaoException;

    ConsumedFood findById(Long id) throws DaoException;

    List<ConsumedFood> findAll() throws DaoException;

    void calculateCaloriesByAmount(ConsumedFood consumedFood);

    ConsumedFood update(ConsumedFood consumedFood) throws DaoException;

    List<ConsumedFood> findAllByDay(Day day) throws DaoException;

    void deleteById(Long id) throws DaoException;
}
