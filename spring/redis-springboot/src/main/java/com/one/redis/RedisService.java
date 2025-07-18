package com.one.redis;

import org.springframework.data.redis.core.BoundSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author wanjunjie
 **/
public interface RedisService<T> {

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    void setCacheObject(final String key, final T value);

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit);
    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    boolean expire(final String key, final long timeout);

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    boolean expire(final String key, final long timeout, final TimeUnit unit);

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    T getCacheObject(final String key);

    /**
     * 删除单个对象
     *
     * @param key
     */
    boolean deleteObject(final String key);

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    Long deleteObject(final Collection<String> collection);

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    long setCacheList(final String key, final List<T> dataList);

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    List<T> getCacheList(final String key);

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet);

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    Set<T> getCacheSet(final String key);

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    void setCacheMap(final String key, final Map<String, T> dataMap);
    /**
     * 缓存Map
     *
     * @param key      缓存的键值
     * @param dataMap    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    boolean setCacheMap(final String key, final Map<String, T> dataMap, final Long timeout, final TimeUnit timeUnit);
    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    Map<String, T> getCacheMap(final String key);

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    void setCacheMapValue(final String key, final String hKey, final T value);
    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    T getCacheMapValue(final String key, final String hKey);

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    List<T> getMultiCacheMapValue(final String key, final Collection<String> hKeys);

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    Collection<String> keys(final String pattern);


    String get(String key);

    boolean hasKey(String key);

    /**
     * 获取过期时间
     * @param key key
     * @return Long
     */
    Long getExpire(String key);


//    操作zSet  Sorted Set的命令

    void zSetAdd(String key, T value, Double score);

    void zSetRemove(String key, T value);

    Long zSetRank(String key, T value);

    Long zSetReverseRank(String key, T value);

    Double score(String key, T value);

    Set<T> reverseRange(String key, int count);
}
