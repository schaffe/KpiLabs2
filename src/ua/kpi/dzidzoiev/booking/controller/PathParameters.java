package ua.kpi.dzidzoiev.booking.controller;

/**
 * Created by midnight coder on 11-Mar-15.
 */
public class PathParameters {
    private String controllerName;
    private String parameter;
    private String pathInfo;

    private PathParameters(String path) {
        pathInfo = path;
        String[] pathParts = path.split("/");
        controllerName = pathParts[1];
        parameter = pathParts[2];
    }

    public String getControllerName() {
        return controllerName;
    }

    public String getParameter() {
        return parameter;
    }

    public boolean hasParameters() {
        return parameter != null;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public static PathParameters getInstance(String path) {
        return new PathParameters(path);
    }
}
