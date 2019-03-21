package com.shen.guava;

import com.google.common.eventbus.Subscribe;
import com.shen.gp.Question;

public class GuavaTeacher {
    private String name;
    private String community = "GP圈子";
    public GuavaTeacher(String name,String community){
        this.name = name;
        this.community = community;
    }

    @Subscribe
    public void subcribe(Question question){
        System.out.println("===============================");
        System.out.println(name + "老师，你好！\n" +
                "您收到了一个来自“" + community + "”的提问，希望您解答，问题内容如下：\n" +
                question.getContent() + "\n" +
                "提问者：" + question.getUserName());
    }
}
