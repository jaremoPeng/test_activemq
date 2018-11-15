package com.jaremo.test_activemq.demo5;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息发送者（事务的支持）
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
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        // 创建一个消息队列
        Queue queue = session.createQueue("mq_queue_trans");
        // 构建消息发送者
        MessageProducer producer = session.createProducer(queue);
        // 发送十条消息，消息中间件开启事务以后，如若不提交事务，那么消息就不加入到队列中去
        try {
            for(int i=0;i<10;i++){
                if(i==2){
                    throw new RuntimeException();
                }
                // 添加消息
                TextMessage msg = session.createTextMessage("Hello I am boss");
                // 通过消息发送者来发送消息
                producer.send(msg);
            }
            // 提交事务
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚
            session.rollback();
        }

        // 关闭的操作
        producer.close();
        session.close();
        connection.close();
    }
}
