package com.one.springcache.impl;

import com.one.springcache.entity.MallOrder;
import com.one.springcache.service.MallOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MallOrderServiceImplTest {

    @Resource
    private MallOrderService mallOrderService;

    @Test
    public void save() {
        mallOrderService.save(new MallOrder(1L,"001","电脑",1, new BigDecimal("1000")));
    }

    @Test
    public void getByNo() {
        System.out.println(mallOrderService.getByNo("001"));
    }

    @Test
    public void deleteByNo() {
        mallOrderService.deleteByNo("001");
    }
}