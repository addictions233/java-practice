package com.one.interceptor;

import com.one.annotation.NoAuthorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: TokenInterceptor
 * @Description: 自定义拦截器
 *                  HandlerInterceptor是Spring MVC提供的拦截器接口
 * @Author: one
 * @Date: 2022/03/25
 */
@Component// 拦截器是Spring MVC框架提供的,所以需要创建bean对象交给Spring容器管理
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    /**
     * 前置处理方法
     * @param handler，这个是真正用来处理这个请求的Controller的方法声明(也就是处理器handler)
     * @return boolean true表示流程继续, 到达拦截器链中的下一个拦截器Interceptor
     *                 false表示流程中断，不会继续调用其他的拦截器Interceptor或处理器Controller
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截器可以拿到原始的http请求和响应的信息，也能拿到真正处理这个请求的Controller方法的信息，
        // 但是其拿不到这个方法被调用的时候真正调用的参数的值
        // 通过handler虽然可以拿到处理此请求的controller和method，但是其没办法拿到这个method的真正的参数的值，
        String method = request.getMethod();
        // 在设置了header之后，浏览器在发送正式请求前，会先发送一个OPTIONS请求，
        // 发送OPTIONS请求是为了验证正式请求的有效性,来检查服务端是否支持正式请求类型（POST、GET等）
        if (method.equals("OPTIONS")) {
            //跳过OPTIONS请求
            return true;
        }
        //判断，请求的方法是否包含了 NoAuthorization ，如果包含了，就不需要做处理
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoAuthorization annotation = handlerMethod.getMethod().getAnnotation(NoAuthorization.class);
            if (annotation != null) {
                return true;
            }
//            throw new ApplicationException(502,"tokenInterceptor中抛出的异常");
        }
        // 对用户信息进行校验
        return true;
    }

    /**
     * 后处理方法，就是Controller 方法调用之后执行，但是它会在DispatcherServlet进行视图返回渲染之前被调用，
     * 所以我们可以在这个方法中对Controller处理之后的modelAndView对象(或者json数据)进行操作。
     * 如果在controller方法中抛出异常,进入到了全局异常处理器中,拦截器中的postHandler()方法是不执行的
     * 这点与afterCompletion()方法是不一样的,afterCompletion()方法是不管controller方法是否抛异常都会执行的
     * 但是如果是preHandler()方法中抛出了异常, postHandler()方法和afterCompletion()方法都不会执行
     * @param request 请求对象
     * @param response 响应对象
     * @param handler controller层处理器对象
     * @param modelAndView handler处理器返回的modelAndView对象
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("tokenInterceptor拦截器中的postHandle方法执行了....");
    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
     * 顾名思义，该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。
     * 这个方法的主要作用是用于进行资源清理工作的
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 如果是过滤器或者拦截器抛出的异常,不会到达全局异常处理器,会在这里被拦截,然后抛出新的异常
        if (ex != null) {
            log.error("exception in Interceptor:", ex);
        }
        System.out.println("tokenInterceptor拦截器中的afterCompletion()方法执行了...");
    }
}
