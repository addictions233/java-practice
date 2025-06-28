package com.one;

import com.one.domain.Student;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * 用mybatis框架实现dao层数据处理,
 * 测试MyBatis框架只需要数据表对应的实体类,数据库表,核心配置文件,映射配置文件,
 * 本测试类就可以简单的执行MyBatis框架了
 *
 * @author one
 */
public class SqlSessionTest {

    InputStream is;
    SqlSessionFactory sqlSessionFactory;
    SqlSession sqlSession;

    @Before
    public void before() {
        //1.通过类加载器加载核心配置文件
        is = SqlSessionTest.class.getClassLoader().getResourceAsStream("mybatis-config.xml");

        //2.获取SqlSessionFactory工厂对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        //3.通过SqlSessionFactory工厂对象获取SqlSession对象
        /*
            没写参数则参数默认为false,得手动提交事务,
            查询不用提交事务
            但是增,删,改必须调用commit()方法sql语句才能生效
         */
        //sqlSession = sqlSessionFactory.openSession();
        //参数为true, 即为自动提交事务,还是隐式开启事务
        sqlSession = sqlSessionFactory.openSession(true);
    }

    @After
    public void test() throws Exception {
        //6.释放资源
        sqlSession.close();
        is.close();
    }


    /**
     * 查询全部: 直接使用sqlSession接口提供的方法
     */
    @Test
    public void selectAll() throws Exception {
        //4.执行映射配置文件中的sql语句，并接收结果
        List<Student> list = sqlSession.selectList("selectAll");
        //5.处理结果
        for (Student stu : list) {
            System.out.println(stu);
        }
    }


    /**
     * 根据id来查询
     */
    @Test
    public void selectById() throws Exception {
        //4.执行映射配置文件中的sql语句，并接收结果
        Student student = sqlSession.selectOne("selectById", 3);
        //5.处理结果
        System.out.println(student);
    }

    @Test
    public void insert() throws Exception {
        Student stu = new Student(null, "马七", 27,null);
        // i表示影响的行数
        int i = sqlSession.insert("insert", stu);
        System.out.println("修改了:" + i);
        // 获取插入数据的自增id
        Integer id = stu.getId();
        System.out.println("生成的自增ID:" + id);
    }

    @Test
    public void update() throws Exception {
        System.out.println("修改前:");
        Student student = sqlSession.selectOne("selectById", 5);
        System.out.println(student);
        Student stu = new Student(5, "王大锤", 88, null);
        int i = sqlSession.update("update", stu);
        System.out.println("修改了:" + i);
        System.out.println("修改后:");
        Student newStudent = sqlSession.selectOne("selectById", 5);
        System.out.println(newStudent);
    }

    @Test
    public void delete() {
        int i = sqlSession.delete("deleteById", 11);
        System.out.println("修改了:" + i);
    }
}
