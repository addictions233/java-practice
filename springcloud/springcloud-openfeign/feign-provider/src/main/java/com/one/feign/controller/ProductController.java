package com.one.feign.controller;

import com.one.feign.pojo.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ProductController
 * @Description: feign只作用于消费者consumer, 生产者provider不用做任何修改
 * @Author: one
 * @Date: 2021/01/18
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping(value = "/one/{id}")
    public Product getProductById(@PathVariable("id") Integer count) {
        System.out.println("接收到了请求:" + count);
        // 模拟feign接口的调用时间超时
        return new Product("老北京", "方便面", 8.88, count);
    }
}
