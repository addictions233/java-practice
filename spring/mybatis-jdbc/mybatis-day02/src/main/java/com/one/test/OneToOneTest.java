package com.one.test;

import com.one.bean.Card;
import com.one.one2one.OneToOneMapper;
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
 * @author one
 * 测试多表查询的一对一的查询结果
 * 多表模型分类:
 *       一对一: 在任意一个表中建立外键,关联另一个表的主键  例如: person表 和 identityCard身份证表
 *       一对多: 在多的一方表中建立外键, 关联一的一方表中的主键  例如: student表 和 class班级表
 *       多对多: 借助中间表,中间表中至少有两个字段,分别关联两张表的主键 例如: student表和 course课程表
 */
public class OneToOneTest {
    private InputStream is = null;
    private SqlSession sqlSession = null;
    private OneToOneMapper mapper = null;
    @Before
    public void before(){
        try {
            //1,加载核心配置文件
            is = Resources.getResourceAsStream("mybatis-config.xml");
            //2,获取SqlSessionFactory工厂对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            //3, 获取SqlSession会话对象
            sqlSession = sqlSessionFactory.openSession(true);
            // 4, 获取代理接口的实现类对象
             mapper = sqlSession.getMapper(OneToOneMapper.class);
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
        List<Card> cards = mapper.selectAll();
        for (Card card : cards) {
            System.out.println(card);
        }

    }


}
