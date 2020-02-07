package org.training.food.tracker.dao.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.training.food.tracker.dao.Const;

import java.util.ResourceBundle;

public enum DataSource {
    INSTANCE;

    private BasicDataSource dataSource;

    DataSource() {
        dataSource = new BasicDataSource();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        dataSource.setDriverClassName(resourceBundle.getString(Const.DATASOURCE_DRIVER_CLASS_NAME));
        dataSource.setUrl(resourceBundle.getString(Const.DATASOURCE_URL));
        dataSource.setUsername(resourceBundle.getString(Const.DATASOURCE_USERNAME));
        dataSource.setPassword(resourceBundle.getString(Const.DATASOURCE_PASSWORD));
        dataSource.setMinIdle(Integer.parseInt(resourceBundle.getString(Const.DATASOURCE_MIN_IDLE)));
        dataSource.setMaxIdle(Integer.parseInt(resourceBundle.getString(Const.DATASOURCE_MAX_IDLE)));
        dataSource.setMaxActive(Integer.parseInt(resourceBundle.getString(Const.DATASOURCE_MAX_ACTIVE)));
        dataSource.setMaxWait(Integer.parseInt(resourceBundle.getString(Const.DATASOURCE_MAX_WAIT)));
        dataSource.setMaxOpenPreparedStatements(
                Integer.parseInt(resourceBundle.getString(Const.DATASOURCE_MAX_OPEN_PREPARED_STATEMENTS))
        );
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }
}
