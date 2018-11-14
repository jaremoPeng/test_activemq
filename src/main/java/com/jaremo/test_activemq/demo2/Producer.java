package com.jaremo.test_activemq.demo2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息发送者（订阅发布者模式： 多个订阅者订阅同个主题topic， 在所有订阅者中有不同的标识（id），不同名称）
 */
public class Producer {

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 创建连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 创建会话 提供两个参数 第一个参数为true 代表支持事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建一个消息主题
        Topic topic = session.createTopic("mq_topic");
        // 构建消息发送者
        MessageProducer producer = session.createProducer(topic);
        // 添加消息
        TextMessage msg = session.createTextMessage("Hello I am boss");
        // 通过消息发送者来发送消息
        producer.send(msg);

        // 关闭的操作
        producer.close();
        session.close();
        connection.close();
    }
}
