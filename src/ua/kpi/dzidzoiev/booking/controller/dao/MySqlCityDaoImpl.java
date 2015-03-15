package ua.kpi.dzidzoiev.booking.controller.dao;

import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPool;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dzidzoiev on 2/21/15.
 */
public class MySqlCityDaoImpl implements CityDao {
    public static final String TABLE = "Cities";

    ConnectionPool pool;

    public MySqlCityDaoImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public City get(Integer id) {
        Connection conn = pool.getConnection();
        String SQL = "SELECT * FROM " + TABLE + " WHERE id = ?;";
        City c;
        try (PreparedStatement s = conn.prepareStatement(SQL)) {
            s.setInt(1, id);
            try (ResultSet resultSet = s.executeQuery()) {
                if (resultSet.next()) {
                    c = new City();
                    c.setId(resultSet.getInt("id"));
                    c.setName(resultSet.getString("name"));
                    c.setPopulation(resultSet.getInt("population"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        } finally {
            pool.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public List<City> getAll() {
        Connection conn = pool.getConnection();
        String SQL = "SELECT * FROM " + TABLE;
        List<City> cities = new ArrayList<>();
        try (Statement s = conn.createStatement()) {
            s.execute(SQL);
            try (ResultSet resultSet = s.getResultSet()) {
                City c;
                while (resultSet.next()) {
                    c = new City();
                    c.setId(resultSet.getInt("id"));
                    c.setName(resultSet.getString("name"));
                    c.setPopulation(resultSet.getInt("population"));
                    cities.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        } finally {
            pool.releaseConnection(conn);
        }
        return cities;
    }

    @Override
    public void create(City city) {
        Connection conn = pool.getConnection();
        String SQL = "INSERT INTO " + TABLE + " (name, population) VALUES (?, ?);";
        try (PreparedStatement s = conn.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            s.setString(1, city.getName());
            if (city.getPopulation() == null) {
                s.setNull(2, Types.NULL);
            } else {
                s.setInt(2, city.getPopulation());
            }
            int affectedRows = s.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = s.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    city.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void update(City city) {
        Connection conn = pool.getConnection();
        String SQL = "UPDATE " + TABLE + " SET name = ?, population = ? WHERE id = ?;";
        try (PreparedStatement s = conn.prepareStatement(SQL)) {
            s.setString(1, city.getName());
            if (city.getPopulation() == null) {
                s.setNull(2, Types.NULL);
            } else {
                s.setInt(2, city.getPopulation());
            }
            s.setInt(3, city.getId());
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(City city) {
        Connection conn = pool.getConnection();
        String SQL = "DELETE FROM " + TABLE + " WHERE id = ?;";
        try (PreparedStatement s = conn.prepareStatement(SQL)) {
            s.setInt(1, city.getId());
            s.execute();
            city.setId(null);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        } finally {
            pool.releaseConnection(conn);
        }
    }
}
