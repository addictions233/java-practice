package com.one.provider.service;

import com.one.api.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author one
 * http:\\127.0.0.1:8080/service-version/group 算作一个服务
 * 协议, ip, 端口, 请求路径path, 版本号version, 分组group
 */
//@Service(version = "default")
public class DefaultDemoService implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("执行了服务" + name);

        URL url = RpcContext.getContext().getUrl();
        // 正常访问
        return String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);
    }


}
