package com.code.demo.collection.list.linked;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SeekTest {
    public void test(List<Integer> ll,Integer desc) {

        //********************************
        //for
        /*long start4 = System.nanoTime();
        for (int i =0;i<ll.size();i++){
            if (ll.get(i).equals(desc)) break;
        }
        System.out.print(System.nanoTime() - start4);
        System.out.print("     ");
        System.out.println("链表for");*/


        //********************************
        //foreach
        long start5 = System.nanoTime();
        for (Integer i:ll){
            if (i.equals(desc)) break;
        }
        System.out.print(System.nanoTime() - start5);
        System.out.print("     ");
        System.out.println("链表for-each");


        //********************************
        //stream
        long start6 = System.nanoTime();
        ll.stream().forEach(x -> x.equals(desc));
        System.out.print(System.nanoTime() - start6);
        System.out.print("     ");
        System.out.println("Stream foreach ");


        long start7 = System.nanoTime();
        ll.stream().filter(x -> x==desc.intValue()).findAny();
        System.out.print(System.nanoTime() - start7);
        System.out.print("     ");
        System.out.println("Stream filter ");

    }

    @Test
    public void multiSeek() {
        Integer desc = 9;
        List<Integer> ll = new LinkedList<>();
        for (int i= 0;i<10000000;i++){
            ll.add(i);
        }
        while (desc <= ll.size()){
            System.out.println("**************遍历目标值"+desc+"******************");
            for (int i =0 ;i<10 ;i++){
                System.out.println("**************第"+String.valueOf(i+1)+"次******************");
                test(ll,desc);
            }
            desc = desc *10;
        }
    }
}
