package com.one.es.v7;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Iterator;

/**
 * sniffer 嗅探器: 允许从正在运行的 Elasticsearch 集群中自动发现节点并将它们设置为现有 RestClient 实例的最小库
 */
@SpringBootTest
public class SnifferTest {

    @Test
    public void sniffer() throws IOException {
        // 失败时重启嗅探
        // 启用失败时嗅探，也就是在每次失败后，节点列表会立即更新，而不是在接下来的普通嗅探轮中更新。
        // 在这种情况下，首先需要创建一个 SniffOnFailureListener 并在 RestClient 创建时提供。
        SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();

        RestClient restClient = RestClient.builder(
                        new HttpHost("localhost", 9200, "http"))
                .setFailureListener(sniffOnFailureListener)
                .build();

        // 默认每五分钟发现一次
        Sniffer sniffer = Sniffer.builder(restClient)  // 将restClient绑定sniffer嗅探器
                .setSniffIntervalMillis(60000)  // 设置嗅探间隔时间为60秒
                .setSniffAfterFailureDelayMillis(30000)  // 设置嗅探失败后的延迟时间为30秒
                .build();

        // 启动监听
        // 将sniffer实例设置为失败监听器
        sniffOnFailureListener.setSniffer(sniffer);

        // 关闭 Sniffer
        sniffer.close();
        restClient.close();
    }

    @Test
    public void snifferTest() throws InterruptedException, IOException {
        // 构建high level rest client客户端对象
        RestHighLevelClient highLevelClient  = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 将client绑定sniffer嗅探器
        Sniffer sniffer = Sniffer.builder(highLevelClient.getLowLevelClient())
                .setSniffIntervalMillis(5000)
                .setSniffAfterFailureDelayMillis(15000)
                .build();


        // 使用嗅探器sniffer找到es集群中的所有的Node节点信息
        Iterator<Node> iterator = highLevelClient.getLowLevelClient().getNodes().iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            System.out.println("node = " + node);
        }

        // 等待5秒，让sniffer嗅探器找到es集群中的所有的Node节点信息
        Thread.sleep(5000);
        System.out.println("------------------等待5s后-------------------------");

        iterator = highLevelClient.getLowLevelClient().getNodes().iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            System.out.println("node = " + node);
        }

        // 关闭 Sniffer
        sniffer.close();
        highLevelClient.close();
    }
}
