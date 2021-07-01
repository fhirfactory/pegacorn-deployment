package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common;

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.PetasosEnabledSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsIPCServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsInitialHostSegment;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCInterface;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCInterfaceDefinition;
import net.fhirfactory.pegacorn.deployment.topology.model.common.valuesets.AdditionalParametersListEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.common.valuesets.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.HTTPServerClusterServiceTopologyEndpointPort;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.edge.InitialHostSpecification;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.edge.StandardEdgeIPCEndpoint;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.TopologyEndpointTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.ProcessingPlantTopologyNode;
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
        HTTPIPCServerPortSegment port = petasosEnabledSubsystemPropertyFile.getEdgeAnswer();
        if(port == null){
            getLogger().debug(".addEdgeAnswerPort(): Exit, no port to add");
            return;
        }
        edgeAnswerPort.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getInterfaceNames().getEndpointServerName(getInterfaceNames().getFunctionNameEdgeAnswer());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        edgeAnswerPort.setNodeRDN(nodeRDN);
        edgeAnswerPort.setName(getInterfaceNames().getFunctionNameEdgeAnswer());
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

    protected void addInterZoneIPCJGroupsPort( EndpointProviderInterface endpointProvider){
        getLogger().debug(".addInterZoneIPCJGroupsPort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        StandardEdgeIPCEndpoint interZoneIPC = new StandardEdgeIPCEndpoint();
        JGroupsIPCServerPortSegment port = petasosEnabledSubsystemPropertyFile.getInterZoneIPC();
        if(port == null){
            getLogger().debug(".addInterZoneIPCJGroupsPort(): Exit, no port to add");
            return;
        }
        interZoneIPC.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getInterfaceNames().getEndpointServerName(getInterfaceNames().getFunctionNameInterZoneJGroupsIPC());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        interZoneIPC.setName(getInterfaceNames().getFunctionNameInterZoneJGroupsIPC());
        interZoneIPC.setNodeRDN(nodeRDN);
        interZoneIPC.constructFDN(endpointProvider.getNodeFDN(), nodeRDN);
        interZoneIPC.setPortType(port.getPortType());
        interZoneIPC.setInterfaceDNSName(port.getHostDNSEntry());
        interZoneIPC.setEndpointType(TopologyEndpointTypeEnum.JGROUPS_INTERZONE_IPC_MESSAGING_SERVICE);
        interZoneIPC.setSecurityZone(NetworkSecurityZoneEnum.fromSecurityZoneString(getPropertyFile().getDeploymentZone().getSecurityZoneName()));
        interZoneIPC.setPortValue(port.getPortValue());
        interZoneIPC.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        interZoneIPC.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        for(JGroupsInitialHostSegment currentInitialHostSegment: port.getInitialHosts()) {
            getLogger().trace("addInterZoneIPCJGroupsPort(): Adding initialHost->{}", currentInitialHostSegment);
            InitialHostSpecification currentInitialHostSpec = new InitialHostSpecification();
            currentInitialHostSpec.setHostName(currentInitialHostSegment.getHostName());
            currentInitialHostSpec.setPort(currentInitialHostSegment.getPortNumber());
            interZoneIPC.getInitialHosts().add(currentInitialHostSpec);
        }
        interZoneIPC.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        interZoneIPC.setaServer(true);
        interZoneIPC.setContainingNodeFDN(endpointProvider.getNodeFDN());
        for(InterfaceDefinitionSegment currentSegment: port.getSupportedInterfaceProfiles()) {
            IPCInterface currentInterface = new IPCInterface();
            IPCInterfaceDefinition currentInterfaceDefinition = new IPCInterfaceDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            currentInterface.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
            interZoneIPC.getSupportedInterfaceSet().add(currentInterface);
        }
        endpointProvider.addEndpoint(interZoneIPC.getNodeFDN());
        getLogger().trace(".addInterZoneIPCJGroupsPort(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getNodeFDN(), interZoneIPC);
        getLogger().debug(".addInterZoneIPCJGroupsPort(): Exit, endpoint added");
    }

    protected void addIntraZoneIPCJGroupsPort( EndpointProviderInterface endpointProvider){
        getLogger().debug(".addInterZoneIPCJGroupsPort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        StandardEdgeIPCEndpoint intraZoneIPC = new StandardEdgeIPCEndpoint();
        JGroupsIPCServerPortSegment port = petasosEnabledSubsystemPropertyFile.getIntraZoneIPC();
        if(port == null){
            getLogger().debug(".addInterZoneIPCJGroupsPort(): Exit, no port to add");
            return;
        }
        intraZoneIPC.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getInterfaceNames().getEndpointServerName(getInterfaceNames().getFunctionNameIntraZoneJGroupsIPC());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        intraZoneIPC.setName(getInterfaceNames().getFunctionNameIntraZoneJGroupsIPC());
        intraZoneIPC.setNodeRDN(nodeRDN);
        intraZoneIPC.setInterfaceDNSName(port.getHostDNSEntry());
        intraZoneIPC.constructFDN(endpointProvider.getNodeFDN(), nodeRDN);
        intraZoneIPC.setPortType(port.getPortType());
        intraZoneIPC.setEndpointType(TopologyEndpointTypeEnum.JGROUPS_INTRAZONE_IPC_MESSAGING_SERVICE);
        intraZoneIPC.setPortValue(port.getPortValue());
        intraZoneIPC.setSecurityZone(NetworkSecurityZoneEnum.fromSecurityZoneString(getPropertyFile().getDeploymentZone().getSecurityZoneName()));
        intraZoneIPC.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        intraZoneIPC.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        for(JGroupsInitialHostSegment currentInitialHostSegment: port.getInitialHosts()) {
            InitialHostSpecification currentInitialHostSpec = new InitialHostSpecification();
            currentInitialHostSpec.setHostName(currentInitialHostSegment.getHostName());
            currentInitialHostSpec.setPort(currentInitialHostSegment.getPortNumber());
            intraZoneIPC.getInitialHosts().add(currentInitialHostSpec);
        }
        intraZoneIPC.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        intraZoneIPC.setaServer(true);
        intraZoneIPC.setContainingNodeFDN(endpointProvider.getNodeFDN());
        for(InterfaceDefinitionSegment currentSegment: port.getSupportedInterfaceProfiles()) {
            IPCInterface currentInterface = new IPCInterface();
            IPCInterfaceDefinition currentInterfaceDefinition = new IPCInterfaceDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            currentInterface.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
            currentInterface.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
            intraZoneIPC.getSupportedInterfaceSet().add(currentInterface);
        }
        endpointProvider.addEndpoint(intraZoneIPC.getNodeFDN());
        getLogger().trace(".addInterZoneIPCJGroupsPort(): Add the IntraZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getNodeFDN(), intraZoneIPC);
        getLogger().debug(".addInterZoneIPCJGroupsPort(): Exit, endpoint added");
    }

    //
    // Build EdgeAnswer Port (if there)
    //
    /*

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

     */

    //
    // Build PetasosReplication Port (if there)
    //
/*
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

 */
}
