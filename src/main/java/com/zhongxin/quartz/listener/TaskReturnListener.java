package com.zhongxin.quartz.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zhongxin.quartz.dao.TaskRecordsDao;
import com.zhongxin.quartz.entity.TaskRecordsEntity;
import com.zhongxin.quartz.service.QuartzService;
import com.zhongxin.quartz.util.Const;
import com.zhongxin.quartz.util.SpringContext;


public class TaskReturnListener implements MessageListener{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				String msg = ((TextMessage) message).getText();
				logger.info("监听到消息：" + msg);
				// taskNo,result(success|failed),failCount,failKey,failReason
				String strs[] = msg.split(",");
				String taskNo = strs[0];
				String result = strs[1];
				int failCount = 0;
				if(!Const.SUCCESS.equals(result)){
					failCount = Integer.parseInt(strs[2]);
				}
				QuartzService quartzService = (QuartzService) SpringContext.getBean("quartzService");
				TaskRecordsDao taskRecordsDao = (TaskRecordsDao) SpringContext.getBean("taskRecordsDao"); 
				// 更新
				quartzService.updateTaskInformations(taskNo);	
				TaskRecordsEntity recordsEntity = taskRecordsDao.selectByTaskNoAndStatus(taskNo);
				if(null != recordsEntity){
					quartzService.modifyTaskRecord(failCount, recordsEntity.getId());
					if(!Const.SUCCESS.equals(result)){
						quartzService.saveTaskErrors(String.valueOf(recordsEntity.getId()),strs[3], strs[4]);
					}
				}
			}
		} catch (Exception e) {
			logger.error("异常：", e);
		}
		
	}

}
