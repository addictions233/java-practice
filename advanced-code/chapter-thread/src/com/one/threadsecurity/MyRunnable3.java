package com.one.threadsecurity;
/**
 *  用同步方法解决线程安全问题, 同步方法的锁对象是this, this指代当前对象本身,哪个对象调用我,我就指代谁
 */
public class MyRunnable3 implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {
        while(true){
            // 如果线程名称为窗口一,那么用同步方法执行
            if("窗口一".equals(Thread.currentThread().getName())){
                boolean result = synchronizedMethod();
                if(!result){
                    break;
                }
            }
            //如果线程名为窗口二,那么用同步代码块执行
            if("窗口二".equals(Thread.currentThread().getName())){
                synchronized (this){
                    if(ticket == 0){
                        break;
                    } else {
                        System.out.println(Thread.currentThread().getName()+"卖了第"+(ticket--)+"号票");
                    }
                }
            }
        }
    }

    /**
     * 定义同步方法进行卖票
     * @return
     */
    private synchronized boolean synchronizedMethod() {
        if(ticket==0){
            return false;
        } else {
            System.out.println(Thread.currentThread().getName()+"卖了第"+(ticket--)+"号票");
            return true;
        }
    }
}
