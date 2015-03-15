package test.ua.kpi.dzidzoiev.booking.controller.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.kpi.dzidzoiev.booking.controller.dao.Dao;
import ua.kpi.dzidzoiev.booking.controller.dao.MySqlCityDaoImpl;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPool;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionProperties;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

import static org.junit.Assert.*;

public class MySqlCityDaoImplTest {
    Dao<City> dao;
    ConnectionPool pool;

    @Before
    public void setUp() throws Exception {
        pool = new MySqlConnectionPool();
        pool.init(new ConnectionProperties("localhost", 3306, "booking_service",
                "booking_user", "password"), 3);
        dao = new MySqlCityDaoImpl(pool);
    }

    @After
    public void tearDown() throws Exception {
        pool.closeConnections();
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