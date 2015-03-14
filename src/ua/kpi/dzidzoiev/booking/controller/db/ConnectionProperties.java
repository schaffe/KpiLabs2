package ua.kpi.dzidzoiev.booking.controller.db;

/**
 * Created by dzidzoiev on 3/14/15.
 */
public class ConnectionProperties {
    private String host;
    private int port;
    private String dbName;
    private String user;
    private String password;

    public ConnectionProperties(String host, int port, String dbName, String user, String password) {
        this.setHost(host);
        this.setPort(port);
        this.setDbName(dbName);
        this.setUser(user);
        this.setPassword(password);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectionString() {
        return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", host, port, dbName, user, password);
    }
}
