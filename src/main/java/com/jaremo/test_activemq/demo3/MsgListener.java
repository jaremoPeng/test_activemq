package com.jaremo.test_activemq.demo3;

import javax.jms.*;

public class MsgListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            String msg = null;
            try {
                msg = ((TextMessage) message).getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.out.println("文本消息： "+msg);
        }
        if(message instanceof ObjectMessage){
            Object msg = null;
            try {
                msg = (Object) ((ObjectMessage) message).getObject();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.out.println("对象消息： "+msg);
        }

        // 消息监听器一直监听消息的话，十分影响性能，处理的方式为： 每隔一定的时间的轮询一下
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
