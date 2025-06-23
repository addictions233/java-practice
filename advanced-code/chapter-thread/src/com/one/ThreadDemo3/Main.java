package com.one.ThreadDemo3;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName: Main
 * @Description: 使用阻塞队列实现生产者和消费者模式
 * @Author: one
 * @Date: 2021/07/05
 */
public class Main {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        Thread cook = new Cook(queue);
        Thread foodie = new Foodie(queue);

        cook.start();
        foodie.start();
    }
}
