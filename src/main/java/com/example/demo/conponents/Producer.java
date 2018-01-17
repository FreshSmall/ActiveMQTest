package com.example.demo.conponents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;


@Component
public class Producer {

    private int count = 0;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

	@Scheduled(fixedDelay=3000)
    public void create() {
		jmsTemplate.convertAndSend(queue,"hello,ActiveMQ,queue!"+(count++));
		jmsTemplate.convertAndSend(topic, "hello,ActiveMQ,topic!"+count++);
	}

}
