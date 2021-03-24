package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DeploymentSiteTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(DeploymentSiteTopologyNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, ClusterServiceTopologyNode> clusterServices;
    private Integer instanceCount;

    @Override
    protected Logger getLogger() {
        return null;
    }

    public Map<TopologyNodeRDN, ClusterServiceTopologyNode> getClusterServices() {
        return clusterServices;
    }

    public void setClusterServices(Map<TopologyNodeRDN, ClusterServiceTopologyNode> clusterServices) {
        this.clusterServices = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> serviceNames = clusterServices.keySet();
        for(TopologyNodeRDN name: serviceNames){
            this.clusterServices.putIfAbsent(name, clusterServices.get(name));
        }
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
    }
}
