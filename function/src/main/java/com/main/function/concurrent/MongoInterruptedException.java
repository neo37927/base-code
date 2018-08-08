package com.code.demo.concurrent;

/**
 * A non-checked exception indicating that the driver has been interrupted by a call to Thread.interrupt.
 *
 * @see Thread#interrupt()
 * @see InterruptedException
 */
public class MongoInterruptedException extends MongoException {
    private static final long serialVersionUID = -4110417867718417860L;

    /**
     * Construct a new instance.
     *
     * @param message the message
     * @param e the cause
     */
    public MongoInterruptedException(final String message, final Exception e) {
        super(message, e);
    }
}
