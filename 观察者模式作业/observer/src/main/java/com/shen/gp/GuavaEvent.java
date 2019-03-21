package com.shen.gp;

import com.google.common.eventbus.Subscribe;

public class GuavaEvent {

    @Subscribe
    public void subscribe(String str){
        System.out.println("guava :"+str);

    }
}
