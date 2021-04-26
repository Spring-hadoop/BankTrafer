package com.plus.bysj.account.quartz;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
@Configuration
public class QuartzConfig {
    @Bean(name = "scheduler")
    public Scheduler scheduler(MyJobFactory myJobFactory) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(myJobFactory);
        System.out.println("myJobFactory:" + myJobFactory);
        schedulerFactoryBean.afterPropertiesSet();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }
}


