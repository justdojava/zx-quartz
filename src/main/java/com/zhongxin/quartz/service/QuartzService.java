package com.zhongxin.quartz.service;

import java.util.List;

import com.zhongxin.quartz.entity.TaskInformationsEntity;
import com.zhongxin.quartz.entity.TaskRecordsEntity;
import com.zhongxin.quartz.vo.TaskInformationsDetailVo;
import com.zhongxin.quartz.vo.TaskInformationsVo;

public interface QuartzService {

	public void initScheduler();
	
	public String resumeScheduler(String key);
	
	public String resumeSchedulerAll();
	
	public String addScheduler(String key);
	
	public TaskRecordsEntity beforeExecute(String taskNo);
	
	public void updateTaskInformations(String taskNo);
	
	public TaskRecordsEntity modifyTaskRecord(int failCount,Long taskRecordsId);
	
	public void saveTaskErrors(String taskRecordsId,String key,String values);
	
	public String delScheduler(String key);
	
	public List<TaskInformationsVo> getList(int currentPage);
	
	public int getTotalCount();
	
	public TaskInformationsDetailVo getTaskDetail(String taskNo);
	
	public int editTaskInformation(TaskInformationsEntity entity);
	
	public TaskInformationsEntity selectTaskInfoById(Long id);
	
	public String executeOnce(String taskNo);
}
