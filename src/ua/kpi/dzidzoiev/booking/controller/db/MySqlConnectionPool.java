package ua.kpi.dzidzoiev.booking.controller.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dzidzoiev on 2/21/15.
 */
public class MySqlConnectionPool implements ConnectionPool {
    private ConnectionProperties properties;
    private Semaphore semaphore;
    private Deque<Connection> available;
    private Set<Connection> busy;
    private Lock lock;
    private int availableNum;
    private int busyNum;
    private int totalNum;
    private boolean init;

    public MySqlConnectionPool() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error instantiating Driver class!", e);
        }
        available = new LinkedList<>();
        busy = new HashSet<>();
        lock = new ReentrantLock();
    }

    @Override
    public ConnectionPool init(ConnectionProperties properties, int number) {
        if (!init) {
            if (number < 1) {
               throw new IllegalArgumentException("number < 1");
            }

            lock.lock();
            this.totalNum = number;
            this.properties = properties;
            this.semaphore = new Semaphore(number);

            try {
                for (int i = 0; i < number; i++) {
                    available.push(DriverManager.getConnection(properties.getConnectionString()));
                }
                init = true;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            } finally {
                lock.unlock();
            }
        }
        return this;
    }

    private void allocateConnection() throws SQLException {
        available.push(DriverManager.getConnection(properties.getConnectionString()));
        semaphore.release();
        totalNum++;
        availableNum++;
    }

    public Connection getConnection() {
        Connection c = null;
        try {
            lock.lock();
            if (!semaphore.tryAcquire()) {
                allocateConnection();
            }
            c = available.pop();
            availableNum--;
            busy.add(c);
            busyNum++;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        } finally {
            lock.unlock();
        }
        return c;
    }

    public void releaseConnection(Connection c) {
        lock.lock();
        busy.remove(c);
        busyNum--;
        available.push(c);
        availableNum++;
        semaphore.release();
        lock.unlock();
    }

    @Override
    public void closeConnections() {
        lock.lock();
        try {
            for (Connection connection : available) {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
            for (Connection connection : busy) {
                if (!connection.isClosed()) {
                    connection.rollback();
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int available() {
        return availableNum;
    }

    @Override
    public int busy() {
        return busyNum;
    }

}
