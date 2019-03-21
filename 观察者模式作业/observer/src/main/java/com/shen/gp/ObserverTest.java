package com.shen.gp;

public class ObserverTest {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("sxl");
        Teacher teacher1 = new Teacher("jack");
        GPer gper = GPer.getInstance();
        Question question = new Question();
        question.setUserName("1");
        question.setContent("问题 内容");
        gper.addObserver(teacher);
        gper.addObserver(teacher1);
        gper.publishQuestion(question);

    }
}
