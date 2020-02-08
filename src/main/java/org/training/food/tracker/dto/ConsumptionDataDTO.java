package org.training.food.tracker.dto;

import java.math.BigDecimal;

public class ConsumptionDataDTO {
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
