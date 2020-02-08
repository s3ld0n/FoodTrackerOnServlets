package org.training.food.tracker.dto;

import org.training.food.tracker.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DTOconverter {

    public static UserDTO userToUserDTO(User user) {
        Biometrics biometrics = user.getBiometrics();
        return UserDTO.builder()
                       .id(user.getId())
                       .username(user.getUsername())
                       .email(user.getEmail())
                       .firstName(user.getFirstName())
                       .lastName(user.getLastName())
                       .age(biometrics.getAge())
                       .sex(biometrics.getSex())
                       .weight(biometrics.getWeight())
                       .height(biometrics.getHeight())
                       .lifestyle(biometrics.getLifestyle())
                       .dailyNorm(user.getDailyNormCalories())
                       .role(user.getRole())
                       .password(user.getPassword())
                       .build();
    }

    public static FoodDTO foodToFoodDTO(Food food) {
        return FoodDTO.builder()
                       .name(food.getName())
                       .totalCalories(food.getCalories())
                       .build();
    }

    public static List<FoodDTO> foodsToFoodDTOs(List<Food> foods) {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        foods.forEach(DTOconverter::foodToFoodDTO);
        return foodDTOS;
    }

    public static ConsumptionDataDTO buildConsumptionDataDTO(List<ConsumedFood> consumedFoods, User user) {
        BigDecimal totalCalories = sumTotalCalories(consumedFoods);
        BigDecimal dailyNorm = user.getDailyNormCalories();
        BigDecimal exceededCalories = findExceededCalories(totalCalories, dailyNorm);

        return ConsumptionDataDTO.builder()
                       .consumedFoods(consumedFoods)
                       .caloriesConsumed(totalCalories)
                       .exceededCalories(exceededCalories)
                       .isDailyNormExceeded(checkIfDailyNormExceeded(exceededCalories))
                       .build();
    }

    private static boolean checkIfDailyNormExceeded(BigDecimal exceededCalories) {
        return exceededCalories.compareTo(new BigDecimal(0)) > 0;
    }

    private static BigDecimal findExceededCalories(BigDecimal totalCalories, BigDecimal dailyNorm) {
        return dailyNorm.compareTo(totalCalories) > 0 ? new BigDecimal(0) : totalCalories.subtract(dailyNorm);
    }

    private static BigDecimal sumTotalCalories(List<ConsumedFood> foodDTOs) {
        BigDecimal sum = new BigDecimal(0);

        for (ConsumedFood food : foodDTOs) {
            sum = sum.add(food.getTotalCalories());
        }
        return sum;
    }
}
