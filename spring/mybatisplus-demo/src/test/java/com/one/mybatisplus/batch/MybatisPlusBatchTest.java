package com.one.mybatisplus.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisPlusBatchTest {

    @Autowired
    private MybatisPlusBatchInsert mybatisPlusBatchInsert;

    @Autowired
    private MybatisBatchInsert mybatisBatchInsert;

    @Test
    public void testBatchInsert() {
        mybatisPlusBatchInsert.batchInsert();
    }

     @Test
    public void testMybatisBatchInsert() {
        mybatisBatchInsert.batchInsert();
    }
}
