package com.one.bak.framework;

import java.io.Serializable;

/**
 * @description: 远程服务调用需要的url
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class URL implements Serializable {

    private String hostName;

    private Integer port;

    public URL(String hostName, Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public Integer getPort() {
        return port;
    }
}
