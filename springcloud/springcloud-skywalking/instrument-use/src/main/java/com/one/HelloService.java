package com.one;

/**
 * @author one
 */

public class HelloService {
    
    
    public String say(){
        System.out.println("===hello world====");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }

    /**
     * 只有使用了自定义注解的才打印方法执行时间
     */
    @ApplyMethod
    public String say2(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }
    
}
