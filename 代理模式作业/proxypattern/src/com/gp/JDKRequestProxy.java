package com.gp;

import com.gp.w.IRequest;
import com.gp.w.TcpRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKRequestProxy implements InvocationHandler {
    private Object request = null;

    public Object proxy(TcpRequest request) {
        this.request = request;
        Class<?> clazz = request.getClass();
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
        return o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(this.request, args);
        after();
        return null;
    }

    private void before() {
        System.out.println("jdk 代理请求前");
    }

    private void after() {
        System.out.println("jdk 代理请求完成");
    }
}
