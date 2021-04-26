package com.plus.bysj.account.quartz;


import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class QuartzManager {
    private static final Logger log = LoggerFactory.getLogger(QuartzManager.class);
    @Autowired
    private Scheduler scheduler ;
    private static String JOB_GROUP_NAME = "JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "TRIGGERGROUP_NAME";

    /**
     *   添加一个定时任务
     *   默认任务组名，触发器组名
     * @param jobName
     * @param triggerName
     * @param jobClass
     * @param time 时间，如需1分钟，传入60*1000L
     * @param map
     */
    public  void addJob(String jobName,String triggerName,Class jobClass, Long time, Map<String, Object> map) {
        String cron = QuartzCronDateUtils.getCron(new Date(new  Date().getTime()+time));
        addJob(jobName, JOB_GROUP_NAME, triggerName, TRIGGER_GROUP_NAME,jobClass, cron, map);

    }

    /**
     * 移除一个任务
     * 默认任务组名，触发器组名
     * @param jobName
     * @param triggerName
     */
    public  void removeJob(String jobName,String triggerName ){
       removeJob(jobName, JOB_GROUP_NAME, triggerName, TRIGGER_GROUP_NAME);
    }

    /**
     * 功能： 添加一个定时任务
     *
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @param jobClass
     *            任务的类类型 eg:TimedMassJob.class
     * @param cron
     *            时间设置 表达式，参考quartz说明文档
     * @param map
     *            参数需要进行传参的值
     */
    public  void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                        Class jobClass, String cron, Map<String, Object> map) {
        try {

            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            //System.out.println("jobDetail.getKey:"+jobDetail.getKey());//jobDetail.getKey:1.1
            log.info("addJob:"+jobName);
            // 触发器
           /* if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                    // 该数据可以通过Job中的JobDataMap dataMap =
                    // context.getJobDetail().getJobDataMap();来进行参数传递值
                    jobDetail.getJobDataMap().put("data" + (i + 1), objects[i]);
                }
            }*/
            // 数据可以通过Job中的JobDataMap dataMap =
            // context.getJobDetail().getJobDataMap();来进行参数传递值
            jobDetail.getJobDataMap().putAll(map);

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 功能：修改一个任务的触发时间
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @param cron
     *            时间设置，参考quartz说明文档
     */
    public  void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                               String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public  void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));

            //System.out.println("removeJob:"+JobKey.jobKey(jobName));
            log.info("removeJob:"+JobKey.jobKey(jobName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * 功能：启动所有定时任务
     */
    public  void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能：关闭所有定时任务
     */
    public  void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
