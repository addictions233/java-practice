package com.one.bf;

import redis.clients.jedis.Jedis;

public class RedisBloomFilter<T> {
    private Jedis jedis;

    public RedisBloomFilter(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            //redisTemplate.opsForValue().setBit(key, i, true);
            jedis.setbit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在: 如果布隆过滤器判断一个数据在一个集合中不存在, 那么它一定在这个集合中不存在,
     * 如果判断一个数据在一个集合中存在, 那么它可能不存在(哈希冲突导致的)
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            //if (!redisTemplate.opsForValue().getBit(key, i)) {
            if (!jedis.getbit(key, i)) {
                return false;
            }
        }
        return true;
    }
}