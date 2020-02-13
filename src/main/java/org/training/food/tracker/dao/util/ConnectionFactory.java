package org.training.food.tracker.dao.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {


    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class.getName());
    private static final DataSource dataSourceInstance = DataSource.INSTANCE;

    public static Connection getConnection() throws DaoException {
        Connection connection = null;

        LOG.debug("getConnection() :: creating a new connection");

        try {
            LOG.debug("getConnection() :: getting datasource");
            BasicDataSource dataSource = dataSourceInstance.getDataSource();
            connection = dataSource.getConnection();

        } catch (SQLException e) {
            LOG.error("Connection has not been created." , e);
            throw new DaoException("Connection has not been created." , e);
        }

        LOG.debug("getConnection() :: connection was successfully created.");

        return connection;
    }

}