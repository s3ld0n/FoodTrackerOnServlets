package org.training.food.tracker.service.defaults;

import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.jdbc.ConsumedFoodDaoJDBC;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.service.ConsumedFoodService;

import java.util.List;

public class ConsumedFoodServiceDefault implements ConsumedFoodService {

    private ConsumedFoodDao consumedFoodDao;

    public ConsumedFoodServiceDefault() {
        this.consumedFoodDao = new ConsumedFoodDaoJDBC();
    }

    public void registerConsumption(ConsumedFood consumedFood) throws DaoException {
        consumedFoodDao.create(consumedFood);
    }

    public void calculateCaloriesByAmount(ConsumedFood consumedFood) {
         consumedFood.setTotalCalories(
                consumedFood.getTotalCalories()
                        .multiply(consumedFood.getAmount())
        );
    }

    public ConsumedFood findById(Long id) throws DaoException {
        return consumedFoodDao.findById(id);
    }

    public List<ConsumedFood> findAllByDay(Day day) throws DaoException {
        return consumedFoodDao.findAllByDayId(day.getId());
    }

    public List<ConsumedFood> findAll() throws DaoException {
        return consumedFoodDao.findAll();
    }

    public ConsumedFood update(ConsumedFood consumedFood) throws DaoException {
        return consumedFoodDao.update(consumedFood);
    }

    public void deleteById(Long id) throws DaoException {
        consumedFoodDao.deleteById(id);
    }
}
