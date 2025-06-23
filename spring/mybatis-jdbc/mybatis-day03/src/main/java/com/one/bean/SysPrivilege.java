package com.one.bean;

/**
 *  权限实体类
 */
public class SysPrivilege {
    private Long id;
    private String privilegeName;
    private String privilegetUrl;

    public SysPrivilege() {
    }

    public SysPrivilege(Long id, String privilegeName, String privilegetUrl) {
        this.id = id;
        this.privilegeName = privilegeName;
        this.privilegetUrl = privilegetUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegetUrl() {
        return privilegetUrl;
    }

    public void setPrivilegetUrl(String privilegetUrl) {
        this.privilegetUrl = privilegetUrl;
    }

    @Override
    public String toString() {
        return "SysPrivilege{" +
                "id=" + id +
                ", privilegeName='" + privilegeName + '\'' +
                ", privilegetUrl='" + privilegetUrl + '\'' +
                '}';
    }
}
