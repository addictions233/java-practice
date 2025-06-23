package com.one.service.impl;

import com.one.dao.UserDao;
import com.one.pojo.User;
import com.one.service.UserTransactionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserTransactionServiceImpl2
 * @Description: 测试spring事务的传播行为
 * @Author: one
 * @Date: 2022/03/10
 */
@Service("userTransactionService2")
public class UserTransactionServiceImpl2 implements UserTransactionService2 {
    @Autowired
    private UserDao userDao;

    /**
     * REQUIRED:如果当前没有事务,就自己新建一个事务,如果当前存在事务,则加入这个事务(默认的事务传播类型)
     * SUPPORTS:如果当前存在事务，则加入当前事务，如果当前没有事务，就以非事务方法执行
     * MANDATORY:如果当前存在事务,就加入该事务, 如果当前没有事务,就抛出异常 (IllegalTransactionStateException)
     * REQUIRES_NEW: 创建一个新事务,如果当前事务存在,就挂起(外层事务挂起,内部事务新申请一个资源,所以外层事务回滚时，不影响内层事务)
     * NOT_SUPPORTED: 始终以非事务方式执行,如果当前存在事务，则挂起当前事务
     * NEVER: 不使用事务,如果当前事务存在,就抛出异常(IllegalTransactionStateException)
     * NESTED: 如果当前事务存在，则在嵌套事务中执行，否则REQUIRED的操作一样（自己新建一个事务）
     */
//    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    @Override
    public void transactionMethod2() {
        User qiaofeng = new User(null, "乔峰", 32, null,null,null);
        // 向tb_user2表中插入一条数据
        userDao.insertUser2(qiaofeng);
        // 抛出运行时异常, Spring声明式事务默认对运行时异常进行回滚,对检查时异常不进行回滚
        // 可以通过配置: @Transactional(rollbackFor=Exception.class) 对检查时异常也回滚
        int i = 1/0;
        // 向tb_user2表中插入一条数据
        User yangguo = new User(null, "杨过", 22, null, null,null);
        userDao.insertUser2(yangguo);

    }

    /**
     * NESTED
     * 如果当前事务存在，则在嵌套事务中执行，否则REQUIRED的操作一样（开启一个事务）
     *
     * 这里需要注意两点：
     *
     * 一,和REQUIRES_NEW的区别
     * REQUIRES_NEW是新建一个事务并且新开启的这个事务与原有事务无关，而NESTED则是当前存在事务时（我们把当前事务称之为父事务）会开启一个嵌套事务（称之为一个子事务）。
     * 在NESTED情况下父事务回滚时，子事务也会回滚，而在REQUIRES_NEW情况下，原有事务回滚，不会影响新开启的事务。
     *
     * 二,和REQUIRED的区别
     * REQUIRED情况下，调用方存在事务时，则被调用方和调用方使用同一事务，那么被调用方出现异常时，由于共用一个事务，所以无论调用方是否catch其异常，事务都会回滚
     * 而在NESTED情况下，被调用方发生异常时，调用方可以catch其异常，这样只有子事务回滚，父事务不受影响
     */
}
