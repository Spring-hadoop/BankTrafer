package com.plus.bysj.account.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class HelpCutoffTimeJobNew implements Job {

    private static final Logger log = LoggerFactory.getLogger(HelpCutoffTimeJobNew.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        String jobName = context.getJobDetail().getKey().getName();
        //System.out.println(jobName+"问题结算定时任务开启..");
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String a = String.valueOf(data.get("a" ));
        log.info("执行定时任务:"+jobName);

        //更新job的执行状态
        //sharesMapper.updateJobStatus(jobName);
        log.info("定时任务"+jobName+"执行结束");
        //System.out.println(jobName+"问题结算定时任务结束..");
    }

}

