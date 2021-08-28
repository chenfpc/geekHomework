package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 第二周作业，okhttp访问自建的http服务器
 */
public class OkHttpClientSample {
    public static final String URL = "http://localhost:8001";

    public static void main(String[] args) {
        try {
            System.out.println(run(URL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String run(String url) throws IOException {

        final OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
