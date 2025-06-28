package com.one.test;

import com.one.bean.Classes;
import com.one.one2many.OneToManyMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 *  测试多表查询的一对多的查询结果
 */
public class OneToManyTest {
    private InputStream is = null;
    private SqlSession sqlSession = null;
    private OneToManyMapper mapper = null;
    @Before
    public void before(){
        try {
            //1,加载核心配置文件
            is = Resources.getResourceAsStream("sqlMapConfig.xml");
            //2,获取SqlSessionFactory工厂对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            //3, 获取SqlSession会话对象
            sqlSession = sqlSessionFactory.openSession(true);
            // 4, 获取代理接口的实现类对象
             mapper = sqlSession.getMapper(OneToManyMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after(){
        try{
            is.close();
            sqlSession.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void test(){
        // 5,用代理接口的实现类对象调用方法执行sql语句
        List<Classes> classes = mapper.selectAll();
        for (Classes aClass : classes) {
            System.out.println(aClass);
        }
    }


}
