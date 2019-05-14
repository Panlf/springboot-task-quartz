package com.plf.task.quartz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.plf.task.quartz.dao.JobDetailInfoMapper;
import com.plf.task.quartz.dto.JobDetailInfo;

/**
 * 查询库表中的定时任务
 * @author plf 2019年5月14日上午9:55:42
 *
 */
@Service
public class JobDetailInfoService {
	
	@Autowired
	private JobDetailInfoMapper jobDetailInfoMapper;
	
	public PageInfo<JobDetailInfo> getJobDetailInfo(int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<JobDetailInfo> list = jobDetailInfoMapper.getJobDetailInfo();
		PageInfo<JobDetailInfo> page = new PageInfo<JobDetailInfo>(list);
		return page;
	}

}
