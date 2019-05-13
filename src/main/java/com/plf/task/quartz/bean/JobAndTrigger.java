package com.plf.task.quartz.bean;

import java.math.BigInteger;

import lombok.Data;

@Data
public class JobAndTrigger  {
	private String job_name;
	private String job_group;
	private String job_class_name;
	private String trigger_name; 
	private String trigger_group;
	private BigInteger repeat_interval;
	private BigInteger times_triggered; 
	private String cron_expression;
	private String time_zone_id;
}
