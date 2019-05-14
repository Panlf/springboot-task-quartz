package com.plf.task.quartz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.plf.task.quartz.dto.JobDetailInfo;


@Mapper
public interface JobDetailInfoMapper {

	@Select("select qjd.JOB_NAME,qjd.JOB_GROUP,qjd.JOB_CLASS_NAME,qjd.DESCRIPTION,qt.TRIGGER_GROUP,qt.TRIGGER_NAME,qct.CRON_EXPRESSION,qct.TIME_ZONE_ID"
			+ " from qrtz_job_details qjd"
			+ " left join qrtz_triggers qt on qt.JOB_GROUP=qjd.JOB_GROUP and qt.JOB_NAME = qjd.JOB_NAME"
			+ " left join qrtz_cron_triggers qct on qct.TRIGGER_GROUP = qt.TRIGGER_GROUP and qct.TRIGGER_NAME = qt.TRIGGER_NAME")
	public List<JobDetailInfo> getJobDetailInfo();
}
