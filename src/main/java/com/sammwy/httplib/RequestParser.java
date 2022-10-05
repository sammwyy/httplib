package com.sammwy.httplib;

public class RequestParser {
    public static Request fromString(String raw) {
        Request request = new Request();

        boolean readingHeaders = false;
        boolean readingBody = false;

        for (String line : raw.split("\n")) {
            if (readingBody) {
                request.setBody(request.getBody() + line);
            } else if (readingHeaders) {
                if (line.isEmpty()) {
                    readingBody = true;
                    readingHeaders = false;
                } else {
                    request.addHeader(line);
                }
            } else {
                request.setHttpMeta(line);
                readingHeaders = true;
            }
        }

        return request;
    }

    public static Request fromByteArray(byte[] bytes) {
        return fromString(new String(bytes));
    }
}
