package com.one.test;

import com.one.service.UserTransactionService1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: UserTransactionServiceTest
 * @Description: TODO
 * @Author: one
 * @Date: 2022/03/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTransactionServiceTest {

    @Autowired
    private UserTransactionService1 userTransactionService1;

    @Test
    public void transactionTest() {
        // 调用测试方法, 测试Spring的事务控制
        userTransactionService1.transactionMethod1();

    }
}
