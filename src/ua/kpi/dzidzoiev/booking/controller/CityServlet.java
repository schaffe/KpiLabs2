package ua.kpi.dzidzoiev.booking.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.kpi.dzidzoiev.booking.controller.dao.CityDao;
import ua.kpi.dzidzoiev.booking.controller.dao.DaoFactory;
import ua.kpi.dzidzoiev.booking.controller.dao.JpaCityDaoImpl;
import ua.kpi.dzidzoiev.booking.controller.dao.MySqlCityDaoImpl;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPool;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPoolFactory;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionProperties;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Created by dzidzoiev on 2/27/15.
 */
public class CityServlet extends javax.servlet.http.HttpServlet {
    CityDao dao;
    EntityManagerFactory emf;
    EntityManager em;

    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory("Booking");
        em = emf.createEntityManager();
        dao = (CityDao) DaoFactory.getInstance().getDao(DaoFactory.PROVIDER_JPA, DaoFactory.ENTITIY_CITY);
        ((JpaCityDaoImpl) dao).init(em);
    }

    @Override
    public void destroy() {
        em.close();
        super.destroy();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("CityServlet - delete");
        PathVariables var = PathVariables.getInstance(request.getRequestURI());
        if (var.hasItem()) {
            Integer id = Integer.parseInt(var.getItem());
            try {
                EntityTransaction tx = em.getTransaction();
                if (!tx.isActive()) {
                    tx.begin();
                }
                City c = dao.get(id);
                dao.delete(c);

                tx.commit();
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (PersistenceException | IllegalStateException e) {
                e.printStackTrace();
                em.getTransaction().rollback();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();

        String s = reader.readLine();
        String[] elem = s.split("&");
        String name = elem[0].split("=")[1];
        Integer pop = Integer.parseInt(elem[1].split("=")[1]);
        Integer id = null;
        PathVariables var = PathVariables.getInstance(request.getRequestURI());
        if (var.hasItem()) {
            id = Integer.parseInt(var.getItem());
        }

        City c = new City(id, name, pop);
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            dao.update(c);
            em.getTransaction().commit();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (PersistenceException | IllegalStateException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String cityName = request.getParameter("name");
        String populationStr = request.getParameter("population");
        Integer population = null;
        if (cityName == null || cityName.trim().equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (populationStr != null)
            population = Integer.parseInt(populationStr);
        City city = new City(null, cityName.trim(), population);
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            dao.create(city);
            em.getTransaction().commit();

            if (city.getId() != null) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(city));
                out.flush();
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (PersistenceException | IllegalStateException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("CityServlet - get");
        PathVariables parameters = PathVariables.getInstance(request.getRequestURI());
        String jsonObject = null;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            if (parameters.hasItem()) {
                int cityId = Integer.valueOf(parameters.getItem());
                jsonObject = gson.toJson(dao.get(cityId));
            } else {
                jsonObject = gson.toJson(dao.getAll());
            }
        } catch (PersistenceException | IllegalStateException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        em.getTransaction().commit();

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
    }

//    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        System.out.println("CityServlet - get");
//        String pathInfo = request.getRequestURI();
//        System.out.println("path: " + pathInfo);
//        String[] pathParts = pathInfo.split("/");
//        List<City> cityList;
//        if (pathParts.length <= 2) {
//            //display all
//            cityList = dao.getAll();
//        } else {
//            //display one
//            int cityId = Integer.valueOf(pathParts[2]);
//            cityList = new ArrayList<>();
//            cityList.add(dao.get(cityId));
//        }
//        request.setAttribute("cityList", cityList);
//        request.getRequestDispatcher("/WEB-INF/city.jsp").forward(request, response);
//    }
}
