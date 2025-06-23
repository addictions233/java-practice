package com.corejava.innerclass.innerclass1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

public class TalkingClock {
    private int interval;
    private boolean beep;
    //构造方法
    public TalkingClock(int interval, boolean beep){
        this.interval = interval;
        this.beep = beep;
    }

    /**
     *  定义一个start()方法,启动定时器,并执行 actionPerformed()方法中的内容
     */
    public void start(){
        Timer timer = new Timer(interval,new TimePrinter());
        timer.start();
    }


    // 内部类
    public class TimePrinter implements ActionListener{
        /**
         *  实现监听器接口,必须重写其中的 actionPerformed()方法
         *      内部类中可以直接使用外部类的 beep 和 interval成员
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("this tone is "+ Instant.ofEpochMilli(e.getWhen()));
            if(TalkingClock.this.beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}
