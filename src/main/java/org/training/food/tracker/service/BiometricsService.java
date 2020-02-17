package org.training.food.tracker.service;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.User;

public interface BiometricsService {
    Biometrics create(Biometrics biometrics) throws DaoException;

    Biometrics findByOwner(User user) throws DaoException;

    Biometrics update(Biometrics biometrics) throws DaoException;
}
