package com.gp.w;

public class TcpRequestStaticProxy {
    private TcpRequest request;

    public TcpRequestStaticProxy(TcpRequest tcpRequest) {
        request = tcpRequest;
    }

    public Object read(){
        befor();
        request.read();
        after();
        return null;
    }
    private void befor(){
        System.out.println("开始处理");
    }
    private void after(){
        System.out.println("读取完毕");
    }
}
