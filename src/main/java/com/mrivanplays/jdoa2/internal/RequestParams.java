package com.mrivanplays.jdoa2.internal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParams {

    private Map<String, String> stringParams = new ConcurrentHashMap<>();

    public void put(String key, String value) {
        stringParams.put(key, value);
    }

    public String toEncodedString() {
        try {
            StringBuilder encoded = new StringBuilder();
            for (Map.Entry<String, String> param : stringParams.entrySet()) {
                if (encoded.length() > 0) {
                    encoded.append("&");
                }
                encoded.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.name()));
                encoded.append("=");
                encoded.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8.name()));
            }

            return encoded.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
