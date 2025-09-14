package com.one.job.quartz;

import com.one.job.quartz.job.MyJob;
import com.one.job.quartz.listener.MyJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;

public class Main {

    public static void main(String[] args) throws SchedulerException {
        // 把 Job 进一步包装成 JobDetail。
        // 必须要指定 JobName 和 groupName，两个合起来是唯一标识符。分组是为了便于任务的统一管理
        // 可以携带 KV 的数据（JobDataMap），用于扩展属性，在运行的时候可以从 context 获取到
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("name", "one")
                .usingJobData("age", 18)
                .build();

        // 定义 Trigger 触发器
        // 基于 SimpleTrigger 定义了一个每 2 秒钟运行一次、不断重复的 Trigger：
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

        // 通过 Factory 获取调度器的实例，把 JobDetail 和 Trigger绑定，注册到容器中。
        // Scheduler 先启动后启动无所谓，只要有 Trigger 到达触发条件，就会执行任务。
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // 调度器一定是单例的。
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 注册 JobDetail 和 Trigger
        // 绑定关系是 1: N, 一个 JobDetail 可以对应多个 Trigger
        // 可以通过多个 Trigger 触发同一个 JobDetail, 实现更灵活的任务触发
        scheduler.scheduleJob(jobDetail, trigger);

        // 注册 JobListener
        MyJobListener myJobListener = new MyJobListener("myJobListener");
        scheduler.getListenerManager().addJobListener(myJobListener, EverythingMatcher.allJobs());

        try {
            // 启动调度器
            scheduler.start();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            scheduler.shutdown(true);
        }
    }
}
