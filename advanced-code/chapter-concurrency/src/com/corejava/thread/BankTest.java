package com.corejava.thread;

/**
 *  测试: 创建一个类Bank对象 ,从账户1向账户3转钱, 从账户2向账户4转钱
 */
public class BankTest {
    /**
     * 定义静态常量 STEP表示每个账户转账次数
     */
    private static final int STEP = 5;

    /**
     * 定义静态常量 MAX_AMOUT 表示每次转账的最大金额
     */
    private static final double MAX_AMOUNT = 1000;

    public static void main(String[] args) {
        //创建一个Bank类对象,4个账户,每个账户金额为100000
        Bank bank = new Bank(4,100000);
        //第一线程的任务是从账户1向账户3转钱
        Runnable run1 = ()->{
            for (int i = 0; i < STEP; i++) {
                bank.transfer(0,1,MAX_AMOUNT*Math.random());
                try {
                    Thread.sleep((int)(1000*Math.random()));
//                    System.out.println(Thread.currentThread().isInterrupted());//判断当前线程是否处在中断状态
                    Thread.interrupted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //第二个线程的任务是从账户2转账到账户4中
        Runnable run2 = ()->{
            for (int i = 0; i < STEP; i++) {
                bank.transfer(1,3,MAX_AMOUNT*Math.random());
                try{
                  Thread.sleep((int)(1000*Math.random()));
                    System.out.println(Thread.currentThread().isInterrupted()); //判断当前线程是否处在中断状态
                    Thread.interrupted();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        //创建第一个线程并启动
        new Thread(run1).start();
        //创建第二个线程并启动
        new Thread(run2).start();
    }
}
