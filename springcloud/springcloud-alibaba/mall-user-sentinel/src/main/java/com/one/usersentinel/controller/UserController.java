package com.one.usersentinel.controller;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.one.common.utils.R;
import com.one.usersentinel.entity.UserEntity;
import com.one.usersentinel.feign.OrderFeignService;
import com.one.usersentinel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;


/**
 * 测试sentinel整合到spring cloud alibaba之后的使用方法
 * @author one
 * @date 2021-01-28 15:53:24
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 测试sentinel和restTemplate进行整合
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试sentinel和openFeign进行整合
     */
    @Autowired
    OrderFeignService orderFeignService;


    /**
     * 所有的mvc接口方法sentinel会自动进行埋点, 在控制台配置流控规则就会生效
     * 而非mvc接口则需要使用@SentinelResource注解进行埋点
     */
    @RequestMapping("/info/{id}")
    //@SentinelResource(value = "userinfo", blockHandler = "handleException")
    public R info(@PathVariable("id") Integer id){
        UserEntity user = userService.getById(id);
        if(id==4){
            throw new IllegalArgumentException("异常参数");
        }
        return R.ok().put("user", user);
    }

    @RequestMapping(value = "/findOrderByUserId/{id}")
    //@SentinelResource(value = "findOrderByUserId",blockHandler = "handleException")
    public R  findOrderByUserId(@PathVariable("id") Integer id) {

//        try {
//            // 模拟测试并发线程数限流
//            Thread.sleep(900);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        
        //feign调用
        R result = orderFeignService.findOrderByUserId(id);

        return result;
    }

    public R handleException(@PathVariable("id") Integer id,BlockException exception){
        return R.error(-1,"===被限流降级啦===");
    }

    public R fallback(@PathVariable("id") Integer id,Throwable e){
        return R.error(-1,"===被熔断降级啦==="+e.getMessage());
    }


    /**
     * 用于测试  restTemplate整合sentinel
     * @param id
     * @return
     */
    @RequestMapping(value = "/findOrderByUserId2/{id}")
    public R  findOrderByUserId2(@PathVariable("id") Integer id) {
        // 调用orderService服务, 当
        // 利用@LoadBalanced，restTemplate需要添加@LoadBalanced注解
        String url = "http://mall-order/order/findOrderByUserId/"+id;
        R result = restTemplate.getForObject(url,R.class);
        return result;
    }


}
