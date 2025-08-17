package com.one.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AOPAdvice
 * @Description: 使用AspectJ注解定义AOP切面
 * @Author: one
 * @Date: 2020/12/02
 */
@Component
@Aspect  // spring的aop只是借用了aspectj的注解, 但是aop实现还是spring自己实现的
public class AOPAdvice {

    /**
     * 定义切点表达式
     */
    @Pointcut("execution(* *.save(..))")
    public void pt(){

    }
    public void function(){
        System.out.println("基础方法执行了...");
    }

    /**
     * 前置通知: 当前通知方法在原始切入点方法前运行
     */
    @Before("pt()")
    public void before(){
        System.out.println("前置通知方法执行了..");
    }

    /**
     * 最终返回通知: 无论原始切入点方法是否正常执行, 当前通知方法都会执行, 但是它在 正常返回通知/异常返回通知之前执行
     * 相当于finally中的内容
     */
    @After("pt()")
    public void after(){
        System.out.println("最终返回通知方法执行了..");
    }

    /**
     * 正常返回通知: 当前通知方法在原始切入点方法正常执行后执行
     */
    @AfterReturning("pt()")
    public void afterReturning(){
        System.out.println("正常返回后通知方法执行了..");
    }

    /**
     * 抛出异常后通知: 当前通知方法在原始切入点方法执行抛出异常时执行
     */
    @AfterThrowing("pt()")
    public void afterThrowing(){
        System.out.println("抛出异常后通知方法执行了..");
    }


    /**
     * 环绕通知: 可以取得上面四种通知
     *
     * @param point 切面
     * @return 结果
     */
    @Around("pt()")
    public double around(ProceedingJoinPoint point) {
        double sum =0;
        try {
            Object[] args = point.getArgs();
            double price = (double) args[0];
            double num = (double) args[1];
            System.out.println("客户买了" + num + "件商品" + "单价为:" + price);
            //对产品价格打八折
            args[0] = ((double) args[0]) * 0.8;
            System.out.println("该产品打8折之后的单格为:"+ args[0]);
            System.out.println("上述内容可以在前置通知中执行..");
            sum = (double) point.proceed(args);
            System.out.println("下述内容可以在后置通知中执行..");
            //如果客户总价满100,总价再打八折
            System.out.println("总价满200,总价将会打八折");
            if(sum > 200){
                sum = sum*0.8;
            }
        } catch (Throwable e) {
            System.out.println("执行异常通知执行了...");
            System.out.println("出异常了");
        } finally {
            System.out.println("最终返回通知执行了...");
        }
        return sum;
    }
}
