package com.one.threadpool.schedule;

import com.one.threadpool.service.AsyncThreadPoolDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ScheduleTask: Spring提供的定时任务
 * @Description: 异步线程执行 + scheduled定时任务
 * @Author: one
 * @Date: 2021/05/08
 */
@Component
public class ScheduleTask {

    @Autowired
    private AsyncThreadPoolDemo asyncThreadPoolDemo;

    /**
     * 这里结合Spring的定时任务来测试异步线程池
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void run(){
        asyncThreadPoolDemo.printNumber();
    }


}
