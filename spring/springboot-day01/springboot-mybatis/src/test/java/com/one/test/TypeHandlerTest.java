package com.one.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.one.dao.UserDao;
import com.one.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: TypeHandlerTest
 * @Description: 测试自定义mybatis的结果映射器
 * @Author: one
 * @Date: 2022/04/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeHandlerTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void typeHandlerTest() throws JsonProcessingException {
        User user = userDao.selectById(1);
        String jsonString = JSON.toJSONString(user);
        System.out.println(jsonString);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);
    }

}
