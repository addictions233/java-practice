package com.one.jedis;

import redis.clients.jedis.Jedis;

public class JedisDemo01 {
    public static void main(String[] args) {
        //1,创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //2,执行jedis语句命令
        jedis.set("name","张三");
        String name = jedis.get("name");
        System.out.println("name:"+name);
        //3, 关闭jedis对象
        jedis.close();
    }
}
