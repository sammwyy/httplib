package com.sammwy.httplib.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.sammwy.httplib.router.Router;

public class HttpServer extends Router {
    private ServerSocketChannel server;

    private int backlog = 128;
    private int maxAlloc = 1024;

    public HttpServer setBacklog(int backlog) {
        this.backlog = backlog;
        return this;
    }

    public HttpServer setMaxAlloc(int maxAlloc) {
        this.maxAlloc = maxAlloc;
        return this;
    }

    private void waitForConnections() throws IOException {
        while (this.server.isOpen()) {
            SocketChannel clientSocket = this.server.accept();
            if (clientSocket.isOpen()) {
                HttpDecoderWorker worker = new HttpDecoderWorker(this, maxAlloc, clientSocket);
                worker.runAsync();
            }
        }
    }

    public void listen(int port, String host) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(host, port);
        server.bind(address, this.backlog);
        this.server = server;
        this.waitForConnections();
    }

    public void listen(int port) throws IOException {
        this.listen(port, "127.0.0.1");
    }
}
