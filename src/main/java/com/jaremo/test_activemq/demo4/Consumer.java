package com.jaremo.test_activemq.demo4;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息接受者(消息的签收)
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
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 创建一个消息队列
        Queue queue = session.createQueue("mq_first");
        // 构建消息接收者
        MessageConsumer consumer = session.createConsumer(queue);
        for(int i=0;i<10;i++){
            // 接受消息
            TextMessage receive = (TextMessage)consumer.receive();
            // 通过消息发送者来的消息
            String text = receive.getText();
            System.out.println("接受到的消息为 "+text);
//            if(i==2){ 只签收第三条消息，前两条消息自动签收
                // 手动签收消息，如若不签收消息，则不会出列，但是消息接收者还是会接收到消息
                receive.acknowledge();
//            }
        }
        // 关闭的操作
        consumer.close();
        session.close();
        connection.close();
    }
}
