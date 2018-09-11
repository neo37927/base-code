package com.main.function.concurrent;

/**
 * An exception indicating that the driver has timed out waiting for either a server or a connection to become available.
 */
public class MongoTimeoutException extends MongoClientException {

    private static final long serialVersionUID = -3016560214331826577L;

    /**
     * Construct a new instance.
     *
     * @param message the message
     */
    public MongoTimeoutException(final String message) {
        super(message);
    }
}
