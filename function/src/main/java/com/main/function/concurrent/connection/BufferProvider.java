package com.main.function.concurrent.connection;

import org.bson.ByteBuf;

/**
 * A provider of instances of ByteBuf.
 *
 * @since 3.0
 */
public interface BufferProvider {
    /**
     * Gets a buffer with the givens capacity.
     *
     * @param size the size required for the buffer
     * @return a ByteBuf with the given size, which is now owned by the caller and must be released.
     */
    ByteBuf getBuffer(int size);
}
