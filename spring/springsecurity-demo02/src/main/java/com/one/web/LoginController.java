package com.one.web;

import com.one.basic.UserAuth;
import com.one.constant.SecurityConstant;
import com.one.util.JWTUtils;
import com.one.util.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("security")
public class LoginController {

    /**
     * 注入自定义认证管理器
     */
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 登录接口, 自定义json登录的接口
     * @return 登录成功返回token
     */
    @PostMapping("login")
    public String login(@RequestBody UserAuth userAuth) {
        // 将登录用户信息username和password封装为Authentication认证对象
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword());
        // 使用认证管理器authenticationManager进行认证,
        // 最终会调用我们定义的UserDetailsServiceImpl#loadUserByUsername方法
        Authentication authenticate = authenticationManager.authenticate(authentication);
        // 判断是否认证通过
        if (authenticate.isAuthenticated()) {
            //认证通过
            Object principal = authenticate.getPrincipal();
            // 生成token
            String token = JWTUtils.createJWT(userAuth.getUsername(), principal, SecurityConstant.SALT, 7, TimeUnit.DAY);
            return token;
        } else {
            return "";
        }

    }

}
