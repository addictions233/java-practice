package com.one.threadmethod;
/**
 * Thread类中的成员方法:
 *      static void sleep​(long millis)
 *          使当前正在执行的线程停留（暂停执行）指定的毫秒数
 *
 *      void setPriority​(int newPriority) 更改此线程的优先级 优先级范围 1~10 默认优先级为5
 *      int getProperties() 获取线程的优先级
 */
public class SleepMethod {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <10 ; i++) {
            /**
             *调用sleep()方法是不释放锁资源的,即抱着锁休眠的,休眠时间过了之后自动执行
             * 让主线程休眠500毫秒
             */
            Thread.sleep(500);
            System.out.println("i="+i);
        }
    }
}
