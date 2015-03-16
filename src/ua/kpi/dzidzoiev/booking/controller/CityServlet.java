package ua.kpi.dzidzoiev.booking.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.kpi.dzidzoiev.booking.controller.dao.CityDao;
import ua.kpi.dzidzoiev.booking.controller.dao.MySqlCityDaoImpl;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPool;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionPoolFactory;
import ua.kpi.dzidzoiev.booking.controller.db.ConnectionProperties;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

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
    ConnectionPool pool;

    @Override
    public void init() throws ServletException {
        super.init();
        Properties properties = new Properties();
        try {
            properties.load(getServletContext().getResourceAsStream("/WEB-INF/connection.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool = ConnectionPoolFactory
                .getInstnce()
                .getConnectionPool(ConnectionPoolFactory.MY_SQL)
                .init(new ConnectionProperties(properties), 1);
        dao = new MySqlCityDaoImpl(pool);
    }

    @Override
    public void destroy() {
        pool.closeConnections();
        super.destroy();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("CityServlet - delete");
        PathVariables var = PathVariables.getInstance(request.getRequestURI());
        if (var.hasItem()) {
            Integer id = Integer.parseInt(var.getItem());
            City c = new City(id);
            dao.delete(c);
            if (c.getId() == null) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
        dao.update(c);
        response.setStatus(HttpServletResponse.SC_OK);
//        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
        dao.create(city);

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
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("CityServlet - get");
        PathVariables parameters = PathVariables.getInstance(request.getRequestURI());
        String jsonObject;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if (parameters.hasItem()) {
            int cityId = Integer.valueOf(parameters.getItem());
            jsonObject = gson.toJson(dao.get(cityId));
        } else {
            jsonObject = gson.toJson(dao.getAll());
        }

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
