package org.training.food.tracker.service;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    User create(User user) throws DaoException;

    User findById(Long id) throws DaoException;

    List<User> findAll() throws DaoException;

    BigDecimal getDailyNormCalories(Biometrics biometrics);
}
