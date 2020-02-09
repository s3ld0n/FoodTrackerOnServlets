package org.training.food.tracker.dto;

import org.training.food.tracker.model.ConsumedFood;

import java.math.BigDecimal;
import java.util.List;

public class ConsumptionDataDTO {
    private List<ConsumedFood> consumedFood;
    private BigDecimal dailyNorm;
    private BigDecimal caloriesConsumed;
    private BigDecimal exceededCalories;
    private boolean isDailyNormExceeded;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ConsumptionDataDTO consumptionDataDTO;

        Builder() {
            this.consumptionDataDTO = new ConsumptionDataDTO();
        }

        public Builder consumedFoods(List<ConsumedFood> consumedFoods) {
            consumptionDataDTO.setConsumedFood(consumedFoods);
            return this;
        }

        public Builder dailyNorm(BigDecimal dailyNorm) {
            consumptionDataDTO.setDailyNorm(dailyNorm);
            return this;
        }

        public Builder caloriesConsumed(BigDecimal caloriesConsumed) {
            consumptionDataDTO.setCaloriesConsumed(caloriesConsumed);
            return this;
        }

        public Builder exceededCalories(BigDecimal exceededCalories) {
            consumptionDataDTO.setExceededCalories(exceededCalories);
            return this;
        }

        public Builder isDailyNormExceeded(boolean isDailyNormExceeded) {
            consumptionDataDTO.setDailyNormExceeded(isDailyNormExceeded);
            return this;
        }

        public ConsumptionDataDTO build() {
            return consumptionDataDTO;
        }
    }

    public List<ConsumedFood> getConsumedFood() {
        return consumedFood;
    }

    public void setConsumedFood(List<ConsumedFood> consumedFood) {
        this.consumedFood = consumedFood;
    }

    public BigDecimal getDailyNorm() {
        return dailyNorm;
    }

    public void setDailyNorm(BigDecimal dailyNorm) {
        this.dailyNorm = dailyNorm;
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
}
