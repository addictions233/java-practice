package com.one.service.impl;

import com.one.bean.SysUser;
import com.one.mapper.UserMapper;
import com.one.service.SysUserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 之前DAO层建立数据库连接,但是现在DAO层变成了接口,所有建立数据库连接代码转义到了service层
 */
public class SysUserServiceImpl implements SysUserService {
    private InputStream is = null;
    private SqlSession sqlSession = null;
    private  UserMapper mapper = null;
    public void before(){
        try {
            //1,加载核心配置文件mybatis-config.xml
            is = Resources.getResourceAsStream("sqlMapConfig.xml");
            // 2,获取SqlSessionFactory工厂对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            // 3,获取SqlSession会话对象,并将事务设置为自动提交
            sqlSession = sqlSessionFactory.openSession(true);
            // 4,获取mapper持久层接口的实现类对象
            mapper = sqlSession.getMapper(UserMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void after(){
        try {
            // 关流
            is.close();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<SysUser> selectAll() {
        before();
        // mapper层接口实现类调用其成员方法,本质是执行sql语句并将结果封装到映射实体类中
        List<SysUser> countries = mapper.findAll();
        after();
        return countries;
    }

    @Override
    public SysUser findById(Long id) {
        before();
        // mapper层接口调用其成员方法,本质是执行sql语句并将查询结果封装到sql表的映射实体类中
        SysUser country = mapper.findById(id);
        return country;
    }

    @Override
    public Integer insert(SysUser country) {
        return null;
    }

    @Override
    public Integer update(SysUser country) {
        return null;
    }

    @Override
    public Integer delete(Long id) {
        return null;
    }
}
