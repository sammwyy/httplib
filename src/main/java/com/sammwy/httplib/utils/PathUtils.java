package com.sammwy.httplib.utils;

public class PathUtils {
    public static String join(String first, String second) {
        String result = first + "/" + second;
        while (result.contains("//")) {
            result = result.replace("//", "/");
        }
        return result;
    }

    public static boolean isMatching(String path, String routing) {
        String[] pathParts = path.split("/");
        String[] routeParts = routing.split("/");

        if (routeParts.length > pathParts.length) {
            return false;
        }

        else if (!routing.endsWith("/*") && routeParts.length != pathParts.length) {
            return false;
        }

        for (int i = 0; i < routeParts.length; i++) {
            String route = routeParts[i];

            if (route.equals("*")) {
                return true;
            }

            else if (route.startsWith(":")) {
                continue;
            }

            else {
                if (!route.equals(pathParts[i])) {
                    return false;
                }
            }
        }

        return true;
    }
}
