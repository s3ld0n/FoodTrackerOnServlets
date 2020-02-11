package org.training.food.tracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Day {

    private Long id;
    private List<ConsumedFood> consumedFoods;
    private BigDecimal caloriesConsumed;
    private BigDecimal exceededCalories;
    private boolean isDailyNormExceeded;
    private User user;
    private LocalDate date;

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

    public BigDecimal getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(BigDecimal caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public BigDecimal getExceededCalories() {
        return exceededCalories;
    }

    public void setExceededCalories(BigDecimal exceededCalories) {
        this.exceededCalories = exceededCalories;
    }

    public boolean isDailyNormExceeded() {
        return isDailyNormExceeded;
    }

    public void setDailyNormExceeded(boolean dailyNormExceeded) {
        isDailyNormExceeded = dailyNormExceeded;
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
