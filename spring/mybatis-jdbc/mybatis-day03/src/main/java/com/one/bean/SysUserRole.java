package com.one.bean;

/**
 * 用户角色关联表
 */
public class SysUserRole {
    private Long uid;  // 用户ID
    private Long rid; // 角色ID

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}
