package com.plf.task.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution //上一次任务没结束，下一次任务不进行
// 上一个任务结束后 再间隔对应的时间 执行下一个任务
public class TaskOneJob implements Job {

    public TaskOneJob() {

    }
    
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("任务一-----------执行中");
    }
}
