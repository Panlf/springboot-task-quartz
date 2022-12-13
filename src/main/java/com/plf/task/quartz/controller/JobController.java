package com.plf.task.quartz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.plf.task.quartz.common.Result;
import com.plf.task.quartz.dto.JobDetailInfo;
import com.plf.task.quartz.service.JobDetailInfoService;
import com.plf.task.quartz.service.TaskQuartzService;
import com.plf.task.quartz.vo.QuartzTaskVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 定时任务控制器
 * @author plf 2019年5月14日上午10:32:44
 *
 */
@Api("增删查改定时任务")
@RestController
@RequestMapping(value = "job")
@Slf4j
public class JobController {

	@Resource
	private JobDetailInfoService jobDetailInfoService;

	@Resource
	private TaskQuartzService taskQuartzService;
	
	/**
	 * 新增任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@ApiOperation("增加定时任务")
	@ApiImplicitParam(name="addjob",dataTypeClass=QuartzTaskVo.class)
	@PostMapping(value = "/addjob")
	public Result addjob(QuartzTaskVo quartzTaskVo) throws Exception {
		log.info("新增Quartz定时任务");
		boolean flag = taskQuartzService.addJob(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName(), quartzTaskVo.getCronExpression(),quartzTaskVo.getDescription());
		return Result.result(flag, "处理完毕", null);
	}
	
	/**
	 * 暂停任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@ApiOperation("暂停定时任务")
	@ApiImplicitParam(name="pausejob",dataTypeClass=QuartzTaskVo.class)
	@GetMapping(value = "/pausejob")
	public Result pausejob(QuartzTaskVo quartzTaskVo) throws Exception {
		log.info("暂停Quartz定时任务");
		boolean flag=taskQuartzService.jobPause(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName());
		return Result.result(flag, "处理完毕", null);
	}

	/**
	 * 恢复某个任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@ApiOperation("恢复某个任务")
	@ApiImplicitParam(name="resumejob",dataTypeClass=QuartzTaskVo.class)
	@GetMapping(value = "/resumejob")
	public Result resumejob(QuartzTaskVo quartzTaskVo) throws Exception {
		log.info("恢复Quartz定时任务");
		boolean flag=taskQuartzService.jobresume(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName());
		return Result.result(flag, "处理完毕", null);
	}

	/**
	 * 恢复所有任务
	 * @throws Exception
	 */
	@ApiOperation("恢复所有任务")
	@GetMapping(value = "/resumealljob")
	public Result resumealljob() {
		log.info("恢复Quartz所有定时任务");
		boolean flag=taskQuartzService.resumeAllJob();
		return Result.result(flag, "处理完毕", null);
	}
	
	/**
	 * 删除任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@ApiOperation("删除任务")
	@ApiImplicitParam(name="deletejob",dataTypeClass=QuartzTaskVo.class)
	@PostMapping(value = "/deletejob")
	public Result deletejob(QuartzTaskVo quartzTaskVo) throws Exception {
		log.info("删除Quartz定时任务");
		boolean flag=taskQuartzService.jobdelete(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName());
		return Result.result(flag, "处理完毕", null);
	}

	/**
	 * 更新任务
	 * @param quartzTaskVo
	 * @throws Exception
	 */
	@ApiOperation("更新任务")
	@ApiImplicitParam(name="updatejob",dataTypeClass=QuartzTaskVo.class)
	@PostMapping(value = "/updatejob")
	public Result rescheduleJob(QuartzTaskVo quartzTaskVo) throws Exception {
		log.info("更新Quartz定时任务");
		boolean flag=taskQuartzService.updatejob(quartzTaskVo.getJobClassName(), quartzTaskVo.getJobGroupName(), quartzTaskVo.getCronExpression());
		return Result.result(flag, "处理完毕", null);
	}

	/**
	 * 查找库表中的定时任务
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@ApiOperation("分页查找库表中的定时任务")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pageNumber",value="页码",required=true,dataTypeClass=Integer.class),
		@ApiImplicitParam(name="pageSize",value="每页数量",required=true,dataTypeClass=Integer.class)
	})
	@GetMapping(value = "/queryjob")
	public Result queryjob(@RequestParam(defaultValue="1") Integer pageNumber,@RequestParam(defaultValue="5") Integer pageSize) {
		PageInfo<JobDetailInfo> jobDetailInfo = jobDetailInfoService.getJobDetailInfo(pageNumber, pageSize);
		return Result.success(jobDetailInfo);
	}
}