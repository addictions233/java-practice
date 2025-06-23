package com.one.service.impl;

import com.one.dao.UserDao;
import com.one.pojo.User;
import com.one.service.UserTransactionService1;
import com.one.service.UserTransactionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: UserTransactionService1
 * @Description: 测试Spring事务的传播行为
 * @Author: one
 * @Date: 2022/03/10
 */
@Service("userTransactionService1")
public class UserTransactionServiceImpl1 implements UserTransactionService1 {
    @Autowired
    private UserTransactionService2 userTransactionService2;

    @Autowired
    private UserDao userDao;

    /**
     * Spring事务的默认实现是使用AOP,也就是动态代理, 如果要想事务注解生效, 同一个service类中的
     * 方法相互调用要使用注入的对象来调用,不能直接使用this.方法名来调用,this.方法名调用是对象内部方法调用,
     * 不会通过Spring生成代理对象,事务也就不起作用
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void transactionMethod1() {
        User xiaoying = new User(null, "小樱", 16, null,null,null);
        // 向tb_user1表中新增一条数据
        userDao.insertUser1(xiaoying);
        // 在service1中调用service2中的方法
        userTransactionService2.transactionMethod2();
        // 抛出运行时异常
//        int i = 1 / 0;
    }
}
