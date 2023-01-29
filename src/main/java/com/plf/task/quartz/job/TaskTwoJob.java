package com.plf.task.quartz.job;

import com.plf.task.quartz.common.QuartzConstant;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskTwoJob implements Job {

    public TaskTwoJob() {

    }
    
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
        String params = jobDataMap.getString(QuartzConstant.QUARTZ_PARAM_NAME);
        log.info("任务二-----------执行中,参数为 ===> {}",params);
    }
}
