package org.training.food.tracker.dao;

import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;

import java.time.LocalDate;
import java.util.List;

public interface DayDao extends CrudDao<Day> {
    Day findByUserAndDate(User user, LocalDate date) throws DaoException;
    List<Day> findAllByUserOrderByDateDesc(User user) throws DaoException;
}
