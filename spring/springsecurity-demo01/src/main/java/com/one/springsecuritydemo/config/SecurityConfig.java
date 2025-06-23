package com.one.springsecuritydemo.config;

import com.one.springsecuritydemo.filter.JsonAuthenticationFilter;
import com.one.springsecuritydemo.handler.MyAuthenticationFailHandler;
import com.one.springsecuritydemo.handler.MyAuthenticationSuccessHandler;
import com.one.springsecuritydemo.handler.MyLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author one
 * @description Spring Security的使用配置类
 * @date 2024-7-23
 */
@Configuration
// 开启权限注解支持, 分别支持jsr250注解, Secured注解, 表达式注解
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    /**
     * 自定义过滤器链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 前后端不分离的情况下, 后端自定义登录页面
//        http.formLogin()
//                .loginPage("/login.html") // 自定义登录页面
//                .loginProcessingUrl("/login") // 登录访问路径
//                .permitAll();  //登录页和登录访问路径无需登录也可以访问

        // 前后端分离的情况下, 后端对请求返回json
        http.formLogin()
                .loginProcessingUrl("/login")
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailHandler());

        // 添加自定义拦截器, 在登录之前获取用户名和密码
        http.addFilterBefore(new JsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 自定义登出逻辑
        http.logout().logoutSuccessHandler(new MyLogoutSuccessHandler());

        // 自定义权限管理
        http.authorizeRequests()  // 学习web授权方式
                .antMatchers("/hello/admin").hasRole("admin") // 对于hello路径的请求必须有admin角色才能访问
                .antMatchers("/css/**","/images/**").permitAll() // 对web静态资源进行放行
                .anyRequest().authenticated();

        // 跨站请求伪造,关闭csrf防护
        http.csrf().disable();

        return http.build();
    }

    /**
     * 定义密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用明文密码
//        return NoOpPasswordEncoder.getInstance();
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }

    /**
     * 基于内存的用户名密码认证方式
     */
//    @Bean
    public UserDetailsService userDetailsService (){
        // 默认的基于内存的用户管理
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        // 角色必须是ROLE_开头, 而权限不需要如此
        userDetailsManager.createUser(User.withUsername("zhangsan").password("123").authorities("ROLE_USER").build());
//        userDetailsManager.createUser(User.withUsername("zhangsan").password(new BCryptPasswordEncoder().encode("123")).authorities("visit").build());
        return userDetailsManager;
    }

}
