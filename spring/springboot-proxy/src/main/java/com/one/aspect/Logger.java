package com.one.aspect;

/**
 * @ClassName: Logger
 * @Description: 测试AOP的使用
 * @Author: one
 * @Date: 2020/12/03
 */
public class Logger {
    /**
     * before类型的通知  在执行切入点方法前执行该通知
     */
    public void beforePrintLog(){
        System.out.println("前置通知Logger类中的beforePrintLog方法开始记录日志了");
    }

    /**
     *  after-returning类型的通知    切入点方法正常执行结束后执行该通知
     */
    public void afterReturningPrintLog(){
        System.out.println("后置通知Logger类中的afterReturningPrintLog方法开始记录日志了");
    }

    /**
     *  after-throwing类型通知  在切入点方法抛出异常时执行该通知
     */
    public void afterThrowingPrintLog(){
        System.out.println("异常通知Logger类中的afterThrowingPrintLog方法开始记录日志了");
    }

    /**
     *  after类型通知   不管切入点方法是否抛出异常, 都会执行该通知
     */
    public void afterPrintLog(){
        System.out.println("最终通知Logger类中的afterPringLog方法开始记录日志了");
    }
}
