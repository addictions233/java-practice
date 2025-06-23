package com.one.user.controller;

import com.one.common.utils.R;
import com.one.user.feign.OrderServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:53:24
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private OrderServiceFeign orderServiceFeign;

    @RequestMapping(value = "/findOrderByUserId/{id}")
    public R findOrderByUserId(@PathVariable("id") Integer id) {
        log.info("根据userId:" + id + "查询订单信息");
        // 方式1：restTemplate调用,url写死
        //String url = "http://localhost:8020/order/findOrderByUserId/"+id;

//        // 方式2: 使用discoveryClient获取所有的客户端
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("mall-order");
//        // 轮询
//        ServiceInstance serviceInstance = serviceInstances.get(0);
//        String url = String.format("http://%s:%s/order/findOrderByUserId/%s",serviceInstance.getHost(),serviceInstance.getPort(),id);



        //方式3： 利用负载均衡器获取mall-order服务列表
        // ServiceInstance serviceInstance = loadBalancerClient.choose("mall-order");
        // String url = String.format("http://%s:%s/order/findOrderByUserId/%s",serviceInstance.getHost(),serviceInstance.getPort(),id);

        // 利用@LoadBalanced注解实现负载均衡，restTemplate需要添加@LoadBalanced注解
//        String url = "http://mall-order/order/findOrderByUserId/"+id;
//        R result = restTemplate.getForObject(url,R.class);


        // 使用openfeign进行远程调用
        R result = orderServiceFeign.findOrderByUserId(id);

        return result;
    }



}
