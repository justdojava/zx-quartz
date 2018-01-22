package com.zhongxin.quartz.service;

import java.util.List;

import com.zhongxin.quartz.entity.TaskRecordsEntity;

public interface TaskRecordsService {
	
	public List<TaskRecordsEntity> getTaskRecordsByTaskNo(String taskNo,int currentPage,String taskStatus);

	public Integer getCountByTaskNo(String taskNo,String taskStatus);
}
