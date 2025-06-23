package com.com.springbootmvc.controller;

import com.com.springbootmvc.entity.Order;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @author one
 * @description mvc的controller层
 * @date 2023-3-14
 */
@RestController
@RequestMapping("/webController")
public class WebController {

    @GetMapping("/queryOrder")
    public Order getOrder(@RequestParam("id") Long id) {
        Order order = new Order();
        BigDecimal unitPrice = new BigDecimal(100);
        order.setId(id)
                .setNumber(10)
                .setUnitPrice(unitPrice)
                .setProductName("钢笔")
                .setTotalAmount(unitPrice.multiply(new BigDecimal(10)));
        return order;
    }

    /**
     * 使用@RequestPart可以接收文件的同时,接收json,前提是必须使用自定义的返回值处理
     * 否则抛出异常:org.springframework.web.HttpMediaTypeNotSupportedException: Content type 'application/octet-stream' not supported
     * 未将原始json格式的数据转换为http能够识别的字符串流
     * 自定义返回值处理器, 比较简单的做法是直接继承 AbstractJackson2HttpMessageConverter, 实现json数据到http字符串流的转换
     *
     * @param file 文件
     * @param order  json
     * @return String
     */
    @PostMapping("/fileUpload")
    public String fileUpload(@RequestPart("file") MultipartFile file, @RequestPart("order") Order order) {
        System.out.println(file.getOriginalFilename());
        System.out.println(order);
        return "success";
    }
}
