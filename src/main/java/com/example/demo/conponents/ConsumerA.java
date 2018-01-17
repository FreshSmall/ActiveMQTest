package com.example.demo.conponents;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class ConsumerA {
	@JmsListener(destination = "queue")
	public void comsume(String text) {
		System.out.println("ConsumerA收到的报文为:"+text);
	}
}
