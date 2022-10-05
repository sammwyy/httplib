package com.sammwy.httplib.errors;

import com.sammwy.httplib.HttpStatus;

public class HttpException extends Exception {
    private HttpStatus status;

    public HttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpException(HttpStatus status) {
        this(status, String.valueOf(status.getStatusCode()) + " " + status.getStatusMessage());
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
