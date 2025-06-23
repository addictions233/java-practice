package com.one.order.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.one.order.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author one
 * @description 学习sentinel中的资源和规则
 * @date 2024-9-28
 */
@RestController
@Slf4j
public class HelloController {

    private static final String RESOURCE_NAME = "HelloWorld";

    /**
     * 定义资源
     */
    @GetMapping("/hello")
    public String hello() {
        try (Entry entry = SphU.entry(RESOURCE_NAME);){
            // 被保护的逻辑
            log.info("hello world");
            return "hello world";
        } catch (BlockException e) {
            log.info("被限制了");
            return "blocking!";
        }
    }

    /**
     * 定义流控规则: 一个资源可以定义多个规则, 一个规则针对一个资源
     */
    @PostConstruct
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        // 设置被保护的资源
        rule.setRefResource(RESOURCE_NAME);
        // 设置流控规则
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源阀值
        rule.setCount(1);
        rules.add(rule);
        // 加载配置好的规则
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 注解@SentinelResource本质上还是AOP，将对资源访问的前后处理逻辑封装到切面中了
     */
    //    @SentinelResource(value = RESOURCE_NAME, blockHandler = "handleException",
//        fallback = "fallbackException")
    @SentinelResource(value = RESOURCE_NAME,
            blockHandler = "handleException",blockHandlerClass = ExceptionUtil.class,
            fallback = "fallbackException",fallbackClass = ExceptionUtil.class)
    @RequestMapping("/hello2")
    public String hello2() {

//        int i = 1 / 0;

        return "helloworld ";
    }


    // Block 异常处理函数，参数最后多一个 BlockException，其余与原方法hello2一致.
    public String handleException(BlockException ex){
        return "被流控了";
    }

    // Fallback 异常处理函数，参数与原方法hello2一致或加一个 Throwable 类型的参数.
    public String fallbackException(Throwable t){
        return "被异常降级了";
    }

}
