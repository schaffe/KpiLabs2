package ua.kpi.dzidzoiev.booking.controller.db;

/**
 * Created by dzidzoiev on 3/14/15.
 */
public class ConnectionPoolFactory {
    private ConnectionPoolFactory(){}

    public static final int MY_SQL = 300001;

    public ConnectionPool getConnectionPool(int dbms) {
        switch (dbms) {
            case MY_SQL:
                return new MySqlConnectionPool();
            default:
                throw new IllegalStateException("No such code.");
        }
    }

    public static ConnectionPoolFactory getInstnce() {
        return SHolder.INSTANCE;
    }

    private static class SHolder {
        static ConnectionPoolFactory INSTANCE = new ConnectionPoolFactory();
    }

}
