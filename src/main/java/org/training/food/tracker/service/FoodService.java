package org.training.food.tracker.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.impl.FoodDaoJDBC;
import org.training.food.tracker.model.User;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FoodService {

    private FoodDaoJDBC foodDaoJDBC;
    private ConsumedFoodService consumedFoodService;

    private static final Logger log = LogManager.getLogger(FoodService.class.getName());

    public FoodService(FoodDaoJDBC foodDaoJDBC, ConsumedFoodService consumedFoodService) {
        this.foodDaoJDBC = foodDaoJDBC;
        this.consumedFoodService = consumedFoodService;
    }

    public void add(FoodDTO foodDTO, User owner) throws DaoException {
        log.debug("adding foodDTO: {}", foodDTO);
        Food food = Food.builder()
                        .name(foodDTO.getName())
                        .calories(foodDTO.getTotalCalories())
                        .owner(owner)
                        .build();
        foodDaoJDBC.create(food);
    }

    public void registerConsumption(FoodDTO foodDTO) {
        consumedFoodService.registerConsumption(foodDTO);
    }

    public List<FoodDTO> findAllCommonExcludingPersonalByUserIdInDTO(Long userId) throws DaoException {
        return findAllCommonExcludingPersonalByUserId(userId).stream()
                       .map(this::foodToFoodDTO)
                       .collect(Collectors.toList());
    }

    private List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException {
        return foodDaoJDBC.findAllCommonExcludingPersonalByUserId(userId);
    }

    public List<FoodDTO> findAllByOwnerInDTOs(User user) throws DaoException {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        foodDaoJDBC.findAllByUserIdOrderByIdDesc(user.getId())
                .forEach(food -> foodDTOS.add(FoodDTO.builder()
                                                .name(food.getName())
                                                .totalCalories(food.getCalories())
                                                .build())
        );
        return foodDTOS;
    }

    public void removeByNameAndUserId(String foodName, User user) {
        foodDaoJDBC.removeByNameAndOwner(foodName, user);
    }

    public List<FoodDTO> findAllCommonInDtos() {
        return foodDaoJDBC.findAllCommon().stream().map(this::foodToFoodDTO).collect(Collectors.toList());
    }

    private FoodDTO foodToFoodDTO(Food food) {
        return FoodDTO.builder()
                       .name(food.getName())
                       .totalCalories(food.getCalories())
                       .build();
    }

}
