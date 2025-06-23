package com.one.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.one.dao.UserDao;
import com.one.pojo.ConditionQuery;
import com.one.service.UserService;
import com.one.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/15
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 使用异步线程同时查询三个表中的数据, 使用countDownLatch让主线程等待所有异步线程执行结束
     * @return List<User>
     */
    @Override
    public List<User> findAllUser() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        List<User> userList = new CopyOnWriteArrayList<>();
        // 第一中写法:使用三个线程执行三条sql查询，将结果合道 userList集合中
//        taskExecutor.execute(new QueryUserTask(userList, countDownLatch, () ->  userDao.getUser()));
//        taskExecutor.execute(new QueryUserTask(userList, countDownLatch, () ->  userDao.getUser1()));
//        taskExecutor.execute(new QueryUserTask(userList, countDownLatch, () ->  userDao.getUser2()));
        // 第二种写法: 不需要返回结果,直接使用 execute()方法,不要使用 submit()方法
        taskExecutor.execute(() -> {
            userList.addAll(userDao.getUser());
            countDownLatch.countDown();
        });

        taskExecutor.execute(() -> {
            userList.addAll(userDao.getUser1());
            countDownLatch.countDown();
        });

        taskExecutor.execute(() -> {
            userList.addAll(userDao.getUser2());
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("countDownLatch throw exception:", e);
        }
        return userList;
    }

    /**
     * 第三种写法: 使用CompleteFuture完成多线程并发执行
     * @return List<User>
     */
    @Override
    public List<User> completeFutureFindAllUser() {
        List<User> userList = new CopyOnWriteArrayList<>();
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            userList.addAll(userDao.getUser());
        }, taskExecutor);
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            userList.addAll(userDao.getUser1());
        }, taskExecutor);
        CompletableFuture<Void> completableFuture2 = CompletableFuture.runAsync(() -> {
            userList.addAll(userDao.getUser2());
        }, taskExecutor);
        // join()方法所在的线程会等待,调用join()方法的异步线程执行结束
        CompletableFuture.allOf(completableFuture,completableFuture1,completableFuture2).join();
        return userList;
    }

    /**
     * 带条件的分页查询, 条件查询的入参格式
     * {
     *   "multiFilters": [
     *     {
     *       "type": "search",
     *       "columnKey": "testContent",
     *       "filters": "aaa"
     *     },
     *     {
     *       "type": "enumerate",
     *       "columnKey": "singleName",
     *       "filters": [
     *         "aaa",
     *         "bbb",
     *         "ccc"
     *       ]
     *     }
     *   ]
     * }
     *
     * @param dataBody 当前页面
     * @return PageInfo
     */
    @Override
    public PageInfo<User> findByPage(JSONObject dataBody) {
        Integer pageNumber = dataBody.getInteger("pageNumber");
        Integer pageSize = dataBody.getInteger("pageSize");
        JSONArray multiFilters = dataBody.getJSONArray("multiFilters");
        Page<User> page = PageHelper.startPage(pageNumber, pageSize);
        System.out.println(page);
        List<User> users = userDao.findAll(); // 这里返回结果是多态, 其实返回的是page对象
        System.out.println(users);
        // 这里判断查询方法的返回结果: page和users对象是否为同一对象
        // 从控制台打印的结果可以看出: users对象和page对象为同一对象, 多态父类指向的子类对象都是page对象
        System.out.println("page==users?" + (users == page));
        // 这里没法直接用对象的地址值,因为GC算法会改变对象的存储地址, 只能用equals()方法
        System.out.println("=====" + page.equals(users) + "=======");
        PageInfo<User> userPageInfo = new PageInfo<>(users); // PageInfo对象是pageHelper分页插件提供的对象,封装分页查询的返回结果
        return userPageInfo;
    }

    /**
     * 将前端入参 multiFilters对象封装成 ConditionQuery对象
     * @param multiFilters 前端多条件查询入参
     * @return List<ConditionQuery> 转换后的ConditionQuery对集合
     */
    private List<ConditionQuery> conditionQueries (JSONArray multiFilters) {
        return null;
    }

    @Override
    public User selectById(Integer id) {
        return userDao.selectById(id);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }
}
