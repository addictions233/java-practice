package com.one.mybatisplus.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testWrapper() {
        QueryWrapper<TbUser> queryWrapper = Wrappers.query(TbUser.class);
        queryWrapper.eq("id", 1);
        queryWrapper.eq("user_name", "zhangsan");

        // 打印最后封装的查询条件
        System.out.println(queryWrapper.getSqlSegment());

        TbUser tbUser = userMapper.selectOne(queryWrapper);
        System.out.println(tbUser);
    }
}
