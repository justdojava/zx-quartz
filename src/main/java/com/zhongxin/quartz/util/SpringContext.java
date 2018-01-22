package com.zhongxin.quartz.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException {
		SpringContext.context = applicationcontext;
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

}
