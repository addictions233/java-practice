package com.one.ratelimiter;

import java.util.Arrays;

public class SlidingWindow {
    private final int[] window; // 窗口数组
    private final int windowSize; // 窗口大小
    private int currentIndex = 0; // 当前窗口索引
    private long lastUpdateTime = System.currentTimeMillis(); // 上次更新时间

    public SlidingWindow(int windowSize) {
        this.windowSize = windowSize;
        this.window = new int[windowSize];
    }

    public boolean allowRequest() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastUpdateTime;

        // 滑动窗口
        if (elapsedTime >= windowSize * 1000) {
            Arrays.fill(window, 0); // 重置窗口
            currentIndex = 0;
            lastUpdateTime = currentTime;
        } else {
            int steps = (int) (elapsedTime / 1000);
            for (int i = 0; i < steps; i++) {
                currentIndex = (currentIndex + 1) % windowSize;
                window[currentIndex] = 0; // 清空旧窗口
            }
            lastUpdateTime = currentTime;
        }

        // 判断是否超过阈值
        if (window[currentIndex] < 10) { // 假设阈值为 10
            window[currentIndex]++;
            return true;
        } else {
            return false;
        }
    }
}