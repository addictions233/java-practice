package com.one.okhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author one
 */
@Slf4j
public class OkHttpUtils {

    private static final OkHttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT = (new OkHttpClient.Builder())
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getClient(){
        return HTTP_CLIENT;
    }





    public static JSONObject sendGetRequest(String url, String authorization){
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("authorization", authorization)
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return JSONObject.parseObject(JSON.toJSONString(response.body()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static JSONObject sendPostRequest(String url, String authorization, JSONObject jsonObject){
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("authorization", authorization)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()){
            return JSONObject.parseObject(JSON.toJSONString(response.body()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static JSONObject sendFormDataRequest(String url,String authorization,File file,String methodType,JSONObject jsonObject, String fileParamName){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(fileParamName,file.getName(),
                RequestBody.create(MediaType.parse("application/octet-stream"),
                        file));
        if (Objects.nonNull(jsonObject) && !jsonObject.isEmpty()) {
            for (String key : jsonObject.keySet()) {
                builder.addFormDataPart(key, String.valueOf(jsonObject.get(key)));
            }
        }
        RequestBody body =builder.build();
        Request request = new Request.Builder()
                .url(url)
                .method(methodType, body)
                .addHeader("authorization", authorization)
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()){
            if (response.code() == HttpStatus.OK.value()) {
                return JSONObject.parseObject(JSON.toJSONString(response.body()));
            } else {
                log.error("发送http请求返回结果异常:{}", response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    public static void sendPostRequestExport(String url, JSONObject jsonObject, String authorization, String methodType, HttpServletResponse servletResponse) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .method(methodType, body)
                .addHeader("authorization", authorization)
                .addHeader("tenantcode", "SSO")
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            //执行成功
            if (response.isSuccessful()) {
                response.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
                // 检查Content-Type来判断响应是否为文件流
                // 获取文件流
                InputStream inputStream = response.body().byteStream();
                // 返回文件流响应
                servletResponse.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode("明细列表导出", "UTF-8"));
                servletResponse.setContentType("application/octet-stream");
                OutputStream outputStream = servletResponse.getOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
            } else {
                // 处理请求失败的情况
                servletResponse.setContentType("application/json;charset=UTF-8");
                String state = "{\"state\":\"10\"}";
                servletResponse.getWriter().write(state);
            }
        } catch (IOException e) {
            // 处理IO异常
            e.printStackTrace();
        }
    }

}
