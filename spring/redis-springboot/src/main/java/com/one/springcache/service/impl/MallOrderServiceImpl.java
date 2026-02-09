package com.one.springcache.service.impl;

import com.one.springcache.entity.MallOrder;
import com.one.springcache.service.MallOrderService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * @description: service层实现
 * @author: wanjunjie
 * @date: 2024/10/17
 */
@Service
public class MallOrderServiceImpl implements MallOrderService {


    /**
     * 更新缓存
     */
    @Override
    // 用于在方法执行后更新缓存
    @CachePut(cacheNames = "mallOrder", key = "#mallOrder.oderNo")
    public MallOrder save(MallOrder mallOrder) {
        // 模拟数据库保存mallOrder
        System.out.println("保存数据:" + mallOrder);
        return mallOrder;
    }

    /**
     * 通过缓存查询
     */
    @Override
    //先访问redis缓存，如果有则直接使用redis里的缓存，如果没有，则需要进行正常查询，并把数据存储到redis缓存中。
    @Cacheable(cacheNames = "mallOrder", key = "#orderNo")
    public MallOrder getByNo(String orderNo) {
        // 模拟数据库查询mallOrder
        return new MallOrder(-1L,"1001" ,"手机", 1, new BigDecimal("100"));
    }

    /**
     * 删除缓存
     */
    @Override
    // 标注在需要清除缓存元素的方法或者类上
    @CacheEvict(cacheNames = "mallOrder", key = "#orderNo", allEntries = false)
    public void deleteByNo(String orderNo) {

    }
}
