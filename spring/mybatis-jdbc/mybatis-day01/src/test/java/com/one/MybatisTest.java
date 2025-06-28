package com.one;

import com.one.domain.Student;
import com.one.mapper.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    /**
     * 这种方式其实就是通过SqlSession中给我们提供的相关的API方法来执行对应的CRUD操作，
     * 查找我们写的SQL语句是通过 namespace+"."+id的方式实现的
     */
    @Test
    public void test1() throws IOException {
        // 1.加在mybatis-config.xml配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 2.构建SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 3.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4.通过sqlSession提供的api方法来操作数据库
        Student student = sqlSession.selectOne("com.one.mapper.StudentMapper.selectById",10);
        System.out.println(student);
        // 5.释放资源
        sqlSession.close();
        inputStream.close();
    }

    /**
     * 通过getMapper的方式来使用的话，我们需要添加对应的映射文件，
     * 在映射文件中我们需要将namespace声明为上面接口的全类路径名，同时对应的sql标签的id要和方法名称一致。
     */
    @Test
    public void test2() throws IOException {
        // 1.加在mybatis-config.xml配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 2.构建SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 3.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4.通过sqlSession获取我们接口对应的代理对象,通过代理对象来执行sql
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectById(10);
        System.out.println(student);
        // 5.释放资源
        sqlSession.close();
        inputStream.close();
    }
}
