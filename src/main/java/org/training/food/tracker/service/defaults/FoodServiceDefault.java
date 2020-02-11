package org.training.food.tracker.service.defaults;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.FoodDao;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;
import org.training.food.tracker.model.builder.FoodBuilder;
import org.training.food.tracker.service.ConsumedFoodService;
import org.training.food.tracker.service.FoodService;

import java.util.List;
import java.util.stream.Collectors;

public class FoodServiceDefault implements FoodService {

    private FoodDao foodDao;
    private ConsumedFoodService consumedFoodService;

    private static final Logger LOG = LoggerFactory.getLogger(FoodServiceDefault.class.getName());

    public FoodServiceDefault(FoodDao foodDao, ConsumedFoodServiceDefault consumedFoodService) {
        this.foodDao = foodDao;
        this.consumedFoodService = consumedFoodService;
    }

    public void addForOwner(FoodDTO foodDTO, User owner) throws DaoException {
        LOG.debug("adding foodDTO: {}", foodDTO);
        Food food = FoodBuilder.instance()
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
                       .map(DTOConverter::foodToFoodDTO)
                       .collect(Collectors.toList());
    }

    public List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException {
        return foodDao.findAllCommonExcludingPersonalByUserId(userId);
    }

    public List<Food> findAllByOwner(User user) throws DaoException {
        return foodDao.findAllByUserIdOrderByIdDesc(user.getId());
    }

    public void removeByNameAndUserId(String foodName, User user) {
        foodDao.removeByNameAndOwner(foodName, user);
    }
}
