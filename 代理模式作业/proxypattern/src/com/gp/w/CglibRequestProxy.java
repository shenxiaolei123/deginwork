package com.gp.w;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibRequestProxy implements MethodInterceptor {

    public Object getInstance(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object r = methodProxy.invokeSuper(o,objects);
        after();
        return r;
    }
    private  void before(){
        System.out.println("cglib 请求前");
    }
    private  void after(){
        System.out.println("cglib 请求后");
    }
}
