package com.one.service.impl;

import com.one.dao.PersonMapper;
import com.one.dao.SchoolMapper;
import com.one.pojo.Person;
import com.one.service.PersonService;
import com.one.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author one
 * @description 如何控制多个线程的事务一致性
 * @date 2022-8-26
 */
@Service
@Slf4j
public class PersonServiceImpl implements PersonService {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private SchoolMapper schoolMapper;

    private final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(8, true);

    private ThreadPoolExecutor.CallerRunsPolicy policy = new ThreadPoolExecutor.CallerRunsPolicy();

    //1、创建核心线程为10的线程池
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 15, 10, TimeUnit.SECONDS
            , queue, policy);


    @Override
    @Transactional
    public void insertPersonBatch(List<Person> list) throws SQLException {

        //2、根据sqlSessionTemplate获取SqlSession工厂
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3、获取Connection来手动控制事务
        Connection connection = sqlSession.getConnection();
        try{
            //4、设置手动提交
            connection.setAutoCommit(false);
            //5、获取PersonMapper（此处是由于无法通过this.baseMapper调用自定义的saveBatch方法）
            PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
            //6、主线程去删除t_school表中id为1的数据
            schoolMapper.deleteById("1");
            //7、将传入List中的10000个数据按1000一组均分成10组
            List<List<Person>> lists = ListUtils.averageAssign(list,1000);
            //8、新建任务列表
            List<Callable<Integer>> callableList = new ArrayList<>();
            //9、根据均分的5组数据分别新建5个Callable任务
            for(int i = 0; i < lists.size(); i++){
                List<Person> insertList = lists.get(i);
                Callable<Integer> callable = () -> {
                    int n = 0;
                    try{
                        n = mapper.saveBatch(insertList);
                    }catch (Exception e){
                        //插入失败返回0
                        return n;
                    }
                    //插入成功返回成功提交数
                    return n;
                };
                callableList.add(callable);
            }

            //10、任务放入线程池开始执行
            List<Future<Integer>> futures = executor.invokeAll(callableList);
            //11、对比每个任务的返回值 <= 0 代表执行失败
            for(Future<Integer> future : futures){
                if(future.get() <= 0){
                    //12、只要有一组任务失败回滚整个connection
                    connection.rollback();
                    return;
                }
            }
            //13、主线程和子线程都执行成功 直接提交
            connection.commit();
            System.out.println("添加成功！");

        }catch (Exception e){
            //14、主线程报错回滚
            connection.rollback();
            log.error(e.toString());
            throw new SQLException("出现异常！");
        }
        return;
    }
}
