package org.training.food.tracker.model.builder;

import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;

import java.math.BigDecimal;

public class FoodBuilder {
    private Food food;

    private FoodBuilder() {
        this.food = new Food();
    }

    public static FoodBuilder instance() {
        return new FoodBuilder();
    }

    public FoodBuilder id(Long id) {
        food.setId(id);
        return this;
    }

    public FoodBuilder name(String name) {
        food.setName(name);
        return this;
    }

    public FoodBuilder calories(BigDecimal calories) {
        food.setCalories(calories);
        return this;
    }

    public FoodBuilder owner(User owner) {
        food.setOwner(owner);
        return this;
    }

    public Food build() {
        return food;
    }
}

