package com.one.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description: 实体类
 * @Author: one
 * @Date: 2020/12/09
 */
@Data
public class User implements Serializable {
    private Integer uuid;
    private String userName;
    private String password;
    private String realName;
    private Integer gender;  // 1 代表男  0 代表女
    private Date birthday;

    public static void main(String[] args) {
        User user = new User();
        user.setUserName("1232");
    }

}
