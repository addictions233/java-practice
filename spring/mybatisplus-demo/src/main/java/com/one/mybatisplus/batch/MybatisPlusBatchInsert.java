package com.one.mybatisplus.batch;

import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MybatisPlusBatchInsert {

    private UserMapper userMapper;

    public MybatisPlusBatchInsert(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String batchInsert() {

        List<TbUser> userInfoList = new ArrayList<>();
        userInfoList.add(new TbUser("张三", 18));
        userInfoList.add(new TbUser("李四", 19));
        userInfoList.add(new TbUser("王五", 20));
        userMapper.insert(userInfoList);

        return "success";
    }
}
