package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SubsystemTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(SubsystemTopologyNode.class);

    private ArrayList<PetasosNodeFDN> businessServices;
    private Integer siteCount;

    public SubsystemTopologyNode(){
        businessServices = new ArrayList<>();
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public ArrayList<PetasosNodeFDN> getBusinessServices() {
        return businessServices;
    }

    public void setBusinessServices(ArrayList<PetasosNodeFDN> businessServices) {
        this.businessServices = businessServices;
    }

    public Integer getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(Integer siteCount) {
        this.siteCount = siteCount;
    }
}
