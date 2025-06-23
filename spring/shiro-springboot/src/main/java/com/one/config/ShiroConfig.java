package com.one.config;

import com.one.cache.RedisCacheManager;
import com.one.filter.RolesOrAuthorizationFilter;
import com.one.realm.ShiroRealm;
import com.one.session.DefaultRedisWebSessionManager;
import com.one.session.RedisSessionDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zjw
 */
@Configuration
public class ShiroConfig {

    @Bean
    public SessionManager sessionManager(RedisSessionDAO sessionDAO) {
        DefaultRedisWebSessionManager sessionManager = new DefaultRedisWebSessionManager();
        // 使用shiro提供的sessionDAO进行会话管理, 而不是使用默认的web容器的会话管理
        sessionManager.setSessionDAO(sessionDAO);
        return sessionManager;
    }



    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm, SessionManager sessionManager, RedisCacheManager redisCacheManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置shiroRealm
        securityManager.setRealm(realm);
        // 设置自定义的Session会话管理
        securityManager.setSessionManager(sessionManager);
        // 设置CacheManager，提供与Redis交互的Cache对象
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        // 构建filterChainDefinitionMap 过滤器链的集合
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 构建过滤器拦截策略
        filterChainDefinitionMap.put("/login.html","anon");  // 登录进行放行
        filterChainDefinitionMap.put("/user/logout","logout");
        filterChainDefinitionMap.put("/user/**","anon");
        filterChainDefinitionMap.put("/item/rememberMe","user");
        filterChainDefinitionMap.put("/item/authentication","authc");
        filterChainDefinitionMap.put("/item/select","rolesOr[超级管理员,运营]");
        filterChainDefinitionMap.put("/item/delete","perms[item:delete,item:insert]");
        filterChainDefinitionMap.put("/**","authc"); // 其他所有的路径请求进行认证

        shiroFilterChainDefinition.addPathDefinitions(filterChainDefinitionMap);

        return shiroFilterChainDefinition;
    }

    @Value("#{ @environment['shiro.loginUrl'] ?: '/login.jsp' }")
    protected String loginUrl;

    @Value("#{ @environment['shiro.successUrl'] ?: '/' }")
    protected String successUrl;

    @Value("#{ @environment['shiro.unauthorizedUrl'] ?: null }")
    protected String unauthorizedUrl;


    @Bean
    protected ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,ShiroFilterChainDefinition shiroFilterChainDefinition) {

        //1. 构建ShiroFilterFactoryBean工厂
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        //2. 设置了大量的路径
        filterFactoryBean.setLoginUrl(loginUrl);
        filterFactoryBean.setSuccessUrl(successUrl);
        filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        //3. 设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);

        //4. 设置过滤器链
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

        //5. 设置自定义过滤器 ， 这里一定要手动的new出来这个自定义过滤器，如果使用Spring管理自定义过滤器，会造成无法获取到Subject
        filterFactoryBean.getFilters().put("rolesOr",new RolesOrAuthorizationFilter());



        //6. 返回工厂
        return filterFactoryBean;
    }















}
