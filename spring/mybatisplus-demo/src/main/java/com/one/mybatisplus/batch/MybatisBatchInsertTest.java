package com.one.mybatisplus.batch;

import com.one.mybatisplus.AppApplication;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试mybatis的批量操作
 */
public class MybatisBatchInsertTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppApplication.class);
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        // 开启批量操作
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insert(new TbUser("张三", 18));
            userMapper.insert(new TbUser("李四", 19));
            userMapper.insert(new TbUser("王五", 20));
            // 上面的三条语句会被批量处理, 只有当调用flushStatements()方法时, 才会向数据库发送sql语句
            sqlSession.flushStatements(); // 刷新缓存, 提交到数据库
            // 手动提交
            sqlSession.commit();
        }
    }
}
