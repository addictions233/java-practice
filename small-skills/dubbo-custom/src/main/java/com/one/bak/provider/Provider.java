package com.one.bak.provider;

import com.one.bak.framework.URL;
import com.one.bak.framework.protocol.Protocol;
import com.one.bak.framework.protocol.ProtocolFactory;
import com.one.bak.framework.register.LocalRegister;
import com.one.bak.framework.register.RemoteFileRegister;
import com.one.bak.provider.api.HelloService;
import com.one.bak.provider.impl.HelloServiceImpl;

/**
 * @description: provider的服务启动类
 * @author: wanjunjie
 * @date: 2024/10/31
 */
public class Provider {

    public static void main(String[] args) {
        // 1. 注册服务
        URL url = new URL("localhost",10086);
        RemoteFileRegister.register(HelloService.class.getName(), url);

        // 2. 注册本地服务
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);

        // 3. 开启服务, 供远程调用
        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);
    }
}
