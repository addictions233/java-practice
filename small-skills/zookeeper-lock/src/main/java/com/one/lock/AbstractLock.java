package com.one.lock;


/**
 * @Author Fox
 *
 */
public abstract class AbstractLock implements Lock{

    /**
     * 加锁，增加重试逻辑
     */
    @Override
    public void lock() {
        // 尝试获取锁
        if (tryLock()) {
            System.out.println("---------获取锁成功---------");
        } else {
            // 等待锁 阻塞
            waitLock();
            // 重试
            lock();
        }
    }


    //尝试获取锁
    public abstract boolean tryLock() ;

    //等待锁
    public abstract void waitLock() ;




}
