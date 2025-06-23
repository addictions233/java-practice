//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.one.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.DispatcherType;

import com.one.filter.XssFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * 过滤器配置类
 * @author one
 */
@Configuration
public class FilterConfig {
    @Value("${xss.enabled:null}")
    private String enabled;
    @Value("${xss.excludes:null}")
    private String excludes;
    @Value("${xss.urlPatterns:null}")
    private String urlPatterns;

    public FilterConfig() {
    }

    /**
     * 将过滤器封装为 FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST, new DispatcherType[0]);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(this.urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(Integer.MIN_VALUE);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", this.excludes);
        initParameters.put("enabled", this.enabled);
        registration.setInitParameters(initParameters);
        return registration;
    }
}
