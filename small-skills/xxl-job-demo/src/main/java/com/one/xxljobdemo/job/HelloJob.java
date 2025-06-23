package com.one.xxljobdemo.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author one
 * @description TODO
 * @date 2022-8-20
 */
@Component
public class HelloJob {

    @Value("${server.port}")
    private int appPort;

    /**
     * 调度器 ---> 执行器(任务1,任务2,...)
     *  每个方法都是一个任务
     *
     * @param param 入参
     * @return  {@link ReturnT}
     * @throws Exception
     */
    @XxlJob("helloJob")
    public ReturnT<String> hello(String param) throws Exception {
        System.out.println("helloJob："+ LocalDateTime.now() + ",端口号" + appPort);
        return ReturnT.SUCCESS;
    }
}
