package com.one.ThreadDemo2;

/**
 * @ClassName: Main
 * @Description: 测试等待唤醒机制
 * @Author: one
 * @Date: 2021/07/05
 */
public class Main {
    public static void main(String[] args) {
        BaoZi baoZi = new BaoZi();
        Thread cook = new Cook("厨师",baoZi);
        Thread foodie = new Foodie("吃货",baoZi);

        //开启线程
        cook.start();
        foodie.start();
    }
}
