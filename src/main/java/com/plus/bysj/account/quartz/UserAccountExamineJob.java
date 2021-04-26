package com.plus.bysj.account.quartz;

import com.plus.bysj.account.service.AccountUserService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.quartz
 * @ClassName: UserAccountExamineJob
 * @Author: rh
 * @Description:
 * @Date: 2021/4/25 20:08
 */
@Component
public class UserAccountExamineJob implements Job {
    @Autowired
    private AccountUserService service;

    private static final Logger log = LoggerFactory.getLogger(UserAccountExamineJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getKey().getName();
        //System.out.println(jobName+"问题结算定时任务开启..");
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String addr = String.valueOf(data.get("addr"));
        String cardNum = String.valueOf(data.get("cardNum"));
        log.info("执行定时任务:"+jobName);
        service.userAccountExamine(cardNum,addr,null);
        //更新job的执行状态
        //sharesMapper.updateJobStatus(jobName);
        log.info("定时任务"+jobName+"执行结束");
    }
}
