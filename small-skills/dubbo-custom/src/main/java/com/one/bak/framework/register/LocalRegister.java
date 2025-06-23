package com.one.bak.framework.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Provider提供的服务
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class LocalRegister {

    private static final Map<String, Class<?>> REGISTER_MAP = new HashMap<>();

    /**
     * 保存provider的提供服务service
     * @param interfaceName 接口名称
     * @param interfaceImplClass 接口的具体功能实现类
     */
    public static void register(String interfaceName, Class<?> interfaceImplClass) {
        REGISTER_MAP.put(interfaceName, interfaceImplClass);
    }

    /**
     * 通过接口名称获取provider提供的接口服务具体实现
     * @param interfaceName 接口名称
     * @return 接口实现
     */
    public static Class<?> get(String interfaceName) {
        return REGISTER_MAP.get(interfaceName);
    }
}
