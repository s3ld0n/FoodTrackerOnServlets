package org.training.food_tracker.service;

import org.training.food_tracker.dao.UserDao;
import org.training.food_tracker.model.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public User create(User user) {
        return userDao.create(user);
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }
}
