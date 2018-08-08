package com.main.function.concurrent.connection;

/**
 * The type of the server.
 *
 * @since 3.0
 */
public enum ServerType {
    /**
     * A standalone mongod server.
     */
    STANDALONE {
        @Override
        public ClusterType getClusterType() {
            return ClusterType.STANDALONE;
        }
    },

    /**
     * A replica set primary.
     */
    REPLICA_SET_PRIMARY {
        @Override
        public ClusterType getClusterType() {
            return ClusterType.REPLICA_SET;
        }
    },

    /**
     * A replica set secondary.
     */
    REPLICA_SET_SECONDARY {
        @Override
        public ClusterType getClusterType() {
            return ClusterType.REPLICA_SET;
        }
    },

    /**
     * A replica set arbiter.
     */
    REPLICA_SET_ARBITER {
        @Override
        public ClusterType getClusterType() {
            return ClusterType.REPLICA_SET;
        }
    },

    /**
     * A replica set member that is none of the other types (a passive, for example).
     */
    REPLICA_SET_OTHER {
        @Override
        public ClusterType getClusterType() {
            return ClusterType.REPLICA_SET;
        }
    },

    /**
     * A replica set member that does not report a set name or a hosts list
     */
    REPLICA_SET_GHOST {
        @Override
        public ClusterType getClusterType() {
            return ClusterType.REPLICA_SET;
        }
    },

    /**
     * A router to a sharded cluster, i.e. a mongos server.
     */
    SHARD_ROUTER {
        @Override
        public ClusterType getClusterType() {
            return ClusterType.SHARDED;
        }
    },

    /**
     * The server type is not yet known.
     */
    UNKNOWN {
        @Override
        public ClusterType getClusterType() {
            return ClusterType.UNKNOWN;
        }
    };

    /**
     * The type of the cluster to which this server belongs
     *
     * @return the cluster type
     */
    public abstract ClusterType getClusterType();
}


