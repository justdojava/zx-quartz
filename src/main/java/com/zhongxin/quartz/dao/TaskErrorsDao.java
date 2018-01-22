package com.zhongxin.quartz.dao;

import java.util.List;
import java.util.Map;

import com.zhongxin.quartz.entity.TaskErrorsEntity;

public interface TaskErrorsDao {
	
	public void saveTaskErrors(TaskErrorsEntity taskErrorsEntity);

	public List<Map<String, String>> getErrorsByTaskExecuteRecordId(String recordId);
}
