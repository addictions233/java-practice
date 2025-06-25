package com.one.interfaceidempotence.controller;

import com.one.interfaceidempotence.annotation.ApildempotentAnn;
import com.one.interfaceidempotence.entity.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: ApiController
 * @Description: 接口的幂等性
 *                  幂等性: 幂等是指多次操作产生的影响只会跟一次执行的结果相同, 通俗的说: 某个行为重复的执行,
 *                  最终获取的结果是相同的, 不会因为重复执行对系统造成变化
 * 场景:  1. 用户重复下订单：当用户下单时，因为网络问题或者手速过快，导致重复下单。
 *       2. 消息重复消费：当使用 MQ 消息中间件时候，如果消息中间件发生异常出现错误未及时提交消费信息，导致消息被重复消费。
 *       3. 抽奖活动(券)：当用户参加抽奖活动需要消耗抽奖券时，如果出现并发请求导致抽奖券余额更新错误。
 *       4. 重复提交表单：当用户填写表单提交时，可能会因为用户点多次连击提交或者网络波动导致服务端未及时响应，会导致用户重复的提交表单，就出现了同一个表单多次请求。
 * 基于token机制实现接口的幂等性:
 *       1. 客户端发送请求，得去服务端获取一个全局唯一的一串随机字符串作为Token 令牌(每次请求获取到的都是一个全新的令牌)，
 *          把令牌保存到 redis 中,需要有过期时间，同时把这个 ID 返回给客户端
 *       2. 客户端第二次调用业务请求的时候必须携带这个 token，服务端会去校验redis中是否有该token。如果存在，表示这是第一次请求，
 *          删除缓存中的token(这边还是建议用lua脚本，保证操作的原子性)；如果缓存中不存在，表示重复请求，直接返回。
 * @Author: one
 * @Date: 2021/03/24
 */
@RestController
@RequestMapping
public class ApiController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 利用原子类创建一个变量 number =100
     */
    private final AtomicInteger number = new AtomicInteger(100);

    /**
     * 获取token令牌的接口
     *      测试接口幂等性的完成案例:  支付请求请求一次或者多次请求, 最终获取的结果都是一致的
     *      1, 获取token接口
     *      2, 对num=100进行修改的接口,在接口中对其进行自减操作, num--
     *      3, 查看num的值的接口 num应该等于99,才算接口的幂等性实现成功
     */
    @GetMapping("/token")
    public String getToken(){
        // 采用UUID生成token
        String token = UUID.randomUUID().toString().substring(1,9);
        // 将token存储在redis中,以便进行幂等性接口校验
//        this.redisTemplate.opsForValue().set(token,"1"); //两种方式设置值
        this.redisTemplate.boundValueOps(token).set("1");
        return token;
    }



    /**
     * Token 令牌如何实现幂等性
     *  所谓的 token 令牌其实就是为了防止用户重复提交一个表单信息，服务端需要生成一个全局唯一的 id，
     *  （例如：snowflake 雪花算法，美团 Leaf 算法，滴滴 TinyID 算法，百度 Uidgenerator 算法，uuid，redis 等）。
     *
     *  客户端每次进入表单页面可以优先申请一个唯一令牌存储本地，服务端存储令牌 token 值(redis,文件，memcache 都可)
     *  每次发送请求时可以在 Headers 头部中带上当前这个 token 令牌
     *  服务端验证 token 是否存在，存在则删除 token，执行后续业务逻辑；不存在则响应客户端重复提交提示语
     */
    @GetMapping("/decrement")
    @ApildempotentAnn // 在需要幂等性的接口上加入自定义注解
    public String decrementNumber(){
        this.number.decrementAndGet();
        return "success";
    }


    /**
     * 唯一主键索引实现幂等性: 使用唯一索引防止重复生成记录
     *      通常情况下，我们在做这种用户活动抽奖券记录数据时，会先 select 下看看是否已经有插入的记录了，如果已存在则 update，否则 insert。
     *      那么我现在先说说不存在添加数据的情况：
     *
     *      由于存在 userid+act_id 唯一键，那么就会出现只有一条数据插入成功，其他的数据就会插入失败，保证了数据的幂等。推荐使用
     */
    @PostMapping("/decrement2")
    public String decrementNumber2(@RequestBody UserActivity userActivity){
        String sql = "insert into raffle_test ('userid', 'act_id', 'lottery') values (?, ?, 1);";
        try {
            int count = jdbcTemplate.update(sql, userActivity.getUserId(), userActivity.getActivityId());
            if (count == 1) { // 插入了数据,表示可以进行更新操作
                this.number.decrementAndGet();
                return "success";
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return "fail";
    }


    /**
     *
     * 使用乐观锁实现接口的幂等性: 不推荐使用乐观锁实现接口的幂等性, 一样乐观锁是防止并发修改的,用来实现幂等性有点牵强
     *
     * 一般使用版本号控制 version，即为数据增加一个版本标识，一般是通过为数据库表行数据增加一个数字类型的“version”字段来实现。
     * 当读取数据时，会将 version 字段的值一同读出，数据每更新一次，对此 version 值加 1 操作。
     * 当我们提交更新的时候，判断数据库表对应记录的当前版本信息与第一次取出来的 version 值进行比对，
     * 如果数据库表当前版本号与第一次取出来的 version 值相等，则予以更新，否则认为是非法操作
     * @param userActivity 用户获取活动优惠券的入参
     * @return
     */
    @PostMapping("/decrement3")
    public String decrementNumber3(@RequestBody UserActivity userActivity){
        // 1.获取抽奖券：
        String getLotterySql = "update raffle_test set lottery = lottery + 1, version = version + 1 where id = ?  and version = ?;";
        // 2.消耗抽奖券
        String costLotterySql = "update raffle_test set lottery = lottery - 1, version = version + 1 where id = ? and version = ?;";
        int count = 0;
        try {
            // 更新数据的同时 version+1，然后判断本次 update 操作的影响行数，如果大于 0，则说明本次更新成功，如果等于 0，则说明本次更新没有让数据变更。
            // 当并发请求过来时，只需要拿到 select 的版本号，进行更新操作即可(where 可带上主键 id)，保证幂等。推荐使用
            count = jdbcTemplate.update(getLotterySql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,userActivity.getId());
                    ps.setInt(3, userActivity.getVersion());
                }
            });
            if (count > 0) {
                this.number.decrementAndGet();
                return "success";
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    /**
     * 获取number的值的接口
     * @return Integer
     */
    @GetMapping("/get")
    public Integer getNumber(){
        return this.number.get();
    }
}
