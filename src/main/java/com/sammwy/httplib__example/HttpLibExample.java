package com.sammwy.httplib__example;

import com.sammwy.httplib.server.HttpServer;

public class HttpLibExample {
    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();

        server.get("/", (req, res) -> {
            res.send("Hello World");
            res.flush();
        });

        server.get("/hello/:username", (req, res) -> {
            res.send("Hello user " + req.getParam("username"));
            res.flush();
        });

        server.listen(8080, "127.0.0.1");
    }
}
