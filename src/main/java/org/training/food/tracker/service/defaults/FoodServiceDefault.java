package org.training.food.tracker.service.defaults;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.FoodDao;
import org.training.food.tracker.dao.jdbc.FoodDaoJDBC;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.model.ConsumedFood;
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

    public FoodServiceDefault() {
        this.foodDao = new FoodDaoJDBC();
        this.consumedFoodService = new ConsumedFoodServiceDefault();
    }

    public void create(Food food) throws DaoException {
        foodDao.create(food);
    }

    public void registerConsumption(ConsumedFood food) throws DaoException {
        consumedFoodService.registerConsumption(food);
    }

    public List<FoodDTO> findAllCommonExcludingPersonalByUserIdInDTO(Long userId) throws DaoException {
        return findAllCommonExcludingPersonalByUserId(userId).stream()
                       .map(DTOConverter::foodToFoodDTO)
                       .collect(Collectors.toList());
    }

    public List<Food> findAllCommonExcludingPersonalByUserId(Long userId) throws DaoException {
        return foodDao.findAllCommonExcludingPersonalByUserId(userId);
    }

    public List<Food> findAllCommon() throws DaoException {
        return foodDao.findAllCommon();
    }

    public List<Food> findAllByOwner(User user) throws DaoException {
        return foodDao.findAllByUserIdOrderByIdDesc(user.getId());
    }

    public void deleteByNameAndUserId(String foodName, User user) throws DaoException {
        foodDao.deleteByNameAndOwner(foodName, user);
    }

    public void deleteCommonFoodByName(String foodName) throws DaoException {
        foodDao.deleteCommonFoodByName(foodName);
    }
}
