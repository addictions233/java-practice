package com.one.springsecuritydemo.config;


import com.one.springsecuritydemo.filter.JsonAuthenticationFilter;
import com.one.springsecuritydemo.handler.MyAuthenticationFailHandler;
import com.one.springsecuritydemo.handler.MyAuthenticationSuccessHandler;
import com.one.springsecuritydemo.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: WebSecurityConfig
 * @Description: 如果不想使用spring security的默认登录方式, 就创建自定义WebSecurityConfigurerAdapter的Bean对象
 *               结合自定义的userDetailService使用,采用配置类的方式对用户名和密码进行配置
 *               spring security提供了用户名密码登录, 认证, 会话管理, 授权等功能, 只需要配置即可使用
 *               在configuration包下定义了WebSecurityConfigurer, 安全配置的内容包括: 用户信息, 密码编辑器, 安全拦截器
 *             主要配置类: SpringBootWebSecurityConfiguration
 * @Author: one
 * @Date: 2021/02/22
 */
//@Configuration
@Deprecated //新版本的security推荐通过自定义SecurityFilterChain的Bean对象来实现过滤器链的配置,
// 而不是通过继承WebSecurityConfigurerAdapter来进行配置
// Spring Security 是在 5.7.0-M2 这个版本中将 WebSecurityConfigurerAdapter 过期
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    /**
     * 自定义的userDetailService
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 定义认证策略: 重写WebSecurityConfigurerAdapter类中的configure()方法, 来配置用户名和密码
     * @param auth 认证管理器
     * @throws Exception 异常信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // BCrypt是替代MD5的一种加密方式,BCrypt每次Hash出来的密码都不一样
        String password = this.passwordEncoder.encode("root123");
        // 基于内存的认证策略, 与在配置文件中配置用户名和密码一样, 通常不使用
        auth.inMemoryAuthentication().withUser("root").password(password).roles("admin");
    }

    /**
     * 定义认证策略: 重写WebSecurityConfigurerAdapter类中的configure()方法, 来配置用户名和密码
     * @throws Exception 异常信息
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 基于用户表的认证策略, 使用自定义的userDetailsService进行认证
//        // 需要实现两个重要的接口: UserDetailsService和PasswordEncoder接口
//        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForDefault = "bcrypt";
        Map<String, PasswordEncoder> encoderMap = new HashMap<String, PasswordEncoder>() {
            {
                put(idForDefault, new BCryptPasswordEncoder());
//                put("SHA-1", new DelegatingPasswordEncoder("SHA-1"));
            }
        };
        return new DelegatingPasswordEncoder(idForDefault, encoderMap);
    }

    /**
     * 配置白名单:对特定的请求进行放行,不进入filterChain
     *
     * @param web 请求
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/public/**"); // 对应public下的请求不进入过滤器链
    }


    /**
     * 定义安全拦截策略
     *
     * @param http http
     * @throws Exception 异常信息
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests(request -> request.mvcMatchers("/login.html").permitAll() // 表单登录放行
//                        .mvcMatchers("/index").permitAll()  // 所有的 /index 的请求直接放行
//                        .mvcMatchers("/hello").authenticated() // 所有的 /hello 的请求都必须认证通过
//                        .anyRequest().authenticated()) // 除此之外所有请求进行拦截
//                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // 使用者定义的filter过滤器替换原有的过滤器
//                .formLogin(login -> login.loginPage("/login.html") // 进行表单登录的配置,可以自定义登录页面覆盖默认的登陆页面(spring security有默认的登陆页面), 使用自定义的登陆页面就必须指定处理登录的url
//                        .loginProcessingUrl("/doLogin") // 指定处理登录请求的虚拟url, 不用真的写处理请求的url, spring security会自己处理登录
//                        .usernameParameter("uname")
//                        .passwordParameter("passwd")
//                        .successForwardUrl("/index.html") // 自定义登录成功后的跳转页面地址,前后端分离不需要跳转页面
////                        .successHandler(new MyAuthenticationSuccessHandler()) // 登录成功的处理器,用于前后端分离的json数据,后端不用跳转页面
//                        .failureUrl("/login.html")) // 自定义登录失败的跳转页面地址
////                        .failureHandler(new MyAuthenticationFailHandler())) // 登录失败处理器
//                .logout(logout -> logout.invalidateHttpSession(true) // 退出登录的配置. 默认为true, 注销登录关闭session
//                        .logoutUrl("/logout") // 也可以自己指定注销登录的url
//                        .clearAuthentication(true) // 默认为true, 清空授权信息
//                        .logoutSuccessUrl("/login.html")) // 注销登录后, 默认跳转到登录页面) // 默认是开启注销登录的, 直接访问 "/logout"
//                .csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(Customizer.withDefaults());
//    }

    /**
     * 定义安全拦截策略
     *
     * @param http http
     * @throws Exception 异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request.mvcMatchers("/authorize/**").permitAll() // 处理登录认证的请求路径进行放行
                        .mvcMatchers("/index").permitAll()  // 所有的 /index 的请求直接放行
                        .mvcMatchers("/hello").authenticated() // 所有的 /hello 的请求都必须认证通过
                        .anyRequest().authenticated()) // 除此之外所有请求进行拦截
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // 使用自定义的filter过滤器获取用户名和密码
                .formLogin(AbstractHttpConfigurer::disable) // 前后端分离的时候, 禁止表单登录
                .logout(logout -> logout.invalidateHttpSession(true) // 退出登录的配置. 默认为true, 注销登录关闭session
                        .logoutSuccessHandler(new MyLogoutSuccessHandler()) // 指定注销登录的处理器
                        .clearAuthentication(true)) // 默认为true, 清空授权信息
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
    }

    private JsonAuthenticationFilter restAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter();
        // 指定登录成功的处理器
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        // 指定登录失败的处理器
        jsonAuthenticationFilter.setAuthenticationFailureHandler(new MyAuthenticationFailHandler());
        // 设置认证管理器
        jsonAuthenticationFilter.setAuthenticationManager(authenticationManager());
        jsonAuthenticationFilter.setFilterProcessesUrl("/authorize/login"); // 指定处理登录请求的虚拟url
        return jsonAuthenticationFilter;
    }

}
