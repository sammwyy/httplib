package com.sammwy.httplib;

public enum HttpStatus {
    OK(200),
    NOT_FOUND(404);

    private int value;

    private HttpStatus(int value) {
        this.value = value;
    }

    public String getStatusMessage() {
        return this.name().toLowerCase().replace("_", " ");
    }

    public int getStatusCode() {
        return this.value;
    }

    public static HttpStatus getFromStatusCode(int statusCode) {
        for (HttpStatus status : HttpStatus.values()) {
            if (status.getStatusCode() == statusCode) {
                return status;
            }
        }

        return null;
    }
}
