package com.one.springsecuritydemo.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author one
 * @description spring security自定义Json登录认证过滤器, 继承自UsernamePasswordAuthenticationFilter,
 * @date 2023-2-27
 */
public class JsonBodyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * spring security默认提供的UsernamePasswordAuthenticationFilter只能获取表单中的用户名和密码,
     * 无法获取请求体JSON中的用户名和密码
     * 这里自定义过滤器, 在登录时, 获取请求中的用户名和密码进行认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ServletInputStream inputStream = null;
        JsonNode jsonNode;
        String username = null;
        String password = null;
        try {
            // 获取请求体中的username和password
            inputStream = request.getInputStream();
            jsonNode = objectMapper.readTree(inputStream);
            username = jsonNode.get("username").textValue();
            password = jsonNode.get("password").textValue();
        } catch (IOException e) {
            throw new BadCredentialsException("没有获取用户名或密码");
        }
        // 构建认证对象
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        super.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
