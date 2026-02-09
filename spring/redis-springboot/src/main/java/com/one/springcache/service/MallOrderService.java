package com.one.springcache.service;

import com.one.springcache.entity.MallOrder;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/10/17
 */
public interface MallOrderService {

    MallOrder save(MallOrder mallOrder);

    MallOrder getByNo(String orderNo);

    void deleteByNo(String orderNo);
}
