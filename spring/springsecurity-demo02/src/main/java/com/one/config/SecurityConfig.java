package com.one.config;
import com.one.core.RestfulAccessDeniedHandler;
import com.one.core.JwtTokenAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Autowired
    private JwtTokenAuthorizationManager jwtTokenAuthorizationManager;


    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                // 对登录接口进行方形
                .antMatchers("/security/**").permitAll()
                .anyRequest()
                // 定义授权管理器
                .access(jwtTokenAuthorizationManager)
                .and()
                .exceptionHandling()
                // 定义访问失败的处理器
                .accessDeniedHandler(restfulAccessDeniedHandler);
        // 关闭csrf
        http.csrf().disable();
        //关闭session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //关闭缓存
        http.headers().cacheControl().disable();
        return http.build();
    }

    /**
     * 定义密码编码器的Bean
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }


    /**
     * 定义认证管理器的Bean
     * 区分一下: 认证管理器 AuthenticationManager ||  授权管理器 AuthorizationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
