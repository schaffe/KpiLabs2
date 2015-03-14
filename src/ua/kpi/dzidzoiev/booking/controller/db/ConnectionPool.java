package ua.kpi.dzidzoiev.booking.controller.db;

import java.sql.Connection;

/**
 * Created by dzidzoiev on 2/21/15.
 */
public interface ConnectionPool {
    ConnectionPool init(ConnectionProperties properties);
    Connection getConnection();
    void releaseConnection(Connection connection);
    void closeConnections();
}
