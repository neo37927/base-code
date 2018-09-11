package com.main.function.concurrent.connection;

/**
 * Enum of the current state of attempting to connect to a server.
 *
 * @since 2.12
 */
public enum ServerConnectionState {
    /**
     * The application is actively attempting to connect to the remote server.
     */
    CONNECTING,

    /**
     * The application is connected to the remote server.
     */
    CONNECTED
}