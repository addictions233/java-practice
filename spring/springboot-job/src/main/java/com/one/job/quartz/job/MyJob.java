package com.one.job.quartz.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("MyJob execute");
    }


}
