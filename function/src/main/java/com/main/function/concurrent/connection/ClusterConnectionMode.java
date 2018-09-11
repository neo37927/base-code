package com.main.function.concurrent.connection;

/**
 * The cluster connection mode.
 *
 * @since 3.0
 */
public enum ClusterConnectionMode {
    /**
     * Connect directly to a server, regardless of the type of cluster it is a part of.
     */
    SINGLE,

    /**
     * Connect to multiple servers in a cluster (either a replica set or multiple mongos servers)
     */
    MULTIPLE
}
