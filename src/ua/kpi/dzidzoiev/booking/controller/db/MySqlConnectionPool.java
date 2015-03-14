package ua.kpi.dzidzoiev.booking.controller.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dzidzoiev on 2/21/15.
 */
public class MySqlConnectionPool implements ConnectionPool {
    private ConnectionProperties properties;

    public MySqlConnectionPool() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error instantiating Driver class!",e);
        }
    }

    @Override
    public ConnectionPool init(ConnectionProperties properties) {
        this.properties = properties;
        return this;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getConnectionString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void releaseConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnections() {
    }

}
