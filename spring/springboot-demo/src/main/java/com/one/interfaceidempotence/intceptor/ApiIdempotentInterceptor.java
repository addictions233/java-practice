package com.one.interfaceidempotence.intceptor;

import com.one.interfaceidempotence.annotation.ApildempotentAnn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @ClassName: ApiIdempotentInterceptor
 * @Description: 利用AOP自定义一个幂等性的拦截器,光定义一个拦截器并没有用,还要配置拦截器
 * @Author: one
 * @Date: 2021/03/24
 */
@Component
public class ApiIdempotentInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 处理器方法的前置拦截器,在处理器的方法被调用前执行,在该方法中可以做类似校验的功能,如果返回true,则调用下一个拦截器
     * 如果返回false,则中断执行, 也就是说我们想调用的方法不会被执行,但是你可以修改response为你想要的响应
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("前置拦截器执行了...");
        //对幂等性接口进行逻辑处理
        // 1, 将处理器对象转换为处理方法对象
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        // 2,通过处理器方法对象获取Method对象
        Method method = handlerMethod.getMethod();
        // 3,判断method上是否有自定义的@ApildempotentAnn注解
        boolean flag = method.isAnnotationPresent(ApildempotentAnn.class);
        // 4, 如果flag为true,表示方法有自定义注解,需要进行幂等性接口处理
        if(flag){
            //5, 通过校验token的方法获取注解
            boolean result = checkToken(request);
            if(result){
                // 如果 result为true,表明是该接口的第一次访问,对请求方法放行
                return true;
            } else {
                // 如果 result为false, 表明不是对该接口的第一个访问,将请求拦截,并给出提示信息
                response.setContentType("application/text; charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write("该接口为幂等性接口,请不要多次请求!");
                writer.close();
                response.flushBuffer();
                return false;
            }
        }
        // 如果处理器方法上没有自定义注解,那么就对该注解进行放行
        return true;
    }

    /**
     * 此次是通过校验token令牌的方式实现接口的幂等性,所有需要单独创建一个方法来处理请求头中的token
     * @return boolean
     */
    private boolean checkToken(HttpServletRequest request) {
        // 每次发送请求时可以在 Headers 头部中带上当前这个 token 令牌
        String token = request.getHeader("token");
        // 对token进行非空校验
        if(StringUtils.hasLength(token)){
            // 如果请求头中有token,就将redis中存储的token键值对删除
            // 如果redis中有token键值对,返回true, 如果redis中没有token键值对,返回false
            return this.redisTemplate.delete(token);
        }
        return false;
    }

    /**
     * 处理器方法的后置拦截, 在处理器方法执行结束后执行, 最后都是通过controller层返回客户端页面
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("后置拦截器执行了...");
    }
}
