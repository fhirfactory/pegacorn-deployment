package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common;

import net.fhirfactory.pegacorn.core.constants.systemwide.PegacornReferenceProperties;
import net.fhirfactory.pegacorn.core.model.componentid.ComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.PetasosEnabledSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusteredInteractHTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractHTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsIPCServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsInitialHostSegment;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCInterface;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCInterfaceDefinition;
import net.fhirfactory.pegacorn.core.model.topology.mode.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.ExternalSystemIPCEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCServerTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.petasos.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.StandardInteractClientTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.technologies.HTTPProcessingPlantTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.technologies.HTTPServerClusterServiceTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.InitialHostSpecification;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.answer.StandardEdgeIPCEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.core.model.topology.nodes.external.ConnectedExternalSystemTopologyNode;
import net.fhirfactory.pegacorn.util.PegacornProperties;

import javax.inject.Inject;

public abstract class PetasosEnabledSubsystemTopologyFactory extends PegacornTopologyFactoryBase{

    @Inject
    private PegacornReferenceProperties pegacornReferenceProperties;

    @Inject
    private PegacornProperties pegacornProperties;

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
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER, getInterfaceNames().getFunctionNameEdgeAnswer());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        edgeAnswerPort.setComponentRDN(nodeRDN);
        edgeAnswerPort.setActualHostIP(getActualHostIP());
        edgeAnswerPort.setActualPodIP(getActualPodIP());
        edgeAnswerPort.setName(getInterfaceNames().getFunctionNameEdgeAnswer());
        edgeAnswerPort.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        edgeAnswerPort.setPortType(port.getPortType());
        edgeAnswerPort.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        edgeAnswerPort.setPortValue(port.getPortValue());
        edgeAnswerPort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        edgeAnswerPort.setBasePath(pegacornReferenceProperties.getPegacornInternalFhirResourceR4Path());
        edgeAnswerPort.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        edgeAnswerPort.setaServer(true);
        edgeAnswerPort.setContainingNodeFDN(endpointProvider.getComponentFDN());
        endpointProvider.addEndpoint(edgeAnswerPort.getComponentFDN());
        getLogger().trace(".addEdgeAnswerPort(): Add the EdgeAnswer Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), edgeAnswerPort);
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
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getFunctionNameInterZoneJGroupsIPC());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        interZoneIPC.setName(getInterfaceNames().getFunctionNameInterZoneJGroupsIPC());
        interZoneIPC.setComponentRDN(nodeRDN);
        interZoneIPC.setActualHostIP(getActualHostIP());
        interZoneIPC.setActualPodIP(getActualPodIP());
        interZoneIPC.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        interZoneIPC.setPortType(port.getPortType());
        interZoneIPC.setHostDNSName(port.getHostDNSEntry());
        interZoneIPC.setEndpointType(PetasosEndpointTopologyTypeEnum.JGROUPS_INTERZONE_SERVICE);
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
        interZoneIPC.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        interZoneIPC.setaServer(true);
        interZoneIPC.setContainingNodeFDN(endpointProvider.getComponentFDN());
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
        endpointProvider.addEndpoint(interZoneIPC.getComponentFDN());
        getLogger().trace(".addInterZoneIPCJGroupsPort(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), interZoneIPC);
        getLogger().debug(".addInterZoneIPCJGroupsPort(): Exit, endpoint added");
    }

    protected void addInterZoneOAMJGroupsPort( EndpointProviderInterface endpointProvider){
        getLogger().debug(".addInterZoneOAMJGroupsPort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        StandardEdgeIPCEndpoint interZoneOAM = new StandardEdgeIPCEndpoint();
        JGroupsIPCServerPortSegment port = petasosEnabledSubsystemPropertyFile.getInterZoneOAM();
        if(port == null){
            getLogger().debug(".addInterZoneOAMJGroupsPort(): Exit, no port to add");
            return;
        }
        interZoneOAM.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getFunctionNameInterZoneJGroupsIPC());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        interZoneOAM.setName(getInterfaceNames().getFunctionNameInterZoneJGroupsIPC());
        interZoneOAM.setComponentRDN(nodeRDN);
        interZoneOAM.setActualHostIP(getActualHostIP());
        interZoneOAM.setActualPodIP(getActualPodIP());
        interZoneOAM.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        interZoneOAM.setPortType(port.getPortType());
        interZoneOAM.setHostDNSName(port.getHostDNSEntry());
        interZoneOAM.setEndpointType(PetasosEndpointTopologyTypeEnum.JGROUPS_INTERZONE_SERVICE);
        interZoneOAM.setSecurityZone(NetworkSecurityZoneEnum.fromSecurityZoneString(getPropertyFile().getDeploymentZone().getSecurityZoneName()));
        interZoneOAM.setPortValue(port.getPortValue());
        interZoneOAM.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        interZoneOAM.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        for(JGroupsInitialHostSegment currentInitialHostSegment: port.getInitialHosts()) {
            getLogger().trace("addInterZoneOAMJGroupsPort(): Adding initialHost->{}", currentInitialHostSegment);
            InitialHostSpecification currentInitialHostSpec = new InitialHostSpecification();
            currentInitialHostSpec.setHostName(currentInitialHostSegment.getHostName());
            currentInitialHostSpec.setPort(currentInitialHostSegment.getPortNumber());
            interZoneOAM.getInitialHosts().add(currentInitialHostSpec);
        }
        interZoneOAM.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        interZoneOAM.setaServer(true);
        interZoneOAM.setContainingNodeFDN(endpointProvider.getComponentFDN());
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
            interZoneOAM.getSupportedInterfaceSet().add(currentInterface);
        }
        endpointProvider.addEndpoint(interZoneOAM.getComponentFDN());
        getLogger().trace(".addInterZoneOAMJGroupsPort(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), interZoneOAM);
        getLogger().debug(".addInterZoneOAMJGroupsPort(): Exit, endpoint added");
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
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getFunctionNameIntraZoneJGroupsIPC());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        intraZoneIPC.setName(getInterfaceNames().getFunctionNameIntraZoneJGroupsIPC());
        intraZoneIPC.setComponentRDN(nodeRDN);
        intraZoneIPC.setActualHostIP(getActualHostIP());
        intraZoneIPC.setActualPodIP(getActualPodIP());
        intraZoneIPC.setHostDNSName(port.getHostDNSEntry());
        intraZoneIPC.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        intraZoneIPC.setPortType(port.getPortType());
        intraZoneIPC.setEndpointType(PetasosEndpointTopologyTypeEnum.JGROUPS_INTRAZONE_SERVICE);
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
        intraZoneIPC.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        intraZoneIPC.setaServer(true);
        intraZoneIPC.setContainingNodeFDN(endpointProvider.getComponentFDN());
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
        endpointProvider.addEndpoint(intraZoneIPC.getComponentFDN());
        getLogger().trace(".addInterZoneIPCJGroupsPort(): Add the IntraZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), intraZoneIPC);
        getLogger().debug(".addInterZoneIPCJGroupsPort(): Exit, endpoint added");
    }

    protected void addIntraZoneOAMJGroupsPort( EndpointProviderInterface endpointProvider){
        getLogger().debug(".addIntraZoneOAMJGroupsPort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        StandardEdgeIPCEndpoint intraZoneOAM = new StandardEdgeIPCEndpoint();
        JGroupsIPCServerPortSegment port = petasosEnabledSubsystemPropertyFile.getIntraZoneOAM();
        if(port == null){
            getLogger().debug(".addIntraZoneOAMJGroupsPort(): Exit, no port to add");
            return;
        }
        intraZoneOAM.setEncrypted(petasosEnabledSubsystemPropertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getFunctionNameIntraZoneJGroupsIPC());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        intraZoneOAM.setName(getInterfaceNames().getFunctionNameIntraZoneJGroupsIPC());
        intraZoneOAM.setComponentRDN(nodeRDN);
        intraZoneOAM.setHostDNSName(port.getHostDNSEntry());
        intraZoneOAM.setActualPodIP(getActualPodIP());
        intraZoneOAM.setActualHostIP(getActualHostIP());
        intraZoneOAM.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        intraZoneOAM.setPortType(port.getPortType());
        intraZoneOAM.setEndpointType(PetasosEndpointTopologyTypeEnum.JGROUPS_INTRAZONE_SERVICE);
        intraZoneOAM.setPortValue(port.getPortValue());
        intraZoneOAM.setSecurityZone(NetworkSecurityZoneEnum.fromSecurityZoneString(getPropertyFile().getDeploymentZone().getSecurityZoneName()));
        intraZoneOAM.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        intraZoneOAM.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        for(JGroupsInitialHostSegment currentInitialHostSegment: port.getInitialHosts()) {
            InitialHostSpecification currentInitialHostSpec = new InitialHostSpecification();
            currentInitialHostSpec.setHostName(currentInitialHostSegment.getHostName());
            currentInitialHostSpec.setPort(currentInitialHostSegment.getPortNumber());
            intraZoneOAM.getInitialHosts().add(currentInitialHostSpec);
        }
        intraZoneOAM.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        intraZoneOAM.setaServer(true);
        intraZoneOAM.setContainingNodeFDN(endpointProvider.getComponentFDN());
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
            intraZoneOAM.getSupportedInterfaceSet().add(currentInterface);
        }
        endpointProvider.addEndpoint(intraZoneOAM.getComponentFDN());
        getLogger().trace(".addIntraZoneOAMJGroupsPort(): Add the IntraZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), intraZoneOAM);
        getLogger().debug(".addIntraZoneOAMJGroupsPort(): Exit, endpoint added");
    }

    //
    // Build an HTTP Client Endpoint (Helper Method)
    //

    protected StandardInteractClientTopologyEndpointPort newHTTPClient(EndpointProviderInterface endpointProvider, String endpointFunctionName, StandardInteractClientPortSegment httpClientPort){
        getLogger().debug(".newHTTPClient(): Entry, endpointProvider->{}, httpClientPort->{}", endpointProvider, httpClientPort);
        StandardInteractClientTopologyEndpointPort httpFHIRClient = new StandardInteractClientTopologyEndpointPort();
        if(httpClientPort == null){
            getLogger().debug(".newHTTPClient(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointServerName(endpointFunctionName);
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        httpFHIRClient.setComponentRDN(nodeRDN);
        httpFHIRClient.setName(endpointFunctionName);
        httpFHIRClient.setActualHostIP(getActualHostIP());
        httpFHIRClient.setActualPodIP(getActualPodIP());
        httpFHIRClient.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        httpFHIRClient.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_CLIENT);
        httpFHIRClient.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        httpFHIRClient.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        httpFHIRClient.setComponentRDN(nodeRDN);
        httpFHIRClient.setContainingNodeFDN(endpointProvider.getComponentFDN());
        ConnectedSystemProperties connectedSystem = httpClientPort.getConnectedSystem();
        ConnectedExternalSystemTopologyNode externalSystem = new ConnectedExternalSystemTopologyNode();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
        ConnectedSystemPort targetPort1 = connectedSystem.getTargetPort1();
        ExternalSystemIPCEndpoint systemEndpointPort1 = newExternalSystemIPCEndpoint(targetPort1);
        externalSystem.getTargetPorts().add(systemEndpointPort1);
        if(connectedSystem.getTargetPort2() != null)
        {
            ConnectedSystemPort targetPort2 = connectedSystem.getTargetPort2();
            ExternalSystemIPCEndpoint systemEndpointPort2 = newExternalSystemIPCEndpoint(targetPort2);
            externalSystem.getTargetPorts().add(systemEndpointPort2);
        }
        if(connectedSystem.getTargetPort3() != null)
        {
            ConnectedSystemPort targetPort3 = connectedSystem.getTargetPort3();
            ExternalSystemIPCEndpoint systemEndpointPort3 = newExternalSystemIPCEndpoint(targetPort3);
            externalSystem.getTargetPorts().add(systemEndpointPort3);
        }
        httpFHIRClient.setTargetSystem(externalSystem);
        endpointProvider.addEndpoint(httpFHIRClient.getComponentFDN());
        getLogger().trace(".newHTTPClient(): Add the httpFHIRClient Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), httpFHIRClient);
        getLogger().debug(".newHTTPClient(): Exit, endpoint added");
        return(httpFHIRClient);
    }

    protected ExternalSystemIPCEndpoint newExternalSystemIPCEndpoint(ConnectedSystemPort connectedSystemPort) {
        getLogger().debug(".newExternalSystemIPCEndpoint(): Entry, connectedSystemPort->{}", connectedSystemPort);
        ExternalSystemIPCEndpoint systemEndpointPort = new ExternalSystemIPCEndpoint();
        systemEndpointPort.setEncryptionRequired(connectedSystemPort.getEncryptionRequired());
        systemEndpointPort.setTargetPortDNSName(connectedSystemPort.getTargetPortDNSName());
        systemEndpointPort.setTargetPortValue(connectedSystemPort.getTargetPortValue());
        systemEndpointPort.setTargetPath(connectedSystemPort.getTargetPath());
        IPCInterfaceDefinition currentInterfaceDefinition = new IPCInterfaceDefinition();
        currentInterfaceDefinition.setInterfaceFormalName(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionName());
        currentInterfaceDefinition.setInterfaceFormalVersion(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionVersion());
        systemEndpointPort.setSupportedInterfaceDefinition(currentInterfaceDefinition);
        getLogger().debug(".newExternalSystemIPCEndpoint(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }

    //
    // Build an HTTP Server Endpoint (Helper Method)
    //

    protected IPCServerTopologyEndpoint newHTTPServer(EndpointProviderInterface endpointProvider, String endpointFunctionName, StandardInteractServerPortSegment httpServerPort){
        getLogger().debug(".newHTTPServer(): Entry, endpointProvider->{}, endpointFunctionName->{}, httpServerPort->{}", endpointProvider, endpointFunctionName, httpServerPort);
        if(httpServerPort == null){
            getLogger().debug(".newHTTPServer(): Exit, no port to add");
            return(null);
        }
        // Unfortunately, the two different HTTP endpoints don't inherent from the same superclass until way up in
        // the chain - so we need to explicitly separate the creation/assignment of parameters... :(
        if(httpServerPort instanceof ClusteredInteractHTTPServerPortSegment) {
            ClusteredInteractHTTPServerPortSegment endpoint = (ClusteredInteractHTTPServerPortSegment) httpServerPort;
            String serverPath = endpoint.getWebServicePath();
            HTTPServerClusterServiceTopologyEndpointPort httpServer = new HTTPServerClusterServiceTopologyEndpointPort();
            httpServer.setEncrypted(endpoint.isEncrypted());
            String name = getInterfaceNames().getEndpointServerName(endpointFunctionName);
            TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
            httpServer.setComponentRDN(nodeRDN);
            httpServer.setActualPodIP(getActualPodIP());
            httpServer.setActualHostIP(getActualHostIP());
            httpServer.setName(endpointFunctionName);
            httpServer.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
            httpServer.setPortType(httpServerPort.getPortType());
            httpServer.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
            httpServer.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
            httpServer.setPortValue(httpServerPort.getPortValue());
            httpServer.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
            httpServer.setBasePath(serverPath);
            httpServer.setaServer(true);
            httpServer.setHostDNSName(httpServerPort.getHostDNSEntry());
            httpServer.setContainingNodeFDN(endpointProvider.getComponentFDN());
            httpServer.setConnectedSystemName(endpoint.getConnectedSystem().getSubsystemName());
            endpointProvider.addEndpoint(httpServer.getComponentFDN());
            getLogger().trace(".newHTTPServer(): Add the HTTP Server Port to the Topology Cache");
            getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), httpServer);
            getLogger().debug(".newHTTPServer(): Exit, endpoint added");
            return(httpServer);
        } else {
            StandardInteractHTTPServerPortSegment endpoint = (StandardInteractHTTPServerPortSegment)httpServerPort;
            String serverPath = endpoint.getWebServicePath();
            HTTPProcessingPlantTopologyEndpointPort httpServer = new HTTPProcessingPlantTopologyEndpointPort();
            httpServer.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
            String name = getInterfaceNames().getEndpointServerName(endpointFunctionName);
            TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
            httpServer.setComponentRDN(nodeRDN);
            httpServer.setActualPodIP(getActualPodIP());
            httpServer.setActualHostIP(getActualHostIP());
            httpServer.setName(endpointFunctionName);
            httpServer.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
            httpServer.setPortType(httpServerPort.getPortType());
            httpServer.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
            httpServer.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
            httpServer.setPortValue(httpServerPort.getPortValue());
            httpServer.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
            httpServer.setBasePath(serverPath);
            httpServer.setaServer(true);
            httpServer.setHostDNSName(httpServerPort.getHostDNSEntry());
            httpServer.setContainingNodeFDN(endpointProvider.getComponentFDN());
            httpServer.setConnectedSystemName(endpoint.getConnectedSystem().getSubsystemName());
            endpointProvider.addEndpoint(httpServer.getComponentFDN());
            getLogger().trace(".newHTTPServer(): Add the HTTP Server Port to the Topology Cache");
            getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), httpServer);
            getLogger().debug(".newHTTPServer(): Exit, endpoint added");
            return(httpServer);
        }
    }
}
