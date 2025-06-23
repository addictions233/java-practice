package com.one.mybatisplus.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.one.mybatisplus.entity.TbUser;
import com.one.mybatisplus.mapper.UserMapper;
import com.one.mybatisplus.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author roy
 * @date 2022/3/17
 * @desc 使用DynamicDataSource框架，通过@DS注解快速切换数据源
 */

@Controller
@RequestMapping("/DS")
public class UserController {
    @Resource
    UserMapper userMapper;

    @Resource
    UserService userService;

    @Resource
    DynamicRoutingDataSource dataSource;

    @ResponseBody
    @RequestMapping("/queryCourse")
    public Object queryOrder(){

//        return courseMapper.selectList(null);
        // 不指定数据源，默认读取read库
        return userService.selectUser();
        // 通过DynamicDataSourceContextHolder切换数据源
//        Object res;
//        try {
//            DynamicDataSourceContextHolder.push("write");
//            res = courseService.selectCourse();
//            return res;
//        } finally {
//            DynamicDataSourceContextHolder.poll();
//        }
    }

    @ResponseBody
    @RequestMapping("/sessionTest")
    public Object sessionTest(@RequestParam(value = "dsKey",defaultValue = "read") String dsKey, HttpServletRequest request){
//        return courseMapper.selectList(null);
        request.getSession().setAttribute("rw",dsKey);
        return userService.selectUser();
    }

    @ResponseBody
    @RequestMapping("/createCourse")
    public String createCourse(TbUser user){
//        courseMapper.insert(course);
        userService.createUser(user);
        return "SUCCESS BY DS";
    }

    @ResponseBody
    @RequestMapping("/addDB")
    public Object addDB(){
        DruidDataSource tmpdb = new DruidDataSource();
        tmpdb.setUsername("root");
        tmpdb.setPassword("root");
        tmpdb.setUrl("jdbc:mysql://localhost:3306/coursedb2?serverTimezone=UTC");
        tmpdb.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.addDataSource("test",tmpdb);
        return "DB added";
    }

    @GetMapping("/customPage")
    public IPage<TbUser> customPage(@RequestParam(defaultValue = "1") int current,
                                     @RequestParam(defaultValue = "10") int size,
                                     Integer state) {
        Page<TbUser> page = new Page<>(current, size);
        IPage<TbUser> userPage = userMapper.selectPageByCustom(page, state);
        return userPage;
    }

}
