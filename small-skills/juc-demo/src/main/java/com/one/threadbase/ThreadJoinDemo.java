package com.one.threadbase;

public class ThreadJoinDemo {

    public static void main(String[] sure) throws InterruptedException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t begin");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t finished");
            }
        });
        long start = System.currentTimeMillis();
        t.start();
        // 主线程等待线程t执行完成, 底层调用wait()方法, 直到线程生命终止才会继续执行
        t.join();

        System.out.println("执行时间:" + (System.currentTimeMillis() - start));
        System.out.println("Main finished");
    }
}