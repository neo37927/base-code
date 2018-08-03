package com.code.demo.concurrent.connection;


import com.code.demo.concurrent.annotations.ThreadSafe;

import java.util.List;

@ThreadSafe
interface InternalConnection extends BufferProvider {

    /**
     * Gets the description of this connection.
     *
     * @return the connection description
     */
    ConnectionDescription getDescription();

    /**
     * Opens the connection so its ready for use
     */
    void open();

    /**
     * Opens the connection so its ready for use
     *
     * @param callback the callback to be called once the connection has been opened
     */
    void openAsync(SingleResultCallback<Void> callback);

    /**
     * Closes the connection.
     */
    void close();

    /**
     * Returns if the connection has been opened
     *
     * @return true if connection has been opened
     */
    boolean opened();

    /**
     * Returns the closed state of the connection
     *
     * @return true if connection is closed
     */
    boolean isClosed();

    /**
     * Send a message to the server. The connection may not make any attempt to validate the integrity of the message.
     *
     * @param byteBuffers   the list of byte buffers to send.
     * @param lastRequestId the request id of the last message in byteBuffers
     */
    void sendMessage(List<ByteBuf> byteBuffers, int lastRequestId);

    /**
     * Receive a response to a sent message from the server.
     *
     * @param responseTo the request id that this message is a response to
     * @return the response
     */
    ResponseBuffers receiveMessage(int responseTo);

    /**
     * Asynchronously send a message to the server. The connection may not make any attempt to validate the integrity of the message.
     *
     * @param byteBuffers   the list of byte buffers to send
     * @param lastRequestId the request id of the last message in byteBuffers
     * @param callback      the callback to invoke on completion
     */
    void sendMessageAsync(List<ByteBuf> byteBuffers, int lastRequestId, SingleResultCallback<Void> callback);

    /**
     * Asynchronously receive a response to a sent message from the server.
     *
     * @param responseTo the request id that this message is a response to
     * @param callback the callback to invoke on completion
     */
    void receiveMessageAsync(int responseTo, SingleResultCallback<ResponseBuffers> callback);

}

