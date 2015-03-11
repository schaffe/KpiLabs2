package ua.kpi.dzidzoiev.booking.controller;

/**
 * Created by midnight coder on 11-Mar-15.
 */
public class PathVariables {
    private String controllerName;
    private String item;
    private String pathInfo;

    private PathVariables(String path) {
        pathInfo = path;
        String[] pathParts = path.split("/");
        controllerName = pathParts[1];
        if (pathParts.length > 2) {
            item = pathParts[2];
        }
    }

    public String getControllerName() {
        return controllerName;
    }

    public String getItem() {
        return item;
    }

    public boolean hasItem() {
        return item != null;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public static PathVariables getInstance(String path) {
        return new PathVariables(path);
    }
}
