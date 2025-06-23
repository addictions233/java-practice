package com.one.order.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class ExceptionUtil {

    public static String fallbackException(Throwable t){
        return "===被异常降级啦===";
    }

    public static String handleException(BlockException ex){
        return "===被限流啦===";
    }
}