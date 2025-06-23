package com.one.service;

import com.one.basic.UserAuth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用于登录认证的service: 必须实现UserDetailsService接口
 * @author one
 */
@Component
public class UserDetailsServiceImpl  implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username.equals("user")){
            UserAuth userAuth =new UserAuth();
            userAuth.setUsername("user");
            // 密码原文: 123456
            userAuth.setPassword("$2a$10$8V3UHgnnI/3RCKhg5aklz.sw448DP4.x9P2hFl/fnw99QU86POlgm");
            userAuth.setNickName("tudou");

            List<String> roles=new ArrayList<>();
            roles.add("USER");
            userAuth.setRoles(roles);

            return userAuth;
        }
        if(username.equals("admin")){
            UserAuth userAuth =new UserAuth();
            userAuth.setUsername("admin");
            // 密码原文: 000000
            userAuth.setPassword("$2a$10$cRH8iMMh6XO0T.ssZ/8qVOo8ThWs/qfntIH3a7yfpbPd05h9ZGx8y");
            userAuth.setNickName("digua");

            List<String> roles=new ArrayList<>();
            roles.add("USER");
            roles.add("ADMIN");
            userAuth.setRoles(roles);

            return userAuth;
        }
        return null;
    }
}
