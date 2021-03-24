package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCEndpoint;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClusterServiceTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterServiceTopologyNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, PlatformNode> platformNodes;
    private Integer platformNodeCount;
    private ConcurrentHashMap<TopologyNodeRDN, IPCEndpoint> clusterServiceServerEndpoints;
    private String defaultDNSName;
    private boolean internalTrafficEncrypted;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public Map<TopologyNodeRDN, PlatformNode> getPlatformNodes() {
        return platformNodes;
    }

    public void setPlatformNodes(Map<TopologyNodeRDN, PlatformNode> platformNodes) {
        this.platformNodes = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> plantNames = platformNodes.keySet();
        for(TopologyNodeRDN name: plantNames){
            this.platformNodes.putIfAbsent(name, platformNodes.get(name));
        }
    }

    public Map<TopologyNodeRDN, IPCEndpoint> getClusterServiceServerEndpoints() {
        return clusterServiceServerEndpoints;
    }

    public void setClusterServiceServerEndpoints(Map<TopologyNodeRDN, IPCEndpoint> clusterServiceServerEndpoints) {
        this.clusterServiceServerEndpoints = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> serverEndpointNames = clusterServiceServerEndpoints.keySet();
        for(TopologyNodeRDN name: serverEndpointNames){
            this.clusterServiceServerEndpoints.putIfAbsent(name, clusterServiceServerEndpoints.get(name));
        }
    }

    public String getDefaultDNSName() {
        return defaultDNSName;
    }

    public void setDefaultDNSName(String defaultDNSName) {
        this.defaultDNSName = defaultDNSName;
    }

    public boolean isInternalTrafficEncrypted() {
        return internalTrafficEncrypted;
    }

    public void setInternalTrafficEncrypted(boolean internalTrafficEncrypted) {
        this.internalTrafficEncrypted = internalTrafficEncrypted;
    }

    public Integer getPlatformNodeCount() {
        return platformNodeCount;
    }

    public void setPlatformNodeCount(Integer platformNodeCount) {
        this.platformNodeCount = platformNodeCount;
    }
}
