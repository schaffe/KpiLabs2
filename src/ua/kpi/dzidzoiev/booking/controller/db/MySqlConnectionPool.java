package ua.kpi.dzidzoiev.booking.controller.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dzidzoiev on 2/21/15.
 */
public class MySqlConnectionPool implements ConnectionPool {
    private ConnectionProperties properties;
    private AtomicInteger number;
    private Semaphore semaphore;
    private Deque<Connection> available;
    private Set<Connection> busy;
    private Lock lock;


    public MySqlConnectionPool() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error instantiating Driver class!", e);
        }
    }

    @Override
    public ConnectionPool init(ConnectionProperties properties, int number) {
        this.number = new AtomicInteger(number);
        this.properties = properties;
        this.semaphore = new Semaphore(number);
        available = new LinkedList<>();
        busy = new HashSet<>();
        lock = new ReentrantLock();

        try {
            for (int i = 0; i < number; i++) {
                available.push(DriverManager.getConnection(properties.getConnectionString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return this;
    }

    public Connection getConnection() {
        Connection c = null;
        try {
            semaphore.acquire();
            lock.lock();
            c = available.pop();
            busy.add(c);
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return c;
    }

    public void releaseConnection(Connection c) {
        lock.lock();
        busy.remove(c);
        semaphore.release();
        available.push(c);
        lock.unlock();
    }

    @Override
    public void closeConnections() {

    }

    @Override
    public int available() {
        return 0;
    }

    @Override
    public int busy() {
        return 0;
    }

}
