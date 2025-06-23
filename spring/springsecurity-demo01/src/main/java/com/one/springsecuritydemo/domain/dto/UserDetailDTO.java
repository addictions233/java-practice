package com.one.springsecuritydemo.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author one
 * @description 进行账户注册的DTO
 * @date 2023-3-5
 */
@Data
public class UserDetailDTO {

    /**
     * 用户名
     */
    @NotBlank
    @Size(min = 4, max = 20, message = "用户名长度必须在4~20个字符之前")
    private String username;

    /**
     * 密码
     */
    @Size(min = 8, max = 20, message = "密码长度必须在8~20个字符之前")
    private String password;

    /**
     * 二次输入的密码
     */
    @Size(min = 8, max = 20, message = "密码长度必须在8~20个字符之前")
    private String matchPassword;

    /**
     * 邮箱
     */
    @Email
    private String email;


    /**
     * 姓名
     */
    private String name;
}
