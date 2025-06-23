package com.one.nacos.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author one
 * @description 自定义负载均衡策略: 权重算法, 负载均衡策略在实现时使用了模板方法的设计模式, 所以要继承抽象类
 * @date 2024-7-14
 */
public class CustomerWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        // 读取配置文件并且初始化, ribbon内部的, 几乎用不上
    }

    @Override
    public Server choose(Object key) {
        try {
            BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            // 获取微服务的名称
            String serviceName = baseLoadBalancer.getName();
            // 获取Nacos服务发现的相关组件API
            NamingService namingService = discoveryProperties.namingServiceInstance();
            Instance instance = namingService.selectOneHealthyInstance(serviceName);
            // 构建一个server实例返回
            return new NacosServer(instance);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }
}
