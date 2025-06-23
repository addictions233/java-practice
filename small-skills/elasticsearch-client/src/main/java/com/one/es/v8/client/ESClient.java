package com.one.es.v8.client;

import co.elastic.clients.elasticsearch.*;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.*;
import org.apache.http.ssl.*;
import org.elasticsearch.client.*;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.nio.file.*;
import java.security.KeyStore;
import java.security.cert.*;


/**
 * 新版本使用Elasticsearch Java创建的ES客户端连接对象
 */
public class ESClient {

    public static void main(String[] args) throws Exception {
        initESClient();
    }

    public static ElasticsearchClient initESClient()  {
        // 获取客户端对象
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        // 基于用户名和密码的身份认证
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", ""));
        // 使用http协议的客户端和服务端之间的通讯
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestClient restClient = builder.build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        // 同步的ES客户端对象
        ElasticsearchClient client = new ElasticsearchClient(transport);
//        // 异步的ES客户端对象
//        ElasticsearchAsyncClient asyncClient = new ElasticsearchAsyncClient(transport);
//        // 同步的客户端才需要关闭
//        transport.close();

        return client;
    }

    public static ElasticsearchAsyncClient initESClient2() throws Exception {
        // 获取客户端对象
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", ""));

        // 使用Https时加载CA证书
        Path caCertificatePath = Paths.get("ca.crt");
        CertificateFactory factory =
                CertificateFactory.getInstance("X.509");
        Certificate trustedCa;
        try (InputStream is = Files.newInputStream(caCertificatePath)) {
            trustedCa = factory.generateCertificate(is);
        }
        KeyStore trustStore = KeyStore.getInstance("pkcs12");
        trustStore.load(null, null);
        trustStore.setCertificateEntry("ca", trustedCa);
        SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                .loadTrustMaterial(trustStore, null);
        final SSLContext sslContext = sslContextBuilder.build();
        // 使用https协议进行客户端和服务端之间的通讯
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "https"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setSSLContext(sslContext)
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .setDefaultCredentialsProvider(credentialsProvider));
        RestClient restClient = builder.build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
//        // 同步的ES客户端对象
//        ElasticsearchClient client = new ElasticsearchClient(transport);
//        transport.close();
        // 异步的ES客户端对象
        ElasticsearchAsyncClient asyncClient = new ElasticsearchAsyncClient(transport);

        return asyncClient;
    }
}
