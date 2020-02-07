package org.training.food.tracker.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

public class AutoRollbacker implements AutoCloseable {

    private Connection connection;
    private boolean committed;

    public AutoRollbacker(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
        committed = true;
    }

    @Override
    public void close() throws SQLException {
        if(!committed) {
            connection.rollback();
        }
        connection.setAutoCommit(true);
    }

}