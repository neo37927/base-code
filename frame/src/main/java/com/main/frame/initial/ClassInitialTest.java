package com.main.frame.initial;

public class ClassInitialTest {
    private static ClassInitialTest singleton = new ClassInitialTest();

    public static int counter1;
    //静态区域的构建顺序，将影响一些变量的初始化过程
    public static int counter2 = 0;



    private ClassInitialTest() {
        counter1++;
        counter2++;
    }

    public static ClassInitialTest getSingleton() {
        return singleton;
    }

    public static void main(String[] args) {
        System.out.println(getSingleton().counter1);
        System.out.println(getSingleton().counter2);
    }
}
