package com.main.function.concurrent.connection;

import com.main.function.concurrent.ServerAddress;
import com.main.function.concurrent.TagSet;
import com.main.function.concurrent.annotations.Immutable;


import java.util.*;

import static com.main.function.concurrent.assertions.Assertions.notNull;
import static java.lang.String.format;

/**
 * Immutable snapshot state of a cluster.
 *
 * @since 3.0
 */
@Immutable
public class ClusterDescription {
    private final ClusterConnectionMode connectionMode;
    private final ClusterType type;
    private final Set<ServerDescription> all;

    /**
     * Creates a new ClusterDescription.
     *
     * @param connectionMode     whether to connect directly to a single server or to multiple servers
     * @param type               what sort of cluster this is
     * @param serverDescriptions the descriptions of all the servers currently in this cluster
     */
    public ClusterDescription(final ClusterConnectionMode connectionMode, final ClusterType type,
                              final List<ServerDescription> serverDescriptions) {
        notNull("all", serverDescriptions);
        this.connectionMode = notNull("connectionMode", connectionMode);
        this.type = notNull("type",
                type);
        Set<ServerDescription> serverDescriptionSet = new TreeSet<ServerDescription>(new Comparator<ServerDescription>() {
            @Override
            public int compare(final ServerDescription o1, final ServerDescription o2) {
                int val = o1.getAddress().getHost().compareTo(o2.getAddress().getHost());
                if (val != 0) {
                    return val;
                }
                return integerCompare(o1.getAddress().getPort(), o2.getAddress().getPort());
            }

            private int integerCompare(final int p1, final int p2) {
                return (p1 < p2) ? -1 : ((p1 == p2) ? 0 : 1);
            }
        });
        serverDescriptionSet.addAll(serverDescriptions);
        this.all = Collections.unmodifiableSet(serverDescriptionSet);
    }

    /**
     * Return whether the cluster is compatible with the driver.
     *
     * @return true if the cluster is compatible with the driver.
     */
    public boolean isCompatibleWithDriver() {
        for (final ServerDescription cur : all) {
            if (!cur.isCompatibleWithDriver()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets whether this cluster is connecting to a single server or multiple servers.
     *
     * @return the ClusterConnectionMode for this cluster
     */
    public ClusterConnectionMode getConnectionMode() {
        return connectionMode;
    }

    /**
     * Gets the specific type of this cluster
     *
     * @return a ClusterType enum representing the type of this cluster
     */
    public ClusterType getType() {
        return type;
    }

    /**
     * Returns the Set of all server descriptions in this cluster, sorted by the String value of the ServerAddress of each one.
     *
     * @return the set of server descriptions
     */
    public Set<ServerDescription> getAll() {
        return all;
    }

    /**
     * Returns the ServerDescription for the server at the given address
     *
     * @param serverAddress the ServerAddress for a server in this cluster
     * @return the ServerDescription for this server
     */
    public ServerDescription getByServerAddress(final ServerAddress serverAddress) {
        for (final ServerDescription cur : getAll()) {
            if (cur.isOk() && cur.getAddress().equals(serverAddress)) {
                return cur;
            }
        }
        return null;
    }

    /**
     * While it may seem counter-intuitive that a MongoDB cluster can have more than one primary, it can in the case where the client's view
     * of the cluster is a set of mongos servers, any of which can serve as the primary.
     *
     * @return a list of servers that can act as primaries
     */
    public List<ServerDescription> getPrimaries() {
        return getServersByPredicate(new Predicate() {
            public boolean apply(final ServerDescription serverDescription) {
                return serverDescription.isPrimary();
            }
        });
    }

    /**
     * Get a list of all the secondaries in this cluster
     *
     * @return a List of ServerDescriptions of all the secondaries this cluster is currently aware of
     */
    public List<ServerDescription> getSecondaries() {
        return getServersByPredicate(new Predicate() {
            public boolean apply(final ServerDescription serverDescription) {
                return serverDescription.isSecondary();
            }
        });
    }

    /**
     * Get a list of all the secondaries in this cluster that match a given TagSet
     *
     * @param tagSet a Set of replica set tags
     * @return a List of ServerDescriptions of all the secondaries this cluster that match all of the given tags
     */
    public List<ServerDescription> getSecondaries(final TagSet tagSet) {
        return getServersByPredicate(new Predicate() {
            public boolean apply(final ServerDescription serverDescription) {
                return serverDescription.isSecondary() && serverDescription.hasTags(tagSet);
            }
        });
    }

    /**
     * Gets a list of ServerDescriptions for all the servers in this cluster which are currently accessible.
     *
     * @return a List of ServerDescriptions for all servers that have a status of OK
     */
    public List<ServerDescription> getAny() {
        return getServersByPredicate(new Predicate() {
            public boolean apply(final ServerDescription serverDescription) {
                return serverDescription.isOk();
            }
        });
    }

    /**
     * Gets a list of all the primaries and secondaries in this cluster.
     *
     * @return a list of ServerDescriptions for all primary and secondary servers
     */
    public List<ServerDescription> getAnyPrimaryOrSecondary() {
        return getServersByPredicate(new Predicate() {
            public boolean apply(final ServerDescription serverDescription) {
                return serverDescription.isPrimary() || serverDescription.isSecondary();
            }
        });
    }

    /**
     * Gets a list of all the primaries and secondaries in this cluster that match the given replica set tags.
     *
     * @param tagSet a Set of replica set tags
     * @return a list of ServerDescriptions for all primary and secondary servers that contain all of the given tags
     */
    public List<ServerDescription> getAnyPrimaryOrSecondary(final TagSet tagSet) {
        return getServersByPredicate(new Predicate() {
            public boolean apply(final ServerDescription serverDescription) {
                return (serverDescription.isPrimary() || serverDescription.isSecondary()) && serverDescription.hasTags(tagSet);
            }
        });
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClusterDescription that = (ClusterDescription) o;

        if (!all.equals(that.all)) {
            return false;
        }
        if (connectionMode != that.connectionMode) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = all.hashCode();
        result = 31 * result + connectionMode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ClusterDescription{"
                + "type=" + getType()
                + ", connectionMode=" + connectionMode
                + ", all=" + all
                + '}';
    }

    /**
     * Returns a short, pretty description for this ClusterDescription.
     *
     * @return a String describing this cluster.
     */
    public String getShortDescription() {
        StringBuilder serverDescriptions = new StringBuilder();
        String delimiter = "";
        for (final ServerDescription cur : all) {
            serverDescriptions.append(delimiter).append(cur.getShortDescription());
            delimiter = ", ";
        }
        return format("{type=%s, servers=[%s]", type, serverDescriptions);
    }

    private interface Predicate {
        boolean apply(ServerDescription serverDescription);
    }

    private List<ServerDescription> getServersByPredicate(final Predicate predicate) {
        List<ServerDescription> membersByTag = new ArrayList<ServerDescription>();

        for (final ServerDescription cur : all) {
            if (predicate.apply(cur)) {
                membersByTag.add(cur);
            }
        }

        return membersByTag;
    }
}

