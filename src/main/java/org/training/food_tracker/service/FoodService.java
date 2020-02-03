package org.training.food_tracker.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.dao.FoodDao;
import org.training.food_tracker.dto.FoodDTO;
import org.training.food_tracker.model.Food;
import org.training.food_tracker.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FoodService {

    private FoodDao foodDao;
    private ConsumedFoodService consumedFoodService;

    private static final Logger log = LogManager.getLogger(FoodService.class.getName());

    public FoodService(FoodDao foodDao, ConsumedFoodService consumedFoodService) {
        this.foodDao = foodDao;
        this.consumedFoodService = consumedFoodService;
    }

    public void add(FoodDTO foodDTO, User owner) throws DaoException {
        log.debug("adding foodDTO: {}", foodDTO);
        Food food = Food.builder()
                        .name(foodDTO.getName())
                        .calories(foodDTO.getTotalCalories())
                        .owner(owner)
                        .build();
        foodDao.create(food);
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
        return foodDao.findAllCommonExcludingPersonalByUserId(userId);
    }

    public List<FoodDTO> findAllByOwnerInDTOs(User user) {
        List<FoodDTO> foodDTOS = new ArrayList<>();
        foodDao.findByOwnerOrderByIdDesc(user)
                .forEach(food -> foodDTOS.add(FoodDTO.builder()
                                                .name(food.getName())
                                                .totalCalories(food.getCalories())
                                                .build())
        );
        return foodDTOS;
    }

    public void removeByNameAndUserId(String foodName, User user) {
        foodDao.removeByNameAndOwner(foodName, user);
    }

    public List<FoodDTO> findAllCommonInDtos() {
        return foodDao.findAllCommon().stream().map(this::foodToFoodDTO).collect(Collectors.toList());
    }

    private FoodDTO foodToFoodDTO(Food food) {
        return FoodDTO.builder()
                       .name(food.getName())
                       .totalCalories(food.getCalories())
                       .build();
    }

}
