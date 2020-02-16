package org.training.food.tracker.dao;

import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.User;

public interface BiometricsDao extends CrudDao<Biometrics> {
    Biometrics findByOwner(User user) throws DaoException;
}
