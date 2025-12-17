package com.one.ratelimiter;

public class TokenBucket {
    private final int capacity; // 令牌桶容量
    private int tokens; // 当前令牌数量
    private long lastRefillTime = System.currentTimeMillis(); // 上次补充令牌时间
    private final int refillRate; // 令牌补充速率（每秒）

    public TokenBucket(int capacity, int refillRate) {
        this.capacity = capacity;
        this.tokens = capacity;
        this.refillRate = refillRate;
    }

    public synchronized boolean allowRequest() {
        refillTokens();
        if (tokens > 0) {
            tokens--;
            return true;
        } else {
            return false;
        }
    }

    private void refillTokens() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRefillTime;
        int tokensToAdd = (int) (elapsedTime * refillRate / 1000);
        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTime = currentTime;
        }
    }
}