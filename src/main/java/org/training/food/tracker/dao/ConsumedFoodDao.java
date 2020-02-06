package org.training.food.tracker.dao;

import org.training.food.tracker.model.ConsumedFood;

import java.util.List;

public interface ConsumedFoodDao extends CrudDao<ConsumedFood> {
    List<ConsumedFood> findAllByDayId(Long dayId) throws DaoException;
}
