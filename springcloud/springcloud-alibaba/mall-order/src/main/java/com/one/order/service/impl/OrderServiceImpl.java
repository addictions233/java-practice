package com.one.order.service.impl;


import com.one.order.dao.OrderDao;
import com.one.order.entity.OrderEntity;
import com.one.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("orderService")
public class OrderServiceImpl  implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<OrderEntity> listByUserId(Integer userId) {
        return orderDao.listByUserId(userId);
    }

    @Override
    public List<OrderEntity> listByUserIdAndCommodityCode(Integer userId, String commodityCode) {
        return orderDao.listByUserIdAndCommodityCode(userId,commodityCode);
    }


}