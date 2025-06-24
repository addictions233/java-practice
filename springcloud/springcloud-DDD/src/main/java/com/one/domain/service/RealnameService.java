package com.one.domain.service;

import com.one.domain.bo.PhoneNumber;
import com.one.domain.bo.RealnameInfo;

/**
 * Domain Service: 抽象并发封装多对象的有状态逻辑
 * 外部服务查询实名信息
 */
public interface RealnameService {

    /**
     * 通过手机号获取实名认证信息
     * @param phone 手机号
     * @return DP对象: RealnameInfo
     */
    RealnameInfo getByPhone(PhoneNumber phone);
}
