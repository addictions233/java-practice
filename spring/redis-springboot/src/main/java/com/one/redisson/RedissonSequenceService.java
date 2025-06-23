package com.one.redisson;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 使用Redis生成分布式序列化ID
 *
 * @author one
 */
@Service
public class RedissonSequenceService {

    private final RedissonClient redissonClient;

    public static final String SEQUENCE_PREFIX = "sequence:";

    public static final int SEQUENCE_VALUE_MAX_LENGTH = 4;

    public RedissonSequenceService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 得到下一个序列号数值
     *
     * @param key              序列标识key
     * @param expire     过期时间,大于0生效
     * @param timeUnit         时间单位
     * @param existSequenceMax 获取已经存在最大的序列号（首次初始化，没有的时候会获取）
     * @return 序列号数值
     */
    public long getNextSequenceNumber(String key,
                                      Long expire,
                                      TimeUnit timeUnit,
                                      Supplier<Long> existSequenceMax) {
        String cacheKey = SEQUENCE_PREFIX + key;
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(cacheKey);
        if (rAtomicLong.get() == 0) {
            if (existSequenceMax != null) {
                Long existMaxSequence = existSequenceMax.get();
                long nextSequence = (existMaxSequence == null ? 0 : existMaxSequence) + 1;
                //cas进行首次赋值
                boolean initSuccess = rAtomicLong.compareAndSet(0, nextSequence);
                if (initSuccess) {
                    if (expire != null && expire > 0) {
                        rAtomicLong.expire(expire, timeUnit);
                    }
                    return nextSequence;
                } else {
                    //这个时候redis已经有值了，再去incrementAndGet
                    return getNextSequenceNumber(key, expire, timeUnit, existSequenceMax);
                }
            } else {
                //直接增加
                return rAtomicLong.incrementAndGet();
            }
        } else {
            //直接增加
            return rAtomicLong.incrementAndGet();
        }

    }



    public long getNextSequence(String key, Long expire, TimeUnit timeUnit, Supplier<Long> supplier) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(SEQUENCE_PREFIX + key);
        // 先判断Redis是否存在这个key, 不存在返回0
        if (atomicLong.get() == 0) {
            if (supplier != null) {
                long init = Optional.ofNullable(supplier.get()).orElse(0L);
                long next = init + 1;
                boolean flag = atomicLong.compareAndSet(0, next);
                if (flag) {
                    if (expire != null && timeUnit != null) {
                        // 为key设置过期时间
                        atomicLong.expire(expire, timeUnit);
                    }
                    return next;
                } else {
                    // CAS失败表示其他线程设置了初始化值,再次尝试就可以
                    return getNextSequence(key, expire, timeUnit, supplier);
                }
            }
        }
        // 直接获取CAS自增值
        return atomicLong.incrementAndGet();
    }


    /**
     * 得到合并key,并填充0的 序列号
     *
     * @param joinPrefixKey          拼接的前缀key
     * @param sequenceValueMaxLength 最大的长度
     * @return 序列号
     */
    public String joinPrefixKeyAndFillZero(String joinPrefixKey, Long sequenceValue,
                                             int sequenceValueMaxLength) {
        int sequenceValueLength = (sequenceValue + "").length();
        if (sequenceValueLength >= sequenceValueMaxLength) {
            return joinPrefixKey + sequenceValue;
        }
        String format = "%0" + sequenceValueMaxLength + "d";
        return joinPrefixKey + String.format(format, sequenceValue);
    }

    /**
     * 得到合并key的 序列号
     *
     * @param joinPrefixKey 拼接的前缀key
     * @return 序列号
     */
    public String joinPrefixKeyAndFillZero(String joinPrefixKey, Long sequenceValue) {
        return joinPrefixKeyAndFillZero(joinPrefixKey, sequenceValue, SEQUENCE_VALUE_MAX_LENGTH);
    }


    /**
     * 得到下一个序列号数值
     *
     * @param key              序列标识key
     * @param expire     过期时间,大于0生效
     * @param timeUnit         时间单位
     * @param existSequenceMax 获取已经存在最大的序列号（首次初始化，没有的时候会获取）
     * @return 序列号数值
     */
    public List<Long> getBatchSequenceNumber(String key,
                                            int size,
                                            Long expire,
                                            TimeUnit timeUnit,
                                            Supplier<Long> existSequenceMax) {
        String cacheKey = SEQUENCE_PREFIX + key;
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(cacheKey);
        if (rAtomicLong.get() == 0) {
            if (existSequenceMax != null) {
                Long existMaxSequence = existSequenceMax.get();
                long nextSequence = (existMaxSequence == null ? 0 : existMaxSequence) + size;
                //cas进行首次赋值
                boolean initSuccess = rAtomicLong.compareAndSet(0, nextSequence);
                if (initSuccess) {
                    if (expire != null && expire > 0) {
                        rAtomicLong.expire(expire, timeUnit);
                    }
                    return getNumbers(size, nextSequence);
                } else {
                    //这个时候redis已经有值了，再去incrementAndGet
                    return getBatchSequenceNumber(key, size, expire, timeUnit, existSequenceMax);
                }
            } else {
                //直接增加
                long nextSequence = rAtomicLong.addAndGet(size);
                return getNumbers(size, nextSequence);
            }
        } else {
            //直接增加
            long nextSequence = rAtomicLong.addAndGet(size);
            return getNumbers(size, nextSequence);
        }

    }

    private List<Long> getNumbers(int size, long nextSequence) {
        long min = nextSequence - ((long) (size - 1));
        List<Long> list = new ArrayList<>();
        list.add(min);
        for (int i = 1; i < size; ++i) {
            list.add(min + i);
        }
        return list;
    }
}
