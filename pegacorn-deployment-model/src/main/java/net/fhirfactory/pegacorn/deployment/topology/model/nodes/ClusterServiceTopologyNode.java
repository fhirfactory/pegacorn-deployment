package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.common.EndpointProviderInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ClusterServiceTopologyNode extends TopologyNode implements EndpointProviderInterface {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterServiceTopologyNode.class);

    private ArrayList<PetasosNodeFDN> platformNodes;
    private Integer platformNodeCount;
    private ArrayList<PetasosNodeFDN> serviceEndpoints;
    private String defaultDNSName;
    private boolean internalTrafficEncrypted;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public ClusterServiceTopologyNode(){
        super();
        this.platformNodes = new ArrayList<>();
        this.serviceEndpoints = new ArrayList<>();
    }

    public ArrayList<PetasosNodeFDN> getPlatformNodes() {
        return platformNodes;
    }

    public void setPlatformNodes(ArrayList<PetasosNodeFDN> platformNodes) {
        this.platformNodes = platformNodes;
    }

    public ArrayList<PetasosNodeFDN> getServiceEndpoints() {
        return serviceEndpoints;
    }

    public void setServiceEndpoints(ArrayList<PetasosNodeFDN> serviceEndpoints) {
        this.serviceEndpoints = serviceEndpoints;
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

    @Override
    public void addEndpoint(PetasosNodeFDN endpointFDN) {
        LOG.debug(".addEndpoint(): Entry, endpointFDN->{}", endpointFDN);
        serviceEndpoints.add(endpointFDN);
    }
}
