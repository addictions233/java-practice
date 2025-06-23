package com.one.consumer.loadbalancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.reactive.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.reactive.Request;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Random;

/**
 * @author one
 * @description 自定义Loadbalancer的负载均衡器
 * @date 2024-12-1
 */
public class CustomRandomLoadBalancerClient implements ReactorServiceInstanceLoadBalancer {

    // 服务列表
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public CustomRandomLoadBalancerClient(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable();
        return supplier.get().next().map(this::getInstanceResponse);
    }

    /**
     * 使用随机数获取服务
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(
            List<ServiceInstance> instances) {
        System.out.println("进入了负载均衡策略");
        if (instances.isEmpty()) {
            return new EmptyResponse();
        }

//        System.out.println("进行随机选取服务");
//        // 随机算法
//        int size = instances.size();
//        Random random = new Random();
//        ServiceInstance instance = instances.get(random.nextInt(size));

        System.out.println("只调用第一个");
        ServiceInstance instance = instances.get(0);

        return new DefaultResponse(instance);
    }
}
