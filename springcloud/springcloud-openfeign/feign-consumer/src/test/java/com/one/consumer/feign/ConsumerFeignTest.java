package com.one.consumer.feign;

import com.one.consumer.pojo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerFeignTest {
    @Resource
    private ConsumerFeign consumerFeign;

    @Test
    public void testFeign() {
        Product productById = consumerFeign.getProductById(1);
        System.out.println(productById);
    }
}