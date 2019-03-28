package com.gpwork.controller;

import com.gpwork.annotations.SLAutowired;
import com.gpwork.annotations.SLController;
import com.gpwork.annotations.SLParam;
import com.gpwork.annotations.SLRequestMapping;
import com.gpwork.service.TestService;

import java.lang.annotation.Annotation;

@SLController
public class TestController {

    @SLAutowired
    private TestService service;

    @SLRequestMapping("/login")
    public String login(@SLParam("u") String u, @SLParam("p") String p){
        if(service!=null) return service.query(u,p);
        return u+p;
    }

    public void test(){}

    public static void main(String[] args) {
        Annotation annotation = TestController.class.getAnnotation(SLController.class);
        System.out.println(annotation);
    }
}
