package com.main.function.concurrent.connection;

import com.main.function.concurrent.ServerAddress;
import com.main.function.concurrent.annotations.Immutable;

import static com.main.function.concurrent.assertions.Assertions.notNull;

/**
 * A client-generated identifier that uniquely identifies a MongoDB server.
 *
 * @since 3.0
 */
@Immutable
public final class ServerId {
    private final ClusterId clusterId;
    private final ServerAddress address;

    /**
     * Construct an instance.
     *
     * @param clusterId the client-generated cluster identifier
     * @param address the server address
     */
    public ServerId(final ClusterId clusterId, final ServerAddress address) {
        this.clusterId = notNull("clusterId", clusterId);
        this.address = notNull("address", address);
    }

    /**
     * Gets the cluster identifier.
     *
     * @return the cluster identifier
     */
    public ClusterId getClusterId() {
        return clusterId;
    }

    /**
     * Gets the server address.
     * @return the server address
     */
    public ServerAddress getAddress() {
        return address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServerId serverId = (ServerId) o;

        if (!address.equals(serverId.address)) {
            return false;
        }
        if (!clusterId.equals(serverId.clusterId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = clusterId.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ServerId{"
                + "clusterId=" + clusterId
                + ", address=" + address
                + '}';
    }
}
