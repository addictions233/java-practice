package com.one.es.v8.service;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;
import co.elastic.clients.elasticsearch.indices.*;
import com.one.es.v7.entity.ProductInfo;
import com.one.es.v8.client.ESClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作ES中的索引index和文档document
 */
public class IndexAndDocumentOperate {

    static ElasticsearchClient client;

    static ElasticsearchAsyncClient asyncClient;

    static {
        client = ESClient.initESClient();
        try {
            asyncClient = ESClient.initESClient2();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void operateIndex() throws IOException {
        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest.Builder().index("myindex").build();
        final CreateIndexResponse createIndexResponse = client.indices().create(request);
        System.out.println("创建索引成功：" + createIndexResponse.acknowledged());
        // 查询索引
        GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index("myindex").build();
        final GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest);
        System.out.println("索引查询成功：" + getIndexResponse.result());
        // 删除索引
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index("myindex").build();
        final DeleteIndexResponse delete = client.indices().delete(deleteIndexRequest);
        final boolean acknowledged = delete.acknowledged();
        System.out.println("删除索引成功：" + acknowledged);
    }

    public void lambdaOperateIndex() throws IOException {
        // 创建索引
        final Boolean acknowledged = client.indices().create(p -> p.index("")).acknowledged();
        System.out.println("创建索引成功");
        // 获取索引
        System.out.println(client.indices().get(req -> req.index("myindex1")).result());
        // 删除索引
        client.indices().delete(reqbuilder -> reqbuilder.index("myindex")).acknowledged();
    }

    public void operateDocument(ProductInfo productInfo) throws IOException {
        // 创建文档
        IndexRequest indexRequest = new IndexRequest.Builder().index("myindex").id(productInfo.getId().toString()).document(productInfo).build();
        final IndexResponse index = client.index(indexRequest);
        System.out.println("文档操作结果:" + index.result());
        // 批量创建文档
        final List<BulkOperation> operations = new ArrayList<BulkOperation>();
        for (int i = 1; i <= 5; i++) {
            final CreateOperation.Builder builder = new CreateOperation.Builder();
            builder.index("myindex");
            builder.id("200" + i);
            builder.document(new ProductInfo(2,"苹果手机", "苹果智能手机，价格实惠便宜",5999, "黑色", LocalDateTime.now()));
            final CreateOperation<Object> objectCreateOperation = builder.build();
            final BulkOperation bulk = new BulkOperation.Builder().create(objectCreateOperation).build();
            operations.add(bulk);
        }
        BulkRequest bulkRequest = new BulkRequest.Builder().operations(operations).build();
        final BulkResponse bulkResponse = client.bulk(bulkRequest);
        System.out.println("数据操作成功：" + bulkResponse);
        // 删除文档
        DeleteRequest deleteRequest = new DeleteRequest.Builder().index("myindex").id("1001").build();
        client.delete(deleteRequest);
    }

    public void lambdaOperateDocument(ProductInfo productInfo) throws IOException {
        // 创建文档
        System.out.println(client.index(req -> req.index("myindex").id(productInfo.getId().toString()).document(productInfo)).result());
        // 批量创建文档
        List<ProductInfo> productInfos = new ArrayList<>();
        client.bulk(req -> {
            productInfos.forEach(obj -> {
                req.operations(b -> {
                    b.create(d -> d.id(obj.getId().toString()).index("myindex").document(obj));
                    return b;
                });
            });
            return req;
        });
        // 删除文档
        client.delete(req -> req.index("myindex").id("1001"));
    }

    public void queryDocument() throws IOException {
        final SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder().index("myindex1");
        MatchQuery matchQuery = new MatchQuery.Builder().field("city").query(FieldValue.of("beijing")).build();
        Query query = new Query.Builder().match(matchQuery).build();
        searchRequestBuilder.query(query);
        SearchRequest searchRequest = searchRequestBuilder.build();
        final SearchResponse<Object> search = client.search(searchRequest, Object.class);
        System.out.println(search);
    }

    public void lambdaQueryDocument() throws IOException {
        client.search(req -> {
            req.query(q -> q.match(m -> m.field("city").query("beijing")));
            return req;
        }, Object.class);

    }

    public void asyncOperate() {
        // 创建索引
        asyncClient.indices().create(req -> {
            req.index("newindex");
            return req;
        }).whenComplete((resp, error) -> {
            System.out.println("回调函数");
            if (resp != null) {
                System.out.println(resp.acknowledged());
            } else {
                error.printStackTrace();
            }
        });
        System.out.println("主线程操作...");
        asyncClient.indices().create(req -> {
            req.index("newindex");
            return req;
        }).thenApply(resp -> {
            return resp.acknowledged();
        }).whenComplete((resp, error) -> {
            System.out.println("回调函数");
            if (!resp) {
                System.out.println();
            } else {
                error.printStackTrace();
            }
        });
    }
}
