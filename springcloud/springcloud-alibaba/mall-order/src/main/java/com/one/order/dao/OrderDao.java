package com.one.order.dao;

import com.one.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 
 * 
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:46:19
 */
@Mapper
public interface OrderDao {

    @Select("select * from t_order where user_id=#{userId}")
    List<OrderEntity> listByUserId(Integer userId);

    @Select("select * from t_order where user_id=#{userId} and commodity_code=#{commodityCode}")
    List<OrderEntity> listByUserIdAndCommodityCode(Integer userId, String commodityCode);
}
