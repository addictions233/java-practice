package com.one.threadbase;

/**
 * 一个线程不应该由其他线程来强制中断或者停止, 需要线程自己来停止, 所以旧版本的#stop, #suspend, #resume方法都已@Deprecated过期了
 * 但是如果我们又想让一个耗时严重的线程在某种条件下提前终止, 就需要借助中断标识位
 */
public class ThreadStopDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() ->  {
           int count = 0;
           while (true) {
               if (!Thread.currentThread().isInterrupted() && count < 1000) {
                   System.out.println("计数器count=" + ++count);
                   try {
                       Thread.sleep(1);
                   } catch (InterruptedException e) {
                       e.printStackTrace(); // 抛出异常时会重新将中断位标识置为false
                       // 需要再将中断位标识置为true, 否则外部循环不会被打破
                       Thread.currentThread().interrupt();
                   }
               } else {
                   break;
               }
           }
            System.out.println("线程stop");
        });

        t1.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 将t1的中断位标识置为true
        t1.interrupt();
    }

}
