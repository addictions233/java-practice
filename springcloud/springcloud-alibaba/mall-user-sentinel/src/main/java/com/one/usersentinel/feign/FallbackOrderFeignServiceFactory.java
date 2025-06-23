package com.one.usersentinel.feign;

import com.one.common.utils.R;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FallbackOrderFeignServiceFactory implements FallbackFactory<OrderFeignService> {

    /**
     * 如果是降级工厂, 传参会多一个异常信息throwable
     * @param throwable cause of an exception.
     * @return OrderFeignService
     */
    @Override
    public OrderFeignService create(Throwable throwable) {

        return new OrderFeignService() {
            @Override
            public R findOrderByUserId(Integer userId) {
                return R.error(-1,"=======服务降级了========");
            }
        };
    }
}