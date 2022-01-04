package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common;

import net.fhirfactory.pegacorn.core.constants.systemwide.PegacornReferenceProperties;
import net.fhirfactory.pegacorn.core.model.component.valuesets.SoftwareComponentSystemRoleEnum;
import net.fhirfactory.pegacorn.core.model.componentid.PegacornSystemComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.JGroupsIntegrationPointNamingUtilities;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointFunctionTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.participant.ProcessingPlantPetasosParticipantNameHolder;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.HTTPClientAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.HTTPServerAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.base.IPCAdapterDefinition;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCServerTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.answer.StandardEdgeAnswerHTTPEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.ask.StandardEdgeAskHTTPEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.jgroups.JGroupsIntegrationPoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.jgroups.datatypes.JGroupsAdapter;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.ExternalSystemIPCEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.StandardInteractClientTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.http.InteractHTTPClientTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.http.InteractHTTPServerTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.mode.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.core.model.topology.nodes.external.ConnectedExternalSystemTopologyNode;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.PetasosEnabledSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusteredInteractHTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractHTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsInterZoneRepeaterClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsKubernetesPodPortSegment;
import net.fhirfactory.pegacorn.util.PegacornProperties;

import javax.inject.Inject;

/**
 * The PetasosEnabledSubsystemTopologyFactory adds the Ports/IntegrationPoints/Endpoints associated with delivering
 * Petasos based services within a ClusterService and/or ProcessingPlant. Predominantly it adds the Edge service ports
 * (EdgeAnswer, a FHIR Server; EdgeAsk, a FHIR Client; JGroupsIntegrationPoints, etc)
 */
public abstract class PetasosEnabledSubsystemTopologyFactory extends PegacornTopologyFactoryBase{

    @Inject
    private PegacornReferenceProperties pegacornReferenceProperties;

    @Inject
    private JGroupsIntegrationPointNamingUtilities jgroupNamingUtilities;

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
        StandardEdgeAnswerHTTPEndpoint edgeAnswerPort = new StandardEdgeAnswerHTTPEndpoint();
        HTTPIPCServerPortSegment port = petasosEnabledSubsystemPropertyFile.getEdgeAnswer();
        if(port == null){
            getLogger().debug(".addEdgeAnswerPort(): Exit, no port to add");
            return;
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_HTTP_API_SERVER, getInterfaceNames().getEdgeAnswerEndpointName());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        edgeAnswerPort.setComponentRDN(nodeRDN);
        edgeAnswerPort.setActualHostIP(getActualHostIP());
        edgeAnswerPort.setEndpointConfigurationName(getInterfaceNames().getEdgeAnswerEndpointName());
        edgeAnswerPort.setServer(true);
        edgeAnswerPort.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        edgeAnswerPort.setEndpointType(PetasosEndpointTopologyTypeEnum.EDGE_HTTP_API_SERVER);
        edgeAnswerPort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        edgeAnswerPort.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        edgeAnswerPort.setComponentSystemRole(SoftwareComponentSystemRoleEnum.COMPONENT_ROLE_SUBSYSTEM_EDGE);
        HTTPServerAdapter httpServer = new HTTPServerAdapter();
        httpServer.setPortNumber(port.getPortValue());
        httpServer.setContextPath(pegacornReferenceProperties.getPegacornInternalFhirResourceR4Path());
        httpServer.setHostName(getActualPodIP());
        httpServer.setEncrypted(port.isEncrypted());
        edgeAnswerPort.setHTTPServerAdapter(httpServer);

        edgeAnswerPort.setContainingNodeFDN(endpointProvider.getComponentFDN());
        endpointProvider.addEndpoint(edgeAnswerPort.getComponentFDN());
        getLogger().trace(".addEdgeAnswerPort(): Add the EdgeAnswer Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), edgeAnswerPort);
        getLogger().debug(".addEdgeAnswerPort(): Exit, endpoint added");
    }

    protected void addEdgeAskPort( EndpointProviderInterface endpointProvider){
        getLogger().debug(".addEdgeAskPort(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        StandardEdgeAskHTTPEndpoint edgeAskPort = new StandardEdgeAskHTTPEndpoint();
        HTTPIPCClientPortSegment port = petasosEnabledSubsystemPropertyFile.getEdgeAsk();
        if(port == null){
            getLogger().debug(".addEdgeAskPort(): Exit, no port to add");
            return;
        }
        String name = PetasosEndpointTopologyTypeEnum.EDGE_HTTP_API_CLIENT.getDisplayName();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        edgeAskPort.setComponentRDN(nodeRDN);
        edgeAskPort.setEndpointConfigurationName(getInterfaceNames().getEdgeAskEndpointName());
        edgeAskPort.setServer(false);
        edgeAskPort.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        edgeAskPort.setEndpointType(PetasosEndpointTopologyTypeEnum.EDGE_HTTP_API_CLIENT);
        edgeAskPort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        edgeAskPort.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        edgeAskPort.setComponentSystemRole(SoftwareComponentSystemRoleEnum.COMPONENT_ROLE_SUBSYSTEM_EDGE);
        HTTPClientAdapter httpClient = new HTTPClientAdapter();
        httpClient.setPortNumber(port.getConnectedSystem().getTargetPort1().getTargetPortValue());
        httpClient.setContextPath(pegacornReferenceProperties.getPegacornInternalFhirResourceR4Path());
        httpClient.setHostName(port.getConnectedSystem().getTargetPort1().getTargetPortDNSName());
        httpClient.setEncrypted(port.isEncrypted());
        edgeAskPort.getHTTPClientAdapters().add(httpClient);

        if(port.getConnectedSystem().getTargetPort2() != null){
            HTTPClientAdapter httpClient2 = new HTTPClientAdapter();
            httpClient2.setPortNumber(port.getConnectedSystem().getTargetPort2().getTargetPortValue());
            httpClient2.setContextPath(pegacornReferenceProperties.getPegacornInternalFhirResourceR4Path());
            httpClient2.setHostName(port.getConnectedSystem().getTargetPort2().getTargetPortDNSName());
            httpClient2.setEncrypted(port.isEncrypted());
            edgeAskPort.getHTTPClientAdapters().add(httpClient2);
        }

        if(port.getConnectedSystem().getTargetPort3() != null){
            HTTPClientAdapter httpClient3 = new HTTPClientAdapter();
            httpClient3.setPortNumber(port.getConnectedSystem().getTargetPort3().getTargetPortValue());
            httpClient3.setContextPath(pegacornReferenceProperties.getPegacornInternalFhirResourceR4Path());
            httpClient3.setHostName(port.getConnectedSystem().getTargetPort3().getTargetPortDNSName());
            httpClient3.setEncrypted(port.isEncrypted());
            edgeAskPort.getHTTPClientAdapters().add(httpClient3);
        }

        edgeAskPort.setContainingNodeFDN(endpointProvider.getComponentFDN());
        endpointProvider.addEndpoint(edgeAskPort.getComponentFDN());
        getLogger().trace(".addEdgeAskPort(): Add the EdgeAnswer Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), edgeAskPort);
        getLogger().debug(".addEdgeAskPort(): Exit, endpoint added");
    }

    /**
     * This method adds ALL the JGroupsIntegrationPoints to the supplied endpointProvider. Note that, in most cases,
     * the JGroupsIntegrationPoints DO NOT expose a kubernetes service, but rather interact with an interzone repeater
     * as a client or via kube-ping based services at the POD level.
     *
     * @param endpointProvider The endpointProvider to which the JGroupsIntegrationPoints should be added (from a Topology
     *                         management perspective). Typically a ProcessingPlant.
     */
    protected void addAllJGroupsEndpoints(EndpointProviderInterface endpointProvider){
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        //
        // Petasos IPC Messaging Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneIPC = petasosEnabledSubsystemPropertyFile.getPetasosIPCMessagingEndpoint();
        String interZoneIPCName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_MESSAGING_ENDPOINT.getDisplayName());
        addInterZoneRepeaterJGroupsIntegrationPoint(endpointProvider, interZoneIPC, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_MESSAGING_ENDPOINT, interZoneIPCName );
        //
        // Petasos Topology Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneTopology = petasosEnabledSubsystemPropertyFile.getPetasosTopologyDiscoveryEndpoint();
        String interZoneTopologyName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_TOPOLOGY_ENDPOINT.getDisplayName());
        addInterZoneRepeaterJGroupsIntegrationPoint(endpointProvider, interZoneTopology, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_TOPOLOGY_ENDPOINT, interZoneTopologyName );
        //
        // Petasos Subscription Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneSubscriptions = petasosEnabledSubsystemPropertyFile.getPetasosSubscriptionsEndpoint();
        String interZoneSubscriptionName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_SUBSCRIPTIONS_ENDPOINT.getDisplayName());
        addInterZoneRepeaterJGroupsIntegrationPoint(endpointProvider, interZoneSubscriptions, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_SUBSCRIPTIONS_ENDPOINT, interZoneSubscriptionName );
        //
        // Petasos Audit Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneAudit = petasosEnabledSubsystemPropertyFile.getPetasosAuditServicesEndpoint();
        String interZoneAuditName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_AUDIT_ENDPOINT.getDisplayName());
        addInterZoneRepeaterJGroupsIntegrationPoint(endpointProvider, interZoneAudit, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_AUDIT_ENDPOINT, interZoneAuditName );
        //
        // Petasos Interception Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneInterception = petasosEnabledSubsystemPropertyFile.getPetasosInterceptionEndpoint();
        String interZoneInterceptionName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_INTERCEPTION_ENDPOINT.getDisplayName());
        addInterZoneRepeaterJGroupsIntegrationPoint(endpointProvider, interZoneInterception, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_INTERCEPTION_ENDPOINT, interZoneInterceptionName );
        //
        // Petasos Tasking Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneTasking = petasosEnabledSubsystemPropertyFile.getPetasosTaskServicesEndpoint();
        String interZoneTaskingName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_TASKING_ENDPOINT.getDisplayName());
        addInterZoneRepeaterJGroupsIntegrationPoint(endpointProvider, interZoneTasking, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_TASKING_ENDPOINT, interZoneTaskingName );
        //
        // Petasos Metrics Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneMetrics = petasosEnabledSubsystemPropertyFile.getPetasosMetricsEndpoint();
        String interZoneMetricsName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_METRICS_ENDPOINT.getDisplayName());
        addInterZoneRepeaterJGroupsIntegrationPoint(endpointProvider, interZoneMetrics, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_METRICS_ENDPOINT, interZoneMetricsName );
        //
        // Multizone Infinispan
        JGroupsInterZoneRepeaterClientPortSegment multiZoneInfinispan = petasosEnabledSubsystemPropertyFile.getMultiuseInfinispanEndpoint();
        String multiZoneInfinispanName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_INFINISPAN_ENDPOINT.getDisplayName());
        addInterZoneRepeaterJGroupsIntegrationPoint(endpointProvider, multiZoneInfinispan, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTEGRATION_POINT, PetasosEndpointFunctionTypeEnum.PETASOS_INFINISPAN_ENDPOINT, multiZoneInfinispanName );
    }

    protected void addJGroupsEndpoint(EndpointProviderInterface endpointProvider, JGroupsKubernetesPodPortSegment jgroupsIPCSegment, PetasosEndpointTopologyTypeEnum petasosEndpointType, PetasosEndpointFunctionTypeEnum function, String name){
        getLogger().info(".addJGroupsEndpoint(): Entry, endpointProvider->{}, jgroupsIPCSegment->{}, petasosEndpointType->{}, function->{}, name->{}",endpointProvider,jgroupsIPCSegment,petasosEndpointType,function,name );
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        JGroupsIntegrationPoint jgroupsIP = new JGroupsIntegrationPoint();
        if(jgroupsIPCSegment == null){
            getLogger().debug(".addJGroupsEndpoint(): Exit, no jgroupsIPCSegment to add");
            return;
        }
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        jgroupsIP.setComponentRDN(nodeRDN);
        jgroupsIP.setEndpointConfigurationName(jgroupsIPCSegment.getName());
        jgroupsIP.setActualHostIP(getActualHostIP());
        jgroupsIP.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        jgroupsIP.setEndpointType(petasosEndpointType);
        NetworkSecurityZoneEnum securityZone = NetworkSecurityZoneEnum.fromDisplayName(getPropertyFile().getDeploymentZone().getSecurityZoneName());
        jgroupsIP.setSecurityZone(securityZone);
        jgroupsIP.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        jgroupsIP.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        jgroupsIP.setComponentSystemRole(SoftwareComponentSystemRoleEnum.COMPONENT_ROLE_SUBSYSTEM_EDGE);
        jgroupsIP.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        jgroupsIP.setServer(true);
        jgroupsIP.setResilienceMode(getResilienceMode());
        jgroupsIP.setConcurrencyMode(getConcurrenceMode());
        jgroupsIP.setContainingNodeFDN(endpointProvider.getComponentFDN());
        String subsystemParticipantName = getPropertyFile().getSubsystemInstant().getParticipantName();
        jgroupsIP.setSubsystemParticipantName(subsystemParticipantName);
        String site = getPropertyFile().getSubsystemInstant().getSite();
        jgroupsIP.setDeploymentSite(site);
        jgroupsIP.setInterfaceFunction(function);
        String channelName = jgroupNamingUtilities.buildChannelName(site, securityZone.getDisplayName(), subsystemParticipantName, function.getDisplayName(), jgroupNamingUtilities.getCurrentUUID());
        jgroupsIP.setChannelName(channelName);
        jgroupsIP.setEnablingProcessingPlantId(getProcessingPlantNode().getComponentID());
        jgroupsIP.setEndpointServiceName(getPropertyFile().getSubsystemInstant().getClusterServiceName());
        jgroupsIP.setEndpointDescription(jgroupsIPCSegment.getPortType());

        JGroupsAdapter adapter = new JGroupsAdapter();
        adapter.setPortNumber(jgroupsIPCSegment.getPortValue());
        adapter.setHostName(jgroupsIPCSegment.getHostDNSEntry());
        adapter.setEncrypted(jgroupsIPCSegment.isEncrypted());
        for(InterfaceDefinitionSegment currentSegment: jgroupsIPCSegment.getSupportedInterfaceProfiles()) {
            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            adapter.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        }
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        jgroupsIP.setJGroupsAdapter(adapter);
        endpointProvider.addEndpoint(jgroupsIP.getComponentFDN());
        getLogger().trace(".addJGroupsEndpoint(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), jgroupsIP);
        getLogger().debug(".addJGroupsEndpoint(): Exit, endpoint added");
    }

    /**
     * This method creates a JGroupsIntegrationPoint (JChannel) for a given integration function (e.g. tasking,
     * subscriptions, messaging, interception, etc.) - populating it with a combination of ProcessingPlant sourced
     * information and PropertyFile() based information as passed in the JGroupsInterZoneRepeaterClientPortSegment
     * data element.
     *
     * @param endpointProvider The entity to which this integrationPoint should be "added"
     * @param jgroupsIPCSegment The PropertyFile segment describing the key attributes of this integrationPoint
     * @param petasosEndpointType What sort of PetasosEndpointType should be assigned to the integrationPoint
     * @param function What is it's integration "function" (i.e. tasking, subscriptions, messaging, etc.)
     * @param name What name should be given to the create integrationPoint (SoftwareComponent)
     */
    protected void addInterZoneRepeaterJGroupsIntegrationPoint(EndpointProviderInterface endpointProvider, JGroupsInterZoneRepeaterClientPortSegment jgroupsIPCSegment, PetasosEndpointTopologyTypeEnum petasosEndpointType, PetasosEndpointFunctionTypeEnum function, String name){
        getLogger().debug(".addJGroupsIntegrationPoint(): Entry, endpointProvider->{}, jgroupsIPCSegment->{}, petasosEndpointType->{}, function->{}, name->{}",endpointProvider,jgroupsIPCSegment,petasosEndpointType,function,name );
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        JGroupsIntegrationPoint jgroupsIP = new JGroupsIntegrationPoint();
        if(jgroupsIPCSegment == null){
            getLogger().debug(".addJGroupsIntegrationPoint(): Exit, no jgroupsIPCSegment to add");
            return;
        }
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        jgroupsIP.setComponentRDN(nodeRDN);
        jgroupsIP.setEndpointConfigurationName(jgroupsIPCSegment.getName());
        jgroupsIP.setActualHostIP(getActualHostIP());
        jgroupsIP.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        jgroupsIP.setEndpointType(petasosEndpointType);
        NetworkSecurityZoneEnum securityZone = NetworkSecurityZoneEnum.fromDisplayName(getPropertyFile().getDeploymentZone().getSecurityZoneName());
        jgroupsIP.setSecurityZone(securityZone);
        jgroupsIP.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        jgroupsIP.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        jgroupsIP.setComponentSystemRole(SoftwareComponentSystemRoleEnum.COMPONENT_ROLE_SUBSYSTEM_EDGE);
        jgroupsIP.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        jgroupsIP.setServer(true);
        jgroupsIP.setResilienceMode(getResilienceMode());
        jgroupsIP.setConcurrencyMode(getConcurrenceMode());
        jgroupsIP.setContainingNodeFDN(endpointProvider.getComponentFDN());
        String subsystemParticipantName = getPropertyFile().getSubsystemInstant().getParticipantName();
        jgroupsIP.setSubsystemParticipantName(subsystemParticipantName);
        String site = getPropertyFile().getSubsystemInstant().getSite();
        jgroupsIP.setDeploymentSite(site);
        jgroupsIP.setInterfaceFunction(function);
        String channelName = jgroupNamingUtilities.buildChannelName(site, securityZone.getDisplayName(), subsystemParticipantName, function.getDisplayName(), jgroupNamingUtilities.getCurrentUUID());
        jgroupsIP.setChannelName(channelName);
        getLogger().info(".addJGroupsIntegrationPoint(): channelName->{}", jgroupsIP.getChannelName());
        jgroupsIP.setEnablingProcessingPlantId(getProcessingPlantNode().getComponentID());
        jgroupsIP.setEndpointServiceName(getPropertyFile().getSubsystemInstant().getClusterServiceName());
        jgroupsIP.setEndpointDescription(jgroupsIPCSegment.getPortType());

        JGroupsAdapter adapter = new JGroupsAdapter();
        adapter.setPortNumber(jgroupsIPCSegment.getTargetPortValue());
        adapter.setHostName(jgroupsIPCSegment.getTargetHostName());
        adapter.setEncrypted(jgroupsIPCSegment.isEncrypted());
        for(InterfaceDefinitionSegment currentSegment: jgroupsIPCSegment.getSupportedInterfaceProfiles()) {
            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            adapter.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        }
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        jgroupsIP.setJGroupsAdapter(adapter);
        endpointProvider.addEndpoint(jgroupsIP.getComponentFDN());
        getLogger().trace(".addJGroupsIntegrationPoint(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), jgroupsIP);
        getLogger().info(".addJGroupsIntegrationPoint(): Exit, integrationPoint added, jgroupsIP->{}", jgroupsIP);
    }

    //
    // Build an HTTP Client Endpoint (Helper Method)
    //

    protected StandardInteractClientTopologyEndpointPort newHTTPClient(EndpointProviderInterface endpointProvider, String endpointFunctionName, StandardInteractClientPortSegment httpClientPort){
        getLogger().debug(".newHTTPClient(): Entry, endpointProvider->{}, httpClientPort->{}", endpointProvider, httpClientPort);
        InteractHTTPClientTopologyEndpoint httpFHIRClient = new InteractHTTPClientTopologyEndpoint();
        if(httpClientPort == null){
            getLogger().debug(".newHTTPClient(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointServerName(endpointFunctionName);
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        httpFHIRClient.setComponentRDN(nodeRDN);
        httpFHIRClient.setEndpointConfigurationName(httpClientPort.getName());
        httpFHIRClient.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        httpFHIRClient.setEndpointType(PetasosEndpointTopologyTypeEnum.INTERACT_HTTP_API_CLIENT);
        httpFHIRClient.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
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
        IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
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
        InteractHTTPServerTopologyEndpoint httpServer = new InteractHTTPServerTopologyEndpoint();

        // Unfortunately, the two different HTTP endpoints don't inherent from the same superclass until way up in
        // the chain - so we need to explicitly separate the creation/assignment of parameters... :(
        if(httpServerPort instanceof ClusteredInteractHTTPServerPortSegment) {
            ClusteredInteractHTTPServerPortSegment endpoint = (ClusteredInteractHTTPServerPortSegment) httpServerPort;
            String serverPath = endpoint.getWebServicePath();
            String name = getInterfaceNames().getEndpointServerName(endpointFunctionName);
            TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
            httpServer.setComponentRDN(nodeRDN);
            httpServer.setEndpointConfigurationName(httpServerPort.getName());
            httpServer.setActualHostIP(getActualHostIP());
            httpServer.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
            httpServer.setEndpointType(PetasosEndpointTopologyTypeEnum.INTERACT_HTTP_API_SERVER);
            httpServer.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
            httpServer.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
            httpServer.setServer(true);
            httpServer.setContainingNodeFDN(endpointProvider.getComponentFDN());
            httpServer.setConnectedSystemName(endpoint.getConnectedSystem().getSubsystemName());

            HTTPServerAdapter httpServerAdapter = new HTTPServerAdapter();
            httpServerAdapter.setPortNumber(httpServerPort.getPortValue());
            httpServerAdapter.setContextPath(serverPath);
            httpServerAdapter.setHostName(httpServerPort.getHostDNSEntry());
            if(endpoint.getServiceDNSEntry() != null) {
                httpServerAdapter.setServiceDNSName(endpoint.getServiceDNSEntry());
            }
            if(endpoint.getServicePortValue() != null){
                httpServerAdapter.setServicePortValue(endpoint.getServicePortValue());
            }
            httpServer.setHTTPServerAdapter(httpServerAdapter);

            endpointProvider.addEndpoint(httpServer.getComponentFDN());
            getLogger().trace(".newHTTPServer(): Add the HTTP Server Port to the Topology Cache");
            getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), httpServer);
            getLogger().debug(".newHTTPServer(): Exit, endpoint added");
            return(httpServer);
        } else {
            StandardInteractHTTPServerPortSegment endpoint = (StandardInteractHTTPServerPortSegment)httpServerPort;
            String serverPath = endpoint.getWebServicePath();
            String name = getInterfaceNames().getEndpointServerName(endpointFunctionName);
            TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
            httpServer.setComponentRDN(nodeRDN);
            httpServer.setEndpointConfigurationName(httpServerPort.getName());
            httpServer.setActualHostIP(getActualHostIP());
            httpServer.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
            httpServer.setEndpointType(PetasosEndpointTopologyTypeEnum.INTERACT_HTTP_API_SERVER);
            httpServer.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
            httpServer.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
            httpServer.setServer(true);
            httpServer.setContainingNodeFDN(endpointProvider.getComponentFDN());
            httpServer.setConnectedSystemName(endpoint.getConnectedSystem().getSubsystemName());

            HTTPServerAdapter httpServerAdapter = new HTTPServerAdapter();
            httpServerAdapter.setPortNumber(httpServerPort.getPortValue());
            httpServerAdapter.setContextPath(serverPath);
            httpServerAdapter.setHostName(httpServerPort.getHostDNSEntry());
            httpServer.setHTTPServerAdapter(httpServerAdapter);

            endpointProvider.addEndpoint(httpServer.getComponentFDN());
            getLogger().trace(".newHTTPServer(): Add the HTTP Server Port to the Topology Cache");
            getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), httpServer);
            getLogger().debug(".newHTTPServer(): Exit, endpoint added");
            return(httpServer);
        }
    }
}
