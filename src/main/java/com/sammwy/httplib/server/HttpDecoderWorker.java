package com.sammwy.httplib.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.sammwy.httplib.HttpHandler;
import com.sammwy.httplib.Request;
import com.sammwy.httplib.RequestParser;
import com.sammwy.httplib.Response;

public class HttpDecoderWorker implements Runnable {
    private HttpHandler handler;
    private SocketChannel socket;

    public HttpDecoderWorker(HttpHandler handler, SocketChannel socket) {
        this.handler = handler;
        this.socket = socket;
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(5000);

        try {
            this.socket.read(buffer);

            Request request = RequestParser.fromByteArray(buffer.array());
            InetSocketAddress address = (InetSocketAddress) this.socket.getRemoteAddress();
            request.setIP(address.getHostString());

            Response response = new Response(this.socket);
            this.handler.handle(request, response);
        } catch (IOException e) {
            if (this.socket.isOpen()) {
                try {
                    this.socket.close();
                } catch (IOException e1) {
                }
            }
        } finally {
            buffer.flip();
            buffer.clear();
        }
    }

    public void runAsync() {
        new Thread(this).start();
    }
}
