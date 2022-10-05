package com.sammwy.httplib;

import java.util.HashMap;
import java.util.Map;

import com.sammwy.httplib.router.Route;

public class Request extends HttpObject {
    private String body = "";
    private String realIP = null;
    private String ip = null;
    private Route routering = null;
    private Map<String, String> queries = new HashMap<>();

    private HttpMethod method = null;
    private String path = null;
    private String version = null;

    public String getBody() {
        return this.body;
    }

    public String getFullPath() {
        return this.path;
    }

    public String getHostname() {
        return this.getHeader("host");
    }

    public String getIP() {
        return this.ip;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path.split("[?]")[0];
    }

    public String getParam(String key) {
        String[] routeringPaths = this.getRoutering().getPath().split("/");
        String[] urlPaths = this.getPath().split("/");

        for (int i = 0; i < routeringPaths.length; i++) {
            if (i >= urlPaths.length) {
                break;
            }

            String path = routeringPaths[i];
            if (path.equals(":" + key)) {
                return urlPaths[i];
            }
        }

        return null;
    }

    public Map<String, String> getQueries() {
        return this.queries;
    }

    public String getQuery(String key) {
        return this.queries.get(key);
    }

    public String getRealIP() {
        return this.realIP;
    }

    public Route getRoutering() {
        return this.routering;
    }

    public String getUserAgent() {
        return this.getHeader("user-agent");
    }

    public String getVersion() {
        return this.version;
    }

    public Request setBody(String body) {
        this.body = body;
        return this;
    }

    public Request setIP(String ip) {
        this.ip = ip;
        if (this.realIP == null) {
            this.realIP = ip;
        }
        return this;
    }

    public Request setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public Request setPath(String path) {
        this.path = path;
        this.queries = QueryParser.fromPath(path);
        return this;
    }

    public Request setVersion(String version) {
        this.version = version;
        return this;
    }

    public void setRoutering(Route routering) {
        this.routering = routering;
    }

    protected boolean setHttpMeta(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
            return false;
        }

        HttpMethod method = HttpMethod.get(parts[0]);
        if (method == null) {
            return false;
        }

        this.setMethod(method);
        this.setPath(parts[1]);
        this.setVersion(parts[2]);
        return true;
    }
}
