package com.one.domain.service;

import com.one.domain.dp.PhoneNumber;
import com.one.domain.entity.User;

/**
 * Domain Service: 抽象并发封装多对象的有状态逻辑
 */
public interface RegistrationService {

    /**
     * 根据用户手机号注册用户
     * @param name 姓名
     * @param phone 手机号
     * @return 用户
     */
    User register(String name, PhoneNumber phone);

    /**
     * 根据用户手机号注册用户 复杂版本
     * @param name 姓名
     * @param phone 手机号
     * @return 用户
     */
    User registerV2(String name, PhoneNumber phone);
}
