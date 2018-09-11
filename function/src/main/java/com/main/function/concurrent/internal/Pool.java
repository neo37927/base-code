package com.main.function.concurrent.internal;

import java.util.concurrent.TimeUnit;

interface Pool<T> {
    T get();

    T get(long timeout, TimeUnit timeUnit);

    void release(T t);

    void close();

    void release(T t, boolean discard);
}
