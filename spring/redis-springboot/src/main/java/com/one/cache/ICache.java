package com.one.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface ICache {

    Object get(String cacheName, String key);

    Map<String, Serializable> getAll(String cacheName, Set<String> keys);

    void put(String cacheName, String key, Serializable value,Long expireTime, TimeUnit timeUnit);

    void putAll(String cacheName, Map<String, Serializable> cacheDat, Long expireTime, TimeUnit timeUnit);


    void removeAll(String cacheName);


    void remove(String cacheName, Set<String> keys);
}
