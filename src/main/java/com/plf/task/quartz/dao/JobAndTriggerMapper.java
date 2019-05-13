package com.plf.task.quartz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.plf.task.quartz.bean.JobAndTrigger;

@Mapper
public interface JobAndTriggerMapper {

	@Select("SELECT qrtz_job_details.* FROM qrtz_job_details JOIN qrtz_triggers"+
			"JOIN qrtz_cron_triggers ON qrtz_job_details.JOB_NAME = qrtz_triggers.JOB_NAME"+
			"AND qrtz_triggers.TRIGGER_NAME = qrtz_cron_triggers.TRIGGER_NAME"+
			"AND qrtz_triggers.TRIGGER_GROUP = qrtz_cron_triggers.TRIGGER_GROUP")
	public List<JobAndTrigger> getJobAndTriggerDetails();
}
