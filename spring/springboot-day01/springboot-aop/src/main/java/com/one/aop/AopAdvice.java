package com.one.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CheckUserAspect
 * @Description: 自定义的一个AOP通知类
 * @Aspect: 声明该类为一个AOP通知类
 * @component: AOP通知类交给Spring容器进行管理
 * @Author: one
 * @Date: 2021/04/02
 */
@Aspect  //表名该类是一个通知类
@Component
public class AopAdvice {
    /**
     * 使用注解替换切点表达式
     * 连接点Joinpoint:指所有的方法,允许进行通知的地方,Spring只支持方法连接点,而AspectJ还可以在构造器和属性注入时进行通知
     * 切入点Pointcut:指进行了通知的方法,就是对target目标对象的连接点进行了通知之后的地方就是切入点
     * 带有通知(advice)的连接点(Joinpoint) 就是 切入点(Pointcut)
     * 可以使用注解的形式定义切入点,我们要对用@annotation注解标记的连接点进行通知
     */
    @Pointcut("@annotation(com.one.annotation.AdminOnly)")
    public void checkAdmin() {

    }

    /**
     * 切点表达式(execution表达式):
     * 1, 第一部分: 访问修饰符pattern  public protected ...  可以省略
     * 2, 第二部分: 返回值类型 必须写  * 代表任意返回值类型的方法
     * 3, 全限定包名: com.one.controller.* 表示controller包下的任意类
     * 4, 方法名(参数) 必须写 com.one.controller.*.* 表示该类中的任意方法
     * 5, 方法抛出的异常 pattern 可以省略
     */
    @Pointcut("execution(* com.one.controller.*.*(..))")
    public void afterCheckAdmin() {

    }

    /**
     * 定义具体的通知advice: 包含通知类型和通知方法
     * 通知advice的五种类型: 前置通知,后置通知,方法正常返回后通知,方法抛异常通知,环绕通知
     */
    @Before("checkAdmin()") // 指定通知类型   执行顺序: 第一,  第二是执行连接点Joinpoint方法
    public void before(JoinPoint joinPoint) { // 指通知方法
        System.out.println("前置通知通知执行了....");
        // 前置通知可以拿到切入点方法的入参,也能对入参进行修改
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    /**
     * 正常返回后通知
     */
    @AfterReturning("checkAdmin()")  // 执行顺序: 第三
    public void afterReturn() {
        System.out.println("正常返回后通知执行了...");
    }

    /**
     * 异常返回时通知
     */
    @AfterThrowing("checkAdmin()")  // 执行顺序: 第三 (与上面有且只有一个执行)
    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("异常返回后通知执行了.....");
    }

    /**
     * 后置通知, 也叫最终通知, 类似finally代码块中的代码
     * 无论是正常返回,还是出现异常返回, 最终通知的代码都会执行
     * 且执行顺序和finally一样,都是最后执行的
     */
    @After("checkAdmin()")  // 执行顺序: 第四
    public void after() {
        System.out.println("后置通知执行了....");
    }

    /**
     * 环绕通知: 功能最为强大, 可以对joinPoint中的参数进行修改,
     * 也可以对jointPoint.proceed()方法返回的结果进行修改
     * @param joinPoint 连接点 就是指方法
     * @throws Throwable 调用proceed()方法抛出的异常
     */
//    @Around("checkAdmin()")
//    public Object around(ProceedingJoinPoint joinPoint) {
//        System.out.println("环绕通知执行了....");
//        // 获取入参
//        Object[] args = joinPoint.getArgs();
//        for (int i = 0; i < args.length; i++) {
//            System.out.println(args[i]);
//        }
//        //------上面是before 前置通知的内容---------
//        // 调用原始方法,response是原始方法返回的结果
//        Object response = null;
//        try {
//            response = joinPoint.proceed();
//            // ----- AfterReturning 正常返回后的通知, 可以对结果进行增强 ------
//            response = null;
//            return response;
//        } catch (Throwable throwable) {
//            // ----- AfterThrowing 异常返回时通知-----
//        } finally {
//            //------下面是after 最终通知执行的代码----------
//
//        }
//        return response;
//    }
}
