package org.training.food.tracker.model.builder;

import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;

import java.math.BigDecimal;
import java.time.LocalTime;

public class ConsumedFoodBuilder {
    private ConsumedFood consumedFood;

    private ConsumedFoodBuilder() {
        this.consumedFood = new ConsumedFood();
    }

    public static ConsumedFoodBuilder instance() {
        return new ConsumedFoodBuilder();
    }
    
    public ConsumedFoodBuilder id(Long id) {
        consumedFood.setId(id);
        return this;
    }

    public ConsumedFoodBuilder name(String name) {
        consumedFood.setName(name);
        return this;
    }

    public ConsumedFoodBuilder amount(BigDecimal amount) {
        consumedFood.setAmount(amount);
        return this;
    }

    public ConsumedFoodBuilder totalCalories(BigDecimal totalCalories) {
        consumedFood.setTotalCalories(totalCalories);
        return this;
    }

    public ConsumedFoodBuilder day(Day day) {
        consumedFood.setDay(day);
        return this;
    }

    public ConsumedFoodBuilder time(LocalTime time) {
        consumedFood.setTime(time);
        return this;
    }

    public ConsumedFood build() {
        return consumedFood;
    }
}
