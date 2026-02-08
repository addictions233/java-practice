package com.one.aopinvalid;

import com.one.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务失效的几种场景:
 *  1. 事务是基于AOP实现的, 如果AOP不生效, 事务肯定不会生效
 *  2. 平台事务管理器使用的dataSource和mybatis执行sql使用的dataSource不是同一个, 也会事务管理失效
 *  3. 多数据源操作的情况下, 使用@Transactional注解会报错, 可以使用mybatis-plus提供的@DSTransactional进行多数据源事务管理
 */
@Service
public class AccountService implements InjectSelf<AccountService>{

    @Autowired
    private AccountDao accountDao;

    private AccountService self;

    @Override
    public void setSelf(AccountService self) {
        this.self = self;
    }

    @Transactional
    public void transferMoney() {
        accountDao.inMoney("jack", 100.00);
        this.outMoney(); // 方法内部调用, 不是代理对象调用, 所以不会走事务代理逻辑

//        UserService self = (UserService) AopContext.currentProxy();
//        self.outMoney();

        self.outMoney();
    }


    @Transactional(propagation = Propagation.NEVER) // 有事务会报错
    public void outMoney() {
        accountDao.outMoney("rose", 200.00);
    }


}
