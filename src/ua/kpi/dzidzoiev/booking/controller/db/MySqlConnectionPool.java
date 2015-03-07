package ua.kpi.dzidzoiev.booking.controller.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dzidzoiev on 2/21/15.
 */
public class MySqlConnectionPool implements ConnectionPool {
    private String user;
    private String password;
    private String dbName;

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
    public ConnectionPool init(String props) {
        Properties properties = new Properties();
//        try {
//            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(props));
//            this.user = properties.getProperty("user");
//            this.password = properties.getProperty("password");
//            this.dbName = properties.getProperty("dbname");
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new IllegalStateException("Properties load failed.", e);
//        }
        this.user = "booking_user";
        this.password = "password";
        this.dbName = "booking_service";
        return this;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/"+this.dbName +"?" +
                    "user="+ this.user +"&password="+this.password);
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
