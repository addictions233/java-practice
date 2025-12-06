package com.one.kafka;

import com.one.kafla.db.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class KafkaTest {

    @Autowired
    private ConsumerService consumerService;

    public void testConsumerService() {
        consumerService.init();
    }
}
