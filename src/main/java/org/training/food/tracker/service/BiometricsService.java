package org.training.food.tracker.service;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.Biometrics;

public interface BiometricsService {
    Biometrics create(Biometrics biometrics) throws DaoException;
}
