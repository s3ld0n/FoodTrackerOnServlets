package org.training.food.tracker.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {

    private static final Logger LOG = LoggerFactory.getLogger(DTOConverter.class.getName());

    public static UserDTO userToUserDTO(User user) {
        Biometrics biometrics = user.getBiometrics();
        return UserDTO.builder()
                       .username(user.getUsername())
                       .email(user.getEmail())
                       .firstName(user.getFirstName())
                       .lastName(user.getLastName())
                       .biometricsDTO(biometricsToBiometricsDTO(biometrics))
                       .role(user.getRole())
                       .password(user.getPassword())
                       .build();
    }

    public static BiometricsDTO biometricsToBiometricsDTO(Biometrics biometrics) {
        return BiometricsDTO.builder()
                       .age(biometrics.getAge())
                       .height(biometrics.getHeight())
                       .weight(biometrics.getWeight())
                       .lifestyle(biometrics.getLifestyle())
                       .sex(biometrics.getSex()).build();
    }

    public static FoodDTO foodToFoodDTO(Food food) {
        return FoodDTO.builder()
                       .name(food.getName())
                       .totalCalories(food.getCalories())
                       .build();
    }

    public static List<FoodDTO> foodsToFoodDTOs(List<Food> foods) {
        List<FoodDTO> foodDTOs = new ArrayList<>();
        foodDTOs.addAll(foods.stream().map(DTOConverter::foodToFoodDTO).collect(Collectors.toList()));
        LOG.debug("foodsToFoodDTOs() :: foodsDTOs: {}", foodDTOs);
        return foodDTOs;
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
