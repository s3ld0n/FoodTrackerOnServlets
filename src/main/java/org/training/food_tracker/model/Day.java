package org.training.food_tracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Day {

    private Long id;
    private List<ConsumedFood> consumedFoods;
    private BigDecimal totalCalories;
    private User user;
    private LocalDate date;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Day day;

        Builder() {
            this.day = new Day();
        }

        public Builder id(Long id) {
            day.setId(id);
            return this;
        }

        public Builder consumedFood(List<ConsumedFood> consumedFoods) {
            day.setConsumedFoods(consumedFoods);
            return this;
        }

        public Builder totalCalories(BigDecimal totalCalories) {
            day.setTotalCalories(totalCalories);
            return this;
        }

        public Builder user(User user) {
            day.setUser(user);
            return this;
        }

        public Builder localDate(LocalDate date) {
            day.setDate(date);
            return this;
        }

        public Day build() {
            return day;
        }
    }

    public void addCalories(BigDecimal calories) {
        totalCalories = totalCalories.add(calories);
    }

    @Override public String toString() {
        return "Day{" + "id=" + id + ", consumedFoods=" + consumedFoods + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ConsumedFood> getConsumedFoods() {
        return consumedFoods;
    }

    public void setConsumedFoods(List<ConsumedFood> consumedFoods) {
        this.consumedFoods = consumedFoods;
    }

    public BigDecimal getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(BigDecimal totalCalories) {
        this.totalCalories = totalCalories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
