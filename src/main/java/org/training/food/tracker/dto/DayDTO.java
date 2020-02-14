package org.training.food.tracker.dto;

import org.training.food.tracker.model.ConsumedFood;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DayDTO {

    private List<ConsumedFood> consumedFoods;
    private BigDecimal caloriesConsumed;
    private BigDecimal exceededCalories;
    private boolean isDailyNormExceeded;
    private LocalDate date;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private DayDTO dayDTO;

        Builder() {
            this.dayDTO = new DayDTO();
        }

        public Builder consumedFoods(List<ConsumedFood> consumedFoods) {
            dayDTO.setConsumedFoods(consumedFoods);
            return this;
        }

        public Builder caloriesConsumed(BigDecimal totalCalories) {
            dayDTO.setCaloriesConsumed(totalCalories);
            return this;
        }

        public Builder exceededCalories(BigDecimal exceededCalories) {
            dayDTO.setExceededCalories(exceededCalories);
            return this;
        }

        public Builder isDailyNormExceeded(boolean isDailyNormExceeded) {
            dayDTO.setDailyNormExceeded(isDailyNormExceeded);
            return this;
        }

        public Builder date(LocalDate date) {
            dayDTO.setDate(date);
            return this;
        }

        public DayDTO build() {
            return dayDTO;
        }
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
