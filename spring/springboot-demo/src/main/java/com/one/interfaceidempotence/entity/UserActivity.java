package com.one.interfaceidempotence.entity;

import lombok.Data;

/**
 * @author one
 * @description 用户获取活动优惠券的入参DTO
 * @date 2022-11-20
 */
@Data
public class UserActivity {

    /**
     * 记录id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 版本号: 用来实现乐观锁
     */
    private Integer version;
}
