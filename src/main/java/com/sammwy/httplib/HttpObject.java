package com.sammwy.httplib;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpObject implements HeaderContainer {
    private Map<String, String> headers;

    public HttpObject() {
        this.headers = new HashMap<>();
    }

    @Override
    public HeaderContainer addHeader(String keyAndValue) {
        if (keyAndValue.contains(":")) {
            String[] parts = keyAndValue.split(":", 2);
            String key = parts[0].trim();
            String value = parts[1].trim();
            this.setHeader(key, value);
        }

        return this;
    }

    @Override
    public String getHeader(String key) {
        return this.headers.get(key.toLowerCase());
    }

    @Override
    public String headersAsString() {
        String result = "";
        for (Entry<String, String> entry : this.headers.entrySet()) {
            String entryStr = entry.getKey() + ": " + entry.getValue();
            if (result.isEmpty()) {
                result = entryStr;
            } else {
                result += "\n" + entryStr;
            }
        }
        return result;
    }

    @Override
    public HeaderContainer setHeader(String key, String value) {
        this.headers.put(key.toLowerCase(), value);
        return this;
    }
}
