package com.main.function.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

public class AopDemo {
    @Aspect
    public class MyAspect {
        /**
         * 前置通知
         */
        @Before("execution(* com.main.function.aop.AopExecutor.addUser(..))")
        public void before() {
            System.out.println("前置通知....");
        }

        /**
         * 后置通知     * returnVal,切点方法执行后的返回值
         */
        @AfterReturning(value = "execution(* com.main.function.aop.AopExecutor.addUser(..))", returning = "returnVal")
        public void AfterReturning(Object returnVal) {
            System.out.println("后置通知...." + returnVal);
        }

        /**
         * 环绕通知     * @param joinPoint 可用于执行切点的类     * @return     * @throws Throwable
         */
        @Around("execution(* com.main.function.aop.AopExecutor.addUser(..))")
        public Object around
        (ProceedingJoinPoint joinPoint) throws Throwable {
            System.out.println("环绕通知前....");
            Object obj = (Object) joinPoint.proceed();
            System.out.println("环绕通知后....");
            return obj;
        }

        /**
         * 抛出通知     * @param e
         */
        @AfterThrowing(value = "execution(* com.main.function.aop.AopExecutor.addUser(..))", throwing = "e")
        public void afterThrowable(Throwable e) {
            System.out.println("出现异常:msg=" + e.getMessage());
        }

        /**
         * 无论什么情况下都会执行的方法
         */
        @After(value = "execution(* com.main.function.aop.AopExecutor.addUser(..))")
        public void after() {
            System.out.println("最终通知....");
        }
    }
}
