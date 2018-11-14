package com.jaremo.test_activemq.demo4;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息发送者（未签收的消息会实现持久化）
 */
public class Producer {

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 创建连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 创建会话 提供两个参数 第一个参数为true 代表支持事务 , 签收方式
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 创建一个消息队列
        Queue queue = session.createQueue("mq_first");
        // 构建消息发送者
        MessageProducer producer = session.createProducer(queue);
        // 发送十条消息，只签收第三条消息，前三条消息会出列，后七条消息不会出列
        for(int i=0;i<10;i++){
            // 添加消息
            TextMessage msg = session.createTextMessage("Hello I am boss");
            // 通过消息发送者来发送消息
            producer.send(msg);
        }

        // 关闭的操作
        producer.close();
        session.close();
        connection.close();
    }
}
