package com.main.function.message.producter;

import com.main.function.message.IMsgQueue;
import com.main.function.message.Message;
import com.main.function.message.MsgQueueFactory;

public class ProducerMessageTask implements Runnable{
    @Override
    public void run() {
        IMsgQueue messageQueue = MsgQueueFactory.getMessageQueue(Boolean.TRUE);
        Message message = new Message("This is message "+ System.nanoTime());
        messageQueue.put(message);
    }
}
