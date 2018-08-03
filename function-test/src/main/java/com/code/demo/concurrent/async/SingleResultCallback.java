package com.code.demo.concurrent.async;

/**
 * An interface to describe the completion of an asynchronous operation.
 *
 * @param <T> the result type
 * @since 3.0
 */
public interface SingleResultCallback<T> {
    /**
     * Called when the operation completes.
     * @param result the result, which may be null.  Always null if e is not null.
     * @param t      the throwable, or null if the operation completed normally
     */
    void onResult(T result, Throwable t);
}

