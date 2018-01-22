package com.example.demo;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.jms.Queue;

@SpringBootApplication
public class QuartzTestApplication {

	//处理完成业务逻辑后，向activemq的queue发送执行成功消息Consumer.java
	@Bean
	public Queue queue() {
		return new ActiveMQQueue("taskReturnQueue");
	}

	public static void main(String[] args) {
		SpringApplication.run(QuartzTestApplication.class, args);
	}
}
