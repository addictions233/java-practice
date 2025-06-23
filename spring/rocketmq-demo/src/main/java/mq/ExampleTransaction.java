package mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author one
 * @description 使用事务消息的最佳实践
 * @date 2025-1-28
 */
public class ExampleTransaction {


    /**
     * 事务消息的作用： A如果执行本地事务失败， 则消息不发送给消费者B，A本地事务执行成功， 则消息发送给消费者B
     * 并不能解决B消息消费失败， A本地事务的回滚问题
     * @throws Exception
     */
    @Test
    public void transactionProducer() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 1. 先发送half半消息
        // 2. 调用#executeLocalTransaction()方法执行本地事务，如果是短事务，Broker可以直接获得事务执行结果
        // 3. Broker调用#checkLocalTransaction()方法查询本地长事务执行结果, 决定是将消息提交还是回滚
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 调用入口：producer发送half半消息之后， 再调用#executeLocalTransaction()方法执行本地事务
             * 参数传递的三种方式：
             * 1. 使用message body, 缺点: 会污染消息体, 不建议使用
             * 2. 使用arg方式， 本地参数
             * 3. 使用userProperty，1，通过网络传递给consumer
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                String transactionId = msg.getTransactionId();
                System.out.println("执行本地事务ID：" + transactionId);
                // Producer发送消息时， 要传递action值, 该方法中根据action值确定需要执行的本地事务
                String action = arg.toString(); // Producer发送消息时， 本地参数传递
//                String action = msg.getUserProperty("action");
//                String action = new String(msg.getBody());
                switch (action) {
                    case "0":
                        System.out.println(Thread.currentThread().getName() + "执行本地事务,action:" + action);
                        // 返回UNKNOW表示需要调用#checkLocalTransaction()方法查询本地事务执行结果
                        return LocalTransactionState.UNKNOW;
                    case "1":
                        System.out.println(Thread.currentThread().getName() + "执行本地事务,action:" + action);
                        try {
                            // 模拟本地事务执行时间, 看Broker会不会在此期间回查本地事务执行状态
                            // 结果： Broker并不会主动回查本地事务，只有Producer返回UNKNOW时，Broker才会回查本地事务
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // 返回COMMIT_MESSAGE表示提交事务, 不是返回UNKNOW, Broker不会回查本地事务
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case "2":
                        System.out.println(Thread.currentThread().getName() + "执行本地事务,action:" + action);
                        try {
                            // 模拟本地事务执行时间, 看Broker会不会在此期间回查本地事务执行状态
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // 返回ROLLBACK_MESSAGE表示回滚事务, Consumer消费不到这个消息
                        return LocalTransactionState.UNKNOW;
                }
                return null;
            }

            /**
             * 调用入口： Broker回调Producer， Producer的这个方法应该是无状态的， 不应该有状态
             * 回查次数和回查时间间隔和消息重试消费的模式相同 15次， 时间逐级增加
             * 这样Producer宕机重启后依然可以去数据表中根据transactionId查询本地事务执行结果
             * @param msg Check message
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                String transactionId = msg.getTransactionId();
                System.out.println("回查本地事务ID：" + transactionId);
                String action = msg.getUserProperty("action");
                switch (action) {
                    case "0":
                        System.out.println(Thread.currentThread().getName() + "回查本地事务,action:" + action);
                        try {
                            // 模拟根据transactionId查询本地事务执行结果耗时
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // 查询结果为返回COMMIT_MESSAGE表示提交事务
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case "1":
                        System.out.println(Thread.currentThread().getName() + "回查本地事务,action:" + action);
                        // 返回COMMIT_MESSAGE表示提交事务
                        return LocalTransactionState.UNKNOW;
                    case "2":
                        System.out.println(Thread.currentThread().getName() + "回查本地事务,action:" + action);
                        // 返回ROLLBACK_MESSAGE表示回滚事务, Consumer消费不到这个消息
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return null;
            }
        });

        // 设置事务Producer执行回查本地事务的线程池， 也可以不设置， 则使用默认的线程池
        producer.setExecutorService(new ThreadPoolExecutor(1, 1, 10000,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "transaction-thread");
            }
        }));

        producer.start();

        // 发送的是half半消息
        for (int i = 0; i < 9; i++) {
            Message message = new Message("transaction-topic3", "transaction-tag", ("message body:" + i + ", type" + (i % 3)).getBytes());
            message.putUserProperty("action", String.valueOf(i % 3));
            // 发送事务消息, 第二个参数就是上面的arg
            TransactionSendResult result = producer.sendMessageInTransaction(message, String.valueOf(i % 3));
            System.out.println(result);
        }
        System.in.read();
    }

    @Test
    public void transactionConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer02");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("transaction-topic3", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("消费到了消息：" + new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}
