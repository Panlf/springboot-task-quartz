package com.plf.task.quartz.vo;

import lombok.Data;

@Data
public class QuartzTaskVo {

	private String jobClassName;
	private String jobGroupName;
	private String cronExpression;
	private String description;
}
