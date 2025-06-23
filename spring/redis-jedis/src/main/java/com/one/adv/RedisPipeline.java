package com.one.adv;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * @author one
 * @description 测试redisPipeline 对redis指令进行批量操作
 * @date 2025-3-17
 */
public class RedisPipeline {
    
    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(2);

        // timeout，这里既是连接超时又是读写超时，从Jedis 2.8开始有区分connectionTimeout和soTimeout的构造函数
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.0.60", 6380, 3000, null);
    }

    public List<Object> plGet(List<String> keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // pipeline是将所有的命令都组装成pipeline指令，然后一次性发送给redis，减少网络开销
            Pipeline pipelined = jedis.pipelined();
//        // 开启事务
//        pipelined.multi();
//        // ... 等待命令
//        // 提交事务
//        pipelined.exec();
            for (String key : keys) {
                // 对应get, set等命令在pipeline中都提供了支持
                pipelined.get(key);
            }
            // 这里只会向redis发送一次请求，返回结果是一个List<Object>，
            return pipelined.syncAndReturnAll();
        } finally {
            if (jedis != null) {
                // 将jedis连接归还连接池
                jedis.close();
            }
        }
    }

    public void plSet(Map<String, String> map) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipelined = jedis.pipelined();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                pipelined.set(entry.getKey(), entry.getValue());
            }
            pipelined.sync();
        } finally {
            if (jedis!= null) {
                // 将jedis连接归还连接池
                jedis.close();
            }
        }
    }
}
