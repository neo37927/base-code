package com.code.demo.concurrent.connection;

/**
 * An enumeration of all possible cluster types.
 *
 * @since 3.0
 */
public enum ClusterType {
    /**
     * A standalone mongod server.  A cluster of one.
     */
    STANDALONE,

    /**
     * A replicas set cluster.
     */
    REPLICA_SET,

    /**
     * A sharded cluster, connected via one or more mongos servers.
     */
    SHARDED,

    /**
     * The cluster type is not yet known.
     */
    UNKNOWN
}

