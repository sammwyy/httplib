package com.sammwy.httplib;

import java.util.HashMap;
import java.util.Map;

public class QueryParser {
    public static Map<String, String> fromQueryString(String raw) {
        Map<String, String> queries = new HashMap<>();

        for (String part : raw.split("&")) {
            if (part.contains("=")) {
                String key = part.split("=")[0];
                String value = part.split("=")[1];
                queries.put(key, value);
            }
        }

        return queries;
    }

    public static Map<String, String> fromPath(String path) {
        if (!path.contains("?")) {
            return new HashMap<>();
        } else {
            return fromQueryString(path.split("[?]", 2)[1]);
        }
    }
}
