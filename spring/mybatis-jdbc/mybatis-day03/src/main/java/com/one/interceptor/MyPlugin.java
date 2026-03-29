package com.one.interceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * 使用mybatis提供的拦截器
 * @author one
 */
@Intercepts({@Signature(
        type = ResultSetHandler.class,  // 具体拦截四大核心对象中的哪个对象
        method = "handleResultSets", // 拦截哪个方法
        args = {Statement.class})})  // 拦截方法的参数类型是什么, 可能有方法重载, 需要表明方法参数类型
public class MyPlugin implements Interceptor {

    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("plugin before");
        System.out.println(this.properties.getProperty("someProperty"));
        // 执行原有方法
        Object result = invocation.proceed();
        System.out.println("plugin after");
        return result;

    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
