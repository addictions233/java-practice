package com.one.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 在要求的多个角色中，有一个满足要求，就放行
 * @author zjw
 * @description
 */
public class RolesOrAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 获取主体subject
        Subject subject = getSubject(request, response);
        // 将传入的角色转成数组操作
        String[] rolesArray = (String[]) mappedValue;
        // 健壮性校验
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        // 开始校验
        for (String role : rolesArray) {
            if(subject.hasRole(role)){
                return true;
            }
        }

        return false;
    }
}
