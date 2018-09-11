package com.main.function.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Eureka
 */
@Slf4j
public class RateLimiter {
    /**
     * 速率单位转换成毫秒
     */
    private final long rateToMsConversion;

    /**
     * 消耗令牌数
     */
    private final AtomicInteger consumedTokens = new AtomicInteger();

    /**
     * 最后填充令牌的时间
     */
    private final AtomicLong lastRefillTime = new AtomicLong(0);

    /**
     * 构造
     *
     * @param averageRateUnit 速率单位 构造方法里将 averageRateUnit 转换成 rateToMsConversion
     */
    public RateLimiter(TimeUnit averageRateUnit) {
        switch (averageRateUnit) {
            case SECONDS: // 秒级
                rateToMsConversion = 1000;
                break;
            case MINUTES: // 分钟级
                rateToMsConversion = 60 * 1000;
                break;
            default:
                throw new IllegalArgumentException("TimeUnit of " + averageRateUnit + " is not supported");
        }
    }

    /**
     * 获取令牌( Token )
     * averageRateUnit = SECONDS
     * averageRate = 2000
     * burstSize = 10
     * 每秒可获取 2000 个令牌。例如，每秒允许请求 2000 次。
     * 每毫秒可填充 2000 / 1000 = 2 个消耗的令牌。
     * 每毫秒可获取 10 个令牌。
     * <p>
     * 例如，每毫秒允许请求上限为 10 次，并且请求消耗掉的令牌，需要逐步填充。
     * 这里要注意下，虽然每毫秒允许请求上限为 10 次，这是在没有任何令牌被消耗的情况下，实际每秒允许请求依然是 2000 次。
     * 这就是基于令牌桶算法的限流的特点：让流量平稳，而不是瞬间流量。1000 QPS 相对平均的分摊在这一秒内，而不是第 1 ms 999 请求，后面 999 ms 0 请求。
     *
     * @param burstSize   令牌桶上限
     * @param averageRate 令牌再装平均速率
     * @return 是否获取成功
     */
    public boolean acquire(int burstSize, long averageRate) {
        return acquire(burstSize, averageRate, System.currentTimeMillis());
    }

    public boolean acquire(int burstSize, long averageRate, long currentTimeMillis) {
        if (burstSize <= 0 || averageRate <= 0) { // Instead of throwing exception, we just let all the traffic go
            return true;
        }

        // 填充 令牌
        refillToken(burstSize, averageRate, currentTimeMillis);
        // 消费 令牌
        return consumeToken(burstSize);
    }

    private void refillToken(int burstSize, long averageRate, long currentTimeMillis) {
        // 获得 最后填充令牌的时间
        long refillTime = lastRefillTime.get();
        // 获得 过去多少毫秒
        long timeDelta = currentTimeMillis - refillTime;

        // 计算 可填充最大令牌数量
        long newTokens = timeDelta * averageRate / rateToMsConversion;
        if (newTokens > 0) {
            // 计算 新的填充令牌的时间
            long newRefillTime = refillTime == 0
                    ? currentTimeMillis
                    : refillTime + newTokens * rateToMsConversion / averageRate;
            // CAS 保证有且仅有一个线程进入填充
            if (lastRefillTime.compareAndSet(refillTime, newRefillTime)) {
                while (true) { // 死循环，直到成功
                    // 计算 填充令牌后的已消耗令牌数量
                    int currentLevel = consumedTokens.get();
                    int adjustedLevel = Math.min(currentLevel, burstSize); // In case burstSize decreased
                    int newLevel = (int) Math.max(0, adjustedLevel - newTokens);
                    // CAS 避免和正在消费令牌的线程冲突
                    if (consumedTokens.compareAndSet(currentLevel, newLevel)) {
                        return;
                    }
                }
            }
        }
    }

    private boolean consumeToken(int burstSize) {
        while (true) { // 死循环，直到没有令牌，或者获取令牌成功
            // 没有令牌
            int currentLevel = consumedTokens.get();
            if (currentLevel >= burstSize) {
                return false;
            }
            // CAS 避免和正在消费令牌或者填充令牌的线程冲突
            if (consumedTokens.compareAndSet(currentLevel, currentLevel + 1)) {
                return true;
            }
        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(10);
        int i = 10000;
        RateLimiter rateLimiter = new RateLimiter(TimeUnit.SECONDS);
        while (i-- > 0){
//            TODO 没有做完
//            service.submit(RateLimiter::initailToken;);
        }
        service.shutdown();
        while (!service.isTerminated()) {
            Thread.sleep(100);
        }
    }

    public static void initailToken(RateLimiter rateLimiter ) {
        Boolean result = rateLimiter.acquire(10, 1000);
        if (result) {
//            log.info("获取token成功");
        } else {
            log.info("获取token失败");
        }
    }
}


