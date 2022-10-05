package com.sammwy.httplib;

public enum HttpMethod {
    ALL, DELETE, GET, HEAD, PATCH, POST, PUT;

    public static HttpMethod get(String query) {
        for (HttpMethod method : HttpMethod.values()) {
            if (method.name().equalsIgnoreCase(query)) {
                return method;
            }
        }

        return null;
    }
}
