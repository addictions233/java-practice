package com.one.infrastructure.service;

import com.one.domain.bo.PhoneNumber;
import com.one.domain.bo.RealnameInfo;
import com.one.domain.service.RealnameService;

/**
 * 电信提供的查询实名认证信息的rpc service
 */
public class TelecomRealnameService implements RealnameService {

    /**
     * 通过手机号获取实名认证信息
     * @param phone 手机号
     * @return DP对象: RealnameInfo
     */
    @Override
    public RealnameInfo getByPhone(PhoneNumber phone) {
        // RPC 调用, 并将返回的结果封装成为RealnameInfo
        // RealnameInfo 就是 DP
        return new RealnameInfo();
    }
}
