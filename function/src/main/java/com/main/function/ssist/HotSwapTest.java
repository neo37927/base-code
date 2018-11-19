package com.main.function.ssist;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import javassist.*;
import javassist.util.HotSwapper;

import java.io.IOException;

/**
 * javassist hotswap 需要JPDA支持
 * JVM通过JPDA (Java Platform Debugger Architecture)选项启动，那么其中的类是可以重新加载的，只不过类的接口必须是相同或者兼容的。
 * 因此需要开启JPDA：
 * JVM -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
 *
 * 虽然Javassist能够提供动态重新加载类的功能，不过由于它要求启用JPDA，
 * 一定程度上会损耗不少性能、留下了潜在的安全漏洞（因为是公开的规范，更容易被攻击），
 * 同时，它仍然要求在系统设计的时候将Javassist纳入体系。
 * 所以应该来说，除非为了设计IDE，更加合适的设计应该是这样的，而不是借助于JPDA的架构：
 *
 * 1、在关键服务环节（最好按照最小粒度模块为单位以最小化影响）预留空的AOP点和指定接口；
 * 2、设置一个watcher定期检查某个参数或者环境变量，如果发生了变化则根据约定的规范动态加载指定接口的实现，在该动态加载的实现中实现调试的逻辑。
 */
public class HotSwapTest {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spider-base-service.xml");
//        System.out.println("---" + context.getApplicationName());
        Student student = new Student();
        student.execute();
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass clazz = pool.get("com.main.function.ssist.Student");
            CtMethod cm = clazz.getDeclaredMethod("execute");
            cm.insertAt(1, "{System.out.println(\"hello HotSwapper.\");}");  // clazz完全可以是全新的，这里只是为了测试方便而已
            HotSwapper swap = new HotSwapper(8000);
            swap.reload("com.main.function.ssist.Student", clazz.toBytecode());
            student.execute();
        } catch (CannotCompileException | IOException | IllegalConnectorArgumentsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
