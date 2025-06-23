package com.one.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName: SpringbootRedisApplicationTest
 * @Description: 测试类
 * @Author: one
 * @Date: 2022/03/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {
    /**
     * RedisTemplate和StringRedisTemplate是常用的模板
     * 1, StringRedisTemplate是RedisTemplate<String, String>的子类，
     *    key和value序列化都是StringRedisSerializer，只能操作string类型
     * 2, RedisTemplate注入的时候如果使用@Autowired则不能使用泛型
     *    如果使用泛型可以使用@Resource注解进行注入（参考@Autowired和@Resource区别）
     * 3, 如果想使用@Autowired注入并使用泛型，可以重新注册bean，在config配置中重新注册redisTemplate的bean
     */
//    @Autowired
//    private RedisTemplate redisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void RedisTemplateTest() {
        // 在redis中设置值
        redisTemplate.opsForValue().set("test",10);
        int value = (int)redisTemplate.opsForValue().get("test");
        System.out.println(value);
    }

    @Test
    public void  stringRedisTemplateTest() {
        stringRedisTemplate.opsForValue().set("表演", "相声");
        String value = stringRedisTemplate.opsForValue().get("表演");
        System.out.println(value);
    }

}
