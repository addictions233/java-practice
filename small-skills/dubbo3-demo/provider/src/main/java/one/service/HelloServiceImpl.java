package one.service;

import com.one.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;


/**
 * @author one
 * @description 测试dubbo3对rest协议的支持
 * @date 2024-11-17
 */
//@DubboService(protocol = "dubbo")
@DubboService(protocol = "rest")
//@DubboService(protocol = "tri")
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello(String name) {
        return "hello," + name;
    }
}
