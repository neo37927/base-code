package com.main.function.message.customer;

import com.main.function.message.IMsgQueue;
import com.main.function.message.Message;
import com.main.function.message.MsgQueueFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Getter
@Slf4j
public class CustomerMessageTask implements Runnable {
    private ExecutorService executorService;
    private IMsgQueue msgQueue;

    public CustomerMessageTask(ExecutorService executorService) {
        this.executorService = executorService;
        this.msgQueue = MsgQueueFactory.getMessageQueue(Boolean.FALSE);
    }

    @Override
    public void run() {
        for (; ; ) {
            Message message = msgQueue.take();
            executorService.submit(() -> log.info("Current class:{},message:{}", this.hashCode(), message.getMessage()));
        }
    }
}
