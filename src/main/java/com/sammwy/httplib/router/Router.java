package com.sammwy.httplib.router;

import java.util.ArrayList;
import java.util.List;

import com.sammwy.httplib.HttpHandler;
import com.sammwy.httplib.HttpMethod;
import com.sammwy.httplib.Request;
import com.sammwy.httplib.Response;

public class Router implements HttpHandler {
    private String prefix;
    private List<Route> routes;

    public Router(String prefix) {
        this.prefix = prefix;
        this.routes = new ArrayList<>();
    }

    public Router() {
        this("/");
    }

    public Router addRoute(Route route) {
        if (route.getRouter() == null) {
            route.setRouter(this);
        }

        this.routes.add(route);
        return this;
    }

    public Router addRouter(Router router) {
        this.routes.addAll(router.getRoutes());
        return this;
    }

    public Router addRouters(List<Router> routers) {
        for (Router router : routers) {
            this.addRouter(router);
        }
        return this;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public List<Route> getRoutes() {
        return this.routes;
    }

    public Route matchRoute(HttpMethod method, String path) {
        for (Route route : routes) {
            if (route.isMatching(method, path)) {
                return route;
            }
        }

        return null;
    }

    public void handle(Request request, Response response) throws Exception {
        Route route = this.matchRoute(request.getMethod(), request.getPath());

        if (route != null) {
            route.handle(request, response);
        } else {
            response.setStatus(404);
            response.send("404 Not Found");
            response.flush();
        }
    }

    public Router all(String path, HttpHandler handler) {
        return this.addRoute(Route.ALL(path, handler));
    }

    public Router delete(String path, HttpHandler handler) {
        return this.addRoute(Route.DELETE(path, handler));
    }

    public Router get(String path, HttpHandler handler) {
        return this.addRoute(Route.GET(path, handler));
    }

    public Router head(String path, HttpHandler handler) {
        return this.addRoute(Route.HEAD(path, handler));
    }

    public Router patch(String path, HttpHandler handler) {
        return this.addRoute(Route.PATCH(path, handler));
    }

    public Router post(String path, HttpHandler handler) {
        return this.addRoute(Route.POST(path, handler));
    }

    public Router put(String path, HttpHandler handler) {
        return this.addRoute(Route.PUT(path, handler));
    }
}
