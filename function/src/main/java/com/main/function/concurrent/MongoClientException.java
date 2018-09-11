package com.main.function.concurrent;

/**
 * A base class for exceptions indicating a failure condition with the MongoClient.
 *
 * @since 2.12
 */
public class MongoClientException extends MongoException {

    private static final long serialVersionUID = -5127414714432646066L;

    /**
     * Constructs a new instance.
     *
     * @param message the message
     */
    public MongoClientException(final String message) {
        super(message);
    }

    /**
     * Constructs a new instance.
     *
     * @param message the message
     * @param cause the cause
     */
    public MongoClientException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

