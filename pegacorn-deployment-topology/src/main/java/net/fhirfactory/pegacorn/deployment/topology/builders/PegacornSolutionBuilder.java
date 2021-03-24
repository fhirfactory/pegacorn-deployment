package net.fhirfactory.pegacorn.deployment.topology.builders;

import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.deployment.topology.model.common.SystemIdentificationInterface;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.SolutionTopologyNode;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public abstract class PegacornSolutionBuilder implements SystemIdentificationInterface, PegacornSolutionBuilderInterface{
    private boolean initialised;

    @Inject
    private TopologyIM topologyIM;

    @Inject
    private SystemIdentificationInterface systemIdentification;

    @Inject
    private CommunicateSubsystemsBuilder communicateSubsystemsBuilder;

    protected abstract Logger getLogger();

    public PegacornSolutionBuilder(){
        this.initialised  = false;
    }

    @PostConstruct
    public void initialiseSolution(){
        SolutionTopologyNode solution = buildSolutionNode();
        topologyIM.addTopologyNode(null, solution);
//        communicateSubsystemsBuilder
    }

    @Override
    public void initialiseSubsystemTopology() {
        if(!this.initialised){
            initialiseSolution();
        }
    }

    /**
     * Solution Node Builder
     *
     * @param
     * @return
     */
    public SolutionTopologyNode buildSolutionNode(){
        getLogger().debug(".buildSolutionNode(): Entry");
        SolutionTopologyNode solution = new SolutionTopologyNode();
        TopologyNodeRDN nodeRDN = new TopologyNodeRDN(TopologyNodeTypeEnum.SOLUTION, systemIdentification.getSystemName(), systemIdentification.getSystemVersion() );
        solution.constructFDN(null, nodeRDN);
        solution.constructFunctionFDN(null,nodeRDN );
        solution.setNodeRDN(nodeRDN);
        getLogger().debug(".buildSolutionNode(): Exit");
        return(solution);
    }

}
