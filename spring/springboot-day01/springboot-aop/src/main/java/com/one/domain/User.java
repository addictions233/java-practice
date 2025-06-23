package com.one.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: User
 * @Description: 用户类
 * @Author: one
 * @Date: 2021/04/03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String name;
    private String password;
}
