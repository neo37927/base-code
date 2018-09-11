package com.main.function.aop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AopExecutor {
    public void addUser(String param){
        log.info("执行成功");
    }
}
