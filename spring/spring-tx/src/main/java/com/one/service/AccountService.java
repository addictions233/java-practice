package com.one.service;

/**
 * @InterfaceName: AccountService
 * @Description: 转账操作的业务层接口
 * @Author: one
 * @Date: 2020/12/04
 */
public interface AccountService {
     /**
      * @description 转账操作
      * @param
      * @return
      */
    void transferMoney(String outName, String inName, Double money);
}
