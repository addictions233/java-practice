package com.one.service.impl;

import com.one.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserServiceImpl
 * @Description: 学习使用spring的aop
 * @Author: one
 * @Date: 2020/12/02
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public void save() {
        System.out.println("user service is running");
//        throw new RuntimeException("模拟异常");
    }

    @Override
    public double sell(double price,double numb){
        double sum = price*numb;
        return sum;
    }

}
