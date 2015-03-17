package ua.kpi.dzidzoiev.booking.controller.dao;

import org.hibernate.Session;
import ua.kpi.dzidzoiev.booking.model.City;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by midnight coder on 17-Mar-15.
 */
public class JpaCityDaoImpl implements CityDao {

    EntityManager entityManager;

    public void init(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public City get(Integer id) {
        return entityManager.find(City.class, id);
    }

    @Override
    public List<City> getAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createCriteria(City.class).list();
    }

    @Override
    public void create(City city) {
        entityManager.getTransaction().begin();
        entityManager.persist(city );
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(City city) {
        entityManager.getTransaction().begin();
        entityManager.persist(city);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(City city) {
        entityManager.getTransaction().begin();
        entityManager.remove(city);
        entityManager.getTransaction().commit();
    }
}
