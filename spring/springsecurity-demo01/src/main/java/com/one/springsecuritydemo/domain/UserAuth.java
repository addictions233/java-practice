package com.one.springsecuritydemo.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义认证对象, 必须是UserDetails接口的实现类对象
 */
@Data
public class UserAuth implements UserDetails {
    private String username;

    private String password;

    private String nickName; //昵称

    private List<String> roles;//角色列表

    /**
     * 获取用户的权限组
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles==null) return null;
        // 直接将角色转换为权限对象
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
