package com.zhongxin.quartz.service;

public interface NoticeService {
	
	public void sendMQmsg(String targetBeanId,String executorNo,String executeParamter);
	
	public void httpRequest(String url,String executeParamter);

}
