package com.one.job.dynamicjob.config2;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringJobFactory extends AdaptableJobFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;



    /**
     * Called by the scheduler at the time of the trigger firing, in order to
     * produce a <code>Job</code> instance on which to call execute.
     *
     * @param bundle
     */
    @Override
    public Object createJobInstance(TriggerFiredBundle bundle) throws Exception{
        if (applicationContext != null) {
          return applicationContext.getAutowireCapableBeanFactory().createBean(bundle.getJobDetail().getJobClass(),
                    AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR, false);
        }
        return super.createJobInstance(bundle);
    }


    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
