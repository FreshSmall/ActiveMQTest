package com.example.demo.activeMQtopic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ProducerDemo {

    private MessageProducer producer;
    private Session session;


    public void init() throws JMSException {
        //创建activemq的连接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616"
        );

        Connection connection = factory.createConnection();

        connection.start();
        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);//第一个参数是是否启动事务，第二个是消息的签收模式

        //获取消息队列
        Destination destination = session.createTopic("topic");

        //获取消息生产者
        producer = session.createProducer(destination);
        //设置消息生产者不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

    }

    public void send() throws JMSException, InterruptedException {
        int count=0;
        while (true) {
            TextMessage message = session.createTextMessage();
            message.setText("hello activemq!--"+count++);
            producer.send(message);
            session.commit();
            Thread.sleep(5000);
        }

    }

    public static void main(String[] args) throws Exception {
        ProducerDemo p = new ProducerDemo();
        p.init();
        p.send();
    }
}
