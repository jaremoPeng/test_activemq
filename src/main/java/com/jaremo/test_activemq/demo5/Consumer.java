package com.jaremo.test_activemq.demo5;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息接受者(消息中间件的事务的支持) , 消息的发送和接受是在不同事务内
 */
public class Consumer {

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 创建连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 创建会话 提供两个参数 第一个参数为true 代表支持事务 ， 签收方式
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        // 创建一个消息队列
        Queue queue = session.createQueue("mq_queue_trans");
        // 构建消息接收者
        MessageConsumer consumer = session.createConsumer(queue);
        try {
            for(int i=0;i<5;i++){
                // 接受消息
                TextMessage receive = (TextMessage)consumer.receive();
                // 通过消息发送者来的消息
                String text = receive.getText();
                System.out.println("接受到的消息为 "+text);
                if(i==2){
                    throw new RuntimeException();
                }
            }
            // 事务不提交，消息不会出列（不会签收）
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
        // 关闭的操作
        consumer.close();
        session.close();
        connection.close();
    }
}
