package com.shen.mq;

import java.util.Observable;
import java.util.Observer;

public class PubMessage extends Observable {

    public void publishMsg(String msg){
        System.out.println("发布消息："+msg);
        setChanged();
        notifyObservers(msg);
    }
}
