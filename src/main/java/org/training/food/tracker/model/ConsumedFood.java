package org.training.food.tracker.model;

import java.math.BigDecimal;
import java.time.LocalTime;

public class ConsumedFood {

    private Long id;
    private String name;
    private BigDecimal amount;
    private BigDecimal totalCalories;
    private LocalTime time;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ConsumedFood consumedFood;

        Builder() {
            this.consumedFood = new ConsumedFood();
        }

        public Builder id(Long id) {
            consumedFood.setId(id);
            return this;
        }

        public Builder name(String name) {
            consumedFood.setName(name);
            return this;
        }

        public Builder amount(BigDecimal amount) {
            consumedFood.setAmount(amount);
            return this;
        }

        public Builder totalCalories(BigDecimal totalCalories) {
            consumedFood.setTotalCalories(totalCalories);
            return this;
        }

        public Builder time(LocalTime time) {
            consumedFood.setTime(time);
            return this;
        }

        public ConsumedFood build() {
            return consumedFood;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(BigDecimal totalCalories) {
        this.totalCalories = totalCalories;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override public String toString() {
        return "ConsumedFood{" + "food='" + name + '\'' + ", amount=" + amount + ", totalCalories=" + totalCalories
                       + '}';
    }
}
