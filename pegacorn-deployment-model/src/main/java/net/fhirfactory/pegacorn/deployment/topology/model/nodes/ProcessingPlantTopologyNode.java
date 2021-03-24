package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.deployment.topology.model.connector.common.IPCConnection;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessingPlantTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessingPlantTopologyNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, WorkshopTopologyNode> workshops;
    private ConcurrentHashMap<TopologyNodeRDN, IPCEndpoint> enpoints;
    private ConcurrentHashMap<TopologyNodeRDN, IPCConnection> connections;

    private String defaultDNSName;
    private boolean internalTrafficEncrypted;
    private Integer instanceCount;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public ProcessingPlantTopologyNode(){
        this.workshops = new ConcurrentHashMap<>();
        this.enpoints = new ConcurrentHashMap<>();
        this.connections = new ConcurrentHashMap<>();

        this.defaultDNSName = null;
        internalTrafficEncrypted = false;
    }

    public Map<TopologyNodeRDN, WorkshopTopologyNode> getWorkshops() {
        return workshops;
    }

    public boolean isInternalTrafficEncrypted() {
        return internalTrafficEncrypted;
    }

    public void setInternalTrafficEncrypted(boolean internalTrafficEncrypted) {
        this.internalTrafficEncrypted = internalTrafficEncrypted;
    }

    public void setWorkshops(Map<TopologyNodeRDN, WorkshopTopologyNode> workshops) {
        this.workshops = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> workshopNames = workshops.keySet();
        for(TopologyNodeRDN name: workshopNames){
            this.workshops.putIfAbsent(name, workshops.get(name));
        }
    }

    public Map<TopologyNodeRDN, IPCEndpoint> getEnpoints() {
        return enpoints;
    }

    public void setEnpoints(Map<TopologyNodeRDN, IPCEndpoint> enpoints) {
        this.enpoints = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> endpointNames = enpoints.keySet();
        for(TopologyNodeRDN name: endpointNames){
            this.enpoints.putIfAbsent(name, enpoints.get(name));
        }
    }

    public Map<TopologyNodeRDN, IPCConnection> getConnections() {
        return connections;
    }

    public void setConnections(Map<TopologyNodeRDN, IPCConnection> connections) {
        this.connections = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> connectionNames = connections.keySet();
        for(TopologyNodeRDN name: connectionNames){
            this.connections.putIfAbsent(name, connections.get(name));
        }
    }

    public String getDefaultDNSName() {
        return defaultDNSName;
    }

    public void setDefaultDNSName(String defaultDNSName) {
        this.defaultDNSName = defaultDNSName;
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
    }
}
