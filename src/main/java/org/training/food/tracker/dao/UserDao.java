package org.training.food.tracker.dao;

import org.training.food.tracker.model.User;

public interface UserDao extends CrudDao<User> {
    User findByUsername(String username) throws DaoException;
}
