package ua.kpi.dzidzoiev.booking.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ua.kpi.dzidzoiev.booking.controller.dao.CityDao;
import ua.kpi.dzidzoiev.booking.controller.dao.MySqlCityDaoImpl;
import ua.kpi.dzidzoiev.booking.controller.db.MySqlConnectionPool;
import ua.kpi.dzidzoiev.booking.model.City;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dzidzoiev on 2/27/15.
 */
public class CityServlet extends javax.servlet.http.HttpServlet {
    CityDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MySqlCityDaoImpl(new MySqlConnectionPool().init("connection.properties"));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("CityServlet - get");
        String pathInfo = request.getRequestURI();
        System.out.println("path: " + pathInfo);
        String[] pathParts = pathInfo.split("/");
        String jsonObject;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if (pathParts.length <= 2) {
            //display all
            jsonObject = gson.toJson(dao.getAll());
        } else {
            //display one
            int cityId = Integer.valueOf(pathParts[2]);
            jsonObject = gson.toJson(dao.get(cityId));
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
