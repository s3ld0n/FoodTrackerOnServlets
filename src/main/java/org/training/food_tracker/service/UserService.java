package org.training.food_tracker.service;

import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.dao.UserDao;
import org.training.food_tracker.dto.UserDTO;
import org.training.food_tracker.model.Biometrics;
import org.training.food_tracker.model.User;

import java.util.List;

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

    public List<User> findAll() throws DaoException {
        return userDao.findAll();
    }

    public UserDTO userToUserDTO(User user) {
        Biometrics biometrics = user.getBiometrics();
        return UserDTO.builder()
                       .id(user.getId())
                       .username(user.getUsername())
                       .fullName(user.getFullName())
                       .nationalName(user.getNationalName())
                       .email(user.getEmail())
                       .age(biometrics.getAge())
                       .sex(biometrics.getSex())
                       .weight(biometrics.getWeight())
                       .height(biometrics.getHeight())
                       .lifestyle(biometrics.getLifestyle())
                       .dailyNorm(biometrics.getDailyNorm())
                       .role(user.getRole())
                       .password(user.getPassword())
                       .build();
    }

}
