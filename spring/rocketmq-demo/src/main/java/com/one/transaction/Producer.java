package com.one.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: Producer
 * @Description: 事务消息: 保证了 producer执行本地事务  和 broker接收消息 的事务一致性
 *               测试事务消息: 可以用来实现分布式事务, 事务消息的三种状态:
 *                  1, 提交状态 COMMIT, 正常的发消息, 正常的消费消息
 *                  2, 回滚状态 ROLLBACK, broker删除消息, 消息不被消费
 *                  3, 未知状态 UNKNOWN, broker会执行事务补偿过程,让producer确认事务消息的状态
 * @Author: one
 * @Date: 2020/12/19
 */
public class Producer {
    public static void main(String[] args) throws Exception {
//        testCommit();
        testCheck();
    }


    /**
     * 测试本地事务的提交状态
     */
    private static void testCommit() throws MQClientException, UnsupportedEncodingException {
        //1,创建一个发送消息的对象producer
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        //2设定发送消息的命名服务器
        producer.setNamesrvAddr("124.220.21.120:9876");
        //设置本地事务对应监听
        producer.setTransactionListener(new TransactionListener() {
            // 正常事务执行的过程
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //返回事务的提交COMMIT状态
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            // 补偿事务执行的过程
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                return null;
            }
        });
        //3,启动发送消息的服务
        producer.start();
        //4,创建要发送的消息对象
        Message msg = new Message("topicX","事务消息:hello! rocketmq".getBytes(StandardCharsets.UTF_8));
        //5,发送消息
        TransactionSendResult sendResult = producer.sendMessageInTransaction(msg, null);
        System.out.println("发送消息返回的结果:"+ sendResult);
        //6,关闭资源,即发送消息的对象
        producer.shutdown();
    }


    /**
     * 测试本地事务返回未知状态(UNKNOW), 进入事务补偿过程
     */
    private static void testCheck() throws MQClientException {
        //1,创建一个发送消息的对象producer
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        //2设定发送消息的命名服务器
        producer.setNamesrvAddr("1124.220.21.120:9876");
        //设置本地事务对应监听
        producer.setTransactionListener(new TransactionListener() {
            // 正常事务执行的过程
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                // 本地事务正常提交了, broker正常推送消息到consumer
//                return LocalTransactionState.COMMIT_MESSAGE;
                // 返回本地事务的回滚状态, broker会丢掉消息, 消息不消费
//                return LocalTransactionState.ROLLBACK_MESSAGE;
                // 返回本地事务的未知状态就会进入补偿过程, broker会主动查询本地事务状态
                return  LocalTransactionState.UNKNOW;

            }
            // 补偿事务执行的过程
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("事务补偿过程执行了!");
                // 补偿过程返回本地事务正常提交, broker会推送消息到consumer
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        //3,启动发送消息的服务
        producer.start();
        //4,创建要发送的消息对象
        Message msg = new Message("topicY","事务消息:hello! rocketmq".getBytes(StandardCharsets.UTF_8));
        //5,发送消息
        TransactionSendResult sendResult = producer.sendMessageInTransaction(msg, null);
        System.out.println("发送消息返回的结果:"+ sendResult);
        //6,关闭资源,即发送消息的对象
        // 事务补偿过程必须保障服务器在运行中,否则broker查询不到本地事务状态, 将无法进行正常事务补偿
//       producer.shutdown();
    }
}
