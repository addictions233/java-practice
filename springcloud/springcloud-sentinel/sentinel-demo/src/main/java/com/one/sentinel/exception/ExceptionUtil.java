package com.one.sentinel.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class ExceptionUtil {

    /**
     * 必须定义为静态方法
     */
    public static String fallbackException(Throwable t){
        return "===被异常降级啦===";
    }

    public static String handleException(BlockException ex){
        return "===被限流啦===";
    }
}