package com.gp.w;

public class TcpRequest implements  IRequest{
    public void read(){
        System.out.println("读取请求");
    }
    public String getTime(){
        System.out.println("获取时间");
        return System.currentTimeMillis()+"";
    }
}
