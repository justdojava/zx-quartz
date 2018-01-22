package com.zhongxin.quartz.dao;

import java.util.List;

import com.zhongxin.quartz.entity.TaskInformationsEntity;
import com.zhongxin.quartz.vo.TaskInformationsDetailVo;
import com.zhongxin.quartz.vo.TaskInformationsVo;

public interface TaskInformationsDao {
	
	public List<TaskInformationsEntity> getTaskList();
	
	public TaskInformationsEntity getTaskByTaskNo(String taskNo);
	
	public int updateById(TaskInformationsEntity entity);
	
	public List<TaskInformationsVo> getList(int currentPage);

	public int getTotalCount();
	
	public TaskInformationsDetailVo getTaskDetail(String taskNo);
	
	public int addTaskInformation(TaskInformationsEntity entity);
	
	public TaskInformationsEntity selectById(long id);
	
}
