package com.one.bean;

import lombok.Data;

import java.util.Arrays;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class SysUser {
    // 实体类中不要使用基本数据类型
//    private Long userId;

    private String userId;

    private String userName;

    private String userPassword;

    private String userEmail;

    private String userInfo;

    private byte[] headImg;

    private Date createTime;

}
