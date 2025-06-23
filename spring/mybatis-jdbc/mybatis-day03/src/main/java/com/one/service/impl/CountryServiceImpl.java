package com.one.service.impl;

import com.one.bean.Country;
import com.one.mapper.CountryMapper;
import com.one.service.CountryService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author one
 * 之前DAO层建立数据库连接,但是现在DAO层变成了接口,所有建立数据库连接代码转义到了service层
 */
public class CountryServiceImpl implements CountryService {
    private InputStream inputStream = null;
    private SqlSession sqlSession = null;
    /**
     * 最终获取的是动态代理对象
     */
    private  CountryMapper mapper = null;
    public void before(){
        try {
            //1,加载核心配置文件mybatis-config.xml
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            // 2,获取SqlSessionFactory工厂对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 3,获取SqlSession会话对象,并将事务设置为自动提交
            sqlSession = sqlSessionFactory.openSession(true);
            // 4,获取mapper持久层接口的实现类对象,mapper是动态代理生成的代理对象
            mapper = sqlSession.getMapper(CountryMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void after(){
        try {
            // 关流
            inputStream.close();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Country> selectAll() {
        before();
        // 这里的mapper对象是mybatis生成的动态代理对象,mapper.selectAll()方法本质上是sqlSession.selectList()
        // 而sqlSession.selectList()方法内部又是用执行器executor.query()
        List<Country> countries = mapper.selectAll();
        after();
        return countries;
    }

    @Override
    public Country selectById(Integer id) {
        before();
        // mapper层接口调用其成员方法,本质是执行sql语句并将查询结果封装到sql表的映射实体类中
        Country country = mapper.selectById(id);
        return country;
    }

    @Override
    public Integer insert(Country country) {
        return null;
    }

    @Override
    public Integer update(Country country) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
