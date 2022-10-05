package com.sammwy.httplib.router;

import com.sammwy.httplib.HttpHandler;
import com.sammwy.httplib.HttpMethod;
import com.sammwy.httplib.Request;
import com.sammwy.httplib.Response;
import com.sammwy.httplib.utils.PathUtils;

public class Route implements HttpHandler {
    private HttpMethod method;
    private String path;
    private Router router;
    private HttpHandler handler;

    public Route(HttpMethod method, String path, Router router, HttpHandler handler) {
        this.method = method;
        this.path = path;
        this.router = router;
        this.handler = handler;
    }

    public Route(HttpMethod method, String path, HttpHandler handler) {
        this(method, path, null, handler);
    }

    public Route(HttpMethod method, String path) {
        this(method, path, null, null);
    }

    public HttpHandler getHandler() {
        return this.handler;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        if (this.router == null) {
            return this.path;
        } else {
            return PathUtils.join(this.router.getPrefix(), this.path);
        }
    }

    public Router getRouter() {
        return this.router;
    }

    public boolean isMatching(HttpMethod method, String path) {
        if (this.getMethod() != HttpMethod.ALL) {
            if (method != this.getMethod()) {
                return false;
            }
        }

        return PathUtils.isMatching(path, this.getPath());
    }

    public Route setHandler(HttpHandler handler) {
        this.handler = handler;
        return this;
    }

    public Route setRouter(Router router) {
        this.router = router;
        return this;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        request.setRoutering(this);
        this.handler.handle(request, response);
    }

    @Override
    public String toString() {
        return this.method.name() + " " + this.getPath();
    }

    public static Route ALL(String path, HttpHandler handler) {
        return new Route(HttpMethod.ALL, path, handler);
    }

    public static Route DELETE(String path, HttpHandler handler) {
        return new Route(HttpMethod.DELETE, path, handler);
    }

    public static Route GET(String path, HttpHandler handler) {
        return new Route(HttpMethod.GET, path, handler);
    }

    public static Route HEAD(String path, HttpHandler handler) {
        return new Route(HttpMethod.HEAD, path, handler);
    }

    public static Route PATCH(String path, HttpHandler handler) {
        return new Route(HttpMethod.PATCH, path, handler);
    }

    public static Route POST(String path, HttpHandler handler) {
        return new Route(HttpMethod.POST, path, handler);
    }

    public static Route PUT(String path, HttpHandler handler) {
        return new Route(HttpMethod.PUT, path, handler);
    }
}
