package com.sammwy.httplib.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.sammwy.httplib.HttpHandler;
import com.sammwy.httplib.HttpStatus;
import com.sammwy.httplib.Request;
import com.sammwy.httplib.RequestParser;
import com.sammwy.httplib.Response;

public class HttpDecoderWorker implements Runnable {
    private HttpHandler handler;
    private int maxAlloc;
    private SocketChannel socket;

    public HttpDecoderWorker(HttpHandler handler, int maxAlloc, SocketChannel socket) {
        this.handler = handler;
        this.maxAlloc = maxAlloc;
        this.socket = socket;
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(maxAlloc);

        try {
            int bytesReaded = this.socket.read(buffer);

            Request request = RequestParser.fromByteArray(buffer.array());
            Response response = new Response(this.socket);

            if (bytesReaded >= maxAlloc) {
                response.setStatus(HttpStatus.PAYLOAD_TOO_LARGE);
                response.send("413 Payload too large");
                response.flush();
                return;
            }

            if (request != null) {
                InetSocketAddress address = (InetSocketAddress) this.socket.getRemoteAddress();
                request.setIP(address.getHostString());
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.send("400 Bad Request");
                response.flush();
                return;
            }

            try {
                this.handler.handle(request, response);
            } catch (Exception e) {
                response.setStatus(500);
                response.send("500 Internal Server Error");
                response.flush();

                e.printStackTrace();
            }
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
