package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class WorkUnitProcessorTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(WorkUnitProcessorTopologyNode.class);

    private ArrayList<PetasosNodeFDN> wupComponents;
    private ArrayList<PetasosNodeFDN> wupInterchangeComponents;

    public WorkUnitProcessorTopologyNode(){
        this.wupComponents = new ArrayList<>();
        this.wupInterchangeComponents = new ArrayList<>();
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public ArrayList<PetasosNodeFDN> getWupComponents() {
        return wupComponents;
    }

    public void setWupComponents(ArrayList<PetasosNodeFDN> wupComponents) {
        this.wupComponents = wupComponents;
    }

    public ArrayList<PetasosNodeFDN> getWupInterchangeComponents() {
        return wupInterchangeComponents;
    }

    public void setWupInterchangeComponents(ArrayList<PetasosNodeFDN> wupInterchangeComponents) {
        this.wupInterchangeComponents = wupInterchangeComponents;
    }

}
