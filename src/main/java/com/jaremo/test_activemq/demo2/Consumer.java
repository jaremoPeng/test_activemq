package com.jaremo.test_activemq.demo2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息接受者（订阅发布者模式）
 */
public class Consumer {

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 创建连接
        Connection connection = connectionFactory.createConnection();
        // 需要一个标识订阅者的id（持久化）
        connection.setClientID("tp_cli1");
        // 开启连接
        connection.start();
        // 创建会话 提供两个参数 第一个参数为true 代表支持事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建一个消息主题
        Topic topic = session.createTopic("mq_topic");
        // 构建消息接收者
        MessageConsumer consumer = session.createDurableSubscriber(topic,"cli_name1"); // 订阅的主题，订阅者的名字
        // 接受消息
        TextMessage receive = (TextMessage)consumer.receive();
        // 通过消息发送者来的消息
        String text = receive.getText();
        System.out.println("订阅者1 接受到的消息为 "+text);
        // 关闭的操作
        consumer.close();
        session.close();
        connection.close();
    }
}
