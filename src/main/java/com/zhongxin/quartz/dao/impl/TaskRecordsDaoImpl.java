package com.zhongxin.quartz.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zhongxin.quartz.dao.BaseDao;
import com.zhongxin.quartz.dao.TaskRecordsDao;
import com.zhongxin.quartz.entity.TaskRecordsEntity;
@Service("taskRecordsDao")
public class TaskRecordsDaoImpl extends BaseDao implements TaskRecordsDao{

	/**
	 * 根据taskNo查询记录表
	 * @param taskNo
	 * @return
	 */
	public TaskRecordsEntity getRecordsByTaskNo(String taskNo,String timeKeyValue){
		Map<String, String> map = new HashMap<String, String>();
		map.put("taskNo", taskNo);
		map.put("timeKeyValue", timeKeyValue);
		TaskRecordsEntity taskRecordsEntity = getTemplate().selectOne("TaskRecordsMapper.selectByTaskNo", map);
		return taskRecordsEntity;
	}
	
	/**
	 * 保存
	 * @param taskRecordsEntity
	 */
	public Long insert(TaskRecordsEntity taskRecordsEntity){
		getTemplate().insert("TaskRecordsMapper.insert",taskRecordsEntity);
		return taskRecordsEntity.getId();
	}
	
	/**
	 * 根据ID修改
	 * @param recordsEntity
	 */
	public void updateById(TaskRecordsEntity recordsEntity){
		getTemplate().update("TaskRecordsMapper.updateById",recordsEntity);
	}
	
	public TaskRecordsEntity selectByTaskNoAndStatus(String taskNo){
		List<TaskRecordsEntity> recordsList = getTemplate().selectList("TaskRecordsMapper.selectByTaskNoAndStatus",taskNo);
		if(null != recordsList){
			return recordsList.get(0);
		}else{
			return null;
		}
	}
	
	public List<TaskRecordsEntity> getListByTaskNo(String taskNo,int currentPage,String taskStatus){
		Map paramMap = new HashMap<>();
		paramMap.put("taskNo", taskNo);
		paramMap.put("currentPage", getPageNum(currentPage, 10));
		if(StringUtils.isNotBlank(taskStatus)){
			paramMap.put("taskStatus", taskStatus);
		}
		List<TaskRecordsEntity> list = getTemplate().selectList("TaskRecordsMapper.selectListByTaskNo",paramMap);
		return list;
	}
	
	public Integer getCountByTaskNo(String taskNo,String taskStatus){
		Map paramMap = new HashMap<>();
		paramMap.put("taskNo", taskNo);
		if(StringUtils.isNotBlank(taskStatus)){
			paramMap.put("taskStatus", taskStatus);
		}
		Integer count = getTemplate().selectOne("TaskRecordsMapper.selectCountByTaskNo",paramMap);
		return count;
	}
}
