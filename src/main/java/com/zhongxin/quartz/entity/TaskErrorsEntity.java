package com.zhongxin.quartz.entity;

public class TaskErrorsEntity extends Entity{

	private long id;
	private String taskExecuteRecordId;
	private String errorKey;
	private byte[] errorValue;
	private String createTime;
	private String lastModifyTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTaskExecuteRecordId() {
		return taskExecuteRecordId;
	}

	public void setTaskExecuteRecordId(String taskExecuteRecordId) {
		this.taskExecuteRecordId = taskExecuteRecordId;
	}

	public String getErrorKey() {
		return errorKey;
	}

	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}

	public byte[] getErrorValue() {
		return errorValue;
	}

	public void setErrorValue(byte[] errorValue) {
		this.errorValue = errorValue;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
