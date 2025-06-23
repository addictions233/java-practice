package com.one.threadsecurity;

/**
 *  线程安全问题:
 *      如果多个线程操作同一个数据,那么可能会出现线程安全问题
 *  经典案例: 火车站卖票案例
 *      火车站有100张车票,三个窗口卖票,直到一百种票都卖完为止
 */
public class MyRunnable1 implements Runnable {
    /**
     *
     */
    private int ticket = 100;
    @Override
    public void run() {
        while(true){
            if(ticket<=0){
                break;  //如果票卖光了,就停止循环
            } else{
                try {  // 子类中方法必须抛出比父类中同一方法更具体的异常
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 会出现卖了-1号票
                 * 为什么存在线程安全的问题?
                 *  因为卖票分为三步: 1, 拿到ticket值,判断是否大于0
                 *                   2, 控制台打印卖了第几张票
                 *                   3, ticket--
                 *  这三步操作是分开执行的,违背了原子性,当本线程执行了第一步或者第二步,其他线程抢占了cpu的执行资源
                 *  该线程就得等待,这样其他线程拿到的ticket的值和本线程拿到的ticket的值是一样的,这样ticket这个成员
                 *  变量就不是线程安全的了
                 */
                System.out.println(Thread.currentThread().getName()+"卖了第"+(ticket--)+"号票");
//                ticket--;
            }
        }
    }
}


