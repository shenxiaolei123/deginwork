package com.shen.gp;

import com.google.common.eventbus.EventBus;

public class GuavaEventTest {
    public static void main(String[] args) {
        EventBus bus = new EventBus();
        bus.register(new GuavaEvent());
        bus.post("消息");
    }
}
