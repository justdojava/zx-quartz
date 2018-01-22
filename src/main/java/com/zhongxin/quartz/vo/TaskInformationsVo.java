package com.zhongxin.quartz.vo;


public class TaskInformationsVo {

	private Long id;
	private String taskNo;
	private String taskName;
	private String schedulerRule;
	private String frozenStatus;
	private String executorNo;
	private String sendType;
	private String url;
	private String executeParamter;
	private String lastModifyTime;
	private String timeKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFrozenStatus() {
		return frozenStatus;
	}

	public void setFrozenStatus(String frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	public String getExecutorNo() {
		return executorNo;
	}

	public void setExecutorNo(String executorNo) {
		this.executorNo = executorNo;
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

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getTimeKey() {
		return timeKey;
	}

	public void setTimeKey(String timeKey) {
		this.timeKey = timeKey;
	}

}
