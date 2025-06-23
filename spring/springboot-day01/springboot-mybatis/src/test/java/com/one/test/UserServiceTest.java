package com.one.test;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.one.pojo.User;
import com.one.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserServiceTest
 * @Description: 测试类
 * @Author: one
 * @Date: 2021/05/22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void findAllUser() {
        List<User> users = userService.findAllUser();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void completeFutureFindAllUser() {
        List<User> users = userService.completeFutureFindAllUser();
        users.forEach(System.out::println);
    }

    @Test
    public void saveTimeStamp() {
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
        user.setBirthday(new Date());
        user.setAge(23);
        userService.update(user);
    }

    /**
     * 什么情况下存储timestamp类型的时间,会进行时区转换?
     * @throws ParseException
     */
    @Test
    public void testTimeStamp() throws ParseException {
        User user = new User();
        user.setUsername("大猪蹄子");
        user.setAge(18);
        String birth = "2000-01-05 10:00:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setBirthday(format.parse(birth));
        userService.insert(user);
    }


    /**
     * 测试SpringBoot集成PageHelper进行分页查询
     */
    @Test
    public void findByPage() throws JsonProcessingException {
        JSONObject dataBody = new JSONObject() {{
            put("pageNumber",1);
            put("pageSize",3);
        }};
        PageInfo<User> pageInfo = userService.findByPage(dataBody);
        long total = pageInfo.getTotal();
        System.out.println("总的记录条数:" + total);
        int pages = pageInfo.getPages();
        System.out.println("总的页数:" + pages);
        // 这个list 是 Page对象,
        List<User> list = pageInfo.getList();
        //将list集合转换为json
        String json = mapper.writeValueAsString(list);
        System.out.println("json:" + json);
        System.out.println("list:" + list);
        for (User user : list) {
            System.out.println(user);
        }
    }
}
