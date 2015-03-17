package test.ua.kpi.dzidzoiev.booking.controller.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.kpi.dzidzoiev.booking.controller.dao.*;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPool;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionProperties;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JpaCityDaoImplTest {
    EntityManagerFactory emf;
    EntityManager em;
    Dao<City> dao;

    @Before
    public void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("Booking");
        em = emf.createEntityManager();
        dao = (CityDao) DaoFactory.getInstance().getDao(DaoFactory.PROVIDER_JPA, DaoFactory.ENTITIY_CITY);
        ((JpaCityDaoImpl)dao).init(em);
    }

    @After
    public void tearDown() throws Exception {
        emf.close();
    }

    @Test
    public void testGet() throws Exception {
        City c = new City();
        c.setName("TestGetCity");
        dao.create(c);
        assertEquals(c, dao.get(c.getId()));
        dao.delete(c);
    }

    @Test
    public void testCreate() throws Exception {
        City c = new City();
        c.setName("TestCreateCity");
        dao.create(c);
        assertEquals(c, dao.get(c.getId()));
        dao.delete(c);
    }

    @Test
    public void testUpdate() throws Exception {
        City c = new City();
        c.setName("TestUpdateCity");
        dao.create(c);
        c.setName("11111111");
        dao.update(c);
        assertEquals(c, dao.get(c.getId()));
        dao.delete(c);
    }

    @Test
    public void testDelete() throws Exception {
        City c = new City();
        c.setName("TestGetCity");
        int id;
        dao.create(c);
        id = c.getId();
        dao.delete(c);
        assertNull(dao.get(id));
    }
}