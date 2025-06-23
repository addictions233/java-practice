package mq;


import org.apache.commons.validator.Msg;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author one
 * @description 测试Rocketmq的简单使用
 * @date 2025-1-21
 */
public class SimpleRocketMQ {

    @Test
    public void producer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        producer.setNamesrvAddr("127.0.0.1:9876");

        producer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("topicA");
            message.setTags("tagA");
            String msg = "hello mq" + i;
            message.setBody(msg.getBytes(StandardCharsets.UTF_8));
            // Broker刷盘成功之后才返回OK
            // 消息是否同步刷盘具体到message级别, 当然也可以设置到topic级别
            message.setWaitStoreMsgOK(Boolean.TRUE);

//            // 1. 同步发送消息, 等待发送结果
//            SendResult result = producer.send(message);
//            System.out.println("send result:" + result);
//
//            // 2. 异步发送消息, 发送结果直接调用回调函数
//            producer.send(message, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.println("success:" + sendResult);
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    System.out.println("exception");
//                }
//            });
//
//            // 3. 单边发送消息, 不关注发送结果
//            producer.sendOneway(message);


            // 消息发送到指定的MessageQueue 队列 . Topic + Broker + Queue 确定唯一的MessageQueue
            // Rocketmq支持将消息发送到指定的topic下某个Broker, 指定的Queue, 功能很具体, 但是有违背分布式
            MessageQueue messageQueue = new MessageQueue("topicA", "broker-a", 1);
            SendResult send = producer.send(message, messageQueue);
            System.out.println("发送结果:" + send);
        }
    }


    @Test
    public void consumerPush() throws Exception {
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("consumerGroupA");
        pushConsumer.setNamesrvAddr("127.0.0.1:9876");

        // 设置消费起点, 即设置消费起始偏移量
        pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 指定消费者需要消费的Topic和Tag
        //此时SubscriptionType为SubscriptionType.SUBSCRIBE，使用该方式后用户不需要考虑消息队列分配的问题
        pushConsumer.subscribe("topic01","*");

        // Push消费模式看做Broker主动向Consumer推消息, Consumer需要注册异步回调函数, 其实本质还是Pull 拉模式
        pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(messageExt -> {
                    String message = new String(messageExt.getBody());
                    System.out.println("消费的消息:" + message);
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者
        pushConsumer.start();

        // 由于Push是异步回调消费, 不能让线程执行结束
//        System.in.read();
    }

    @Test
    public void consumerPull() throws Exception {
        DefaultLitePullConsumer pullConsumer = new DefaultLitePullConsumer("consumerGroupB");
        pullConsumer.setNamesrvAddr("127.0.0.1:9876");

//        // 设置是否自动提交offset, 默认为true, 即自动提交offset, 每次消费完消息, 都会将offset提交到Broker
//        pullConsumer.setAutoCommit(Boolean.FALSE);

        pullConsumer.start();

        pullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        // 获取指定Topic对应的MessageQueue 消息队列
        System.out.println("----------------messageQueue---------------");
        Collection<MessageQueue> messageQueues = pullConsumer.fetchMessageQueues("topicA");
        messageQueues.forEach(System.out::println);


//        System.out.println("--------------message--------------------");
//        pullConsumer.assign(messageQueues);
//        List<MessageExt> messageExts = pullConsumer.poll();
//        messageExts.forEach(messageExt -> {
//            System.out.println("消费消息:" + new String(messageExt.getBody()));
//        });

        // 测试pullConsumer只消费一个MessageQueue 消息队列上的消息
        MessageQueue messageQueue = new MessageQueue("topicA", "broker-a", 1);
        //指定consumer消费的消息队列，使用该模式后消息队列不会自动重平衡，
        // 此时SubscriptionType为SubscriptionType.ASSIGN, 区别于SubscriptionType.SUBSCRIBE
        pullConsumer.assign(Collections.singleton(messageQueue));
        //修改consumer拉取消息的偏移量
        pullConsumer.seek(messageQueue, 0);
        List<MessageExt> messageExts1 = pullConsumer.poll();
        messageExts1.forEach(msg -> {
            System.out.println("消费消息:" + new String(msg.getBody()));
        });

        try {
            while (true) {
                //消息拉取
                List<MessageExt> messageExts = pullConsumer.poll();
                System.out.printf("%s %n", messageExts);
                // 由于前面调用setAutoCommit方法将自动提交位点属性设置为false，
                // 所以这里调用commitSync将消费位点提交到内存中的offsetstore，最终会通过定时任务将消费位点提交给broker
                pullConsumer.commitSync();
                Thread.sleep(100);
            }
        } finally {
            pullConsumer.shutdown();
        }

    }


    @Test
    public void admin() throws Exception {
        // Admin可以获取所有的Topic, 路由等信息, 是Console控制台实现的基础
        DefaultMQAdminExt admin = new DefaultMQAdminExt();
        admin.setNamesrvAddr("127.0.0.1:9876");
        admin.start();

        // 获取所有的Topic信息
        TopicList topicList = admin.fetchAllTopicList();
        Set<String> set = topicList.getTopicList();
        for (String topic : set) {
            System.out.println(topic);
        }

        // 获取Topic对应的路由信息, 都是保存在NameServer中
        System.out.println("-----------------topic route | info----------------------");
        TopicRouteData topicRouteData = admin.examineTopicRouteInfo("broker-a");
        System.out.println(topicRouteData);

        // 获取集群信息
//        System.out.println("-----------------cluster info----------------------");
//        ClusterInfo clusterInfo = admin.examineBrokerClusterInfo();
//        HashMap<String, BrokerData> brokerAddrTable = clusterInfo.getBrokerAddrTable();
//        Set<Map.Entry<String, BrokerData>> entries = brokerAddrTable.entrySet();
//        Iterator<Map.Entry<String, BrokerData>> iterator = entries.iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, BrokerData> next = iterator.next();
//            System.out.println(next.getKey() + ":" + next.getValue() );
//        }
    }
}
