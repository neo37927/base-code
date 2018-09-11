package com.main.function.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * BeanPostProcessor 提供BeanFactoryHook机制
 * 举例：代理或是动态实现？
 */
@Slf4j
@Component
public class SpringContextHookHolder implements BeanPostProcessor {

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName:{},bean:{}", beanName, bean.getClass());
        return bean;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName:{},bean:{}", beanName, bean.getClass());
        return bean;
    }
}
