package com.one.transaction.impl;

import com.one.transaction.TxService;
import org.springframework.aop.framework.AopContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author one
 * @description 解决事务在异步线程中失效的问题
 * @date 2025-4-11
 */
@Service
public class TxServiceImpl implements TxService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private DataSource dataSource;

    @Override
    @Transactional
    public void testTx() throws InterruptedException {
        // 从ThreadLocal中获取当前现场的数据库连接对象
        ConnectionHolder connectionHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);

        jdbcTemplate.execute("INSERT INTO `tb_user` (`age`, `name`) values ('张三', 23)");

        TxService txService = (TxService)AopContext.currentProxy();
        Thread thread = new Thread(() -> {
            // 将上面获取的数据库连接对象放入异步线程的ThreadLocal中
            TransactionSynchronizationManager.bindResource(dataSource, connectionHolder);
            txService.b();
        });
        thread.start();
        thread.join();
    }


    @Override
    @Transactional
    public void b() {
        jdbcTemplate.execute("INSERT INTO `tb_user` (`age`, `name`) values ('lisi', 24)");
        // 异步线程抛出异常， 两个事务都会回滚
        int i = 1 /0;
    }
}
