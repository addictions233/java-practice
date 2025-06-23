package com.one.user.feign;


import com.one.common.utils.R;
import com.one.user.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "mall-order", path = "/order", configuration = FeignConfig.class)
public interface OrderServiceFeign {

    /**
     * 基于springmvc注解来进行远程调用, 所以必须springmvc注解
     * 请求参数的注意事项:
     *   1, 在openFeign中的方法前如果没有加参数注解, 默认使用@RequestBody注解, 所以不加注解时,方法参数只能有一个
     *   2, 普通表单参数必须添加@RequestParam注解, 如果变量名和参数名对应可以不写name
     *   3, 如果有多个url参数, 可以使用@SpringQueryMap注解用实体对象的属性接收多个url参数
     */
    @RequestMapping("/findOrderByUserId/{userId}")
    R findOrderByUserId(@PathVariable("userId") Integer userId);
}
