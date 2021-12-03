package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common;

import net.fhirfactory.pegacorn.core.constants.systemwide.PegacornReferenceProperties;
import net.fhirfactory.pegacorn.core.model.componentid.PegacornSystemComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.HTTPClientAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.HTTPServerAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.answer.StandardEdgeAnswerHTTPEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.ask.StandardEdgeAskHTTPEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.datatypes.JGroupsAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.http.InteractHTTPClientTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.http.InteractHTTPServerTopologyEndpoint;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.PetasosEnabledSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusteredInteractHTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractHTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.*;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.base.IPCAdapterDefinition;
import net.fhirfactory.pegacorn.core.model.topology.mode.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.ExternalSystemIPCEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCServerTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.petasos.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.StandardInteractClientTopologyEndpointPort;
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
        edgeAnswerPort.setActualPodIP(getActualPodIP());
        edgeAnswerPort.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        edgeAnswerPort.setEndpointType(PetasosEndpointTopologyTypeEnum.EDGE_HTTP_API_SERVER);
        edgeAnswerPort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        edgeAnswerPort.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);

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
        edgeAskPort.setActualHostIP(getActualHostIP());
        edgeAskPort.setEndpointConfigurationName(getInterfaceNames().getEdgeAskEndpointName());
        edgeAskPort.setServer(false);
        edgeAskPort.setActualPodIP(getActualPodIP());
        edgeAskPort.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        edgeAskPort.setEndpointType(PetasosEndpointTopologyTypeEnum.EDGE_HTTP_API_CLIENT);
        edgeAskPort.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        edgeAskPort.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);

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

    protected void addAllJGroupsEndpoints(EndpointProviderInterface endpointProvider){
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        //
        // InterZoneIPC
        JGroupsInterZoneRepeaterClientPortSegment interZoneIPC = petasosEnabledSubsystemPropertyFile.getInterZoneIPC();
        String interZoneIPCName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getInterZoneJGroupsIPCEndpointName());
        addJGroupsInterZoneRepeaterClientEndpoint(endpointProvider, interZoneIPC, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, interZoneIPCName );
        //
        // InterZoneTopology
        JGroupsInterZoneRepeaterClientPortSegment interZoneTopology = petasosEnabledSubsystemPropertyFile.getInterZoneTopology();
        String interZoneTopologyName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getInterZoneJGroupsTopologyEndpointName());
        addJGroupsInterZoneRepeaterClientEndpoint(endpointProvider, interZoneTopology, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, interZoneTopologyName );
        //
        // InterZoneSubscriptions
        JGroupsInterZoneRepeaterClientPortSegment interZoneSubscriptions = petasosEnabledSubsystemPropertyFile.getInterZoneSubscriptions();
        String interZoneSubscriptionName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getInterZoneJGroupsSubscriptionsEndpointName());
        addJGroupsInterZoneRepeaterClientEndpoint(endpointProvider, interZoneSubscriptions, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, interZoneSubscriptionName );
        //
        // InterZoneIPC
        JGroupsKubernetesPodPortSegment intraZoneIPC = petasosEnabledSubsystemPropertyFile.getIntraZoneIPC();
        String intraZoneIPCName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getIntraZoneJGroupsIPCEndpointName());
        addJGroupsEndpoint(endpointProvider, intraZoneIPC, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, intraZoneIPCName );
        //
        // IntraZoneTopology
        JGroupsKubernetesPodPortSegment intraZoneTopology = petasosEnabledSubsystemPropertyFile.getIntraZoneTopology();
        String intraZoneTopologyName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getIntraZoneJGroupsTopologyEndpointName());
        addJGroupsEndpoint(endpointProvider, intraZoneTopology, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, intraZoneTopologyName );
        //
        // IntraZoneSubscriptions
        JGroupsKubernetesPodPortSegment intraZoneSubscriptions = petasosEnabledSubsystemPropertyFile.getIntraZoneSubscriptions();
        String intraZoneSubscriptionName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getIntraZoneJGroupsSubscriptionsEndpointName());
        addJGroupsEndpoint(endpointProvider, intraZoneSubscriptions, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, intraZoneSubscriptionName );
        //
        // InterZoneAudit
        JGroupsInterZoneRepeaterClientPortSegment interZoneAudit = petasosEnabledSubsystemPropertyFile.getInterZoneAudit();
        String interZoneAuditName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getInterZoneJGroupsAuditEndpointName());
        addJGroupsInterZoneRepeaterClientEndpoint(endpointProvider, interZoneAudit, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, interZoneAuditName );
        //
        // IntraZoneAudit
        JGroupsKubernetesPodPortSegment intraZoneAudit = petasosEnabledSubsystemPropertyFile.getIntraZoneAudit();
        String intraZoneAuditName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getIntraZoneJGroupsAuditEndpointName());
        addJGroupsEndpoint(endpointProvider, intraZoneAudit, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, intraZoneAuditName );
        //
        // InterZoneInterception
        JGroupsInterZoneRepeaterClientPortSegment interZoneInterception = petasosEnabledSubsystemPropertyFile.getInterZoneInterception();
        String interZoneInterceptionName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getInterZoneJGroupsInterceptionEndpointName());
        addJGroupsInterZoneRepeaterClientEndpoint(endpointProvider, interZoneInterception, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, interZoneInterceptionName );
        //
        // IntraZoneInterception
        JGroupsKubernetesPodPortSegment intraZoneInterception = petasosEnabledSubsystemPropertyFile.getIntraZoneInterception();
        String intraZoneInterceptionName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getIntraZoneJGroupsInterceptionEndpointName());
        addJGroupsEndpoint(endpointProvider, intraZoneInterception, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, intraZoneInterceptionName );
        //
        // InterZoneTasking
        JGroupsInterZoneRepeaterClientPortSegment interZoneTasking = petasosEnabledSubsystemPropertyFile.getInterZoneTasking();
        String interZoneTaskingName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getInterZoneJGroupsTaskingEndpointName());
        addJGroupsInterZoneRepeaterClientEndpoint(endpointProvider, interZoneTasking, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, interZoneTaskingName );
        //
        // IntraZoneTasking
        JGroupsKubernetesPodPortSegment intraZoneTasking = petasosEnabledSubsystemPropertyFile.getIntraZoneTasking();
        String intraZoneTaskingName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getIntraZoneJGroupsTaskingEndpointName());
        addJGroupsEndpoint(endpointProvider, intraZoneTasking, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, intraZoneTaskingName );
        //
        // InterZoneMetrics
        JGroupsInterZoneRepeaterClientPortSegment interZoneMetrics = petasosEnabledSubsystemPropertyFile.getInterZoneMetrics();
        String interZoneMetricsName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getInterZoneJGroupsMetricsEndpointName());
        addJGroupsInterZoneRepeaterClientEndpoint(endpointProvider, interZoneMetrics, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, interZoneMetricsName );
        //
        // IntraZoneMetrics
        JGroupsKubernetesPodPortSegment intraZoneMetrics = petasosEnabledSubsystemPropertyFile.getIntraZoneMetrics();
        String intraZoneMetricsName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, getInterfaceNames().getIntraZoneJGroupsMetricsEndpointName());
        addJGroupsEndpoint(endpointProvider, intraZoneMetrics, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTRAZONE_SERVICE, intraZoneMetricsName );
        //
        // Multizone Infinispan
        JGroupsInterZoneRepeaterClientPortSegment multiZoneInfinispan = petasosEnabledSubsystemPropertyFile.getMultiZoneInfinispan();
        String multiZoneInfinispanName = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, getInterfaceNames().getMultiZoneInfinispaEndpointName());
        addJGroupsInterZoneRepeaterClientEndpoint(endpointProvider, multiZoneInfinispan, PetasosEndpointTopologyTypeEnum.EDGE_JGROUPS_INTERZONE_SERVICE, multiZoneInfinispanName );
    }

    protected void addJGroupsEndpoint(EndpointProviderInterface endpointProvider, JGroupsKubernetesPodPortSegment jgroupsIPCSegment, PetasosEndpointTopologyTypeEnum petasosEndpointType, String name){
        getLogger().debug(".addJGroupsEndpoint(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        StandardEdgeIPCEndpoint interZoneIPC = new StandardEdgeIPCEndpoint();
        if(jgroupsIPCSegment == null){
            getLogger().debug(".addJGroupsEndpoint(): Exit, no jgroupsIPCSegment to add");
            return;
        }
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        interZoneIPC.setComponentRDN(nodeRDN);
        interZoneIPC.setEndpointConfigurationName(jgroupsIPCSegment.getName());
        interZoneIPC.setActualHostIP(getActualHostIP());
        interZoneIPC.setActualPodIP(getActualPodIP());
        interZoneIPC.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        interZoneIPC.setEndpointType(petasosEndpointType);
        interZoneIPC.setSecurityZone(NetworkSecurityZoneEnum.fromDisplayName(getPropertyFile().getDeploymentZone().getSecurityZoneName()));
        interZoneIPC.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        interZoneIPC.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );

        interZoneIPC.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        interZoneIPC.setServer(true);
        interZoneIPC.setContainingNodeFDN(endpointProvider.getComponentFDN());

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
        interZoneIPC.setJGroupsAdapter(adapter);
        endpointProvider.addEndpoint(interZoneIPC.getComponentFDN());
        getLogger().trace(".addJGroupsEndpoint(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), interZoneIPC);
        getLogger().debug(".addJGroupsEndpoint(): Exit, endpoint added");
    }

    protected void addJGroupsInterZoneRepeaterClientEndpoint(EndpointProviderInterface endpointProvider, JGroupsInterZoneRepeaterClientPortSegment jgroupsIPCSegment, PetasosEndpointTopologyTypeEnum petasosEndpointType, String name){
        getLogger().debug(".addJGroupsInterZoneRepeaterClientEndpoint(): Entry");
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)getPropertyFile();
        StandardEdgeIPCEndpoint interZoneIPC = new StandardEdgeIPCEndpoint();
        if(jgroupsIPCSegment == null){
            getLogger().debug(".addJGroupsInterZoneRepeaterClientEndpoint(): Exit, no jgroupsIPCSegment to add");
            return;
        }
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        interZoneIPC.setComponentRDN(nodeRDN);
        interZoneIPC.setEndpointConfigurationName(jgroupsIPCSegment.getName());
        interZoneIPC.setActualHostIP(getActualHostIP());
        interZoneIPC.setActualPodIP(getActualPodIP());
        interZoneIPC.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        interZoneIPC.setEndpointType(petasosEndpointType);
        interZoneIPC.setSecurityZone(NetworkSecurityZoneEnum.fromDisplayName(getPropertyFile().getDeploymentZone().getSecurityZoneName()));
        interZoneIPC.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        interZoneIPC.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );

        interZoneIPC.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        interZoneIPC.setServer(true);
        interZoneIPC.setContainingNodeFDN(endpointProvider.getComponentFDN());

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
        interZoneIPC.setJGroupsAdapter(adapter);
        endpointProvider.addEndpoint(interZoneIPC.getComponentFDN());
        getLogger().trace(".addJGroupsInterZoneRepeaterClientEndpoint(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), interZoneIPC);
        getLogger().debug(".addJGroupsInterZoneRepeaterClientEndpoint(): Exit, endpoint added");
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
        httpFHIRClient.setActualHostIP(getActualHostIP());
        httpFHIRClient.setActualPodIP(getActualPodIP());
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
            httpServer.setActualPodIP(getActualPodIP());
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
            httpServer.setActualPodIP(getActualPodIP());
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
