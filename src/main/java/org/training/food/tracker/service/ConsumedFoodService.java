package org.training.food.tracker.service;

import org.training.food.tracker.dto.FoodDTO;

public interface ConsumedFoodService {
    void registerConsumption(FoodDTO foodDTO);
}
