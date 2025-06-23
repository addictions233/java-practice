package com.one.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;



/**
 * @author one
 * @description 登录认证的入参DTO
 * @date 2022-9-18
 */
@Data
public class LoginDTO {
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
     * 验证码
     */
    @Size(min = 6, max = 6, message = "验证码长度不符合要求")
    private String verificationCode;

}
