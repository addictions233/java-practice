package com.one;

import com.one.config.WebMvcConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

/**
 * 使用SPI机制启动 SSM项目, 用来替代web.xml的方式启动
 */
@ComponentScan(basePackages = "com.one",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,value={Controller.class}),
        @ComponentScan.Filter(type = ASSIGNABLE_TYPE,value = WebMvcConfig.class ),
})
public class MyAnnotationStarterInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * @return 父容器Root WebApplicationContext的启动类 Spring容器
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MyAnnotationStarterInitializer.class};
    }

    /**
     * @return 子容器 Servlet WebApplicationContext的启动类, Springmvc容器
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }

    /**
     * @return 返回我们前端控制器 DispatchServlet 的拦截路径
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
