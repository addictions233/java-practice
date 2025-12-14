package com.one.mybatisplus.wrapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
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

        // 打印sql片段
        String sqlSegment = queryWrapper.getSqlSegment();
        System.out.println(sqlSegment);
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

    public void testChainQueryWrapper() {
        // 使用ChainWrappers进行链式查询
        TbUser tbUser = ChainWrappers.queryChain(TbUser.class)
                .eq("username", "one")
                .eq("age", 1)
                .one();
        System.out.println(tbUser);
    }

    public void testChainLambdaQueryWrapper() {
        // 使用ChainWrappers进行链式查询
        TbUser tbUser = ChainWrappers.lambdaQueryChain(TbUser.class)
                .eq(TbUser::getUserName, "one")
                .eq(TbUser::getAge, 1)
                .one();
        System.out.println(tbUser);
    }

    public void testChainUpdateWrapper() {
        // 使用ChainWrappers进行链式更新
        boolean result = ChainWrappers.updateChain(TbUser.class)
                .set("username", "two")
                .set("age", 2)
                .eq("username", "one")
                .eq("age", 1)
                .update();
        System.out.println(result);
    }

    public void testChainLambdaUpdateWrapper() {
        // 使用ChainWrappers进行链式更新
        boolean result = ChainWrappers.lambdaUpdateChain(TbUser.class)
                .set(TbUser::getUserName, "two")
                .set(TbUser::getAge, 2)
                .eq(TbUser::getUserName, "one")
                .eq(TbUser::getAge, 1)
                .update();
        System.out.println(result);
    }
}
