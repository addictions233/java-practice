package com.corejava.thread;

import java.util.Arrays;

/**
 *  定义一个Bank类
 */
public class Bank {
    /**
     *  成员变量 accounts数组 数组长度表示账户个数,数组元素值表示账户的金额
     */
    private final double[] accounts;

    /**
     * 构造方法, 对账户个数和账户初始化金额进行初始化
     */
    public Bank(int n, double initialBalance){
        accounts = new double[n];  // 在构造方法中对final修饰的成员变量进行初始化
        Arrays.fill(accounts,initialBalance); //对数组元素进行初始化赋值
    }

    /**
     * 定义一个transfer方法 ,功能是从 账户from 向账户to 转义指定金额
     */
    public void transfer(int from, int to, double amount){
        if(accounts[from] < amount ) return;
        accounts[from] -= amount;
        accounts[to] += amount;
        System.out.print(Thread.currentThread().getName()); //打印当前线程名字
        System.out.printf("%10.2f from %d to %d%n",amount,from,to);
    }

    /**
     * 定义一个size方法,用来获取银行账户个数
     */
    public int size(){
        return accounts.length;
    }
}
