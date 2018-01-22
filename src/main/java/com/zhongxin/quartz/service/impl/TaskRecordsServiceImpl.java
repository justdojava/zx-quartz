package com.zhongxin.quartz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhongxin.quartz.dao.TaskRecordsDao;
import com.zhongxin.quartz.entity.TaskRecordsEntity;
import com.zhongxin.quartz.service.TaskRecordsService;

@Service
public class TaskRecordsServiceImpl implements TaskRecordsService{
	
	@Resource
	private TaskRecordsDao recordsDao;
	
	public List<TaskRecordsEntity> getTaskRecordsByTaskNo(String taskNo,int currentPage,String taskStatus){
		return recordsDao.getListByTaskNo(taskNo, currentPage,taskStatus);
	}

	public Integer getCountByTaskNo(String taskNo,String taskStatus){
		return recordsDao.getCountByTaskNo(taskNo,taskStatus);
	}
}
