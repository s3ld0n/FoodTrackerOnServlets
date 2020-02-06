package org.training.food_tracker.dao.util;

import org.training.food_tracker.dao.DaoFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory extends DaoFactory {
    private static DataSource dataSource = ConnectionPoolHolder.getDataSource();

    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
