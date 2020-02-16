package org.training.food.tracker.dao;

import java.util.List;

public interface CrudDao<T> {
    
    T create(T t) throws DaoException;
    
    T findById(Long id) throws DaoException;
    
    T update(T t) throws DaoException;
    
    List<T> findAll() throws DaoException;
    
    void deleteById(Long id) throws DaoException;
}
