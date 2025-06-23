package com.one.bak.framework.protocol;

import com.one.bak.framework.protocol.dubbo.DubboProtocol;
import com.one.bak.framework.protocol.http.HttpProtocol;

/**
 * @description: 远程调用协议工厂
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class ProtocolFactory {

    public static Protocol getProtocol() {
        String protocol = System.getProperty("protocol");
        if (protocol == null || "".equals(protocol)) {
            protocol = "http";
        }
        return switch (protocol) {
            case "http" -> new HttpProtocol();
            case "dubbo" -> new DubboProtocol();
            default -> new HttpProtocol();
        };
    }
}
