package com.one.usersentinel.sentinel;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.one.common.utils.R;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

/**
 * @author Fox
 */
public class ExceptionUtil {

    /**
     * 注意： 必须为 static 函数
     */
    public static R fallback(Integer id, Throwable e){
        return R.error(-1,"===被异常降级啦===");
    }

    public static R handleException(Integer id, BlockException e){
        return R.error(-2,"===被限流啦===");
    }


    /**
     * 注意： static修饰，参数类型不能出错
     * @param request  org.springframework.http.HttpRequest
     * @param body
     * @param execution
     * @param ex
     * @return
     */
    public static SentinelClientHttpResponse handleBlockException(HttpRequest request,
                                                             byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        R r = R.error(-1, "===被限流啦===");
        try {
            return new SentinelClientHttpResponse(new ObjectMapper().writeValueAsString(r));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SentinelClientHttpResponse handleFallback(HttpRequest request,
                                                      byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        R r = R.error(-2, "===被异常降级啦===");
        try {
            return new SentinelClientHttpResponse(new ObjectMapper().writeValueAsString(r));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}