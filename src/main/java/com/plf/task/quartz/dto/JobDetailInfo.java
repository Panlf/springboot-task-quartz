package com.plf.task.quartz.dto;

import lombok.Data;

@Data
public class JobDetailInfo  {
	private String JOB_NAME;
	private String JOB_GROUP;
	private String JOB_CLASS_NAME;
	private String TRIGGER_NAME; 
	private String TRIGGER_GROUP;
	private String DESCRIPTION;
	private String CRON_EXPRESSION;
	private String TIME_ZONE_ID;
}
