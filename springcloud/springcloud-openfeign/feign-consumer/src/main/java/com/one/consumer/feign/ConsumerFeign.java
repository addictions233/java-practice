package com.one.consumer.feign;

import com.one.consumer.config.FeignLogConfig;
import com.one.consumer.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Interface: ProviderFeign
 * @Description: 编写Feign的调用接口,用于调用注册中心的Provider的服务,
 *               Feign底层实现依赖动态代理,所以定义为接口,类似于mybatis的dao层接口
 *              开发过程中会创建一个feign模块,去集成所用的feign接口,然后引入到使用feign的模块
 * @Author: one
 * @Date: 2021/01/18
 */
@FeignClient(value = "feign-provider", configuration = FeignLogConfig.class)  //value属性值:获取注册中心的服务提供者Provider的名称
public interface ConsumerFeign {
    /**
     * feign声明式接口,发起远程调用
     *  String url = "http://FIGN-PROVIDER/products/findOne/"+id
     *  Product product = restTemplate.getForObject(url,Product.class)
     *  1,定义消费端的接口,只需要把生产者provider中的controller的方法拷贝过来
     *  2,在接口上添加注解 @FeignClient, 设置value属性为服务提供者Provider的应用名称
     *  3,编写调用接口中的方法, 方法的声明规则和服务提供方Provider中的方法保持一致
     *  4,注入该接口对象, 调用接口中的方法完成远程服务调用
     */
    //当前feign接口的访问方法,一般情况下我们要和Provider生产者中的请求类型,请求路径,方法参数,方法返回值类型保持一致
    @GetMapping("product/one/{id}")   // http://ip:port/goods/one/1
    Product getProductById(@PathVariable("id") Integer id);
}
