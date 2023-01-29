package com.plf.task.quartz.service;

import com.plf.task.quartz.common.QuartzConstant;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.plf.task.quartz.common.ReflectUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@Slf4j
public class TaskQuartzService {
	
	// 加入Qulifier注解，通过名称注入bean
	@Qualifier("scheduler")
	@Resource
	private Scheduler scheduler;
	
	public boolean addJob(String jobClassName,
						  String jobGroupName,
						  String cronExpression,
						  String description,
						  String params) throws Exception {
		if (!CronExpression.isValidExpression(cronExpression)) {
            return false;
		}
		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(ReflectUtils.getObjectByClass(jobClassName).getClass())
				.withIdentity(jobClassName, jobGroupName).withDescription(description).build();

		// 表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

		// 按照cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
				.startNow().withSchedule(scheduleBuilder).build();

		//传递参数
		if(!StringUtils.isEmpty(params)){
			trigger.getJobDataMap().put(QuartzConstant.QUARTZ_PARAM_NAME,params);
		}

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			log.error("创建任务失败,失败原因:{}",e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean updateJob(String jobClassName,
							 String jobGroupName,
							 String cronExpression,
							 String params) throws Exception {
		if (!CronExpression.isValidExpression(cronExpression)) {
            return false;
		}
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);

			//传递参数
			if(!StringUtils.isEmpty(params)){
				trigger.getJobDataMap().put(QuartzConstant.QUARTZ_PARAM_NAME,params);
			}
		} catch (SchedulerException e) {
			log.error("更新定时任务失败,失败原因:{}" ,e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean deleteJob(String jobClassName, String jobGroupName) {
		JobKey jobKey = new JobKey(jobClassName, jobGroupName);
		TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
	    JobDetail jobDetail=null;
		try {
			jobDetail = scheduler.getJobDetail(jobKey);
		} catch (SchedulerException e) {
			log.error("删除{}-任务失败,失败原因:{}",jobClassName,e.getMessage());
			return false;
		}
		try{
		    if(jobDetail!=null && scheduler.checkExists(jobKey)){
		    	scheduler.pauseTrigger(triggerKey);
		    	scheduler.unscheduleJob(triggerKey);
		    	scheduler.deleteJob(jobKey);
	    	}
		}catch (Exception e) {
			log.error("删除{}-任务失败,失败原因:{}",jobClassName,e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean resumeJob(String jobClassName, String jobGroupName) {
		JobKey jobKey = new JobKey(jobClassName, jobGroupName);
        JobDetail jobDetail = null;
		try {
			jobDetail = scheduler.getJobDetail(jobKey);
		} catch (SchedulerException e) {
			log.error("恢复{}-任务失败,失败原因:{}",jobClassName,e.getMessage());
			return false;
		}
        if(jobDetail!=null){
        	try {
				scheduler.resumeJob(jobKey);
			} catch (SchedulerException e) {
				log.error("恢复{}-任务失败,失败原因:{}",jobClassName,e.getMessage());
				return false;
			}
        }
        return true;
	}
	
	public boolean pauseJob(String jobClassName, String jobGroupName) {
		JobKey jobKey = new JobKey(jobClassName, jobGroupName);
        JobDetail jobDetail=null;
		
        try {
			jobDetail = scheduler.getJobDetail(jobKey);
		} catch (SchedulerException e) {
			log.error("暂停任务失败,失败原因:{}",e.getMessage());
			return false;
		}
        
        if(jobDetail!=null){
        	try {
				scheduler.pauseJob(jobKey);
			} catch (SchedulerException e) {
				log.error("暂停任务失败,失败原因:{}",e.getMessage());
				e.printStackTrace();
				return false;
			}
        }
        return true;
	}
	
	public boolean resumeAllJob(){
		try {
			scheduler.resumeAll();
			return true;
		} catch (SchedulerException e) {
			log.error("恢复所有任务失败,失败原因:{}",e.getMessage());
			return false;
		}
	}
}
