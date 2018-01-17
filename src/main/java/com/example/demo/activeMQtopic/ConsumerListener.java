package com.example.demo.activeMQtopic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;

import javax.jms.*;

public class ConsumerListener implements MessageListener{

    private static MessageConsumer consumer;
    private static Session session;
    public ConsumerListener(){
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

    //当消息队列中接受到消息时，会自动调用该方法
    @Override
    public void onMessage(Message message) {
        try {
            ActiveMQDestination queue= (ActiveMQDestination) message.getJMSDestination();
            //监听topic消息队列中的消息
            if(queue.getPhysicalName().equals("topic")){
                TextMessage m= (TextMessage) message;
                if(m!=null){
                    System.out.println("监听器收到的消息:"+m.getText());
                }else{
                    System.out.println("监听器没有收到消息！");
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws JMSException {
        ConsumerListener m=new ConsumerListener();
        consumer.setMessageListener(m);
    }

}
