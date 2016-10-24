package com.xqkj.quanjin.helper;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp的便捷封装类
 */
public class OkHttpHelper {
    private final static MediaType contentType = MediaType.parse("text/html; charset=UTF-8");

    private OkHttpHelper() {
    }

    private static RequestBody createRequestBody(String content) {
        return RequestBody.create(contentType, content);
    }

    private static Request buildRequest(String content, String url) {
        Request request;
        RequestBody requestBody = createRequestBody(content);
        request = new Request.Builder().url(url).post(requestBody).build();
        return request;
    }

    private static Request buildRequest(RequestBody requestBody, String url) {
        Request request;
        request = new Request.Builder().url(url).post(requestBody).build();
        return request;
    }

    //对多途数据MultipartBody的处理
    public static void enqueue(RequestBody requestBody, String url, Callback callback) {
        Request request = buildRequest(requestBody, url);
        getClientInstance().newCall(request).enqueue(callback);
    }

    /**
     * 异步执行
     *
     * @param content
     * @param url
     * @param callback
     */
    public static void enqueue(String content, String url, Callback callback) {
        Request request = buildRequest(content, url);
        getClientInstance().newCall(request).enqueue(callback);
    }

    /**
     * 同步执行
     *
     * @param content
     * @param url
     * @return
     */
    public static Response execute(String content, String url) {
        Response response = null;
        Request request = buildRequest(content, url);
        try {
            response = getClientInstance().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 获取OkHttpClient唯一实例
     *
     * @return
     */
    private static OkHttpClient getClientInstance() {
        return OkHttpClientHolder.okHttpClient;
    }

    /**
     * 嵌套类
     */
    private final static class OkHttpClientHolder {
        private final static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    }
}
