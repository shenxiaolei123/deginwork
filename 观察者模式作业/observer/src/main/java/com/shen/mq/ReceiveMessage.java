package com.shen.mq;

import java.util.Observable;
import java.util.Observer;

public class ReceiveMessage implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        PubMessage msg = (PubMessage)o;
        System.out.println(arg+" 接收");
    }
}
