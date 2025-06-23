package mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.common.protocol.route.QueueData;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.junit.Test;

import java.util.*;

/**
 * @author one
 * @description 官方给出的RocketMQ示例
 * @date 2025-1-23
 */
public class ExampleRocketMQ {

    /**
     * NameServer中保存了Broker集群和Topic信息, 通过admin获取
     * @throws Exception
     */
    @Test
    public void admin() throws Exception {
        DefaultMQAdminExt admin = new DefaultMQAdminExt();
        admin.setNamesrvAddr("127.0.0.1:9876");
        admin.start();

        System.out.println("-----------------------broker cluster info-----------------");
        ClusterInfo clusterInfo = admin.examineBrokerClusterInfo();
        HashMap<String, BrokerData> brokerAddrTable = clusterInfo.getBrokerAddrTable();
        Set<Map.Entry<String, BrokerData>> entries = brokerAddrTable.entrySet();
        Iterator<Map.Entry<String, BrokerData>> iter = entries.iterator();
        while(iter.hasNext()){
            Map.Entry<String, BrokerData> next = iter.next();
            System.out.println(next.getKey()+ ":"+ next.getValue());
        }

        System.out.println("-----------------------topic list-----------------");
        TopicList topicList = admin.fetchAllTopicList();
        topicList.getTopicList().forEach(System.out::println);


        System.out.println("-----------------------topic route info-----------------");
        TopicRouteData topicRouteData = admin.examineTopicRouteInfo("topic01");
        // 通过topic名称可以获取哪些Broker可以发送, 以及每个Broker上有几个队列,
        // 这样作为Producer, 就可以通过topic名称获取到Broker信息, 然后将消息发送到指定的Broker
        List<BrokerData> brokerDatas = topicRouteData.getBrokerDatas();
        brokerDatas.forEach(System.out::println);
        List<QueueData> queueDatas = topicRouteData.getQueueDatas();
        queueDatas.forEach(System.out::println);
    }


    //------------------------------------测试批量消息---------------------------

    /**
     * 测试使用Producer批量发送消息
     * @throws Exception
     */
    @Test
    public void producer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group01");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // 消息中的key是消息在业务层面的唯一标识, 例如订单号, 商品id等, 可以在业务层面根据key做幂等处理
            // Rocketmq会对key创建hash索引, 可以根据key快速查找消息
            Message message = new Message("topic01", "tag01", "key" + i, ("hello mq" + i).getBytes());

            // 延迟消息, 设置延迟级别, 消息会先进对应级别的延迟队列, 等到了延迟时间, 才会被转移到真正的消费队列
            // 延迟级别从1开始, 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
//            message.setDelayTimeLevel(1);

            messages.add(message);
        }
        // 批量发送消息
        SendResult result = producer.send(messages);
        System.out.println(result);
    }

    /**
     *messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 18个延迟级别
     *1：
     *ConsumeConcurrentlyStatus.RECONSUME_LATER
     *consumer.setMaxReconsumeTimes(2);
     * %RETRY%consumer01 重试队列
     * %DLQ%consumer01 死信队列
     *总结：rocketmq：重试队列，死信队列：以topic形式，且：以consumerGroup
     *补充一下:kafka，客户端自己维护逻辑
     *
     *2:延迟队列的场景
     * @throws Exception
     */
    @Test
    public void consumer() throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer01");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("topic01", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMaxReconsumeTimes(2); // 最大重试次数, 第一次消费失败, 最多重试2次

        // 默认值就是1, 如果按照自己的需求设置这个消费消息数量, 这样单次可以消费多条消息,
        // 也就是控制List<MessageExt> msgs 集合大小
        // 虽然设置为1时,但是只是控制一次消费的消息数量, 并不是控制每次拉取的消息数量
        // 例如设置为1, 但是Broker上有10条消息, 那么每次只会消费1条消息, 但是一次会拉取10条消息
        consumer.setConsumeMessageBatchMaxSize(1); // 一次最多消费一条消息

        // push消费模式本质上还是利用的pull拉模式, 但是Rocketmq会将拉取到的消息缓存到本地, 然后再进行消费
        // 这个参数控制的是每次拉取的消息数量, 也就是每次consumer从Broker拉取的消息数量是1, 默认是32
        consumer.setPullBatchSize(1); // 一次拉取消息的数量

//        // 广播模式消费消息, 即是是同一个ConsumerGroup中的所有Consumer都会消费到所有的消息
//        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt msg : msgs) {
                        System.out.println(new String(msg.getBody()));
                        if (msg.getKeys().equals("key5")) {
                            // 模拟消费失败, hello mq5会被消费两次, 然后进入重试队列, 重试次数超过2次, 会将消息投递到死信队列
                            throw new RuntimeException("消费失败异常");
                        }
                    }
                    // 返回消费成功标识, 表示消费成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    // 消费失败, 会重新消费该消息, 先进重试队列, 重试次数超过16次, 会将消息投递到死信队列
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });

        consumer.start();

        System.in.read();
    }


    // ------------------------------------测试顺序消息---------------------------
    @Test
    public void orderProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group01");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

//        MyMessageQueueSelector myMessageQueueSelector = new MyMessageQueueSelector();

        for (int i = 0; i < 10; i++) {
            // 相同type的消息会进入同一个队列, 保证顺序性, 不同type不需要保证顺序性
            Message message = new Message("topic02", "tag02", "key" + i,
                    ("message body:" + i + ", type" + (i % 3)).getBytes());
            // 每次发送消息都new 一个MessageQueueSelector, 是存在性能问题的
//            producer.send(message, myMessageQueueSelector, i%3);

            // 如果能够保证MessageQueueSelector的无状态, 是可以复用的, 这样就可以避免每次发送消息都new 一个MessageQueueSelector
            SendResult result = producer.send(message, new MessageQueueSelector() {
                /**
                 * 定义消息选择器, 选择发送到哪个messageQueue
                 * @param mqs 所有messageQueue
                 * @param msg 消息
                 * @param arg i%3 就会作为arg 传入到这个方法中, 可以根据arg的值来选择发送到哪个messageQueue
                 * @return 最终选择的messageQueue
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    // arg就是i%3
                    Integer type = (Integer) arg;
                    // 如果broker集群不稳定, 处理mqs集合大小的变动, 用这种取模的方式来保证顺序消息是有问题的
                    // 因为如果broker集群不稳定, 会导致mqs集合大小的变动, 这样就会导致顺序消息的问题
                    // 在生产环境中, 我们可以直接new MessageQueue()来指定messageQueue的名称, 这样就可以保证顺序消息
                    // 例如: new MessageQueue("broker01", "topic02", 0)
//                    return new MessageQueue("broker-a", "topic02", 0);
                    return mqs.get(type % mqs.size());
                }
            }, i % 3);
            System.out.println(result);
        }
    }

    static class MyMessageQueueSelector implements MessageQueueSelector {

        @Override
        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
            Integer type = (Integer) arg;
            return mqs.get(type % mqs.size());
        }
    }

    @Test
    public void orderConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer02");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("topic02", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 顺序消息, 这里的MessageListener必须是MessageListenerOrderly
        // 顺序消息的消费是按照消息发送的顺序来消费的, 也就是说, 同一个type的消息会进入同一个队列, 保证顺序性
        // 消费端只能用一个线程来消费一个队列, 不能用多线程来消费同一个队列, 否则无法保证消息顺序消费
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                msgs.forEach(msg -> {
                    byte[] body = msg.getBody();
                    // 保证顺序消息的消费还需要消费端只能使用一个线程消费同一个队列, 不能用多线程来消费同一个队列
                    // 否则无法保证消息顺序消费
                    System.out.println(Thread.currentThread().getName() + "消费消息:" + new String(body));
                        }
                );
                // ConsumeOrderlyStatus只有两个值, 一个是SUCCESS, 一个是SUSPEND_CURRENT_QUEUE_A_MOMENT
                // 不能返回失败重试进行消费, 如果重试就不能保证有序性, 所有顺序消息没有重试队列
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        System.in.read();
    }
}
