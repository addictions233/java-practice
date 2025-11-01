package com.one.job.quartzjob;

import com.one.job.quartz.job.MyJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 在spring项目中使用Quartz创建定时任务
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail printTimeJobDetail(){
        return JobBuilder.newJob(MyJob.class)
                .withIdentity("msbJob")
                .usingJobData("msb", "涛哥")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger printTimeJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(printTimeJobDetail())
                .withIdentity("quartzTaskService")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
