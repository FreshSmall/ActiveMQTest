package com.example.demo.activeMQqueue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestProcessor {


    public static void main(String[] args) throws JMSException {
        //JMS通过ConnectionFaction来创建连接
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616"
        );

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("queue");
        MessageConsumer consumer=session.createConsumer(destination);
        while(true){

            ObjectMessage message = (ObjectMessage) consumer.receive(1000);
            if(null != message)
            {
                System.out.println(message);
                HashMap<Serializable,Serializable> requestParam = (HashMap<Serializable,Serializable>) message.getObject();
                RequestProcessor.requestHandler(requestParam);
            }
            System.out.println("消费者一直等待消费消息！");


        }

    }

    private static void requestHandler(HashMap<Serializable, Serializable> requestParam) {
        System.out.println("requestHandler....."+requestParam.toString());
        for(Map.Entry<Serializable, Serializable> entry : requestParam.entrySet())
        {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}
