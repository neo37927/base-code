package com.main.function.algorithm;

import java.util.BitSet;

/**
 * 布隆过滤器
 * 缺点：
 * 不支持删除
 * Counting Bloom filter
 * 基于BitSet实现，性能上可能存在问题
 */
public class SimpleBloomFilter {
    //2的25次方
    private static final int DEFAULT_SIZE = 2 << 24;
    //不同的hash函数种子，一般应取质数，seeds的数值共有7位，则采取7种不同的hash算法
    private static final int[] seeds = new int[]{5, 7, 11, 13, 31, 37, 61};
    //BitSet实际由"二进制位"构成的一个Vector。假如希望高效保存大量"开-关"信息，就应该使用BitSet
    //BitSet的最小长度是一个长整数（Long）的长度64位
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    //Hash函数对象
    private SimpleHash[] func = new SimpleHash[seeds.length];

    public static void main(String[] args) {
        String value = "stone2083@yahoo.cn";
        //定义filter，初始化7个hash函数的对象所需要的信息
        SimpleBloomFilter filter = new SimpleBloomFilter();
        System.out.println(filter.contains(value));
        filter.add(value);
        System.out.println(filter.contains(value));
    }

    public SimpleBloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    public void add(String value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }

    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    public static class SimpleHash {
        //结果的最大字符串长度
        private int cap;
        //为计算hash值的给定的key
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        /**
         * 计算hash值的简单函数，计算方式：加权和
         * @param value
         * @return
         */
        public int hash(String value) {
            //int的范围为2^31-1，或超过值则用负数来表示
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                //数字和字符串相加，字符串转换成ASCII码
                result = seed * result + value.charAt(i);
            }
            //过滤负数
            return (cap - 1) & result;
        }
    }
}