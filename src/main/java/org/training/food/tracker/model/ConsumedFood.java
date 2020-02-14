package org.training.food.tracker.model;

import java.math.BigDecimal;
import java.time.LocalTime;

public class ConsumedFood {

    private Long id;
    private String name;
    private BigDecimal amount;
    private BigDecimal totalCalories;
    private Day day;
    private LocalTime time;

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

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
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
