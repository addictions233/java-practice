package com.one.jmm;


/**
 * volatile能使用内存屏障, 而内存屏障能禁止重排序, 保证有序性, 所以volatile能保证有序性
 */
public class ReOrderTest {

    private static  int x = 0, y = 0;
    private static  int a = 0, b = 0;
//    private volatile static  int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i=0;
        while (true) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            /**
             *  x,y的可能结果: 00 , 10, 01, 11
             *  结果出现00就说明存在指令重排序: 将 a = 1 和 x = b 互换顺序, 导致 x = 0
             */
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    shortWait(20000);
                    a = 1; // volatile写
                    //  StoreLoad 使用内存屏障可以防止指令重排序
                    // UnsafeFactory.getUnsafe().storeFence();
                    x = b; // volatile读
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    //  StoreLoad 使用内存屏障可以防止指令重排序
                    // UnsafeFactory.getUnsafe().storeFence();
                    y = a;
                }
            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();

            System.out.println("第" + i + "次（" + x + "," + y + ")");
            if (x==0&&y==0){
                break;
            }
        }
    }

    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }

}
