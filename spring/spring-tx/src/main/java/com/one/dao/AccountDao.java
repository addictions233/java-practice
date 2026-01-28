package com.one.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author one
 */
@Mapper
public interface AccountDao {
    /**
     * 入账操作
     * @param name      入账用户名
     * @param money     入账金额
     */
    void inMoney(@Param("name") String name, @Param("money") Double money);

    /**
     * 出账操作
     * @param name      出账用户名
     * @param money     出账金额
     */
    void outMoney(@Param("name") String name, @Param("money") Double money);

}


