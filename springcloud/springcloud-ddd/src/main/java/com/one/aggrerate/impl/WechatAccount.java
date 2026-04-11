package com.one.aggrerate.impl;

import com.one.aggrerate.AggregateRoot;
import com.one.aggrerate.entity.Balance;
import com.one.aggrerate.entity.Wallet;

public class WechatAccount implements AggregateRoot<PhoneNumber> {

    private PhoneNumber phoneNumber;

    private String nickName;

    // 钱包
    private Wallet wallet;


}
