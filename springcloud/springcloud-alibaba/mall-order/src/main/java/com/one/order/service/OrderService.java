package com.one.order.service;


import com.one.order.entity.OrderEntity;

import java.util.List;

/**
 * 
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:46:19
 */
public interface OrderService {


    List<OrderEntity> listByUserId(Integer userId);

    List<OrderEntity> listByUserIdAndCommodityCode(Integer userId,String commodityCode);
}

