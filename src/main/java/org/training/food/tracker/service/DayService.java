package org.training.food.tracker.service;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface DayService {
    Day getCurrentDayOfUser(User user) throws DaoException;

    List<Day> getAllDaysByUser(User user) throws DaoException;

    BigDecimal getTotalCaloriesOfDay(Day day);
}
