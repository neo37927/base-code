package com.main.function.concurrent.connection;

import org.bson.ByteBuf;

import java.io.Closeable;

class ResponseBuffers implements Closeable {
    private final ReplyHeader replyHeader;
    private final ByteBuf bodyByteBuffer;
    private volatile boolean isClosed;

    /**
     * Construct an instance.
     *
     * @param replyHeader the reply header
     * @param bodyByteBuffer a byte buffer containing the message body
     */
    public ResponseBuffers(final ReplyHeader replyHeader, final ByteBuf bodyByteBuffer) {
        this.replyHeader = replyHeader;
        this.bodyByteBuffer = bodyByteBuffer;
    }

    /**
     * Gets the reply header.
     *
     * @return the reply header
     */
    public ReplyHeader getReplyHeader() {
        return replyHeader;
    }

    /**
     * Returns a read-only buffer containing the response body.  Care should be taken to not use the returned buffer after this instance has
     * been closed.
     *
     * @return a read-only buffer containing the response body
     */
    public ByteBuf getBodyByteBuffer() {
        return bodyByteBuffer.asReadOnly();
    }

    @Override
    public void close() {
        if (!isClosed) {
            if (bodyByteBuffer != null) {
                bodyByteBuffer.release();
            }
            isClosed = true;
        }
    }
}

