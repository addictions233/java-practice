package com.one.websocketwechat.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: User
 * @Description: 封装用户登录的请求参数
 * @Author: one
 * @Date: 2021/05/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
