package com.code.demo.queue.message.customer;

import com.code.demo.queue.message.IMsgQueue;
import com.code.demo.queue.message.MsgQueueFactory;
import lombok.Getter;

import java.util.concurrent.ExecutorService;

@Getter
public class CustomerMessageTask implements Runnable{
    private ExecutorService executorService;
    private IMsgQueue msgQueue;

    public CustomerMessageTask(ExecutorService executorService) {
        this.executorService = executorService;
        this.msgQueue = MsgQueueFactory.getMessageQueue(Boolean.FALSE);
    }

    @Override
    public void run() {

    }
}
