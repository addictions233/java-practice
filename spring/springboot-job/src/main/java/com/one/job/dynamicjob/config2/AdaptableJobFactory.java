package com.one.job.dynamicjob.config2;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.scheduling.quartz.DelegatingJob;
import org.springframework.util.ReflectionUtils;


public class AdaptableJobFactory implements JobFactory {

    /**
     * Called by the scheduler at the time of the trigger firing, in order to
     * produce a <code>Job</code> instance on which to call execute.
     */
    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        try {
            Object jobInstance = createJobInstance(bundle);
            return adapt(jobInstance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Class<? extends Job> jobClass = bundle.getJobDetail().getJobClass();
        return ReflectionUtils.accessibleConstructor(jobClass).newInstance();
    }

    private Job adapt(Object jobInstance) {
        if (jobInstance instanceof Job) {
            return (Job) jobInstance;
        }

        if (jobInstance instanceof Runnable) {
            return new DelegatingJob((Runnable) jobInstance);
        }

        throw new IllegalArgumentException("Job instance must be either a Quartz Job or a Runnable");
    }
}
