package com.one.sentinel.ratelimiter;

import java.util.Deque;
import java.util.LinkedList;

public class SlideWindow {

    private long windowSize;

    private int slotCount;

    private long slotSize;

    private Deque<Integer> slotQueue;

    private long lastAccessTime;

    public SlideWindow(long windowSize, int slotCount) {
        this.windowSize = windowSize;
        this.slotCount = slotCount;
        this.slotSize = windowSize / slotCount;
        this.slotQueue = new LinkedList<>();
        for (int i = 0; i < slotCount; i++) {
            slotQueue.add(0);
        }
        this.lastAccessTime = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastAccessTime;
        if (elapsedTime >= windowSize) {
            slotQueue.clear();
            for (int i = 0; i < slotCount; i++) {
                slotQueue.add(0);
            }
            lastAccessTime = currentTime;
        }
        int currentSlot = (int) ((currentTime - lastAccessTime) / slotSize);
        if (slotQueue.get(currentSlot) >= slotSize) {
            return false;
        }
        slotQueue.set(currentSlot, slotQueue.get(currentSlot) + 1);
        return true;
    }
}
