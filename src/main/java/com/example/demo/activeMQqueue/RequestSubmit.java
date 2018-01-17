package com.example.demo.activeMQqueue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;

public class RequestSubmit {

    //消息的发送者
    private MessageProducer producer;

    //发送或者接受消息的线程
    private Session session;

    private void init() throws JMSException {
        //JMS通过ConnectionFaction来创建连接
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616"
        );

        //从构造工厂中获取connection连接对象
        Connection connection = factory.createConnection();

        //启动
        connection.start();
        //获取连接
        session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue");
        //获取消息发送者
        producer = session.createProducer(destination);
        //设置不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public void submit(HashMap<Serializable, Serializable> map) throws JMSException {
        ObjectMessage message = session.createObjectMessage(map);
        producer.send(message);
        session.commit();
    }

    public static void main(String[] args) throws JMSException {
        RequestSubmit submit = new RequestSubmit();
        submit.init();
        HashMap<Serializable, Serializable> map = new HashMap<>();
        while(true){
            map.put("zhangsan", "123");
            submit.submit(map);
        }


    }

}

