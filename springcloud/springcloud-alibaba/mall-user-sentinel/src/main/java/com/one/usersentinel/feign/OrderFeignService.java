package com.one.usersentinel.feign;

import com.one.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 使用sentinel整合openfeign需要指定fallbackFactory降级工厂类
 * @author one
 */
@FeignClient(value = "mall-order",path = "/order", fallback = FallbackOrderFeignService.class)
//@FeignClient(value = "mall-order",path = "/order",fallbackFactory = FallbackOrderFeignServiceFactory.class)
public interface OrderFeignService {

    @RequestMapping("/findOrderByUserId/{userId}")
    public R findOrderByUserId(@PathVariable("userId") Integer userId);
}
