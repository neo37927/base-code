package com.main.frame.initial;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Bean工厂
 */
@Slf4j
public class BeanFactory {

    /**
     * 实例
     */
    @Data
    public static class Entity {
        private String name;

        private Entity() {
        }
       /* public int hashCode(){
            return new Random().nextInt(10000);
        }*/

    }

    /**
     * 实例控制器
     */
    private static class SingletonHolder {
        public static Entity entity = new Entity();
    }


    public static Entity getEntity(Boolean isSingleton) {
        return isSingleton ? SingletonHolder.entity : new Entity();
    }


    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(30);
        testBeanInitial(service, Boolean.TRUE, 10000);

        service.shutdown();
        while (!service.isTerminated()) {
            Thread.sleep(100);
        }

    }

    public static void testBeanInitial(ExecutorService service, Boolean isSingleton, Integer times) {
        HashSet<Entity> entities = new HashSet<>();
        AtomicInteger ai = new AtomicInteger(0);
        while (times-- > 0) {
            service.submit(() -> {
                Entity entity = BeanFactory.getEntity(isSingleton);
//                log.info(""+entity.hashCode());
                boolean result = entities.add(entity);
                if (ai.compareAndSet(0,1)) {
                    return;
                }
                if (result & isSingleton) {
                    log.error("单例模式，创建实例存在问题");
                } else if (!(result || isSingleton)) {
                    log.error("多例模式，创建实例存在问题");

                }
            });
        }
    }


}
