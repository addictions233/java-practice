package com.one.user.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class CustomLoadBalancerConfiguration {

    /**
     * 自定义负载均衡策略
     * @param environment
     * @param loadBalancerClientFactory
     * @param nacosDiscoveryProperties
     * @return
     */
    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory, NacosDiscoveryProperties nacosDiscoveryProperties){
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        // 使用NacosLoadBalancer作为负载均衡器, 当然也可以自己实现接口, 定义自己的负载均衡器
        return new NacosLoadBalancer(loadBalancerClientFactory
                .getLazyProvider(name, ServiceInstanceListSupplier.class),name,nacosDiscoveryProperties);
    }
}