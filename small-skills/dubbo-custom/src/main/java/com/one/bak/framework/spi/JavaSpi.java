package com.one.bak.framework.spi;

import com.one.bak.framework.URL;
import com.one.bak.framework.protocol.Protocol;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @description: 使用spi主动加载接口的所有实现类,这样框架可以提供接口的自由扩展
 * @author: wanjunjie
 * @date: 2024/10/31
 */
public class JavaSpi {

    public static void main(String[] args) {
        ServiceLoader<Protocol> serviceLoader = ServiceLoader.load(Protocol.class);
        Iterator<Protocol> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Protocol protocol = iterator.next();
            protocol.start(new URL("localhost",8080));
        }
    }
}
