package com.one.bean;

import java.util.Date;

/**
 * 角色实体类
 */
public class SysRole {
    // 在映射实体类中不要使用基本数据类型
    private Long id;
    private String name;
    private Integer enabled;
    private Long creatBy;
    private Date createTime;

    public SysRole() {
    }

    public SysRole(Long id, String name, Integer enabled, Long creatBy, Date createTime) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.creatBy = creatBy;
        this.createTime = createTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Long getCreatBy() {
        return creatBy;
    }

    public void setCreatBy(Long creatBy) {
        this.creatBy = creatBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", creatBy=" + creatBy +
                ", createTime=" + createTime +
                '}';
    }
}
