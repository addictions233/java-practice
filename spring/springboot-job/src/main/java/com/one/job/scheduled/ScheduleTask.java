package com.one.job.scheduled;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Scheduled(fixedRate = 5000, fixedDelayString = "${fixedDelay}") // 每5秒执行一次
    public void fixRateTask() {
        System.out.println("线程:" + Thread.currentThread().getName() + "执行固定频率任务:" + System.currentTimeMillis());
    }

    @Scheduled(cron = "0/5 * * * * ?", fixedDelayString = "${fixedDelay}")
    public void cronTask() {
        System.out.println("线程:" + Thread.currentThread().getName() +  ",执行Cron表达式任务:" + System.currentTimeMillis());
    }
}
