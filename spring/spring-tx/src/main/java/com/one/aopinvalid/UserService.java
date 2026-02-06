package com.one.aopinvalid;

import com.one.dao.AccountDao;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements InjectSelf<UserService>{

    @Autowired
    private AccountDao accountDao;

    private UserService self;

    @Override
    public void setSelf(UserService self) {
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
