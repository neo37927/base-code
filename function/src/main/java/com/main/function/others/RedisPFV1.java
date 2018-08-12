package com.main.function.others;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class RedisPFV1 {
    static class BitKeeper {
        private int maxbits;

        public void random() {
            long value = ThreadLocalRandom.current().nextLong(8L << 32);
//            log.debug("random value :{}",Long.toBinaryString(value));
            int bits = lowZeros(value);
//            log.debug("low zero count:{}",Integer.toString(bits));
            if (bits > this.maxbits) {
                this.maxbits = bits;
            }
//            log.debug("maxbits:{}",Integer.toString(maxbits));
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
        private BitKeeper keeper;

        public Experiment(int n) {
            this.n = n;
            this.keeper = new BitKeeper();
        }

        public void work() {
            for (int i = 0; i < n; i++) {
                this.keeper.random();
            }
        }

        public void debug() {
            System.out.printf("%d %.2f %d\n", this.n, Math.log(this.n) / Math.log(2), this.keeper.maxbits);
        }

    }

    public static void main(String[] args) {
        log.debug("random base {}", Long.toBinaryString(1L));
        log.debug("random base {}", Long.toBinaryString(1L << 32));
        long m = ThreadLocalRandom.current().nextLong(1L << 32);
        log.debug("random base {}", Long.toBinaryString(m));
        log.debug("random base {}", Long.toBinaryString(m & 0xfff0000));
        log.debug("random base {}", Long.toBinaryString((m & 0xfff0000) >> 16));
        log.debug("random base {}", (m & 0xfff0000) >> 16);
        log.debug("random base {}", ((m & 0xfff0000) >> 16) % 1024);
        log.debug("random base {}", Integer.toBinaryString(1024));
        for (int i = 1000; i < 2000; i += 100) {
            Experiment exp = new Experiment(i);
            exp.work();
            exp.debug();
        }
    }
}
