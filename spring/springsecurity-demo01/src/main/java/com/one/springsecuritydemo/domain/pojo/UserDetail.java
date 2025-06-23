package com.one.springsecuritydemo.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Set;

/**
 * @ClassName: UserDetail
 * @Description: 用户实体类
 * @Author: one
 * @Date: 2021/02/22
 */
@Data
@TableName("tb_user_detail")
public class UserDetail extends Model<UserDetail> {
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
     * 用户的权限
     */
    @TableField(exist = false)
    private Set<String> authorities;
}
