package com.one.bak.framework;

import com.one.bak.framework.protocol.Protocol;
import com.one.bak.framework.protocol.ProtocolFactory;
import com.one.bak.framework.protocol.http.HttpProtocol;
import com.one.bak.framework.register.RemoteFileRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @description: Consumer对provider接口生成代理对象
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class ProxyFactory {

    public static <T> T getProxy(Class<T> interfactClass) {
        return (T)Proxy.newProxyInstance(interfactClass.getClassLoader(), new Class[]{interfactClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 当provider的接口实现还没有完成时, 返回mock数据
                String mock = System.getProperty("mock");
                if (mock != null && mock.startsWith("return:")) {
                    return mock.replace("return:","");
                }
                // 构建rpc调用的传递参数
                Invocation invocation = new Invocation(interfactClass.getName(), method.getName(), method.getParameterTypes(), args);
                // 获取rpc调用的请求url
                List<URL> urlList = RemoteFileRegister.get(interfactClass.getName());
                URL url = LoadBalance.getUrl(urlList);

                // 进行rpc调用
                Protocol protocol = ProtocolFactory.getProtocol();
                return protocol.send(url, invocation);
            }
        });

    }
}
