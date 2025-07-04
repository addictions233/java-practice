package com.one.configdemo;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.config.listener.Listener;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author Fox
 */
public class ConfigServerDemo {

    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "192.168.65.103:8848";
        String namespace = "c6987f16-2c92-41fb-a3c8-1bb30812d6c1";
        String dataId = "nacos-config-demo.yml";
        String group = "DEFAULT_GROUP";

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);


        //获取配置中心服务
        ConfigService configService = NacosFactory.createConfigService(properties);

        //从配置中心拉取配置
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println("拉取到配置：" + content);
        //注册监听器
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("感知配置变化:" + configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });


        //发布配置 yml格式
        configService.publishConfig(dataId, group,
                "server:\n" +
                        "  port: 8010\n" +
                        "\n" +
                        "spring:\n" +
                        "  application:\n" +
                        "    name: nacos-config-demo\n" +
                        "\n" +
                        "logging:\n" +
                        "  level:\n" +
                        "    com.alibaba.cloud.nacos.client.NacosPropertySourceBuilder: debug\n" +
                        "\n" +
                        "common:\n" +
                        "  name: zhangsan\n" +
                        "  age: 30    ", ConfigType.YAML.getType());


//        Thread.sleep(3000);
//        //从配置中心拉取配置
//        content = configService.getConfig(dataId, group, 5000);
//        System.out.println(content);
//
//        boolean isRemoveOk = configService.removeConfig(dataId, group);
//        System.out.println(isRemoveOk);
//        Thread.sleep(3000);
//
//        content = configService.getConfig(dataId, group, 5000);
//        System.out.println(content);
//        Thread.sleep(300000);


        Thread.sleep(Integer.MAX_VALUE);

    }
}
