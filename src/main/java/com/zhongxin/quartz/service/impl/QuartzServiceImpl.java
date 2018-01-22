package com.zhongxin.quartz.service.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zhongxin.quartz.core.QuartzJobFactory;
import com.zhongxin.quartz.dao.TaskErrorsDao;
import com.zhongxin.quartz.dao.TaskInformationsDao;
import com.zhongxin.quartz.dao.TaskRecordsDao;
import com.zhongxin.quartz.entity.TaskErrorsEntity;
import com.zhongxin.quartz.entity.TaskInformationsEntity;
import com.zhongxin.quartz.entity.TaskRecordsEntity;
import com.zhongxin.quartz.enums.StatusEnum;
import com.zhongxin.quartz.enums.TaskStatusEnum;
import com.zhongxin.quartz.service.NoticeService;
import com.zhongxin.quartz.service.QuartzService;
import com.zhongxin.quartz.util.Const;
import com.zhongxin.quartz.util.DateUtil;
import com.zhongxin.quartz.util.ExceptionUtils;
import com.zhongxin.quartz.vo.TaskInformationsDetailVo;
import com.zhongxin.quartz.vo.TaskInformationsVo;

@Service("quartzService")
public class QuartzServiceImpl implements QuartzService{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private TaskInformationsDao taskInformationsDao;
	@Resource
	private TaskRecordsDao recordsDao;
	@Resource
	private TaskErrorsDao taskErrorsDao;
	@Autowired
	SchedulerFactoryBean schedulerBean;
	@Resource
	private NoticeService noticeService;
	
	AtomicInteger ai = new AtomicInteger(0);

	public void initScheduler(){
		List<TaskInformationsEntity> taskList = taskInformationsDao.getTaskList();
		Scheduler scheduler = schedulerBean.getScheduler();
		for(TaskInformationsEntity task : taskList){
			try {
				this.scheduler(task, scheduler);
			} catch (Exception e) {
				logger.error("定时：" + task.getTaskNo() + "启动失败");
			}
		}
	}
	
	/**
	 * 增加任务到定时器
	 * @param key
	 * @return
	 */
	public String addScheduler(String key){
		TaskInformationsEntity entity = taskInformationsDao.getTaskByTaskNo(key);
		if(null != entity){
			Scheduler scheduler = schedulerBean.getScheduler();
			try {
				scheduler.deleteJob(new JobKey(key));
				this.scheduler(entity, scheduler);
				entity.setFrozenStatus(TaskStatusEnum.UNFROZEN);
				entity.setUnfrozenTime(DateUtil.getLastModifyTime());
				entity.setLastModifyTime(DateUtil.getLastModifyTime());
				taskInformationsDao.updateById(entity);
				return "任务启动成功";
			} catch (Exception e) {
				logger.info("异常：",e);
				return "任务启动失败";
			}
		}else{
			return "该任务编号不存在";
		}
	}
	
	/**
	 * 删除任务从定时器中
	 * @param key
	 * @return
	 */
	public String delScheduler(String key){
		TaskInformationsEntity entity = taskInformationsDao.getTaskByTaskNo(key);
		if(null != entity && TaskStatusEnum.UNFROZEN == entity.getFrozenStatus()){
			Scheduler scheduler = schedulerBean.getScheduler();
			try {
				scheduler.deleteJob(new JobKey(key));
				entity.setFrozenStatus(TaskStatusEnum.FROZEN);
				entity.setFrozenTime(DateUtil.getLastModifyTime());
				entity.setLastModifyTime(DateUtil.getLastModifyTime());
				taskInformationsDao.updateById(entity);
				return "暂停任务成功";
			} catch (Exception e) {
				logger.error("异常：",e);
				return "暂停任务异常";
			}
		}else{
			return "该任务编号不存在";
		}
	}
	
	/**
	 * 重启
	 */
	public String resumeScheduler(String key){
		TaskInformationsEntity entity = taskInformationsDao.getTaskByTaskNo(key);
		if(null != entity){
			Scheduler scheduler = schedulerBean.getScheduler();
			try {
				scheduler.deleteJob(new JobKey(key));
				this.scheduler(entity, scheduler);
				return "重启成功";
			} catch (SchedulerException e) {
				logger.info("异常：",e);
				return "重启异常";
			}
		}else{
			return "该任务编号不存在";
		}
	}
	
	/**
	 * 全部重启
	 * @return
	 */
	public String resumeSchedulerAll(){
		List<TaskInformationsEntity> taskList = taskInformationsDao.getTaskList();
		Scheduler scheduler = schedulerBean.getScheduler();
		try {
			scheduler.clear();
		} catch (SchedulerException e1) {
			logger.error("异常:",e1);
		}
		for(TaskInformationsEntity entity : taskList){
			try {
				this.scheduler(entity, scheduler);
			} catch (Exception e) {
				logger.error("异常：",e);
				return "重启异常";
			}
		}
		return "重启成功";
	}
	
	public void scheduler(TaskInformationsEntity task,Scheduler scheduler){
		TriggerKey triggerKey = TriggerKey.triggerKey(task.getTaskNo(), Scheduler.DEFAULT_GROUP);
		JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withDescription(task.getTaskName()).withIdentity(task.getTaskNo(), Scheduler.DEFAULT_GROUP).build();
		jobDetail.getJobDataMap().put("targetObjectId", task.getTaskNo());
		jobDetail.getJobDataMap().put("executorNo", task.getExecutorNo());
		jobDetail.getJobDataMap().put("sendType", task.getSendType());
		jobDetail.getJobDataMap().put("url", task.getUrl());
		jobDetail.getJobDataMap().put("executeParamter", task.getExecuteParamter());
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getSchedulerRule());
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			logger.info("task "+task.getTaskNo()+" schedulerRule :"+task.getSchedulerRule()+" reload succeed");
		} catch (Exception e) {
			logger.error("scheduler--异常：",e);
			throw new RuntimeException();
		}
	}
	
	/**
	 * 执行一次当前任务
	 * @param taskNo
	 * @return
	 */
	public String executeOnce(String taskNo){
		TaskInformationsEntity entity = taskInformationsDao.getTaskByTaskNo(taskNo);
		String msg = "执行成功";
		if(null != entity){
			TaskRecordsEntity recordsEntity =null;
			try {
				recordsEntity = beforeExecute(taskNo);
				if(null==recordsEntity || (StatusEnum.INIT!=recordsEntity.getTaskStatus())){
					logger.error("targetBeanId:" + taskNo+" record error return");
					return "执行失败，该任务已冻结或该时间段可能已经执行。";
				}
				if(Const.ACTIVE_MQ.equals(entity.getSendType())){
					try {
						noticeService.sendMQmsg(taskNo,entity.getExecutorNo(),entity.getExecuteParamter());
					} catch (Exception e) {
						// TODO: handle exception
						logger.error("sendMQmsg send is failed：", e);
						ai.incrementAndGet();
						throw e;
					}
				}else if (Const.URL_REQUEST.equals(entity.getSendType())){
					try {
						noticeService.httpRequest(entity.getUrl(),entity.getExecuteParamter());
					} catch (Exception e) {
						logger.error("http request is failed：",e);
						ai.incrementAndGet();
						throw e;
					}
				}else{
					return "不支持当前类型";
				}
			} catch (Exception e) {
				logger.info("QuartzJobFactory-execute thorw：",e);
				saveTaskErrors(String.valueOf(recordsEntity.getId()),entity.getExecutorNo()+e.getMessage(), ExceptionUtils.getStackTrace(e));
				msg = "执行失败";
			}
			if(ai.get() > 0){
				if(null!=recordsEntity){
					recordsEntity = modifyTaskRecord(ai.get(), recordsEntity.getId());
				}
				updateTaskInformations(taskNo);
			}
			/*finally{
				if(null!=recordsEntity){
					recordsEntity = modifyTaskRecord(ai.get(), recordsEntity.getId());
				}
				updateTaskInformations(taskNo);
			}*/
		}else{
			return "当前任务不存在";
		}
		return msg;
	}
	
	/**
	 * 执行前操作
	 * @return
	 */
	public TaskRecordsEntity beforeExecute(String taskNo){
		try {
			TaskRecordsEntity taskRecordsEntity =null;
			TaskInformationsEntity taskInformationsEntity = taskInformationsDao.getTaskByTaskNo(taskNo);
			if(null == taskInformationsEntity || TaskStatusEnum.FROZEN == taskInformationsEntity.getFrozenStatus()){
				logger.warn("taskNo :"+taskNo+" is not exits or taskNo is frozed.",taskInformationsEntity);
				return null;
			}
			String timeKeyValue = DateUtil.getTimeKeyValue(taskInformationsEntity.getTimeKey());
			logger.info("timeKeyValue：" + timeKeyValue);
//			TaskRecordsEntity recordsEntity = recordsDao.getRecordsByTaskNo(taskNo,timeKeyValue);
//			if(null != recordsEntity){
//				logger.info("task no :" + taskNo + " this time " + timeKeyValue + " hava been executed");
//				return null;
//			}

//			taskInformationsEntity.setFrozenStatus(TaskStatusEnum.FROZEN);
			taskInformationsEntity.setFrozenTime(DateUtil.getLastModifyTime());
			taskInformationsEntity.setLastModifyTime(DateUtil.getLastModifyTime());
			taskInformationsDao.updateById(taskInformationsEntity);
			taskRecordsEntity = this.saveTaskRecords(taskNo, timeKeyValue);
			return taskRecordsEntity;
		} catch (Exception e) {
			logger.error("QuartzServiceImpl-beforeExecute is failed",e);
			throw e;
		}
	}
	
	/**
	 * 保存定时记录表
	 * @param taskNo
	 * @param timeKey
	 */
	public TaskRecordsEntity saveTaskRecords(String taskNo,String timeKeyValue){
		TaskRecordsEntity entity = new TaskRecordsEntity();
		try {
			entity.setCreateTime(String.valueOf(DateUtil.getLastModifyTime()));
			entity.setExecuteTime(String.valueOf(DateUtil.getLastModifyTime()));
			entity.setFailcount(0);
			entity.setLastModifyTime(String.valueOf(DateUtil.getLastModifyTime()));
			entity.setTaskNo(taskNo);
			entity.setTaskStatus(StatusEnum.INIT);
			entity.setTimeKeyValue(timeKeyValue);
			recordsDao.insert(entity);
		} catch (Exception e) {
			logger.error("saveTaskRecords error：",e);
		}
		return entity;
	}
	
	public void saveTaskErrors(String taskRecordsId,String key,String values){
		TaskErrorsEntity errorsEntity = new TaskErrorsEntity();
		errorsEntity.setCreateTime(String.valueOf(DateUtil.getLastModifyTime()));
		errorsEntity.setLastModifyTime(String.valueOf(DateUtil.getLastModifyTime()));
		errorsEntity.setErrorKey(key);
		errorsEntity.setErrorValue(values.replaceAll("'", "").getBytes());
		errorsEntity.setTaskExecuteRecordId(taskRecordsId);
		taskErrorsDao.saveTaskErrors(errorsEntity);
	}
	
	public void updateTaskInformations(String taskNo){
		TaskInformationsEntity taskInformationsEntity = taskInformationsDao.getTaskByTaskNo(taskNo);
		if(null != taskInformationsEntity && TaskStatusEnum.FROZEN == taskInformationsEntity.getFrozenStatus()){
			try {
				taskInformationsEntity.setFrozenStatus(TaskStatusEnum.UNFROZEN);
				taskInformationsEntity.setUnfrozenTime(DateUtil.getLastModifyTime());
				taskInformationsEntity.setLastModifyTime(DateUtil.getLastModifyTime());
				taskInformationsDao.updateById(taskInformationsEntity);
			} catch (Exception e) {
				logger.error("updateTaskInformations error：",e);
			}
		}
	}
	
	/**
	 * 更新任务记录表
	 * @param failCount
	 * @param taskRecordsId
	 */
	public TaskRecordsEntity modifyTaskRecord(int failCount,Long taskRecordsId){
		TaskRecordsEntity entity = new TaskRecordsEntity();
		entity.setId(taskRecordsId);
		entity.setFailcount(failCount);
		if(failCount > 0){
			entity.setTaskStatus(StatusEnum.FAILED);
		}else{
			entity.setTaskStatus(StatusEnum.SUCCESS);
		}
		entity.setLastModifyTime(String.valueOf(DateUtil.getLastModifyTime()));
		recordsDao.updateById(entity);
		return entity;
	}
	
	public List<TaskInformationsVo> getList(int currentPage){
		return taskInformationsDao.getList(currentPage);
	}
	
	public int getTotalCount(){
		return taskInformationsDao.getTotalCount();
	}
	
	public TaskInformationsDetailVo getTaskDetail(String taskNo){
		return taskInformationsDao.getTaskDetail(taskNo);
	}
	
	public int editTaskInformation(TaskInformationsEntity entity){
		boolean flag = false;
		int count = 0;
		if(TaskStatusEnum.FROZEN == entity.getFrozenStatus()){
			entity.setFrozenTime(DateUtil.getLastModifyTime());
		}else{
			entity.setUnfrozenTime(DateUtil.getLastModifyTime());
		}
		if(null != entity.getId() && 0 != entity.getId()){
			// 修改
			entity.setLastModifyTime(DateUtil.getLastModifyTime());
			count = taskInformationsDao.updateById(entity);
			if(count > 0){
				// 重新装载定时器
				Scheduler scheduler = schedulerBean.getScheduler();
				try {
					scheduler.deleteJob(new JobKey(entity.getTaskNo()));
					if(TaskStatusEnum.UNFROZEN == entity.getFrozenStatus()){
						this.scheduler(entity, scheduler);
					}
				} catch (Exception e) {
					logger.error("移除任务异常：",e);
					flag = true;
				}
				if(flag){
					entity.setFrozenStatus(TaskStatusEnum.FROZEN);
					entity.setLastModifyTime(DateUtil.getLastModifyTime());
					taskInformationsDao.updateById(entity);
					return 2;
				}
			}
		}else{
			// 增加
			count = taskInformationsDao.addTaskInformation(entity);
		}
		return count;
	}
	
	public TaskInformationsEntity selectTaskInfoById(Long id){
		return taskInformationsDao.selectById(id);
	}
	
}
