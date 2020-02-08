package org.training.food.tracker.service.defaults;

import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.jdbc.ConsumedFoodDaoJDBC;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.service.ConsumedFoodService;

public class ConsumedFoodServiceDefault implements ConsumedFoodService {

    private ConsumedFoodDao consumedFoodDao;

    public ConsumedFoodServiceDefault() {
        this.consumedFoodDao = new ConsumedFoodDaoJDBC();
    }

    public void registerConsumption(FoodDTO foodDTO) {

    }

}
