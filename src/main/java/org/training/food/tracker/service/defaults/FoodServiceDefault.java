package org.training.food.tracker.service.defaults;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.jdbc.FoodDaoJDBC;
import org.training.food.tracker.dto.DTOconverter;
import org.training.food.tracker.model.User;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.service.FoodService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FoodServiceDefault implements FoodService {

    private FoodDaoJDBC foodDaoJDBC;
    private ConsumedFoodServiceDefault consumedFoodServiceDefault;

    private static final Logger log = LogManager.getLogger(FoodServiceDefault.class.getName());

    public FoodServiceDefault(FoodDaoJDBC foodDaoJDBC, ConsumedFoodServiceDefault consumedFoodServiceDefault) {
        this.foodDaoJDBC = foodDaoJDBC;
        this.consumedFoodServiceDefault = consumedFoodServiceDefault;
    }

    public void addForOwner(FoodDTO foodDTO, User owner) throws DaoException {
        log.debug("adding foodDTO: {}", foodDTO);
        Food food = Food.builder()
                        .name(foodDTO.getName())
                        .calories(foodDTO.getTotalCalories())
                        .owner(owner)
                        .build();
        foodDaoJDBC.create(food);
    }

    public void registerConsumption(FoodDTO foodDTO) {
        consumedFoodServiceDefault.registerConsumption(foodDTO);
    }

    public List<FoodDTO> findAllCommonExcludingPersonalByUserIdInDTO(Long userId) throws DaoException {
        return findAllCommonExcludingPersonalByUserId(userId).stream()
                       .map(DTOconverter::foodToFoodDTO)
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
        return foodDaoJDBC.findAllCommon().stream().map(DTOconverter::foodToFoodDTO).collect(Collectors.toList());
    }
}
