package com.code.demo.queue.message.dispatch;

import com.code.demo.queue.message.Message;
import com.code.demo.queue.message.MsgQueueFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReferenceArray;

@Slf4j
public class DispatchMessageTask implements Runnable {
//    private AtomicReferenceArray;
    private List<BlockingQueue> subMsgQueues;
    @Override
    public void run() {
        BlockingQueue<Message> subQueue;
        for (; ; ) {
            // 如果没有数据，则阻塞在这里
            Message msg = MsgQueueFactory.getMessageQueue(Boolean.TRUE).take();
            // 如果为空，则表示没有Session机器连接上来，
            // 需要等待，直到有Session机器连接上来
            while ((subQueue = getInstance().getSubQueue()) == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // 把消息放到小队列里
            try {
                subQueue.put(msg);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public DispatchMessageTask getInstance(){
        //顺序
        //闲置优先
        return new DispatchMessageTask();
    }

    /**
     * 均衡获取一个子队列。
     *
     * @return
     */
    public BlockingQueue<Message> getSubQueue() {
        int errorCount = 0;
        for (; ; ) {
            if (subMsgQueues.isEmpty()) {
                return null;
            }
            int index = (int) (System.nanoTime() % subMsgQueues.size());
            try {
                return subMsgQueues.get(index);
            } catch (Exception e) {
                // 出现错误表示，在获取队列大小之后，队列进行了一次删除操作
                log.error("获取子队列出现错误", e);
                if ((++errorCount) < 3) {
                    continue;
                }
            }
        }
    }
}
