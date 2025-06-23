package com.one.jmm;

/**
 * @author  one
 * volatile不能保证操作的原子性
 */
public class AtomicTest {

    /**
     * 即是变量使用volatile修饰, 但是不能保证操作的原子性
     * 必须使用CAS, synchronized, Lock锁等保持原子性
     */
    private volatile static int sum = 0;
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    // 获取sum值, 然后将sum+1 这两步操作不是原子的
                    // 即是使用volatile也不能保证操作的原子性
                    sum++;
                }
            });
            thread.start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(sum);

    }

}
