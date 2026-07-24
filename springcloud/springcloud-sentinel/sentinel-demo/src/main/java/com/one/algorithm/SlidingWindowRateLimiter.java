package com.one.algorithm;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SlidingWindowRateLimiter {
    /**
     * 每个窗口的最大请求数量
     */
    private final int limit;

    /**
     * 窗口大小 (毫秒)
     */
    private final long windowSizeMillis;

    /**
     * 窗口划分的区间数
     */
    private final int slotCount;

    /**
     * 每个区间大小 (毫秒)
     */
    private final long slotSizeInMillis;

    /**
     * 滑动窗口的请求数队列
     */
    private Queue<Integer> slots;


    /**
     * 上次刷新时间
     */
    private long lastRefreshTime;

    public SlidingWindowRateLimiter(int limit, long windowSizeMillis, int slotCount) {
        this.limit = limit;
        this.windowSizeMillis = windowSizeMillis;
        this.slotCount = slotCount;
        this.slotSizeInMillis = windowSizeMillis / slotCount;
        // 使用队列实现 (左右都能进出)
        this.slots = new ConcurrentLinkedDeque<>();
        for (int i = 0; i < slotCount; i++) {
            slots.add(0);
        }
        this.lastRefreshTime = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {
        refreshSlots(); // 更新窗口内的区间计数

        // 累计当前窗口内的请求总数
        int currentWindowRequestCount = slots.stream().mapToInt(Integer::intValue).sum();

        if (currentWindowRequestCount < limit) {
            // 若未超限，则放行并记录请求
            int lastSlot = slots.poll();
            slots.add(lastSlot + 1); // 增加当前区间的请求计数
            return true;
        } else {
            // 请求超过阈值，拒绝请求
            return false;
        }
    }

    // 更新窗口区间的请求计数
    private void refreshSlots() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRefreshTime;
        // 如果不是大于等于, 要么是小于 0 (时钟回拨), 要么 大于0且小于slotSizeInMillis 落在上次刷新时间的槽位
        if (elapsedTime >= slotSizeInMillis) {
            int slotsToUpdate = (int) (elapsedTime / slotSizeInMillis);
            for (int i = 0; i < Math.min(slotsToUpdate, slotCount); i++) {
                slots.poll();
                slots.add(0); // 初始化新时间片的请求计数
            }

            // 每次筛选时间是一个精准的槽位时间点, 所以要减掉差值
            lastRefreshTime = currentTime - (elapsedTime % slotSizeInMillis);
        }
    }



    public static void main(String[] args) throws InterruptedException {
        SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(5, 1000, 10);

        // 测试连续请求
        for (int i = 0; i < 15; i++) {
            System.out.println("请求 " + (i + 1) + ": " + (limiter.allowRequest() ? "通过" : "拒绝"));
            Thread.sleep(100);
        }
    }

}
