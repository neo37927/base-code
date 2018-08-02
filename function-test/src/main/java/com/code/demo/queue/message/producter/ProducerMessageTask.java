package com.code.demo.queue.message.producter;

import com.code.demo.queue.message.IMsgQueue;
import com.code.demo.queue.message.Message;
import com.code.demo.queue.message.MsgQueueFactory;

public class ProducerMessageTask implements Runnable{
    @Override
    public void run() {
        IMsgQueue messageQueue = MsgQueueFactory.getMessageQueue(Boolean.TRUE);
        Message message = new Message("This is message "+ System.nanoTime());
        messageQueue.put(message);
    }
}
