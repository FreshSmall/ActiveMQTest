package com.example.demo.activeMQtopic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConsumerDemoA implements Runnable{

    private static MessageConsumer consumer;
    private static Session session;


    public ConsumerDemoA(){
        //创建activemq的连接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616"
        );

        Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//创建事务的级别

            //获取消息队列
            Destination destination = session.createTopic("topic");

            //获取消息生产者
            consumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receive() throws JMSException {
        while(true){
            TextMessage message = (TextMessage) consumer.receive(1000);
            if (message == null) {
                System.out.println("等待消息发送！");
            }else {
                System.out.println(message.getText()+"---消费者A");
            }
        }
    }

    @Override
    public void run() {
        //创建activemq的连接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616"
        );

        Connection connection = null;
        try {
            connection = factory.createConnection();
//            connection.setClientID("consumer"+Thread.currentThread().getId());
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//创建事务的级别

            //获取消息队列
            Destination destination = session.createTopic("topic");

            //获取消息生产者
            consumer = session.createConsumer(destination);
//            consumer = session.createDurableSubscriber((Topic) destination,"consumer"+Thread.currentThread().getId());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                Thread.sleep(2000);
               TextMessage message = (TextMessage) consumer.receive(1000);
                if (message == null) {
                    System.out.println("等待消息发送！"+"---"+Thread.currentThread().getId());
                }else{
                    System.out.println(message.getText()+"---"+Thread.currentThread().getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws JMSException {
        ConsumerDemoA c=new ConsumerDemoA();
        c.receive();
    }


}
