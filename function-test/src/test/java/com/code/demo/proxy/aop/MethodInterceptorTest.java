package com.code.demo.proxy.aop;

import com.sun.tools.javac.util.Assert;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;


public class MethodInterceptorTest {

    public class CustomMethodInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            System.out.println("method " + methodInvocation.getMethod() + " is called on " +
                    methodInvocation.getThis() + " with args " + methodInvocation.getArguments());
            Object ret = methodInvocation.proceed();
            System.out.println("method " + methodInvocation.getMethod() + " returns " + ret);
            return ret;
        }
    }

    public String get(String s) throws Exception{
        System.out.println("toString 被执行了。");
        if (s.endsWith("exception")) throw new Exception();
        return s;
    }

    @Test
    public void test() throws Exception{
        ProxyFactory factory = new ProxyFactory();
//        factory.setTargetClass(this.getClass());
        factory.setTarget(new MethodInterceptorTest());
        factory.addAdvice(new CustomMethodInterceptor());

        Object object = factory.getProxy();
        MethodInterceptorTest test = (MethodInterceptorTest) object;

        test.get("params");


//        test.get("exception");
    }
}
