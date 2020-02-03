package org.training.food_tracker.dto;

import java.math.BigDecimal;

public class FoodDTO {
    private String name;
    private BigDecimal totalCalories;
    private BigDecimal amount;

    public static Builder build() {
        return new Builder();
    }

    public static class Builder {
        private FoodDTO foodDTO;

        Builder() {
            this.foodDTO = new FoodDTO();
        }

        public Builder name(String name) {
            foodDTO.setName(name);
            return this;
        }

        public Builder totalCalories(BigDecimal totalCalories) {
            foodDTO.setTotalCalories(totalCalories);
            return this;
        }

        public Builder amount(BigDecimal amount) {
            foodDTO.setAmount(amount);
            return this;
        }

        public FoodDTO build() {
            return foodDTO;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(BigDecimal totalCalories) {
        this.totalCalories = totalCalories;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
