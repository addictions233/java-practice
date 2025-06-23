package com.one.filter;

import com.one.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * @author one
 * @description 自定义过滤器进行jwt认证
 * @date 2024-10-1
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1.从请求头中获取token来进行判断, 如果没有携带token, 则继续走其他的filter过滤逻辑
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasLength(tokenHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2, 校验token
        // 2.1 将token切割前缀"bearer", 然后使用封装的JWT工具解析token, 得到一个map对象
        String token = tokenHeader.substring("bearer".length());
        Map<String, Object> tokenMap = JWTUtils.parseToken(token);

        // 2.2 获取token中的过期时间, 调用JWT工具中封装的过期时间校验, 如果token已经过期, 则删除登录的用户, 继续往下走其他filter逻辑
        if (JWTUtils.isExpiresIn((Long) tokenMap.get("expiresIn"))) {
            // token已经过期, 清空当前登录用户
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        // 3, 设置当前登录用户
        String username = (String) tokenMap.get("username");
        if (StringUtils.hasLength(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 获取用户信息
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null && userDetails.isEnabled()) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 设置当前用户的登录状态
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }
}
