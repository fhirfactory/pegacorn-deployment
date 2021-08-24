package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SolutionTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(SolutionTopologyNode.class);

    private ArrayList<PetasosNodeFDN> subsystemList;
    private String systemOwner;

    public SolutionTopologyNode(){
        super();
        subsystemList = new ArrayList<>();
    }

    public ArrayList<PetasosNodeFDN> getSubsystemList() {
        return subsystemList;
    }

    public void setSubsystemList(ArrayList<PetasosNodeFDN> subsystemList) {
        this.subsystemList = subsystemList;
    }

    public String getSystemOwner() {
        return systemOwner;
    }

    public void setSystemOwner(String systemOwner) {
        this.systemOwner = systemOwner;

    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }
}
