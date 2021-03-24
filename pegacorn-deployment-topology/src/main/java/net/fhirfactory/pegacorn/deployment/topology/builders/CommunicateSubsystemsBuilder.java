package net.fhirfactory.pegacorn.deployment.topology.builders;

import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.deployment.topology.map.communicate.CommunicateIrisIMTopologyFactory;
import net.fhirfactory.pegacorn.deployment.topology.map.communicate.CommunicateRoomServerDBaaSTopologyFactory;
import net.fhirfactory.pegacorn.deployment.topology.map.communicate.CommunicateRoomServerIMTopologyFactory;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.SolutionTopologyNode;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommunicateSubsystemsBuilder {

    @Inject
    private CommunicateIrisIMTopologyFactory irisIMTopologyFactory;

    @Inject
    private CommunicateRoomServerDBaaSTopologyFactory roomServerDBaaSTopologyFactory;

    @Inject
    private CommunicateRoomServerIMTopologyFactory roomServerIMTopologyFactory;

    @Inject
    private TopologyIM topologyIM;

    public void buildCommunicteIrisSubsystems(SolutionTopologyNode solution){

    }

    public void buildCommunicateRoomeServerSubsystems(SolutionTopologyNode solution){

    }
}
