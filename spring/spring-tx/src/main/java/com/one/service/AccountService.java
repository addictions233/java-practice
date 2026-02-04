package com.one.service;

/**
 * @InterfaceName: AccountService
 * @Description: 转账操作的业务层接口
 * @Author: one
 * @Date: 2020/12/04
 */
public interface AccountService {

     /**
      * 转账操作
      * @param outName 出账账户
      * @param inName 入账账户
      * @param money 金额
      */
    void transferMoney(String outName, String inName, Double money);
}
