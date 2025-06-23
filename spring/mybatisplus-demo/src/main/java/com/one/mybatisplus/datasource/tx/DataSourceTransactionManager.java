package com.one.mybatisplus.datasource.tx;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author one
 * @description 多数据源的事务管理
 * @date 2025-4-2
 */
public class DataSourceTransactionManager {

    @Transactional(transactionManager = "transactionManager1")
    @DSTransactional
    public void insert() {

    }
}
