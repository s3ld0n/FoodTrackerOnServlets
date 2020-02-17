package org.training.food.tracker.service;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.User;

import java.util.List;

public interface BiometricsService {
    Biometrics create(Biometrics biometrics) throws DaoException;

    Biometrics findById(Long id) throws DaoException;

    Biometrics findByOwner(User user) throws DaoException;

    List<Biometrics> findAll() throws DaoException;

    Biometrics update(Biometrics biometrics) throws DaoException;

    void deleteById(Long id) throws DaoException;
}
