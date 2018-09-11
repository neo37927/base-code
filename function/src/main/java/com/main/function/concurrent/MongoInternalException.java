package com.main.function.concurrent;

/**
 * A Mongo exception internal to the driver, not carrying any error code.
 */
public class MongoInternalException extends MongoException {
    private static final long serialVersionUID = -4415279469780082174L;

    /**
     * @param msg the description of the problem
     */
    public MongoInternalException(final String msg) {
        super(msg);
    }

    /**
     * @param msg the description of the problem
     * @param t   the Throwable root cause
     */
    public MongoInternalException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
