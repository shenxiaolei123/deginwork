package com.shen.mq;

public class MqTest {
    public static void main(String[] args) {
        PubMessage pubMessage = new PubMessage();
        ReceiveMessage receiveMessage = new ReceiveMessage();
        pubMessage.addObserver(receiveMessage);
        pubMessage.publishMsg("消息内容");
    }
}
