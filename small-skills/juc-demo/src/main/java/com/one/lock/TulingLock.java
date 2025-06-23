package com.one.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义AQS锁实现
 * AQS (AbstractQueuedSynchronizer): java api层面提供的一种解决多线程并发访问共享资源问题的框架
 * 区别于synchronized: jvm层面提供的解决方案, 需要进行系统调用, 从用户态切换到内核态, 有一定的开销
 */
public class TulingLock extends AbstractQueuedSynchronizer{

    @Override
    protected boolean tryAcquire(int unused) {
        //cas 加锁  state=0
        if (compareAndSetState(0, 1)) {
            // 设置独占线程
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int unused) {
        //释放锁
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    @Override
    protected boolean isHeldExclusively() {
        return true;
    }

    public void lock() {
        acquire(1);
    }

    public boolean tryLock() {
        return tryAcquire(1);
    }

    public void unlock() {
        release(1);
    }

    public boolean isLocked() {
        return isHeldExclusively();
    }


}