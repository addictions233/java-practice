package com.one.gateway.controller;

import com.one.gateway.pojo.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ProviderController
 * @Description: 服务提供者
 * @Author: one
 * @Date: 2021/01/18
 */
@RestController
@RequestMapping("product")
public class ProviderController {

    @GetMapping("one/{id}")
    public Product getProductById(@PathVariable("id") Integer id){
        return new Product(id,"华为旗舰手机",9999.99,"你值得拥有");
    }
}
