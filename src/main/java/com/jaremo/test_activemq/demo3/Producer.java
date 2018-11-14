package com.jaremo.test_activemq.demo3;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息发送者
 */
public class Producer {

    public static void main(String[] args) throws JMSException {
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
        // 构建消息发送者
        MessageProducer producer = session.createProducer(queue);
        // 添加消息
        for (int i=0;i<5;i++){
            ObjectMessage msg = session.createObjectMessage();
            // 构建对象
            User user = new User();
            user.setAge(i);
            user.setName("user"+i);
            msg.setObject(user);
            // 通过消息发送者来发送消息
            producer.send(msg);
        }

        // 关闭的操作
        producer.close();
        session.close();
        connection.close();
    }
}
