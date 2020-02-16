package org.training.food.tracker.service.defaults;

import org.training.food.tracker.dao.BiometricsDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.jdbc.BiometricsDaoJDBC;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.BiometricsService;

public class BiometricsServiceDefault implements BiometricsService {
    private BiometricsDao biometricsDao = new BiometricsDaoJDBC();

    @Override public Biometrics create(Biometrics biometrics) throws DaoException {
        return biometricsDao.create(biometrics);
    }

    @Override public Biometrics findByOwner(User user) throws DaoException {
        return biometricsDao.findByOwner(user);
    }

    @Override public Biometrics update(Biometrics biometrics) throws DaoException {
        return biometricsDao.update(biometrics);
    }

}
