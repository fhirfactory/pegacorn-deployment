package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BusinessServiceTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessServiceTopologyNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, DeploymentSiteTopologyNode> deploymentSites;
    private ConcurrentHashMap<TopologyNodeRDN, IPCEndpoint> externalisedServices;
    private boolean preferringEncryption;
    private String defaultDNSName;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public Map<TopologyNodeRDN, DeploymentSiteTopologyNode> getDeploymentSites() {
        return deploymentSites;
    }

    public void setDeploymentSites(Map<TopologyNodeRDN, DeploymentSiteTopologyNode> deploymentSites) {
        this.deploymentSites = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> siteNames = deploymentSites.keySet();
        for(TopologyNodeRDN name: siteNames){
            this.deploymentSites.putIfAbsent(name, deploymentSites.get(name));
        }
    }

    public Map<TopologyNodeRDN, IPCEndpoint> getExternalisedServices() {
        return externalisedServices;
    }

    public void setExternalisedServices(Map<TopologyNodeRDN, IPCEndpoint> externalisedServices) {
        this.externalisedServices = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> serviceNames = externalisedServices.keySet();
        for(TopologyNodeRDN name: serviceNames){
            this.externalisedServices.putIfAbsent(name, externalisedServices.get(name));
        }
    }

    public boolean isPreferringEncryption() {
        return preferringEncryption;
    }

    public void setPreferringEncryption(boolean preferringEncryption) {
        this.preferringEncryption = preferringEncryption;
    }

    public String getDefaultDNSName() {
        return defaultDNSName;
    }

    public void setDefaultDNSName(String defaultDNSName) {
        this.defaultDNSName = defaultDNSName;
    }
}
