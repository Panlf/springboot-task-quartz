package com.plf.task.quartz.controller;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.plf.task.quartz.bean.JobAndTrigger;
import com.plf.task.quartz.service.JobAndTriggerService;
import com.plf.task.quartz.vo.QuartzTaskVo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "job")
@Slf4j
public class JobController {

	@Autowired
	private JobAndTriggerService jobAndTriggerService;

	// 加入Qulifier注解，通过名称注入bean
	@Qualifier("scheduler")
	@Autowired	
	private Scheduler scheduler;

	/**
	 * 新增任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@GetMapping(value = "/addjob")
	public void addjob(QuartzTaskVo quartzTaskVo) throws Exception {
		log.info("新增Quartz定时任务");
		addJob(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName(), quartzTaskVo.getCronExpression(),quartzTaskVo.getDescription());
	}

	public void addJob(String jobClassName, String jobGroupName, String cronExpression,String description) throws Exception {
		if (!CronExpression.isValidExpression(cronExpression)) {
            return;
		}
		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
				.withIdentity(jobClassName, jobGroupName).withDescription(description).build();

		// 表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
				.startNow().withSchedule(scheduleBuilder).build();

		try {
			scheduler.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {
			log.error("创建任务失败,失败原因:{}",e.getMessage());
		}
	}

	/**
	 * 暂停任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@GetMapping(value = "/pausejob")
	public void pausejob(QuartzTaskVo quartzTaskVo) throws Exception {
		log.info("新增Quartz定时任务");
		jobPause(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName());
	}

	public void jobPause(String jobClassName, String jobGroupName) throws Exception {
		JobKey jobKey = new JobKey(jobClassName, jobGroupName);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if(jobDetail!=null){
        	scheduler.pauseJob(jobKey);
        }
	}

	/**
	 * 恢复某个任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@GetMapping(value = "/resumejob")
	public void resumejob(QuartzTaskVo quartzTaskVo) throws Exception {
		jobresume(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName());
	}

	public void jobresume(String jobClassName, String jobGroupName) throws Exception {
		JobKey jobKey = new JobKey(jobClassName, jobGroupName);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if(jobDetail!=null){
        	scheduler.resumeJob(jobKey);
        }
	}

	/**
	 * 恢复所有任务
	 * @throws Exception
	 */
	@GetMapping(value = "/resumealljob")
	public void resumealljob() throws Exception {
		scheduler.resumeAll();
	}
	
	/**
	 * 删除任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@PostMapping(value = "/deletejob")
	public void deletejob(QuartzTaskVo quartzTaskVo) throws Exception {
		jobdelete(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName());
	}

	public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
		JobKey jobKey = new JobKey(jobClassName, jobGroupName);
		TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
	    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
	    if(jobDetail!=null && scheduler.checkExists(jobKey)){
	    	scheduler.pauseTrigger(triggerKey);
	    	scheduler.unscheduleJob(triggerKey);
	    	scheduler.deleteJob(jobKey);
	    }
	}
	
	/**
	 * 更新任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@PostMapping(value = "/updatejob")
	public void rescheduleJob(QuartzTaskVo quartzTaskVo) throws Exception {
		updatejob(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName(), quartzTaskVo.getCronExpression());
	}

	public void updatejob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
		if (!CronExpression.isValidExpression(cronExpression)) {
            return;
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
		} catch (SchedulerException e) {
			log.error("更新定时任务失败,失败原因:{}" ,e.getMessage());
		}
	}

	

	@GetMapping(value = "/queryjob")
	public Map<String, Object> queryjob(Integer pageNum,Integer pageSize) {
		PageInfo<JobAndTrigger> jobAndTrigger = jobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("JobAndTrigger", jobAndTrigger);
		map.put("number", jobAndTrigger.getTotal());
		return map;
	}

	/**
	 * @param classname
	 * @return
	 * @throws Exception
	 */
	public static Job getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (Job) class1.newInstance();
	}

}