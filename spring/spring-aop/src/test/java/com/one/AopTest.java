package com.one;

import com.one.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class AopTest {

    @Test
    public void testAop() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopApp.class);
        UserService userService = applicationContext.getBean("userService",UserService.class);
        userService.save();
    }
}
