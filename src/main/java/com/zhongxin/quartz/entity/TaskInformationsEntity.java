package com.zhongxin.quartz.entity;

import com.zhongxin.quartz.enums.TaskStatusEnum;

public class TaskInformationsEntity extends Entity{

	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer version;
	private String taskNo;
	private String taskName;
	private String schedulerRule;
	private TaskStatusEnum frozenStatus;
	private String executorNo;
	private String sendType;
	private String url;
	private String executeParamter;
	private Long frozenTime;
	private Long unfrozenTime;
	private Long createTime;
	private Long lastModifyTime;
	private String timeKey;

	public String getTimeKey() {
		return timeKey;
	}

	public void setTimeKey(String timeKey) {
		this.timeKey = timeKey;
	}

	public Long getFrozenTime() {
		return frozenTime;
	}

	public void setFrozenTime(Long frozenTime) {
		this.frozenTime = frozenTime;
	}

	public Long getUnfrozenTime() {
		return unfrozenTime;
	}

	public void setUnfrozenTime(Long unfrozenTime) {
		this.unfrozenTime = unfrozenTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExecuteParamter() {
		return executeParamter;
	}

	public void setExecuteParamter(String executeParamter) {
		this.executeParamter = executeParamter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getSchedulerRule() {
		return schedulerRule;
	}

	public void setSchedulerRule(String schedulerRule) {
		this.schedulerRule = schedulerRule;
	}

	public TaskStatusEnum getFrozenStatus() {
		return frozenStatus;
	}

	public void setFrozenStatus(TaskStatusEnum frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	public String getExecutorNo() {
		return executorNo;
	}

	public void setExecutorNo(String executorNo) {
		this.executorNo = executorNo;
	}

}
