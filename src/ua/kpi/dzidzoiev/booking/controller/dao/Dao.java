package ua.kpi.dzidzoiev.booking.controller.dao;

/**
 * Created by dzidzoiev on 2/22/15.
 */
public interface Dao<T> {
    T get(Integer id);
    void update(T t);
    void create(T t);
    void delete(T t);
}
