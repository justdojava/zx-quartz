package com.zhongxin.quartz.core;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zhongxin.quartz.service.QuartzService;

@Component
public class DynamicJobAssembler {

	private static Logger logger = LoggerFactory.getLogger(DynamicJobAssembler.class);
	@Resource
	private QuartzService quartzService;
	
	@PostConstruct
	public void init() {
		logger.info("加载定时任务开始");
		try {
			quartzService.initScheduler();
		} catch (Exception e) {
			logger.info("定时任务加载失败，请手动加载！\n");
			logger.info("定时任务加载异常:", e);
		}
		logger.info("定时任务加载完成");
	}


}
