package com.code.demo.queue.message;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

/**
 * 消息队列工厂类
 * 支持单例多例模式
 */
@Slf4j
public class MsgQueueFactory {
    private static class MsgQueueHolder {
        private static MsgQueueManager msgQueueManager = new MsgQueueManager();

        private static MsgQueueManager getMsgQueueManager() {
            return new MsgQueueManager();
        }
    }

    /**
     * 获取消息队列
     *  True 单例
     *  False 多例
     * @return
     */
    public static IMsgQueue getMessageQueue(Boolean isSingleton) {
        return isSingleton ? MsgQueueHolder.msgQueueManager : MsgQueueHolder.getMsgQueueManager();
    }


    public static class MsgQueueManager implements IMsgQueue {

        /**
         * 消息总队列
         */
        public final BlockingQueue<Message> messageQueue;

        private MsgQueueManager() {
            messageQueue = new LinkedTransferQueue<>();
        }

        public void put(Message msg) {
            try {
                messageQueue.put(msg);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public Message take() {
            try {
                return messageQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        int i = 1000;
        while (i-- >= 0) {
            Executors.newCachedThreadPool().submit(() -> {
                try {
                    Thread.sleep(1000);
                    IMsgQueue queue = MsgQueueFactory.getMessageQueue(Boolean.FALSE);
                    System.out.println(queue.hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
