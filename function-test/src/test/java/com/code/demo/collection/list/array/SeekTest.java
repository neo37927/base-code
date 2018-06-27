package com.code.demo.collection.list.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 针对数组实现的测试
 * <p>
 * 问题：
 * 1、各种遍历方式的区别：for、foreach、stream-foreach、stream-filter
 * 2、针对JMM的优化，采用多次查询同一值
 * 3、针对全范围查询检测
 */
public class SeekTest {

    public void test(List<Integer> al, Integer desc) {
        //********************************
        //for
        long start1 = System.nanoTime();
        for (int i = 0; i < al.size(); i++) {
            if (al.get(i).equals(desc)) break;
        }
        System.out.print(System.nanoTime() - start1);
        System.out.print("     ");
        System.out.println("数组for");
        //********************************
        //foreach
        long start2 = System.nanoTime();
        for (Integer i : al) {
            if (i.equals(desc)) break;
        }
        System.out.print(System.nanoTime() - start2);
        System.out.print("     ");
        System.out.println("数组for-each");


        //********************************
        //stream
        long start3 = System.nanoTime();
//        al.parallelStream().forEach(x -> x.equals(desc));
        al.stream().forEach(x -> x.equals(desc));
        System.out.print(System.nanoTime() - start3);
        System.out.print("     ");
        System.out.println("Stream foreach ");


        long start33 = System.nanoTime();
        al.stream().filter(x -> x == desc.intValue()).findAny();
        System.out.print(System.nanoTime() - start33);
        System.out.print("     ");
        System.out.println("Stream filter ");


    }

    @Test
    public void multiSeek() {
        Integer desc = 8;
        List<Integer> al = new ArrayList<>();
        List<Integer> ll = new LinkedList<>();
        for (int i = 0; i < 10000000; i++) {
            al.add(i);
            ll.add(i);
        }
        while (desc <= al.size()) {
            System.out.println("**************目标值" + desc + "******************");
            for (int i = 0; i < 10; i++) {
                System.out.println("**************第" + String.valueOf(i + 1) + "次******************");
                test(al, desc);
            }
            desc = desc * 10;
        }
    }
}
