package org.training.food.tracker.dto;

import java.math.BigDecimal;
import java.util.List;

public class ConsumptionDataDTO {
    private List<FoodDTO> consumedFoodDTOs;
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

        public Builder consumedFoodDTOs(List<FoodDTO> foodDTOs) {
            consumptionDataDTO.setConsumedFoodDTOs(foodDTOs);
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

    public List<FoodDTO> getConsumedFoodDTOs() {
        return consumedFoodDTOs;
    }

    public void setConsumedFoodDTOs(List<FoodDTO> consumedFoodDTOs) {
        this.consumedFoodDTOs = consumedFoodDTOs;
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
