package org.training.food_tracker.dto;

import java.math.BigDecimal;

public class ConsumeStatsDTO {
    private BigDecimal caloriesConsumed;
    private BigDecimal exceededCalories;
    private boolean isDailyNormExceeded;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ConsumeStatsDTO consumeStatsDTO;

        Builder() {
            this.consumeStatsDTO = new ConsumeStatsDTO();
        }

        public Builder caloriesConsumed(BigDecimal caloriesConsumed) {
            consumeStatsDTO.setCaloriesConsumed(caloriesConsumed);
            return this;
        }

        public Builder exceededCalories(BigDecimal exceededCalories) {
            consumeStatsDTO.setExceededCalories(exceededCalories);
            return this;
        }

        public Builder isDailyNormExceeded(boolean isDailyNormExceeded) {
            consumeStatsDTO.setDailyNormExceeded(isDailyNormExceeded);
            return this;
        }

        public ConsumeStatsDTO build() {
            return consumeStatsDTO;
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
