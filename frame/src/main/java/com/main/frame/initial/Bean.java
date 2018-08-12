package com.main.frame.initial;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.font.TrueTypeFont;

@Data
@Slf4j
public class Bean {
    private String priName;
    private static String priSName = "priSName";

    public String pubName;
    public static String pubSName = "pubSName";

    public static BeanFactory beanFactory = new BeanFactory();

    private static class Holder{
        {
            log.info("静态内部类：构造方法");
        }
        static {
            log.info("静态内部类：静态代码块");
        }
        public static BeanFactory bf = new BeanFactory();

        public static BeanFactory getBf(){
            return new BeanFactory();
        }
    }

    {
        log.info("构造代码块");
    }
    static {
        log.info("静态代码块");
    }

    public Bean() {
        new Holder();
        log.info("普通构造方法");
    }

    public static void setBeanFactory(BeanFactory beanFactory) {
        Bean.beanFactory = beanFactory;
    }

    public static void getBeanFactory(BeanFactory beanFactory) {
        Bean.beanFactory = beanFactory;
    }

    public static BeanFactory getbf(Boolean flag) {
        if (flag) return Holder.bf;
        return Holder.getBf();
    }

    public static void main(String[] args) {
        log.info("JVM创建");
        log.info("静态常量：pubSName:{},hashcode:{}",Bean.pubSName,Bean.pubSName.hashCode());
        log.info("静态常量：priSName:{},hashcode:{}",Bean.priSName,Bean.pubSName.hashCode());
        log.info("静态常量：beanFactory:{},hashcode:{}",Bean.beanFactory,Bean.beanFactory.hashCode());

        BeanFactory temp = Bean.getbf(Boolean.TRUE);
        log.info("静态常量：bf:{},hashcode:{}",temp,temp.hashCode());
        BeanFactory temp1 = Bean.getbf(Boolean.TRUE);
        log.info("静态常量：bf:{},hashcode:{}",temp1,temp1.hashCode());
        BeanFactory temp2 = Bean.getbf(Boolean.FALSE);
        log.info("静态常量：bf:{},hashcode:{}",temp2,temp2.hashCode());
        BeanFactory temp3 = Bean.getbf(Boolean.FALSE);
        log.info("静态常量：bf:{},hashcode:{}",temp3,temp3.hashCode());



        Bean b = null;
        if (b == null){
            b = new Bean();
        }
        log.info("JVM销毁");
    }
}
