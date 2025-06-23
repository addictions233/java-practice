package com.one.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * 使用HttpClient工具类
 * @author one
 */
@Slf4j
public class HttpClientUtil {

    /**
     * 设置5s超时
     */
    private static final int TIME_OUT = 300;

    /**
     * 遵循单例模式
     */
    private volatile static CloseableHttpClient httpClient = null;

    private final static Object syncLock = new Object();

    private static void config(HttpRequestBase httpRequestBase, int connectRequestTimeout,
                               int connectTimeout, int socketTimeout) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectRequestTimeout * 1000)
                .setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        httpRequestBase.setConfig(requestConfig);
    }

    public static void config(HttpRequestBase httpRequestBase, int allTimeout) {
        config(httpRequestBase, allTimeout, allTimeout, allTimeout);
    }

    public static void config(HttpRequestBase httpRequestBase) {
        config(httpRequestBase, TIME_OUT);
    }

    /**
     * 获取HttpClient对象
     */
    public static CloseableHttpClient getHttpClient(String url) {
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }
        // 双重锁保证单例
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient(500, 200, 300, hostname, port);
                }
            }
        }
        return httpClient;
    }

    /**
     * 创建CloseableHttpClient对象，采用重试机制，默认重试三次
     *
     * @param maxTotal    最大连接数增加
     * @param maxPerRoute 将每个路由基础的连接增加
     * @param maxRoute    将目标主机的最大连接数增加
     * @param hostname    主机IP
     * @param port        主机端口
     * @return
     */
    public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute,
                                                       String hostname, int port) {
        return createHttpClient(maxTotal, maxPerRoute, maxRoute, hostname, port, 3);
    }

    /**
     * 创建HttpClient对象
     *
     * @param maxTotal    最大连接数增加
     * @param maxPerRoute 将每个路由基础的连接增加
     * @param maxRoute    将目标主机的最大连接数增加
     * @param hostname    主机IP
     * @param port        主机端口
     * @param times       重试次数
     * @return
     */
    public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute,
                                                       String hostname, int port, int times) {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        TrustStrategy trustStrategy = (xc, msg) -> true;
        SSLContextBuilder builder = new SSLContextBuilder();
        LayeredConnectionSocketFactory sslsf;
        try {
            builder.loadTrustMaterial(trustStrategy);
            sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
            sslsf = SSLConnectionSocketFactory.getSocketFactory();
        }
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加
        cm.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        cm.setDefaultMaxPerRoute(maxPerRoute);
        HttpHost httpHost = new HttpHost(hostname, port);
        // 将目标主机的最大连接数增加
        cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);
        // 指定某条链路的最大连接数
        ConnectionKeepAliveStrategy kaStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    keepAlive = 60000;
                }
                return keepAlive;
            }
        };
        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            if (executionCount >= times) {// 如果已经重试了次数，就放弃
                log.error("===============http请求重试多次失败==============");
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                log.error("===============http请求服务器丢掉了连接进行重试==============");
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                log.error("===============http请求服务器SSL握手异常==============");
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                log.error("===============http请求服务器超时==============");
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                log.error("===============http请求目标服务器不可达==============");
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            return !(request instanceof HttpEntityEnclosingRequest);
        };
        return HttpClients.custom().setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler)
                .setKeepAliveStrategy(kaStrategy)
                .build();
    }

    private static void setPostParams(HttpPost httpost, Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
    }

    /**
     * POST请求URL获取内容
     */
    public static String post(String url, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        config(httpPost);
        setPostParams(httpPost, params);
        return execute(url, httpPost);
    }

    /**
     * POST请求URL获取内容
     *
     * @param url                   请求地址
     * @param json                  传递参数json
     * @param connectRequestTimeout 从连接池获取连接的timeout,单位秒
     * @param connectTimeout        和服务器建立连接的timeout,单位秒
     * @param socketTimeout         从服务器读取数据的timeout,单位秒
     * @return String结果
     */
    public static String postJson(String url, String json, int connectRequestTimeout, int connectTimeout, int socketTimeout) {
        log.info("url:{}", url);
        HttpPost httpPost = new HttpPost(url);
        config(httpPost, connectRequestTimeout, connectTimeout, socketTimeout);
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(stringEntity);
        return execute(url, httpPost);
    }

    /**
     * POST请求URL获取内容
     *
     * @param url        请求地址
     * @param json       传递参数json
     * @param allTimeout 超时秒数
     * @return String结果
     */
    public static String postJson(String url, String json, int allTimeout) {
        return postJson(url, json, allTimeout, allTimeout, allTimeout);
    }

    /**
     * POST请求URL获取内容
     *
     * @param url  请求地址
     * @param json 传递参数json
     * @return
     */
    public static String postJson(String url, String json) {
        return postJson(url, json, TIME_OUT);
    }


    public static String execute(String url, HttpRequestBase requestBase) {
        try (CloseableHttpResponse response = getHttpClient(url).execute(requestBase, HttpClientContext.create())) {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET请求URL获取内容
     */
    public static String get(String url) {
        HttpGet httpget = new HttpGet(url);
        config(httpget);
        return execute(url, httpget);
    }
}
