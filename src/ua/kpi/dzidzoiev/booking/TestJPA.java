package ua.kpi.dzidzoiev.booking;

import ua.kpi.dzidzoiev.booking.model.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by midnight coder on 17-Mar-15.
 */
public class TestJPA {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Booking");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            City city = em.find(City.class, 3);

            System.out.println(city);

            em.getTransaction().commit();
        }
        catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        finally{
            emf.close();
        }
    }
}
