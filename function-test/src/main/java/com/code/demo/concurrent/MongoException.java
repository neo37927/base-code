package com.code.demo.concurrent;

/**
 * Top level Exception for all Exceptions, server-side or client-side, that come from the driver.
 */
public class MongoException extends RuntimeException {
    private static final long serialVersionUID = -4415279469780082174L;

    private final int code;

    /**
     * Static helper to create or cast a MongoException from a throwable
     *
     * @param t a throwable
     * @return and MongoException
     */
    public static MongoException fromThrowable(final Throwable t) {
        if (t == null) {
            return null;
        } else if (t instanceof MongoException) {
            return (MongoException) t;
        } else {
            return new MongoException(t.getMessage(), t);
        }
    }

    /**
     * @param msg the message
     */
    public MongoException(final String msg) {
        super(msg);
        code = -3;
    }

    /**
     * @param code the error code
     * @param msg  the message
     */
    public MongoException(final int code, final String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * @param msg the message
     * @param t   the throwable cause
     */
    public MongoException(final String msg, final Throwable t) {
        super(msg, t);
        code = -4;
    }

    /**
     * @param code the error code
     * @param msg  the message
     * @param t    the throwable cause
     */
    public MongoException(final int code, final String msg, final Throwable t) {
        super(msg, t);
        this.code = code;
    }

    /**
     * Gets the exception code
     *
     * @return the error code.
     */
    public int getCode() {
        return code;
    }
}

