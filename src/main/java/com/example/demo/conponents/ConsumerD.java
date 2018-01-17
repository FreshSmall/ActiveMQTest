package com.example.demo.conponents;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerD {
    @JmsListener(destination = "topic")
    public void comsume(String text) {
        System.out.println("ConsumerD收到的报文为:"+text);
    }
}
