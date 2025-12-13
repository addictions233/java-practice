package com.one.mybatisplus;


import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MySqlInjectorTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMySelectOne() {
        TbUser tbUser = userMapper.mySelectOne();
        System.out.println(tbUser);
    }
}
