package com.main.function.concurrent.connection;

import com.main.function.concurrent.async.SingleResultCallback;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

interface ConnectionPool extends Closeable {

    InternalConnection get();

    InternalConnection get(long timeout, TimeUnit timeUnit);

    void getAsync(SingleResultCallback<InternalConnection> callback);

    void invalidate();

    void close();
}
