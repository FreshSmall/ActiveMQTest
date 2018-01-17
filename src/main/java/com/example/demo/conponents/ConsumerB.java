package com.example.demo.conponents;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerB {
    @JmsListener(destination = "queue")
    public void receiveQueue(String text) {
        System.out.println("ConsumerB收到的报文为:"+text);
    }
}
