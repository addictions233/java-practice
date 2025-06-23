package com.one.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * sentinel服务使用控制台进行流控规则配置:
 *   1. 配置应用名称：-Dproject.name=xxxx
 *   2. 配置控制台地址：-Dcsp.sentinel.dashboard.server=ip:port （默认是 8719），端口冲突会依次递增
 *   注意: Sentinel 会在客户端首次调用时候进行初始化，开始向控制台发送心跳包。因此需要确保客户端有访问量，才能在控制台上看到监控数据。
 *   Sentinel控制台与Java应用之间，实现了一套服务发现机制，集成了Sentinel的应用都会将元数据传递给Sentinel控制台
 */
@RestController
@Slf4j
public class HelloController {

    private static final String RESOURCE_NAME = "HelloWorld";

    /**
     * 将普通的接口定义为资源
     */
    @RequestMapping(value = "/hello")
    public String hello() {
        // 定义一个资源Resource
        try (Entry entry = SphU.entry(RESOURCE_NAME)) {
            // 被保护的逻辑
            log.info("hello world");
            return "hello world";
        } catch (BlockException ex) {
            // 处理被流控的逻辑
            log.info("blocked!");
            return "被流控了";
        }
    }

    /**
     * 定义规则: 一个资源可以定义多个规则, 但是一个规则只针对一个资源
     * 通过代码设置流控规则比较麻烦，最好是通过控制台直接推到服务端
     */
    @PostConstruct
    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        // 定义一个流控规则
        FlowRule rule = new FlowRule();
        //设置受保护的资源
        rule.setResource(RESOURCE_NAME);
        // 设置流控规则 QPS, 也可以设置为基于线程 thread
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源阈值
        rule.setCount(3);
        rules.add(rule);
        // 加载配置好的规则
        FlowRuleManager.loadRules(rules);
    }


    /**
     * 使用sentinel切面进行资源,规则定义
     */
    @SentinelResource(value = RESOURCE_NAME, blockHandler = "handleException",
        fallback = "fallbackException")
//    @SentinelResource(value = RESOURCE_NAME,
//            blockHandler = "handleException",blockHandlerClass = ExceptionUtil.class,
//            fallback = "fallbackException",fallbackClass = ExceptionUtil.class)
    @RequestMapping("/hello2")
    public String hello2() {

        int i = 1 / 0;

        return "helloworld ";
    }


    /**
     * Block 异常处理函数，参数最后多一个 BlockException，其余与原方法hello2一致.
     */
    public String handleException(BlockException ex){
        return "被流控了";
    }

    /**
     * 异常处理函数，参数与原方法hello2一致或加一个 Throwable 类型的参数.
     */
    public String fallbackException(Throwable t){
        return "被异常降级了";
    }


}