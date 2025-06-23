package com.one.bak.framework.protocol.http;

import com.one.bak.framework.Invocation;
import com.one.bak.framework.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * @description: 对于tomcat的http请求的处理器
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class HttpServerHandler {

    public void handler(HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(request.getInputStream());
            Invocation invocation = (Invocation) objectInputStream.readObject();

            // 进行方法调用
            String interfaceName = invocation.getInterfaceName();
            Class<?> clazz = LocalRegister.get(interfaceName);
            Method method = clazz.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            Object result = method.invoke(clazz.getDeclaredConstructor().newInstance(), invocation.getParameterValues());
            System.out.println("tomcat:" + result);

            // 响应结果
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(result);
            IOUtils.write(byteArrayOutputStream.toByteArray(), response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
