package com.one.order.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowChecker;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MyBlockExceptionHandler
 * @Description: 当sentinel控制的服务出现限流或者容器降级时,
 *               使用此异常处理器返回我们自定义的异常信息,对用户友好
 * @Author: one
 * @Date: 2022/04/12
 */
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        // 给前端返回json格式的数据
        Map<String, Object> exceptionInfo =  new HashMap<>();
        if (e instanceof FlowException) {  // 限流异常
            exceptionInfo.put("code", 501);
            exceptionInfo.put("msg", "出现限流异常");
        } else if (e instanceof DegradeException) {  // 熔断降级异常
            exceptionInfo.put("code", 502);
            exceptionInfo.put("msg", "出现服务降级异常");
        } else if (e instanceof AuthorityException) {  // 授权异常
            exceptionInfo.put("code", 503);
            exceptionInfo.put("msg", "出现授权异常");
        } else if (e instanceof ParamFlowException) {  // 参数流异常
            exceptionInfo.put("code", 504);
            exceptionInfo.put("msg", "出现参数流异常");
        } else if (e instanceof SystemBlockException) {  // 系统阻塞异常
            exceptionInfo.put("code", 505);
            exceptionInfo.put("msg", "出现系统阻塞异常");
        }
        // 当sentinel出现限流或者熔断降级的异常之后,给用户返回友好的结
        response.setStatus(200);
        response.setHeader("content-type","application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(exceptionInfo));
    }
}
