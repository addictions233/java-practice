package com.one.propagation;

import com.one.aopinvalid.InjectSelf;
import com.one.dao.AccountDao;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronizationUtils;

@Service
public class UserService {

    @Autowired
    private AccountDao accountDao;

    @Transactional(propagation = Propagation.REQUIRED) // 默认的传播机制: 如果当前有事务使用当前的,如果没有新建一个事务
    public void transferMoney() {
        accountDao.inMoney("jack", 100.00);
        this.outMoney(); // 方法内部调用, 不是代理对象调用, 所以不会走事务代理逻辑

        UserService self = (UserService) AopContext.currentProxy();
        self.outMoney();

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW) // 如果存在事务, 将当前事务挂起, 新建一个事务, 两个事务不影响, 如果不存在事务,新建一个事务
//    @Transactional(propagation = Propagation.NEVER) //以非事务执行, 如果有事务会抛出异常
//    @Transactional(propagation = Propagation.NESTED) // 事务回滚到 savepoint, 可以实现事务的部分提交和部分回滚
    public void outMoney() {
        // 使用事务同步管理器注册事务的生命周期的钩子函数, 即注册事务同步器
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void beforeCommit(boolean readOnly) {
                System.out.println("事务管理器打印事务提交前的日志消息");
            }

            @Override
            public void suspend() {
                System.out.println("事务管理器打印事务挂起的日志消息");
            }
        });

        // 手动触发事务同步器
        TransactionSynchronizationUtils.triggerBeforeCommit(false);

        accountDao.outMoney("rose", 200.00);
    }


}
