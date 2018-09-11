package com.main.function.message;

public interface IMsgQueue {
    void put(Message msg);

    Message take();
}
