package org.training.food_tracker.dao.impl;

import org.training.food_tracker.dao.ConnectionPoolHolder;
import org.training.food_tracker.dao.DaoFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
