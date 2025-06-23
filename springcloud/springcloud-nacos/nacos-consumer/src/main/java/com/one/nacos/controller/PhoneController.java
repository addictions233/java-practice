package com.one.nacos.controller;

import com.one.nacos.pojo.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @ClassName: PhoneController
 * @Description: controller层
 * @Author: one
 * @Date: 2022/04/05
 */
@RestController
@RequestMapping("/consumer")
public class PhoneController {
    /**
     * 按照类型进行注入, 发起Restful风格的http请求的
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 使用@EnableDiscoveryClient创建的bean对象,用于获取注册中心的所有的服务对象
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 未引入Ribbon执行,使用RestTemplate进行微服务之间的调用
     * @param id 路径参数
     * @return com.one.nacos.pojo.Phone
     */
    @GetMapping("/phone/{id}")
    public Phone getPhoneInfo(@PathVariable("id") Integer id) {
        System.out.println("consumer request id:" + id);
        // 根据服务名称获取注册中心的服务对象
        List<ServiceInstance> provider = discoveryClient.getInstances("nacos-provider");
        // 判断服务对象是否存在
        if (provider == null || provider.size() == 0) {
            throw new RuntimeException("nacos-provider service is not exist");
        }
        // 获取服务对象,同一个服务名称为了保证高可用会搭建集群,所以一个服务名称可能对应多台实例
        ServiceInstance serviceInstance = provider.get(0);
        // 获取服务对应的ip和port
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String serviceId = serviceInstance.getServiceId();
        System.out.println("服务的ip:" + host + ",端口:" + port);
        // 使用restTemplate对服务进行调用
        String url = "http://" + host + ":" + port + "/provider/phone/" + id;
        Phone phone = restTemplate.getForObject(url, Phone.class);
        // 返回结果
        return phone;
    }

    /**
     * Ribbon主要有两个作用:
     *     1,简化RestTemplate的使用,直接使用Provider的服务名称替换ip和端口
     *     2,实现consumer,即客户端的负载均衡
     * @param id 路径参数
     * @return com.one.nacos.pojo.Phone
     */
    @GetMapping("/product/{id}")
    public Phone getProductInfo(@PathVariable("id") Integer id) {
        System.out.println("consumer id:" + id);
        // Ribbon简化restTemplate的服务调用,直接用服务名称
        String serviceName = "nacos-provider";
        String url = "http://" + serviceName + "/provider/phone/" + id;
        Phone phone = restTemplate.getForObject(url, Phone.class);
        return phone;
    }
}
