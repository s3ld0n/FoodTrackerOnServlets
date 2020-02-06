package org.training.food_tracker.model;

import java.math.BigDecimal;

public class DailyNorm {
    private Long id;
    private BigDecimal calories;

    public DailyNorm(Long id, BigDecimal calories) {
        this.id = id;
        this.calories = calories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCalories() {
        return calories;
    }

    public void setCalories(BigDecimal calories) {
        this.calories = calories;
    }
}
