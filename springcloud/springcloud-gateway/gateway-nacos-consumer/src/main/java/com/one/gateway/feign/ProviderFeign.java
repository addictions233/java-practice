package com.one.gateway.feign;

import com.one.gateway.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName: ProviderFeign
 * @Description: TODO
 * @Author: one
 * @Date: 2021/01/30
 */
@Component
@FeignClient("nacos-provider")
public interface ProviderFeign {

    @GetMapping("/product/one/{id}")
    public Product getProduct(@PathVariable("id") Integer id);
}
