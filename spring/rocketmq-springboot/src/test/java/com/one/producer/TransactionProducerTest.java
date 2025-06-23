package com.one.producer;

import com.one.basic.SpringProducer;
import com.one.config.MyTransactionRocketMQTemplate;
import com.one.domain.OrderPaidEvent;
import com.one.service.OrderPaidService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author one
 * @description 使用rocketmq测试事务消息
 * @date 2022-9-4
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionProducerTest {

    @Resource
    private OrderPaidService orderPaidService;

    @Resource
    private SpringProducer springProducer;

    @Test
    public void testTransactionMessage() throws InterruptedException {
        orderPaidService.handleOrderPaidEvent(new OrderPaidEvent("oderId_001", "productId_005",new BigDecimal("100.00")));
        // 服务不能立刻停止, 需要给broker回调检查本地事务留有时间
        Thread.sleep(60000);
    }

    @Test
    public void sendTransactionMessage() throws InterruptedException {
        springProducer.sendMessageInTransaction("test-transaction-topic","transaction message");
    }
}
