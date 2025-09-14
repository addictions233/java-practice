package com.one.job.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * JDK提供的定时器
 */
public class TimerDemo {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        // 启动定时任务
        timer.schedule(new TimerTaskDemo(), 1000, 1000);
        Thread.sleep(10000);
        // 取消定时任务
        timer.cancel();
    }


    static class TimerTaskDemo extends TimerTask {
        @Override
        public void run() {
            System.out.println("TimerTaskDemo run");
        }
    }
}
