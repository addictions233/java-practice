package com.one.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
//        IPage<TbUser> page = new Page<>(1, 10);
        // 如果传入-1表示查询全部
        IPage<TbUser> page = new Page<>(-1, -1);
        IPage<TbUser> tbUser = userMapper.selectPageByCustom(page);
        System.out.println(tbUser);

    }

    @Test
    public void test03() {
        TbUser tbUser = new TbUser();
        tbUser.setId(1L);
        TbUser user = userMapper.getByUser(1L, tbUser);
        System.out.println(user);
    }
}
