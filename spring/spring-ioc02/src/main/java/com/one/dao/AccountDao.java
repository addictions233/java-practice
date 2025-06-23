package com.one.dao;

import com.one.domain.Account;

import java.util.List;

/**
 * @InterfaceName: AccountDao
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/01
 */

public interface AccountDao {
    /**
     * @description 通过id查询
     * @param
     * @return
     */
    Account findById(Integer id);

    /**
     * @description 查询所有
     * @param
     * @return
     */
    List<Account> findAll();

    /**
     * @description 增
     * @param
     * @return
     */
    void save(Account account);

    /**
     * @description 删
     * @param
     * @return
     */
    void delete(Integer id);

    /**
     * @description 改
     * @param
     * @return
     */
    void update(Account account);
}
