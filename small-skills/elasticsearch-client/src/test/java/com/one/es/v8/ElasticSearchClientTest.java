package com.one.es.v8;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.indices.Alias;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.one.es.v7.entity.ProductInfo;
import com.one.es.v7.service.ProductService;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 虽然 High Level REST Client 在易用性和开发效率方面有优势，但是为了获得更好的性能、更好的稳定性，并且获得更多的特性支持，
 * 官方建议从 Elasticsearch 7.15.0 版本开始，使用新的 Java 客户端 ElasticsearchClient 进行操作。
 */
@SpringBootTest
public class ElasticSearchClientTest {

    @Resource
    private ProductService service;

    public ElasticsearchClient esClient;


    @PostConstruct
    public void initClient() {
        String username = "elastic";
        String password = "";
        // 基本的用户名密码认证
        BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"));
        restClientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder ->
                httpAsyncClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider));
        RestClient restClient = restClientBuilder.build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        this.esClient = new ElasticsearchClient(transport);
    }

    /**
     * 创建索引
     */
    @Test
    public void createIndex() throws IOException {
        CreateIndexResponse createIndexResponse = esClient.indices().create(
                new CreateIndexRequest.Builder()
                        .index("product_info")
                        .aliases("product_info_alias", new Alias.Builder().isWriteIndex(true).build())
                        .build());
        if(createIndexResponse.acknowledged()){
            System.out.println("创建索引成功！");
        } else {
            System.out.println("创建索引失败！");
        }
    }

    @Test
    public void addData() throws IOException {
        ProductInfo productInfo = new ProductInfo(1,"红米手机", "红米 Note，智能手机，价格便宜", 999,"蓝色", LocalDateTime.now());
        CreateResponse createResponse = esClient.create(createRequest -> createRequest.id("1003").index("product_info").document(productInfo));
        System.out.println("添加索引请求结果：" + createResponse.result());
    }

    @Test
    public void delDataVidId(String id) throws IOException {
        DeleteResponse deleteResponse = esClient.delete(deleteReqest -> deleteReqest.index("product_info").id(id));
        System.out.println("按照 ID 删除索引数据结果：" + deleteResponse.result());
    }

    @Test
    public void delDataVidQuery(String value) throws IOException {
        DeleteByQueryResponse deleteByQueryResponse = esClient.deleteByQuery(deleteByQuery ->
                deleteByQuery.index("product_info").query(query ->
                        query.match(MatchQuery.of(builder ->
                                builder.field("productName").query(value)))));
        System.out.println("按照条件删除索引数据结果：" + deleteByQueryResponse.deleted());
    }

    @Test
    public void updateViaId(String id) throws IOException {
        ProductInfo productInfo = new ProductInfo(2,"苹果手机", "苹果智能手机，价格实惠便宜",5999, "黑色", LocalDateTime.now());
        UpdateResponse<ProductInfo> updateResponse = esClient.update(updateRequest -> updateRequest.index("product_info").id(id).doc(productInfo), ProductInfo.class);
        System.out.println("根据 ID 修改：" + updateResponse.result());
    }


    @Test
    public void query(String value) throws IOException {
        SearchResponse<ProductInfo> searchResponse = esClient.search(searchRequest ->
                searchRequest.index("product_info").query(q -> q.match(MatchQuery.of(builder ->
                        builder.field("name").query(value)))), ProductInfo.class);
        searchResponse.hits().hits().forEach(productInfoHit -> System.out.println(productInfoHit));
    }



}
