package com.gpwork.service;

@SLService
public class TestService {

    public String query(String u,String p){
        System.out.println("service查询--"+u+","+p);
        return "查询通过";
    }
}
