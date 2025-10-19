package com.one.job.quartz;


import com.one.job.quartz.job.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class MyScheduler {
    public static void main(String[] args) throws SchedulerException {

        // JobDetail
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("msb", "涛哥")
                .usingJobData("666", 5.21F)
                .build();

        // Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        // SchedulerFactory
        SchedulerFactory factory = new StdSchedulerFactory();

        // Scheduler
        Scheduler scheduler = factory.getScheduler();

        // 绑定关系是1：N
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动任务
        scheduler.start();
    }

}
