package com.shen.guava;

import com.google.common.eventbus.EventBus;
import com.shen.gp.Question;

import javax.management.Query;

public class GuvavEventTest {
    public static void main(String[] args) {
        EventBus bus = new EventBus();
        GuavaTeacher guavaTeacher = new GuavaTeacher("jacky","java圈子");
        bus.register(guavaTeacher);
        Question question = new Question();
        question.setContent("提问内容");
        question.setUserName("zhangshan");
        bus.post(question);
    }
}
