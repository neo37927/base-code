package com.code.demo.queue.message;

public interface IMsgQueue {
    void put(Message msg);

    Message take();
}
