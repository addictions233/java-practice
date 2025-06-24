package com.one.domain.bo;

import lombok.Data;

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
