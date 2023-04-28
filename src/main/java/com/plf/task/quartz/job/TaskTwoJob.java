package com.plf.task.quartz.job;

import com.plf.task.quartz.common.QuartzConstant;
import com.plf.task.quartz.service.HelloService;
import com.plf.task.quartz.service.JobDetailInfoService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Slf4j
public class TaskTwoJob implements Job {

    public TaskTwoJob() {

    }

    @Resource
    private HelloService helloService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
        String params = jobDataMap.getString(QuartzConstant.QUARTZ_PARAM_NAME);
        helloService.sayHello();
        log.info("任务二-----------执行中,参数为 ===> {}",params);
    }
}
