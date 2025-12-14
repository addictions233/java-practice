package com.one.mybatisplus.wrapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWrapper {

    @Autowired
    private UserMapper userMapper;

    public void testQueryWrapper(){
        QueryWrapper<TbUser> queryWrapper = Wrappers.<TbUser>query()
                .eq("username", "one")
                .eq("age", 1);
        userMapper.selectList(queryWrapper);
    }

    public void testLambdaQueryWrapper(){
        LambdaQueryWrapper<TbUser> lambdaQueryWrapper = Wrappers.<TbUser>lambdaQuery()
                .eq(TbUser::getUserName, "one")
                .eq(TbUser::getAge, 1);
        userMapper.selectList(lambdaQueryWrapper);
    }

    public void testUpdateWrapper(){
        UpdateWrapper<TbUser> updateWrapper = Wrappers.<TbUser>update()
                .set("username", "two")
                .set("age", 2)
                .eq("username", "one")
                .eq("age", 1);
        userMapper.update(null, updateWrapper);
    }

    public void testLambdaUpdateWrapper(){
        LambdaUpdateWrapper<TbUser> lambdaUpdateWrapper = Wrappers.<TbUser>lambdaUpdate()
                .set(TbUser::getUserName, "two")
                .set(TbUser::getAge, 2)
                .eq(TbUser::getUserName, "one")
                .eq(TbUser::getAge, 1);
        userMapper.update(null, lambdaUpdateWrapper);
    }
}
