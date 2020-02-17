package org.training.food.tracker.service.defaults;

import org.training.food.tracker.dao.BiometricsDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.jdbc.BiometricsDaoJDBC;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.BiometricsService;

import java.util.List;

public class BiometricsServiceDefault implements BiometricsService {
    private BiometricsDao biometricsDao = new BiometricsDaoJDBC();

    @Override public Biometrics create(Biometrics biometrics) throws DaoException {
        return biometricsDao.create(biometrics);
    }

    @Override public Biometrics findById(Long id) throws DaoException {
        return biometricsDao.findById(id);
    }

    @Override public Biometrics findByOwner(User user) throws DaoException {
        return biometricsDao.findByOwner(user);
    }

    @Override public List<Biometrics> findAll() throws DaoException {
        return biometricsDao.findAll();
    }

    @Override public Biometrics update(Biometrics biometrics) throws DaoException {
        return biometricsDao.update(biometrics);
    }

    @Override public void deleteById(Long id) throws DaoException {
        biometricsDao.deleteById(id);
    }
}
