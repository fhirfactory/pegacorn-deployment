package net.fhirfactory.pegacorn.deployment.topology.factories.common.archetypes.common;

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.PetasosEnabledSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.HTTPServerClusterServiceTopologyEndpointPort;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.TopologyEndpointTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.internals.PegacornReferenceProperties;

import javax.inject.Inject;

public abstract class PetasosEnabledSubsystemTopologyFactory extends PegacornTopologyFactoryBase{

    @Inject
    private PegacornReferenceProperties pegacornReferenceProperties;

    public PetasosEnabledSubsystemTopologyFactory(){
        super();
    }

    //
    // Build EdgeAnswer Port (if there)
    //

    protected void addEdgeAnswerPort( EndpointProviderInterface endpointProvider){
        getLogger().debug(".addEdgeAnswerPort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        HTTPServerClusterServiceTopologyEndpointPort edgeAnswerPort = new HTTPServerClusterServiceTopologyEndpointPort();
        HTTPIPCPortSegment port = petasosEnabledSubsystemPropertyFile.getEdgeAnswer();
        if(port == null){
            getLogger().debug(".addEdgeAnswerPort(): Exit, no port to add");
            return;
        }
        edgeAnswerPort.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameEdgeAnswer()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        edgeAnswerPort.setNodeRDN(nodeRDN);
        edgeAnswerPort.constructFDN(endpointProvider.getNodeFDN(), nodeRDN);
        edgeAnswerPort.setPortType(port.getPortType());
        edgeAnswerPort.setEndpointType(TopologyEndpointTypeEnum.HTTP_API_SERVER);
        edgeAnswerPort.setPortValue(port.getPortValue());
        edgeAnswerPort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        edgeAnswerPort.setBasePath(pegacornReferenceProperties.getPegacornInternalFhirResourceR4Path());
        edgeAnswerPort.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        edgeAnswerPort.setaServer(true);
        edgeAnswerPort.setContainingNodeFDN(endpointProvider.getNodeFDN());
        endpointProvider.addEndpoint(edgeAnswerPort.getNodeFDN());
        getLogger().trace(".addEdgeAnswerPort(): Add the EdgeAnswer Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getNodeFDN(), edgeAnswerPort);
        getLogger().debug(".addEdgeAnswerPort(): Exit, endpoint added");
    }

    //
    // Build EdgeAnswer Port (if there)
    //

    protected void addEdgeReceivePort( EndpointProviderInterface endpointProvider){
        getLogger().debug(".addEdgeReceivePort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        HTTPServerClusterServiceTopologyEndpointPort edgeReceivePort = new HTTPServerClusterServiceTopologyEndpointPort();
        HTTPIPCPortSegment port = petasosEnabledSubsystemPropertyFile.getEdgeReceive();
        if(port == null){
            getLogger().debug(".addEdgeReceivePort(): Exit, no port to add");
            return;
        }
        edgeReceivePort.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameEdgeReceive()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        edgeReceivePort.setNodeRDN(nodeRDN);
        edgeReceivePort.constructFDN(endpointProvider.getNodeFDN(), nodeRDN);
        edgeReceivePort.setPortType(port.getPortType());
        edgeReceivePort.setEndpointType(TopologyEndpointTypeEnum.HTTP_API_SERVER);
        edgeReceivePort.setPortValue(port.getPortValue());
        edgeReceivePort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        edgeReceivePort.setBasePath(pegacornReferenceProperties.getPegacornPetasosIPCPath());
        edgeReceivePort.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        edgeReceivePort.setaServer(true);
        edgeReceivePort.setContainingNodeFDN(endpointProvider.getNodeFDN());
        endpointProvider.addEndpoint( edgeReceivePort.getNodeFDN());
        getLogger().trace(".addEdgeReceivePort(): Add the EdgeReceive Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getNodeFDN(), edgeReceivePort);
        getLogger().debug(".addEdgeReceivePort(): Exit, endpoint added");
    }

    //
    // Build PetasosReplication Port (if there)
    //

    protected void addPetasosReplicationPort(EndpointProviderInterface endpointProvider){
        getLogger().debug(".addPetasosReplicationPort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        HTTPServerClusterServiceTopologyEndpointPort petasosReplicationPort = new HTTPServerClusterServiceTopologyEndpointPort();
        HTTPIPCPortSegment port = petasosEnabledSubsystemPropertyFile.getPetasosReplication();
        if(port == null){
            getLogger().debug(".addPetasosReplicationPort(): Exit, no port to add");
            return;
        }
        petasosReplicationPort.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameEdgeReceive()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        petasosReplicationPort.setNodeRDN(nodeRDN);
        petasosReplicationPort.constructFDN(endpointProvider.getNodeFDN(), nodeRDN);
        petasosReplicationPort.setPortType(port.getPortType());
        petasosReplicationPort.setEndpointType(TopologyEndpointTypeEnum.HTTP_API_SERVER);
        petasosReplicationPort.setPortValue(port.getPortValue());
        petasosReplicationPort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        petasosReplicationPort.setBasePath(pegacornReferenceProperties.getPegacornPetasosReplicationPath());
        petasosReplicationPort.setaServer(true);
        petasosReplicationPort.setContainingNodeFDN(endpointProvider.getNodeFDN());
        petasosReplicationPort.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        endpointProvider.addEndpoint( petasosReplicationPort.getNodeFDN());
        getLogger().trace(".addPetasosReplicationPort(): Add the PetasosReplication Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getNodeFDN(), petasosReplicationPort);
        getLogger().debug(".addPetasosReplicationPort(): Exit, endpoint added");
    }

    //
    // Build PetasosStatus Port (if there)
    //

    protected void addPetasosStatusPort(EndpointProviderInterface endpointProvider){
        getLogger().debug(".addPetasosStatusPort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        HTTPServerClusterServiceTopologyEndpointPort petasosStatusPort = new HTTPServerClusterServiceTopologyEndpointPort();
        HTTPIPCPortSegment port = petasosEnabledSubsystemPropertyFile.getPetasosStatus();
        if(port == null){
            getLogger().debug(".addPetasosStatusPort(): Exit, no port to add");
            return;
        }
        petasosStatusPort.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameEdgeReceive()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        petasosStatusPort.setNodeRDN(nodeRDN);
        petasosStatusPort.constructFDN(endpointProvider.getNodeFDN(), nodeRDN);
        petasosStatusPort.setPortType(port.getPortType());
        petasosStatusPort.setEndpointType(TopologyEndpointTypeEnum.HTTP_API_SERVER);
        petasosStatusPort.setPortValue(port.getPortValue());
        petasosStatusPort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        petasosStatusPort.setBasePath(pegacornReferenceProperties.getPegacornPetasosStatusPath());
        petasosStatusPort.setaServer(true);
        petasosStatusPort.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        petasosStatusPort.setContainingNodeFDN(endpointProvider.getNodeFDN());
        endpointProvider.addEndpoint(petasosStatusPort.getNodeFDN());
        getLogger().trace(".addPetasosStatusPort(): Add the PetasosStatus Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getNodeFDN(), petasosStatusPort);
        getLogger().debug(".addPetasosStatusPort(): Exit, endpoint added");
    }
}
