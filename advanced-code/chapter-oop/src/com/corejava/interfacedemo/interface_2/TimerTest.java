package com.corejava.interfacedemo.interface_2;


import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *  Timer类就是定时器类,传给定时器Timer类的对象是 listner , Timer类的对象timer会执行listener对象重写的actionPerformed()方法
 *  定时器类 : Timer类
 *      构造器  new Timer(int interval,ActionListener listener)
 *              第一个形参: 是一个时间间隔,单位是毫秒, 每隔多少毫秒通知一次后面的ActionListener类对象
 *              第二个参数: ActionListener类对象
 *       方法  void start()  :  启动定时器  定时器将周期性的调用监听器的actionPerformed()方法
 *             void stop()   :   停止定时器  定时器将不再调用监听器的actionPerformed()方法
 */
public class TimerTest {
    public static void main(String[] args) {
        TimePrinter listener  = new TimePrinter();

//        Timer timer = new Timer(1000,listener); // 每过1000毫秒 即一秒通知监听器
//        timer.start();

        Timer timer1 = new Timer(1000,(ActionEvent event)-> System.out.println("welcome to java world "));
        timer1.start();

        JOptionPane.showMessageDialog(null,"quit program?");
        System.exit(0);  //系统正常退出

    }
}

