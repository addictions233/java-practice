package com.one.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisplusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01() {
        LambdaQueryWrapper<TbUser> queryWrapper = Wrappers.lambdaQuery(TbUser.class)
                .eq(TbUser::getId, 1L);
        TbUser tbUser = userMapper.selectOne(queryWrapper);
        System.out.println(tbUser);

    }

    @Test
    public void test02() {
        TbUser user = userMapper.getById(1L);
        System.out.println(user);

    }
}
