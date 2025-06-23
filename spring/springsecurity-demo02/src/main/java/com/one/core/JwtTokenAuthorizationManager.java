package com.one.core;

import com.alibaba.fastjson2.JSON;
import com.one.basic.UserAuth;
import com.one.constant.SecurityConstant;
import com.one.util.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

/**
 * 自定义授权管理器: 必须实现AuthorizationManager接口, 在授权管理器中进行JWT校验
 * 利用过滤器动态控制权限，在AuthorizationFilter中，只需要实现接口AuthorizationManager
 * @author one
 */
@Component
public class JwtTokenAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext requestAuthorizationContext) {

        HttpServletRequest request = requestAuthorizationContext.getRequest();
        // 1,获取token
        String token = request.getHeader(SecurityConstant.USER_TOKEN);
        if (StringUtils.isBlank(token)) {
            return new AuthorizationDecision(false);
        }
        // 2,解析token中的对象
        Object object = JWTUtils.verifyJWT(token, SecurityConstant.SALT);
        if (object == null) {
            return new AuthorizationDecision(false);
        }
        UserAuth userAuth = JSON.parseObject(JSON.toJSONString(object), UserAuth.class);

        // 3, 将认证对象放入上下文
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword(), userAuth.getAuthorities());
        authenticationToken.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 返回授权通过
        return new AuthorizationDecision(true);
    }
}
