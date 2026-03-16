package com.one.springsecuritydemo.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: UserDetail
 * @Description: 用户实体类,  自定义认证对象, 必须是UserDetails接口的实现类对象
 * @Author: one
 * @Date: 2021/02/22
 */
@Data
@TableName("tb_user_detail")
public class MyUserDetail extends Model<MyUserDetail> implements UserDetails {

    public static final String SESSION_USER_KEY = "_user";

    private static final long serialVersionUID = 2581916531728694284L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private List<String> roles;

    /**
     * 用户的权限
     */
    @TableField(exist = false)
    private Set<String> authorities;

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
