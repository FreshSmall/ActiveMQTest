package com.example.demo.conponents;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerC {
    @JmsListener(destination = "topic")
    public void comsume(String text) {
        System.out.println("ConsumerC收到的报文为:"+text);
    }
}
