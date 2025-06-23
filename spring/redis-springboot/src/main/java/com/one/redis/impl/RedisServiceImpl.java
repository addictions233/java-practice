package com.one.redis.impl;

import com.one.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 实现类
 *
 * @author wanjunjie
 * @date 2023/6/13
 */
@Service
public class RedisServiceImpl<T> implements RedisService<T> {

	@Resource
	public RedisTemplate<String, T> redisTemplate;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 缓存基本的对象
	 *
	 * @param key   缓存的键值
	 * @param value 缓存的值
	 */
	@Override
	public void setCacheObject(final String key, final T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 *
	 * @param key      缓存的键值
	 * @param value    缓存的值
	 * @param timeout  时间
	 * @param timeUnit 时间颗粒度
	 */
	@Override
	public void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	/**
	 * 设置有效时间
	 *
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @return true=设置成功；false=设置失败
	 */
	@Override
	public boolean expire(final String key, final long timeout) {
		return expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置有效时间
	 *
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @param unit    时间单位
	 * @return true=设置成功；false=设置失败
	 */
	@Override
	public boolean expire(final String key, final long timeout, final TimeUnit unit) {
		return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
	}

	/**
	 * 获得缓存的基本对象。
	 *
	 * @param key 缓存键值
	 * @return 缓存键值对应的数据
	 */
	@Override
	public T getCacheObject(final String key) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}

	/**
	 * 删除单个对象
	 *
	 * @param key 缓存键值
	 * @return boolean 是否删除成功
	 */
	@Override
	public boolean deleteObject(final String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

	/**
	 * 删除集合对象
	 *
	 * @param collection 多个对象
	 * @return Long 删除条数
	 */
	@Override
	public Long deleteObject(final Collection<String> collection) {
		return redisTemplate.delete(collection);
	}

	/**
	 * 缓存List数据
	 *
	 * @param key      缓存的键值
	 * @param dataList 待缓存的List数据
	 * @return 缓存的对象
	 */
	@Override
	public long setCacheList(final String key, final List<T> dataList) {
		Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
		return count == null ? 0 : count;
	}

	/**
	 * 获得缓存的list对象
	 *
	 * @param key 缓存的键值
	 * @return 缓存键值对应的数据
	 */
	@Override
	public List<T> getCacheList(final String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 缓存Set
	 *
	 * @param key     缓存键值
	 * @param dataSet 缓存的数据
	 * @return 缓存数据的对象
	 */
	@Override
	public BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
		BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
		for (T t : dataSet) {
			setOperation.add(t);
		}
		return setOperation;
	}

	/**
	 * 获得缓存的set
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Set<T> getCacheSet(final String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 缓存Map
	 *
	 * @param key
	 * @param dataMap
	 */
	@Override
	public void setCacheMap(final String key, final Map<String, T> dataMap) {
		if (dataMap != null) {
			redisTemplate.opsForHash().putAll(key, dataMap);
		}
	}
	/**
	 * 缓存Map
	 *
	 * @param key      缓存的键值
	 * @param dataMap    缓存的值
	 * @param timeout  时间
	 * @param timeUnit 时间颗粒度
	 */
	@Override
	public boolean setCacheMap(final String key, final Map<String, T> dataMap, final Long timeout, final TimeUnit timeUnit) {
		try {
			if (dataMap != null) {
				redisTemplate.opsForHash().putAll(key, dataMap);
				redisTemplate.expire(key,timeout, timeUnit);

			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获得缓存的Map
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Map<String, T> getCacheMap(final String key) {
		BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
		return boundHashOperations.entries();

	}

	/**
	 * 往Hash中存入数据
	 *
	 * @param key   Redis键
	 * @param hKey  Hash键
	 * @param value 值
	 */
	@Override
	public void setCacheMapValue(final String key, final String hKey, final T value) {
		redisTemplate.opsForHash().put(key, hKey, value);
	}

	/**
	 * 获取Hash中的数据
	 *
	 * @param key  Redis键
	 * @param hKey Hash键
	 * @return Hash中的对象
	 */
	@Override
	public T getCacheMapValue(final String key, final String hKey) {
		if(StringUtils.isBlank(hKey)){
			return null;
		}
		HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
		return opsForHash.get(key, hKey);
	}

	/**
	 * 获取多个Hash中的数据
	 *
	 * @param key   Redis键
	 * @param hKeys Hash键集合
	 * @return Hash对象集合
	 */
	@Override
	public List<T> getMultiCacheMapValue(final String key, final Collection<String> hKeys) {
		BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
		return boundHashOperations.multiGet(hKeys);
	}

	/**
	 * 获得缓存的基本对象列表
	 *
	 * @param pattern 字符串前缀
	 * @return 对象列表
	 */
	@Override
	public Collection<String> keys(final String pattern) {
		return redisTemplate.keys(pattern);
	}

	@Override
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	@Override
	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	/**
	 * 获取过期时间
	 * @param key key
	 * @return 过期时间
	 */
	@Override
	public Long getExpire(String key){
		return redisTemplate.getExpire(key);
	}

	@Override
	public void zSetAdd(String key, T value, Double score) {
		redisTemplate.opsForZSet().add(key, value, score);
	}

	@Override
	public void zSetRemove(String key, T value) {
		redisTemplate.opsForZSet().remove(key, value);
	}

	@Override
	public Long zSetRank(String key, T value) {
		return redisTemplate.opsForZSet().rank(key, value);
	}

	@Override
	public Long zSetReverseRank(String key, T value) {
		return redisTemplate.opsForZSet().reverseRank(key, value);
	}

	@Override
	public Double score(String key, T value) {
		return redisTemplate.opsForZSet().score(key, value);
	}

	@Override
	public Set<T> reverseRange(String key, int count) {
		return redisTemplate.opsForZSet().reverseRange(key, 0, count - 1);
	}


}
