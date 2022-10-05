package com.sammwy.httplib.utils;

import com.google.gson.Gson;
import com.sammwy.httplib.Request;

public class BodyParser {
    private static Gson gson = new Gson();

    public static <T> T parse(Request request, Class<T> type) {
        T result = null;

        String body = request.getBody();
        String contentType = request.getHeader("content-type");

        if (contentType.equalsIgnoreCase("application/json")) {
            result = gson.fromJson(body, type);
        }

        return result;
    }
}
