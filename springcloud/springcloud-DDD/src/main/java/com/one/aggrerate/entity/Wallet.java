package com.one.aggrerate.entity;

import com.one.aggrerate.Entity;

public class Wallet implements Entity<WalletNumber> {

    // 钱包编号
    private WalletNumber walletNumber;

    // 余额
    private Balance balance;
}
