package org.training.food.tracker.service;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.model.User;

import java.util.List;

public interface FoodService {

    void addForOwner(FoodDTO foodDTO, User owner) throws DaoException;

    void registerConsumption(FoodDTO foodDTO);

    List<FoodDTO> findAllCommonExcludingPersonalByUserIdInDTO(Long userId) throws DaoException;

    void removeByNameAndUserId(String foodName, User user);

    List<FoodDTO> findAllCommonInDtos();
}
