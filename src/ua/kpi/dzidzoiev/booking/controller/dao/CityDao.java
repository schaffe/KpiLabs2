package ua.kpi.dzidzoiev.booking.controller.dao;

import ua.kpi.dzidzoiev.booking.model.City;

import java.util.List;

/**
 * Created by dzidzoiev on 2/21/15.
 */
public interface CityDao extends Dao<City> {
    City get(Integer id);
    List<City> getAll();
    void create(City city);
    void update(City city);
    void delete(City city);
}
