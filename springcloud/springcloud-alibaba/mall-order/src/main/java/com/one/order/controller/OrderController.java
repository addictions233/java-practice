package com.one.order.controller;


import com.one.common.dto.OrderDTO;
import com.one.common.utils.R;
import com.one.order.entity.OrderEntity;
import com.one.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:46:19
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据用户id查询订单信息
     * @param userId
     * @return
     */
    @RequestMapping("/findOrderByUserId/{userId}")
    public R findOrderByUserId(@PathVariable("userId") Integer userId) {

        //模拟异常
        if(userId == 5){
            throw new IllegalArgumentException("非法参数异常");
        }
        //模拟超时
        if(userId == 6){
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("根据userId:"+userId+"查询订单信息");
        List<OrderEntity> orderEntities = orderService.listByUserId(userId);
        return R.ok().put("orders", orderEntities);
    }


    @RequestMapping(value = "/orderInfo")
    public R  findOrder(@RequestParam Integer userId,@RequestParam String commodityCode) {

        List<OrderEntity> orderEntities = orderService.listByUserIdAndCommodityCode(userId,commodityCode);
        return R.ok().put("orders", orderEntities);
    }

    @RequestMapping(value = "/findOrderByUserIdAndCode")
    public R  findOrderByUserIdAndCode(@RequestBody OrderDTO orderDTO) {
        List<OrderEntity> orderEntities = orderService.listByUserIdAndCommodityCode(orderDTO.getUserId(), orderDTO.getCommodityCode());
        return R.ok().put("orders", orderEntities);
    }

}
