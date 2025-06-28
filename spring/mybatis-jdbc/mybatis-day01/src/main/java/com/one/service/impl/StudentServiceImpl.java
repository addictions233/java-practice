package com.one.service.impl;

import com.github.pagehelper.PageHelper;
import com.one.domain.Student;
import com.one.mapper.StudentMapper;
import com.one.service.StudentService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *  实现service层的接口,调用mapper层
 */
public class StudentServiceImpl implements StudentService {
    @Override
    public List<Student> selectAll() {
        //1,加载核心配置文件
        InputStream is = StudentServiceImpl.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        //2,获取 SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        // 3,获取 SqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 4,获取StudentMapper接口的实现类对象
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        // 5, 用StudentMapper接口的实现类对象调用selectAll()方法
        List<Student> list = studentMapper.selectAll();
        try {
            is.close();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 分页查询
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return Student
     */
    @Override
    public List<Student> selectByPage(int currentPage, int pageSize) {
        InputStream is = null;
        SqlSession sqlSession = null;
        try {
            // 1, 加载核心配置文件
            is = Resources.getResourceAsStream("mybatis-config.xml");
            // 2, 获取SqlSessionFactory工厂对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            // 3, 获取SqlSession对象 参数true表示自动提交事务
            sqlSession = sqlSessionFactory.openSession(true);
            // 4, 获取StudentMapper接口的实现类对象
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            // 使用PageHelper开启分页查询
            PageHelper.startPage(currentPage, pageSize);
            // 5,执行sql
            List<Student> students = mapper.selectAll();
            // 6,关流
            is.close();
            sqlSession.close();

            System.out.println("类型：" + students.getClass());
            return students;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Student selectById(Integer id) {
        InputStream is = null;
        SqlSession sqlSession = null;
        Student student = null;
        try {
            // 1, 加载核心配置文件
            is = Resources.getResourceAsStream("mybatis-config.xml");
            // 2,创建 SqlSessionFactor工厂对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            // 3, 获取SqlSession会话对象
            sqlSession = sqlSessionFactory.openSession(true);
            // 4, 获取StudentService接口的实现类对象
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            // 5, 用StudentService接口的实现类对象调用 selectById()方法
           student = studentMapper.selectById(id);

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                is.close();
                sqlSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return student;
    }

    private InputStream is;
    private SqlSession sqlSession;
    private StudentMapper studentMapper;

    public void before(){
        // 1,读取核心配置文件
//        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        this.is = StudentServiceImpl.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        // 2, 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(this.is);
        // 3,获取SqlSession会话对象
         sqlSession = sqlSessionFactory.openSession(true);
        // 4, 获取Mapper层接口的实现类，mybatis使用接口代理的方式获取了接口实现类
         studentMapper = sqlSession.getMapper(StudentMapper.class);

    }
    public void after(){
        try{
            is.close();
            sqlSession.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(Student stu) {
        before();
        Integer i = studentMapper.insert(stu);
        after();
        return i;
    }

    @Override
    public Integer update(Student stu) {
        before();
        // 返回影响数据库中的行数
        int i = studentMapper.update(stu);
        after();
        return i;
    }

    @Override
    public Integer delete(Integer id) {
        before();
        // 返回影响数据库中的行数
        Integer i = studentMapper.delete(id);
        return i;
    }

    /**
     * 使用动态sql
     * @param stu
     * @return
     */
    @Override
    public List<Student> selectCondition(Student stu) {
        before();
        List<Student> students = studentMapper.selectCondition(stu);
        after();
        return students;
    }

    @Override
    public List<Student> selectByIds(List<Integer> ids) {
        before();
        List<Student> students = studentMapper.selectByIds(ids);
        after();
        return students;
    }

    @Test
    public void test(){
        List<Student> students = selectAll();
        for (Student student : students) {
            System.out.println(student);
        }

        Student student = selectById(1);
        System.out.println(student);
    }
}
