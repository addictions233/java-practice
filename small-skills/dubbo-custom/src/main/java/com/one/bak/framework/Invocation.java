package com.one.bak.framework;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @description: 远程接口调用需要传递的参数
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class Invocation implements Serializable {

    /**
     * 调用的接口名称
     */
    private String interfaceName;

    /**
     * 调用的接口的方法名称
     */
    private String methodName;

    /**
     * 调用的方法的参数类型
     */
    private Class[] parameterTypes;

    /**
     * 调用的方法的参数值
     */
    private Object[] parameterValues;

    public Invocation(String interfaceName, String methodName, Class[] parameterTypes, Object[] parameterValues) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getParameterValues() {
        return parameterValues;
    }
}
