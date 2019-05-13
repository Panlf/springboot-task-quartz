package com.plf.task.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskOneJob implements Job {

    public TaskOneJob() {

    }
    
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("任务一-----------执行中");
    }
}
