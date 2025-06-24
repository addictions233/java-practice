package com.one.domain.dp;

import lombok.Data;

/**
 * DP 也可以称为 value object (值对象)
 */
@Data
public class RealnameInfo {

    private String userId;

    private String realName;

    public void check(String name) {
        if (!realName.equals(name)) {
            throw new IllegalStateException("实名认证失败!");
        }
    }
}
