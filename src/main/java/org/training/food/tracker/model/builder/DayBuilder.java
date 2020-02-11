package org.training.food.tracker.model.builder;

import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class DayBuilder {

    private Day day;

    public static DayBuilder instance() {
        return new DayBuilder();
    }

    private DayBuilder() {
        this.day = new Day();
    }

    public DayBuilder id(Long id) {
        day.setId(id);
        return this;
    }

    public DayBuilder consumedFoods(List<ConsumedFood> consumedFoods) {
        day.setConsumedFoods(consumedFoods);
        return this;
    }

    public DayBuilder totalCalories(BigDecimal totalCalories) {
        day.setTotalCalories(totalCalories);
        return this;
    }

    public DayBuilder user(User user) {
        day.setUser(user);
        return this;
    }

    public DayBuilder date(LocalDate date) {
        day.setDate(date);
        return this;
    }

    public Day build() {
        return day;
    }
}

