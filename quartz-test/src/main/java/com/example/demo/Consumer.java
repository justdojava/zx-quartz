package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * 消息消费者.
 */
@Component
public class Consumer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @JmsListener(destination = "quartzTopic")
    public void receiveQueue(String text) {
        //示例输入，可以写入自己的业务逻辑
        System.out.println("quartzTopic="+text);

        //返回成功或失败消息 taskNo,result(success|failed)
        this.jmsMessagingTemplate.convertAndSend(this.queue, "test,success");
    }
}