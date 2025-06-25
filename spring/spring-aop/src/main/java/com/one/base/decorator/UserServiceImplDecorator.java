package com.one.base.decorator;

import com.one.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @ClassName: UserServiceImplDecorator
 * @Description: 装饰设计模式的案例演示: 也是对原有对象方法的一种增强
 * @Author: one
 * @Date: 2020/12/04
 */
@Component("userServiceImplDecorator")
public class UserServiceImplDecorator implements UserService {
    /**
     *  对原有的实现类进行功能增强, 或者说能够使用原有的实现类UserServiceImpl中的方法,然后还能使用自己特有的方法
     */
    private final UserService userService;    //用于接收原有的接口实现类对象 UserServiceImpl类的对象

     /**
      * @description 有参构造对成员变量进行赋值
      * @param userService 接口的实现类对象
      * @return
      */
     public UserServiceImplDecorator(UserService userService){
         this.userService = userService;
     }


    @Override
    public void save() {
        if(userService != null){
            userService.save();
        }
        System.out.println("墙上摸了白灰");
    }

    @Override
    public double sell(double price, double numb) {
        return 0;
    }
}
