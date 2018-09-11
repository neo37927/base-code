package com.main.function.concurrent.async;

/**
 * 描述异步操作完成的接口.
 *
 * @param <T> the result type
 * @since 3.0
 */
public interface SingleResultCallback<T> {
    /**
     * 操作完成后调用
     * @param result the result, which may be null.  Always null if e is not null.
     * @param t      the throwable, or null if the operation completed normally
     */
    void onResult(T result, Throwable t);
}

