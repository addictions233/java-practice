package com.one.mybatisplus;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisPlusMain {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("config.xml");
        // mybatis plus重新的SqlSessionFactoryBuilder为MybatisSqlSessionFactoryBuilder
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 调用BaseMapper中提供的方法
            List<TbUser> tbUserList = userMapper.selectList(null);
            for (TbUser tbUser : tbUserList) {
                System.out.println(tbUser);
            }
        }

    }
}
