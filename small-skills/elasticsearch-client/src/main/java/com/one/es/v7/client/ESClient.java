package com.one.es.v7.client;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;

import java.io.IOException;

/**
 * 使用RestHighLevelClient创建的ES客户端连接对象
 */
public class ESClient {
    private static ESClient ESClient;
    private String host = "localhost:9200,localhost:9201";
    private static Sniffer sniffer;
    private static RestHighLevelClient restHighLevelClient;

    private ESClient() {
    }

    public static ESClient getInstance() {
        if (ESClient == null) {
            synchronized (ESClient.class) {
                if (ESClient == null) {
                    ESClient = new ESClient();
                    restHighLevelClient = ESClient.getClient();
                }
            }
        }
        return ESClient;
    }


    public RestHighLevelClient getClient() {
        if (restHighLevelClient == null) {
            synchronized (ESClient.class) {
                if (restHighLevelClient == null) {
                    restHighLevelClient = buildClient();
                }
            }
        }
        return restHighLevelClient;
    }

    private RestHighLevelClient buildClient() {
        String[] hosts = host.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String[] host = hosts[i].split(":");
            httpHosts[i] = new HttpHost(host[0], Integer.parseInt(host[1]), "http");
        }

        RestClientBuilder builder = RestClient.builder(httpHosts);

        // 在Builder中设置请求头
        // 1.设置请求头
        Header[] defaultHeaders = new Header[]{
                new BasicHeader("Content-Type", "application/json")
        };
        builder.setDefaultHeaders(defaultHeaders);
        // 构建RestHighLevelClient客户端对象
        RestHighLevelClient highClient = new RestHighLevelClient(builder);

        //启用嗅探器
        SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
        builder.setFailureListener(sniffOnFailureListener);
        sniffer = Sniffer.builder(highClient.getLowLevelClient())
                .setSniffIntervalMillis(5000) // 5秒刷新并更新一次节点
                .setSniffAfterFailureDelayMillis(10000)
                .build();
        sniffOnFailureListener.setSniffer(sniffer);

        return highClient;
    }

    /**
     * 关闭sniffer client
     */
    public void closeClient() {
        if (null != restHighLevelClient) {
            try {
                //需要在highClient close之前操作
                sniffer.close();
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
