package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class BusinessServiceTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessServiceTopologyNode.class);

    private ArrayList<PetasosNodeFDN> deploymentSites;
    private ArrayList<PetasosNodeFDN> externalisedServices;
    private boolean preferringEncryption;
    private String defaultDNSName;

    public BusinessServiceTopologyNode(){
        super();
        this.deploymentSites = new ArrayList<>();
        this.externalisedServices = new ArrayList<>();
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public ArrayList<PetasosNodeFDN> getDeploymentSites() {
        return deploymentSites;
    }

    public void setDeploymentSites(ArrayList<PetasosNodeFDN> deploymentSites) {
        this.deploymentSites = deploymentSites;
    }

    public ArrayList<PetasosNodeFDN> getExternalisedServices() {
        return externalisedServices;
    }

    public void setExternalisedServices(ArrayList<PetasosNodeFDN> externalisedServices) {
        this.externalisedServices = externalisedServices;
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
