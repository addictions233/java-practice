package com.one.mapper.impl;

import com.one.bean.Student;
import com.one.mapper.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Mybatis有两种开发方式: 第一种: 传统开发方式, 自己手写Mapper层接口的实现类
 *                       第二种: 基于接口代理的方式开发(底层依赖动态代理,自动生成接口的代理实现类)
 * mapper层或者Dao层   Mybatis传统开发方式必须自己手动实现Dao层或者Mapper层接口的实现类
 * @author  one
 */
public class StudentMapperImpl implements StudentMapper {
    private InputStream is = null;
    private SqlSession sqlSession = null;

    /**
     * 初始化, 获取sqlSession这个SQL语句的执行者对象
     */
    public void before() {
        //1,通过类加载器加载核心配置文件 sqlMapConfig.xml
        // is = Resources.getResourceAsStream("sqlMapConfig.xml"); // mybatis中提供的加载核心配置文件的api
        is = StudentMapperImpl.class.getClassLoader().getResourceAsStream("sqlMapConfig.xml");
        //2,获取sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        // 3,通过SqlSessionFactory工厂类对象开启sqlSession, sqlSession才是执行sql语句的功能对象
        // 参数true表示sql事务自动提交, 参数false表示手动提交事务
        sqlSession = sqlSessionFactory.openSession(true);
    }

    /**
     * 当执行完sql语句之后,要进行关流
     */
    public void after(){
        // 关流
        try {
            is.close();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有学生列表
     * @return
     */
    @Override
    public List<Student> selectAll() {
        before();
        List<Student> list = sqlSession.selectList("selectAll");
        after();
        return list;
    }


    /**
     * 通过id查询单个学生
     * @param id
     * @return
     */
    @Override
    public Student selectById(Integer id) {
        before();
        /**
         *  sqlSession对象是通过加载核心配置文件和映射配置文件生成的sql会话对象,里面保存了所有要执行的sql语句,以及
         *  需要封装的实体类,所以用 sqlSession对象调用方法执行sql语句只用在参数位置给定sql语句在映射配置文件中的id
         *  即可调用到正确的sql语句并封装查询结果
         *  传统开发方式和接口代理开发方式执行sql语句的过程是不一样的
         *  一,传统开发方式
         *      Student student = sqlSession.selectOne("selectById", id); //mapper层接口及其实现类和映射配置文件没有任何关系
         *  二,接口代理开发方式:
         *      StudentMapper mapper = sqlSession.getMapper(StudentMapper.class); //获取mapper层接口的实现类对象
         *      Student student = mapper.selectById(); //执行sql语句  此时mapper层接口的全限定类名必须和映射配置文件的namespace属性
         *      值一致,否则mapper层接口的实现类对象调用其方法怎么知道是执行哪条sql语句呢??
         */
        Student student = sqlSession.selectOne("selectById", id);
        after();
        return student;
    }

    /**
     * 向数据库中插入学生对象
     * @param stu
     * @return
     */
    @Override
    public Integer insert(Student stu) {
        before();
        //用sqlSession对象执行sql语句  namespace.id确定唯一的sql语句
        int i = sqlSession.insert("insert", stu);
        after();
        return i;
    }

    /**
     * 修改学生对象
     * @param stu
     * @return
     */
    @Override
    public Integer update(Student stu) {
        // 返回值i表示修改的行数
        int i = sqlSession.update("update", stu);
        return i;
    }

    /**
     * 删除学生对象
     * @param id
     * @return
     */
    @Override
    public Integer delete(Integer id) {
        // 返回值表示修改的行数
        return sqlSession.delete("deleteById",id);
    }
}
