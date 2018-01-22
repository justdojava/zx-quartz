package com.zhongxin.quartz.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zhongxin.quartz.service.NoticeService;
import com.zhongxin.quartz.util.HttpClientUtil;
import com.zhongxin.quartz.util.JmsClient;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private JmsClient jmsClient;
	@Resource
	private Destination quartzTopic;
	
	/**
	 * @author ZQ
	 * @date 2016-2-26
	 * @param targetBeanId
	 * @param executorNo
	 * @param executeParamter
	 */
	public void sendMQmsg(String targetBeanId,String executorNo,String executeParamter){
		try {
			if (StringUtils.isBlank(targetBeanId)|| StringUtils.isBlank(executorNo)) {
				logger.error("param is null ");
				return;
			}
			String message = targetBeanId + "," + executorNo + "," + executeParamter;
			jmsClient.sendMessage(quartzTopic, message);
			logger.info("quartzTopic send succeed"+message);
		} catch (Exception e) {
			logger.error("sendMQmsg send is failed：", e);
			throw e;
		}
	}
	
	
	
	/**
	 * @author ZQ
	 * @date 2016-2-26
	 * @param executeParamter
	 * @param url
	 */
	public void httpRequest(String url,String executeParamter){
		try {
			if(StringUtils.isBlank(url)){
				logger.error("param is null ");
				return;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("executeParamter", executeParamter);
			//String res = SimpleHttpUtils.httpPost(url,map);
			String res = HttpClientUtil.doPost(url,map);
			
			logger.info("http request result is "+res);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("http request is failed：",e);
			
			throw e;
		}
	}

}
