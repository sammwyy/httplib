package com.sammwy.httplib;

public interface HeaderContainer {
    public HeaderContainer addHeader(String keyAndValue);

    public String getHeader(String key);

    public String headersAsString();

    public HeaderContainer setHeader(String key, String value);

    public default HeaderContainer setHeader(String key, int value) {
        return this.setHeader(key, String.valueOf(value));
    }
}
