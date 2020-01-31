package org.training.food_tracker.service;

import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.dao.UserDao;
import org.training.food_tracker.model.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public User create(User user) throws DaoException {
        return userDao.create(user);
    }

    public User findById(Long id) throws DaoException {
        return userDao.findById(id);
    }

    public User findByUsername(String username) throws DaoException {
        return userDao.findByUsername(username);
    }
}
