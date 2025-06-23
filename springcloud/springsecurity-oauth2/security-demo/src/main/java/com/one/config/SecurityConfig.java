package com.one.config;

import com.one.handler.BusinessAccessDeniedHandler;
import com.one.handler.BusinessAuthenticationEntryPoint;
import com.one.handler.LoginFailureHandler;
import com.one.handler.LoginSuccessHandler;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author one
 * @description 使用spring security的配置类
 * @date 2024-9-29
 */
@Configuration
@EnableWebSecurity
//启用权限控制的注解支持
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    /**
     * 定义密码加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 不加密, 不推荐使用, 数据库密码不能存明文
//        return NoOpPasswordEncoder.getInstance();

        // 加密方式使用bcrypt
        return new BCryptPasswordEncoder();
    }

    /**
     * 基于内存的UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("zhangsan")
                .password("123")
                .roles("user").build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("456"))
                .roles("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 前后端分离的认证逻辑
        http.formLogin((fromLogin) ->  {
            fromLogin.loginProcessingUrl("/login") // 登录访问接口
                    .successHandler(new LoginSuccessHandler()) // 登录成功的处理逻辑
                    .failureHandler(new LoginFailureHandler()); // 登录失败的处理逻辑
        });

        // 对请求进行访问权限控制
        http.authorizeHttpRequests(authorizeHttpRequests -> {
            authorizeHttpRequests
                    .requestMatchers("/login").permitAll() // 登录接口, 不需要认证
                    .requestMatchers("/index").hasRole("user") // 需要user角色, 底层会判断是否具有ROLE_user权限
                    .requestMatchers("/admin").hasRole("admin") // 需要admin角色, 底层会判断是否具有ROLE_admin权限
                    .requestMatchers("/user/**").hasAuthority("user:api") // 需要user:api权限
                    .requestMatchers("/order/**").hasAuthority("order:api") // 需要order:api权限
                    .anyRequest().authenticated(); // 其他路径的请求都需要认证
        });

        // 关闭跨站点请求伪造csrf防护
        http.csrf(AbstractHttpConfigurer::disable);

        // 访问受限后的异常处理
        http.exceptionHandling(exceptionHandling ->  {
            exceptionHandling.authenticationEntryPoint(new BusinessAuthenticationEntryPoint()); // 访问没有认证时的处理逻辑
            exceptionHandling.accessDeniedHandler(new BusinessAccessDeniedHandler()); // 访问没有权限时的处理逻辑
        });

        return http.build();
    }
}
