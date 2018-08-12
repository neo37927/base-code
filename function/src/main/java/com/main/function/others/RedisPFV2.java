package com.main.function.others;

import java.util.concurrent.ThreadLocalRandom;

public class RedisPFV2 {
    static class BitKeeper {
        private int maxbits;

        public void random(long value) {
            int bits = lowZeros(value);
            if (bits > this.maxbits) {
                this.maxbits = bits;
            }
        }

        private int lowZeros(long value) {
            int i = 1;
            for (; i < 32; i++) {
                if (value >> i << i != value) {
                    break;
                }
            }
            return i - 1;
        }
    }

    static class Experiment {
        private int n;
        private int k;
        private BitKeeper[] keepers;

        public Experiment(int n) {
            //默认分配1024个桶
            this(n, 1024);
        }

        public Experiment(int n, int k) {
            this.n = n;
            this.k = k;
            this.keepers = new BitKeeper[k];
            for (int i = 0; i < k; i++) {
                this.keepers[i] = new BitKeeper();
            }
        }

        public void work() {
            for (int i = 0; i < this.n; i++) {
                //高位取余分组
                long m = ThreadLocalRandom.current().nextLong(1L << 32);
                BitKeeper keeper = keepers[(int) (((m & 0xfff0000) >> 16) % keepers.length)];
                keeper.random(m);
            }
        }

        /**
         * 计算调和平均数，也就是倒数的平均
         * @return
         */
        public double estimate() {
            double sumbitsInverse = 0.0;
            //普通的平均法可能因为个别离群值对平均结果产生较大的影响，调和平均可以有效平滑离群值的影响。
            for (BitKeeper keeper : keepers) {
                sumbitsInverse += 1.0 / (float) keeper.maxbits;
            }
            double avgBits = (float) keepers.length / sumbitsInverse;
            //2^avgBits * 1024
            return Math.pow(2, avgBits) * this.k;
        }
    }

    public static void main(String[] args) {
        for (int i = 100000; i < 1000000; i += 100000) {
            Experiment exp = new Experiment(i);
            exp.work();
            double est = exp.estimate();
            System.out.printf("%d %.2f %.2f\n", i, est, Math.abs(est - i) / i);
        }
    }
}
