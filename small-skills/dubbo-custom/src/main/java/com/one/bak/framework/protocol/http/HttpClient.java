package com.one.bak.framework.protocol.http;

import com.one.bak.framework.Invocation;
import com.one.bak.framework.URL;

import java.io.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @description: 使用http请求进行远程调用的客户端
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class HttpClient {

    public Object send(String hostName, Integer port, Invocation invocation) {

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // 进行http远程服务调用
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(invocation);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http", null, hostName, port, "/", null, null))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(byteArrayOutputStream.toByteArray()))
                    .build();
            HttpResponse<byte[]> response = java.net.http.HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

            // 进行远程响应结果进行处理
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(response.body()));
            return objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
