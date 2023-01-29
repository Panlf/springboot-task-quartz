package com.plf.task.quartz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务VO类
 * @author plf 2019年5月14日上午9:31:18
 *
 */
@Data
@ApiModel("定时任务VO类")
public class QuartzTaskVo {
	/**
	 * 任务全类名
	 */
	@ApiModelProperty(value="任务全类名",example="com.plf.task.quartz.job.TaskOneJob")
	private String jobClassName;
	/**
	 * 组名
	 */
	@ApiModelProperty(value="组名",example="显示任务")
	private String jobGroupName;
	/**
	 * 表达式
	 */
	@ApiModelProperty(value="任务表达式",example="*/5 * * * * ?")
	private String cronExpression;
	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述",example="任务功能")
	private String description;
	/**
	 * 参数
	 */
	@ApiModelProperty(value = "参数",example = "testParam")
	private String params;
}
