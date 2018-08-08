package com.main.function.concurrent;

/**
 * Subclass of {@link MongoException} representing a network-related exception
 *
 * @since 2.12
 */
public class MongoSocketException extends MongoException {

    private static final long serialVersionUID = -4415279469780082174L;

    private final ServerAddress serverAddress;

    /**
     * @param serverAddress the address
     * @param msg the message
     * @param e the cause
     */
    MongoSocketException(final String msg, final ServerAddress serverAddress, final Throwable e) {
        super(-2, msg, e);
        this.serverAddress = serverAddress;
    }

    /**
     * Construct a new instance.
     *
     * @param message the message
     * @param serverAddress the address
     */
    public MongoSocketException(final String message, final ServerAddress serverAddress) {
        super(-2, message);
        this.serverAddress = serverAddress;
    }

    /**
     * Gets the server address for this exception.
     *
     * @return the address
     */
    public ServerAddress getServerAddress() {
        return serverAddress;
    }
}

