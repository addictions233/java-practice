package com.one.gateway.controller;

import com.one.gateway.feign.ProviderFeign;
import com.one.gateway.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ConsumerController
 * @Description: TODO
 * @Author: one
 * @Date: 2021/01/18
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ProviderFeign providerFeign;

    @GetMapping("/one/{id}")
    public Product consume(@PathVariable("id") Integer id){
        return providerFeign.getProduct(id);
    }
}
