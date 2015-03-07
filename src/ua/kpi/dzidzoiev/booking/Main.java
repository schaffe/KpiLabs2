package ua.kpi.dzidzoiev.booking;

import ua.kpi.dzidzoiev.booking.controller.dao.CityDao;
import ua.kpi.dzidzoiev.booking.controller.dao.MySqlCityDaoImpl;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPool;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        CityDao dao = new MySqlCityDaoImpl(new MySqlConnectionPool().init("properties/connection.properties"));
        System.out.println(Arrays.asList(dao.getAll().toArray()));
        City test = new City(null, "Lol", null);
        dao.create(test);
        System.out.println(dao.get(test.getId()));
    }
}