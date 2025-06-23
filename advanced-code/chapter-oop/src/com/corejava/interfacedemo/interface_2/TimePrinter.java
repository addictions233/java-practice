package com.corejava.interfacedemo.interface_2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

/**
 *  向定时器类(Timer类)的对象传入某个类的对象,然后定时器对象就会周期性的调用该对传入象的方法
 *      该逻辑存在一个问题: 定时器对象该执行传入对象的哪个方法呢?
 *          所以传入给定时器的对象必须实现ActionListener接口并重写该接口的actionPerformed()方法
 *                public interface ActionListener {
 *                   // 定时器对象会周期性的调用 actionPerformed()方法
 *                   void actionPerformed(ActionEvent event);
 *                   // 参数 ActionEvent event 会提供事件的相关信息,例如 event.getWhen()就会返回调用该事件的时间
 *                }
 *          等同于一个数组元素对象必须实现Comparable接口并重写该接口中的compareTo()方法,才能给 sort()方法调用进行排序
 */
public class TimePrinter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        // 该方法体中写需要定期执行的行为
        System.out.println("at that tone,the time is "+
                //静态方法 Instant.ofEpochMilli()方法将 event.getWhen()方法返回的毫秒值转换为一个可读时间点
                Instant.ofEpochMilli(event.getWhen()));
        Toolkit.getDefaultToolkit().beep();  // 这条语句是为了出响铃声音   单词 beep : 哔哔声


    }
}
