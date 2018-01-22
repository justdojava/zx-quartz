package com.zhongxin.quartz.core;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zhongxin.quartz.entity.TaskRecordsEntity;
import com.zhongxin.quartz.enums.StatusEnum;
import com.zhongxin.quartz.service.NoticeService;
import com.zhongxin.quartz.service.QuartzService;
import com.zhongxin.quartz.util.Const;
import com.zhongxin.quartz.util.ExceptionUtils;
import com.zhongxin.quartz.util.SpringContext;

@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	AtomicInteger ai = new AtomicInteger(0);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ai = new AtomicInteger(0);
		String targetBeanId = context.getMergedJobDataMap().getString("targetObjectId");
		String executorNo = context.getMergedJobDataMap().getString("executorNo");
		String sendType = context.getMergedJobDataMap().getString("sendType");
		String executeParamter = context.getMergedJobDataMap().getString("executeParamter");
		String url = context.getMergedJobDataMap().getString("url");
		logger.info("targetBeanId:" + targetBeanId + "|executorNo:"+ executorNo+ "|sendType:"+ sendType
						+ "|executeParamter:"+ executeParamter+ "|url:"+ url);
		QuartzService quartzService = (QuartzService) SpringContext.getBean("quartzService");
		TaskRecordsEntity recordsEntity =null;
		try {
			recordsEntity = quartzService.beforeExecute(targetBeanId);
			if(null==recordsEntity || (StatusEnum.INIT!=recordsEntity.getTaskStatus())){
				logger.error("targetBeanId:" + targetBeanId+" record error return");
				return;
			}
			NoticeService noticeService = (NoticeService) SpringContext.getBean("noticeService");
			if (Const.ACTIVE_MQ.equals(sendType)) {
				try {
					noticeService.sendMQmsg(targetBeanId,executorNo,executeParamter);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("sendMQmsg send is failed：", e);
					ai.incrementAndGet();
					throw e;
				}
			} else if(Const.URL_REQUEST.equals(sendType)){
				try {
					noticeService.httpRequest(url,executeParamter);
				} catch (Exception e) {
					logger.error("http request is failed：",e);
					ai.incrementAndGet();
					throw e;
				}
			}else{
				logger.info("不支持改类型任务调度，当前类型为：" + sendType);
			}
		} catch (Exception e) {
			logger.info("QuartzJobFactory-execute thorw：",e);
			ai.incrementAndGet();
			quartzService.saveTaskErrors(String.valueOf(recordsEntity.getId()),executorNo+e.getMessage(), ExceptionUtils.getStackTrace(e));
		}
		if(ai.get() > 0){
			if(null!=recordsEntity){
				recordsEntity = quartzService.modifyTaskRecord(ai.get(), recordsEntity.getId());
			}
			quartzService.updateTaskInformations(targetBeanId);
		}
		// 接收到执行端返回结果在修改任务为未冻结
		/*finally{
			if(null!=recordsEntity){
				recordsEntity = quartzService.modifyTaskRecord(ai.get(), recordsEntity.getId());
			}
			quartzService.updateTaskInformations(targetBeanId);
		}*/
	}
	
	
	
	

	
}
