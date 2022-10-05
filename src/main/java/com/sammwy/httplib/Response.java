package com.sammwy.httplib;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class Response extends HttpObject {
    private SocketChannel socket;

    private String version = "HTTP/1.1";
    private String body = "";
    private HttpStatus status = HttpStatus.OK;

    public Response(SocketChannel socket) {
        this.socket = socket;
        this.setHeader("date", new Date().toString());
        this.setHeader("connection", "closed");
        this.setHeader("content-type", "text/plain");
    }

    public String asString() {
        String firstline = version + " " + String.valueOf(status.getStatusCode()) + " " + status.name();
        String headers = this.headersAsString();
        String all = firstline + "\n" + headers + "\n\n" + body;
        return all;
    }

    public Response end() {
        try {
            this.socket.close();
        } catch (IOException e) {
        }

        return this;
    }

    public Response flush() {
        String raw = this.asString();
        try {
            this.socket.write(ByteBuffer.wrap(raw.getBytes()));
        } catch (IOException e) {
        }
        return this.end();
    }

    public SocketChannel getSocket() {
        return this.socket;
    }

    public Response json(String json) {
        this.write(json);
        this.setHeader("content-type", "application-json");
        return this;
    }

    public Response send(String html) {
        this.setBody(html);
        this.setHeader("content-type", "text/html");
        return this;
    }

    public Response setBody(String body) {
        this.body = body;
        this.setHeader("content-length", body.length());
        return this;
    }

    public Response setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public Response setStatus(int status) {
        return this.setStatus(HttpStatus.getFromStatusCode(status));
    }

    public Response write(String value) {
        this.setBody(this.body + value);
        return this;
    }
}