package com.one.domain;


import lombok.Data;

import java.util.Date;

/**
 * @Description 与用户表cloud_user表映射的实体类
 * @Author one
 * @Version 1.0
 **/
@Data
public class CloudUser {
    private Integer id;
    private String name;
    private String pwd;
    private String headImg;
    private String phone;
    private Date createTime;
    private String wechat;
}
