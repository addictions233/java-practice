package com.one.cas;

import sun.misc.Unsafe;

/**
 * @author Fox
 */
public class CASLock {

    //加锁标记
    private volatile int state;
    private static final Unsafe UNSAFE;
    private static final long OFFSET;

    static {
        try {
            // 借助UnsafeFactory来实现CAS(compareAndSwap)
            UNSAFE = UnsafeFactory.getUnsafe();
            OFFSET = UnsafeFactory.getFieldOffset(
                    UNSAFE, CASLock.class, "state");
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * 进行CAS加锁
     * @return true表示加锁成功, false表示加锁失败
     */
    public boolean cas() {
        return UNSAFE.compareAndSwapInt(this, OFFSET, 0, 1);
    }

    /**
     * 获取sate属性值
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * 设置state属性值
     * @param state sate
     */
    public void setState(int state) {
        this.state = state;
    }

}
