package com.one.ThreadDemo1;

/**
 * @author one
 * 测试类: 使用等待唤醒机制实现生产者和消费者模型
 */
public class Main {
    public static void main(String[] args) {
        new Cook().start();
        new Eater().start();
    }
}
