package org.training.food_tracker.model;

import java.math.BigDecimal;


public class Food {
    private Long id;
    private String name;
    private BigDecimal calories;
    private User owner;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Food food;

        public Builder() {
            this.food = new Food();
        }

        public Builder id(Long id) {
            food.setId(id);
            return this;
        }

        public Builder name(String name) {
            food.setName(name);
            return this;
        }

        public Builder calories(BigDecimal calories) {
            food.setCalories(calories);
            return this;
        }

        public Builder owner(User owner) {
            food.setOwner(owner);
            return this;
        }

        public Food build() {
            return food;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCalories() {
        return calories;
    }

    public void setCalories(BigDecimal calories) {
        this.calories = calories;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
