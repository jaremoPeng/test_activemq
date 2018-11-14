package com.jaremo.test_activemq.demo3;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消息接受者
 */
public class ConsumerListener {

    public static void main(String[] args) throws JMSException, IOException {
        // 创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 信任所有的包
        connectionFactory.setTrustAllPackages(true);
        // 创建连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 创建会话 提供两个参数 第一个参数为true 代表支持事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建一个消息队列
        Queue queue = session.createQueue("mq_first");
        // 构建消息接收者
        MessageConsumer consumer = session.createConsumer(queue);
        // 构建监听器
        consumer.setMessageListener(new MsgListener());
        // 让监听器处于一个监听状态
        System.in.read();
        // 关闭的操作
        consumer.close();
        session.close();
        connection.close();
    }
}
