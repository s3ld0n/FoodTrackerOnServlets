package org.training.food.tracker.service.defaults;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.UserDao;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.Sex;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.UserService;

import java.math.BigDecimal;
import java.util.List;

public class UserServiceDefault implements UserService {
    private UserDao userDao;

    public UserServiceDefault(UserDao userDao) {
        this.userDao = userDao;
    }

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

    /**
     * Total energy expenditure calculation using Harris–Benedict equation
     * @return daily norm of calories
     */
    public BigDecimal getDailyNormCalories(Biometrics biometrics) {
        if (biometrics.getSex() == Sex.FEMALE) {
            return (new BigDecimal(655.1)
                            .add(new BigDecimal(9.563).multiply(biometrics.getWeight()))
                            .add(new BigDecimal(1.850).multiply(biometrics.getHeight()))
                            .subtract(new BigDecimal(4.676).multiply(biometrics.getAge())))
                           .multiply(biometrics.getLifestyle().getCoefficient())
                           .setScale(2, BigDecimal.ROUND_HALF_DOWN);
        }
        return (new BigDecimal(66.5)
                        .add(new BigDecimal(13.75).multiply(biometrics.getWeight()))
                        .add(new BigDecimal(5.003).multiply(biometrics.getHeight()))
                        .subtract(new BigDecimal(6.755).multiply(biometrics.getAge())))
                       .multiply(biometrics.getLifestyle().getCoefficient())
                       .setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }
}
