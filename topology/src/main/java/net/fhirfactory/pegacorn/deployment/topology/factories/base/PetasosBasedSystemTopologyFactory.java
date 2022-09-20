package net.fhirfactory.pegacorn.deployment.topology.factories.base;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import net.fhirfactory.dricats.model.component.datatypes.ComponentTypeDefinition;
import net.fhirfactory.dricats.model.component.valuesets.ComponentTypeEnum;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.PetasosClusterPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.valuesets.ClusterFunctionNameEnum;
import net.fhirfactory.dricats.model.configuration.api.interfaces.PetasosConfigurationFileService;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.http.HTTPClientEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.jgroups.JGroupsInterZoneRepeaterClientPortSegment;
import net.fhirfactory.dricats.model.topology.endpoints.edge.ask.EdgeAskETN;
import net.fhirfactory.dricats.model.topology.endpoints.jgroups.JGroupsClusterConnectorETN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.WorkUnitProcessorSubComponentTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.processingplants.base.ProcessingPlantTopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workshops.PetasosIPCWorkshopTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workshops.PetasosObservationsWorkshopTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workshops.PetasosTaskingWorkshopTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.PetasosOversightTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.base.WorkUnitProcessorTopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.ipc.InternalMessagingEgressProxyTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.ipc.InternalMessagingIngressProxyTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.ipc.PetasosEdgeAskTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.observations.PetasosAbnormalEventsAgentTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.observations.PetasosConfigurationReportsAgentTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.observations.PetasosMetricsAgentTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.observations.PetasosNotificationsAgentTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.tasking.PetasosActionableTaskActivityManagerTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.tasking.PetasosActionableTaskOversightManagerTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.tasking.PetasosAuditEventServicesManagerTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.tasking.PetasosFulfillmentTaskActivityManagerTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.tasking.PetasosFulfillmentTaskOutcomeProcessorTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.tasking.PetasosTaskContinuityProcessorTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.tasking.PetasosTaskRoutingProcessorTN;
import net.fhirfactory.pegacorn.deployment.topology.factories.base.endpoints.HTTPTopologyEndpointFactory;
import net.fhirfactory.pegacorn.deployment.topology.factories.base.endpoints.JGroupsTopologyEndpointFactory;
import net.fhirfactory.pegacorn.deployment.topology.factories.interfaces.PetasosEnabledSubsystemFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The PetasosEnabledSubsystemTopologyFactory adds the
 * Ports/IntegrationPoints/Endpoints associated with delivering Petasos based
 * services within a ClusterService and/or ProcessingPlant. Predominantly it
 * adds the Edge service ports (EdgeAnswer, a FHIR Server; EdgeAsk, a FHIR
 * Client; JGroupsIntegrationPoints, etc)
 */
public abstract class PetasosBasedSystemTopologyFactory extends WildflyBasedSubsystemTopologyFactory implements PetasosConfigurationFileService, PetasosEnabledSubsystemFactoryService {
    private static final Logger LOG = LoggerFactory.getLogger(PetasosBasedSystemTopologyFactory.class);
    
    private PetasosClusterPropertyFile petasosClusterPropertyFile;

    @Inject
    private HTTPTopologyEndpointFactory httpTopologyEndpointFactory;

    @Inject
    private JGroupsTopologyEndpointFactory jgroupsTopologyEndpointFactory;
    
    //
    // Constructor(s)
    //

    public PetasosBasedSystemTopologyFactory() {
        super();
        loadPetasosPropertyFile();
    }

    //
    // Getters (and Setters)
    //


    @Override
    public PetasosClusterPropertyFile getPropertyFile() {
        return (petasosClusterPropertyFile);
    }

    protected HTTPTopologyEndpointFactory getHTTPTopologyEndpointFactory() {
            return (httpTopologyEndpointFactory);
    }

    protected JGroupsTopologyEndpointFactory getJGroupsTopologyEndpointFactory() {
            return (jgroupsTopologyEndpointFactory);
    }
    
    protected Logger getLogger(){
        return(LOG);
    }

    public PetasosClusterPropertyFile getPetasosPropertyFile() {
        return petasosClusterPropertyFile;
    }

    public void setPetasosPropertyFile(PetasosClusterPropertyFile petasosClusterPropertyFile) {
        this.petasosClusterPropertyFile = petasosClusterPropertyFile;
    }

    @Override
    public PetasosBasedSystemTopologyFactory getPetasosSubsystemFactory() {
        return (this);
    }

    //
    // Overall Action Function
    //
    
    public void buildAllPetasosTopology(ProcessingPlantTopologyNode processingPlant){
        PetasosTaskingWorkshopTN taskingWorkshop = createPetasosTaskingWorkshopAndWUPNodes(processingPlant);
        processingPlant.getWorkshopMap().put(taskingWorkshop.getParticipantName(), taskingWorkshop);
        PetasosObservationsWorkshopTN observationsWorkshop = createPetasosObservationsWorkshopAndWUPNodes(processingPlant);
        processingPlant.getWorkshopMap().put(observationsWorkshop.getParticipantName(), observationsWorkshop);
        PetasosIPCWorkshopTN ipcWorkshop = createPetasosIPCWorkshopAndWUPNodes(processingPlant);
        processingPlant.getWorkshopMap().put(ipcWorkshop.getParticipantName(), ipcWorkshop);
        createAllJGroupsEndpoints(processingPlant, ipcWorkshop);
        createEdgeAskPort(ipcWorkshop.getEdgeAsk());
    }


    /**
     * This method creates the default edgeAskPort (a HTTP Client) which is
     * typically bound to the HAPIClient for access/manipulation of FHIR Resources
     * from a RESTful (HTTP) Server (e.g. a HAPI JPA Server, for instance).
     *
     * @param wupNode
     */
    protected void createEdgeAskPort(PetasosEdgeAskTN wupNode) {
        getLogger().debug(".createEdgeAskPort(): Entry");
        HTTPClientEndpointSegment httpClientPortConfigSegment = getPetasosPropertyFile().getEdgeAsk();
        if (httpClientPortConfigSegment == null) {
                getLogger().debug(".addEdgeAskPort(): Exit, no httpClientPortConfigSegment to add");
                return;
        }
        EdgeAskETN edgeAskEndpoing = httpTopologyEndpointFactory.newEdgeAskEndpoint(getPetasosPropertyFile(), wupNode, httpClientPortConfigSegment);
        wupNode.setEdgeAskEndpoint(edgeAskEndpoing);
        getLogger().debug(".createEdgeAskPort(): Exit, endpoint added");
    }

    /**
     * This method adds ALL the JGroupsIntegrationPoints to the supplied
     * endpointProvider. Note that, in most cases, the JGroupsIntegrationPoints DO
     * NOT expose a kubernetes service, but rather interact with an interzone
     * repeater as a client or via kube-ping based services at the POD level.
     * 
     * @param processingPlant 
     * @param wupNode The endpointProvider to which the
     *                JGroupsIntegrationPoints should be added (from a
     *                Topology management perspective). Typically a
     *                ProcessingPlant.
     */
    protected void createAllJGroupsEndpoints(ProcessingPlantTopologyNode processingPlant, PetasosIPCWorkshopTN wupNode) {
        //
        // Petasos IPC Messaging Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneIPC = getPetasosPropertyFile().getPetasosMessagingEndpoint();
        JGroupsClusterConnectorETN messagingServiceClientConnector = getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode.getMessagingServicesConnection(), 
                interZoneIPC,
                ClusterFunctionNameEnum.PETASOS_MESSAGING_SERVICES.getParticipantName(),
                ClusterFunctionNameEnum.PETASOS_MESSAGING_SERVICES.getGroupName());
        wupNode.getMessagingServicesConnection().setClusterConnector(messagingServiceClientConnector);
        //
        // Petasos Topology Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneTopology = getPetasosPropertyFile().getPetasosTopologyServicesEndpoint();
        JGroupsClusterConnectorETN topologyServicesClientConnector = getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode.getTopologyServicesConnection(), 
                interZoneTopology,
                ClusterFunctionNameEnum.PETASOS_TOPOLOGY_SERVICES.getParticipantName(),
                ClusterFunctionNameEnum.PETASOS_TOPOLOGY_SERVICES.getGroupName());
        wupNode.getTopologyServicesConnection().setClusterConnector(topologyServicesClientConnector);
        //
        // Petasos Subscription Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneSubscriptions = getPetasosPropertyFile().getPetasosRoutingServicesEndpoint();
        JGroupsClusterConnectorETN routingServicesClientConnector = getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode.getRoutingServicesConnection(), 
                interZoneSubscriptions,
                ClusterFunctionNameEnum.PETASOS_ROUTING_SERVICES.getParticipantName(),
                ClusterFunctionNameEnum.PETASOS_ROUTING_SERVICES.getGroupName());
        wupNode.getRoutingServicesConnection().setClusterConnector(routingServicesClientConnector);
        //
        // Petasos Audit Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneAudit = getPetasosPropertyFile().getPetasosAuditServicesEndpoint();
        JGroupsClusterConnectorETN auditServicesClientConnector = getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode.getAuditServicesConnection(), 
                interZoneAudit,
                ClusterFunctionNameEnum.PETASOS_AUDIT_SERVICES.getParticipantName(),
                ClusterFunctionNameEnum.PETASOS_AUDIT_SERVICES.getGroupName());
        wupNode.getAuditServicesConnection().setClusterConnector(auditServicesClientConnector);
        //
        // Petasos Interception Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneInterception = getPetasosPropertyFile().getPetasosInterceptionEndpoint();
        JGroupsClusterConnectorETN interceptionServicesClientConnector = getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode.getInterceptionServicesConnection(), 
                interZoneInterception,
                ClusterFunctionNameEnum.PETASOS_INTERCEPTION_SERVICES.getParticipantName(),
                ClusterFunctionNameEnum.PETASOS_INTERCEPTION_SERVICES.getGroupName());
        wupNode.getInterceptionServicesConnection().setClusterConnector(interceptionServicesClientConnector);
        //
        // Petasos Tasking Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneTasking = getPetasosPropertyFile().getPetasosTaskingServicesEndpoint();
        JGroupsClusterConnectorETN taskingServicesClientConnector = getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode.getTaskingServicesConnection(), 
                interZoneTasking,
                ClusterFunctionNameEnum.PETASOS_TASKING_SERVICES.getParticipantName(),
                ClusterFunctionNameEnum.PETASOS_TASKING_SERVICES.getGroupName());
        wupNode.getTaskingServicesConnection().setClusterConnector(taskingServicesClientConnector);
        //
        // Petasos Observations Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment interZoneMetrics = getPetasosPropertyFile().getPetasosObservationsEndpoint();
        JGroupsClusterConnectorETN observationServicesClientConnector = getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode.getObservationsServicesConnection(), 
                interZoneMetrics,
                ClusterFunctionNameEnum.PETASOS_OBSERVATION_SERVICES.getParticipantName(),
                ClusterFunctionNameEnum.PETASOS_OBSERVATION_SERVICES.getGroupName());
        wupNode.getObservationsServicesConnection().setClusterConnector(observationServicesClientConnector);
        //
        // Petasos Media Services Endpoint
        JGroupsInterZoneRepeaterClientPortSegment mediaServicesConfig = getPetasosPropertyFile().getPetasosMediaServicesEndpoint();
        JGroupsClusterConnectorETN mediaServicesClientConnector = getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode.getMediaServicesConnection(), 
                interZoneMetrics,
                ClusterFunctionNameEnum.PETASOS_MEDIA_SERVICES.getParticipantName(),
                ClusterFunctionNameEnum.PETASOS_MEDIA_SERVICES.getGroupName());
        wupNode.getMediaServicesConnection().setClusterConnector(mediaServicesClientConnector);
        //
        // Multizone Infinispan
        /*
        JGroupsInterZoneRepeaterClientPortSegment multiZoneInfinispan = getPetasosPropertyFile().getMultiuseInfinispanEndpoint();
        getJGroupsTopologyEndpointFactory().createJGroupsClientEndpoint(
                processingPlant,
                getSubsystemPropertyFile(),
                wupNode, 
                multiZoneInfinispan,
                JGroupsChannelFunctionNameEnum.PETASOS_MULTI_INFINISPAN.getParticipantName(),
                JGroupsChannelFunctionNameEnum.PETASOS_MULTI_INFINISPAN.getGroupName());
        */
    }
        
    public PetasosTaskingWorkshopTN createPetasosTaskingWorkshopAndWUPNodes(ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".createLocalPetasosServices(): Entry, processingPlant->{}", processingPlant);
        
        //
        // PetasosTaskingWorkshop Details
        PetasosTaskingWorkshopTN petasosTaskingWorkshop = new PetasosTaskingWorkshopTN(processingPlant.getParticipantName(), processingPlant.getNodeVersion());
        petasosTaskingWorkshop.loadConfigurationFromParent(processingPlant);
        // PetasosRoutingService
        PetasosTaskRoutingProcessorTN petasosRouter = new PetasosTaskRoutingProcessorTN(petasosTaskingWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        petasosRouter.loadConfigurationFromParent(petasosTaskingWorkshop);
        petasosTaskingWorkshop.setRoutingService(petasosRouter);
        // PetasosTaskContinuity
        PetasosTaskContinuityProcessorTN petasosTaskContinuityProcessor = new PetasosTaskContinuityProcessorTN(petasosTaskingWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        petasosTaskContinuityProcessor.loadConfigurationFromParent(petasosTaskingWorkshop);
        petasosTaskingWorkshop.setContinuityService(petasosTaskContinuityProcessor);
        // PetasosFulfillmentService
        PetasosFulfillmentTaskActivityManagerTN fulfillmentNode = new PetasosFulfillmentTaskActivityManagerTN(petasosTaskingWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        fulfillmentNode.loadConfigurationFromParent(petasosTaskingWorkshop);
        petasosTaskingWorkshop.setFulfillmentManager(fulfillmentNode);
        // PetasosFullfilmentOutcomeProcessor
        PetasosFulfillmentTaskOutcomeProcessorTN outcomeProcessorNode = new PetasosFulfillmentTaskOutcomeProcessorTN(petasosTaskingWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        outcomeProcessorNode.loadConfigurationFromParent(petasosTaskingWorkshop);
        petasosTaskingWorkshop.setOutcomeProcessor(outcomeProcessorNode);
        // PetasosAuditService
        PetasosAuditEventServicesManagerTN auditNode = new PetasosAuditEventServicesManagerTN(petasosTaskingWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        auditNode.loadConfigurationFromParent(petasosTaskingWorkshop);
        petasosTaskingWorkshop.setAuditService(auditNode);
        // PetasosOversightService
        PetasosOversightTN oversightNode = new PetasosOversightTN(petasosTaskingWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        oversightNode.loadConfigurationFromParent(petasosTaskingWorkshop);
        petasosTaskingWorkshop.setOversightService(oversightNode);
        // PetasosActionableTaskActivityManager
        PetasosActionableTaskActivityManagerTN activityManager = new PetasosActionableTaskActivityManagerTN(petasosTaskingWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        activityManager.loadConfigurationFromParent(petasosTaskingWorkshop);
        petasosTaskingWorkshop.setActionableTaskActivityManager(activityManager);
        // PetasosActionableTaskOversightManager
        PetasosActionableTaskOversightManagerTN oversightManager = new PetasosActionableTaskOversightManagerTN(petasosTaskingWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        oversightManager.loadConfigurationFromParent(petasosTaskingWorkshop);
        petasosTaskingWorkshop.setActionableTaskOversightManager(oversightManager);
        // Done
        return(petasosTaskingWorkshop);
    }
     
    public PetasosObservationsWorkshopTN createPetasosObservationsWorkshopAndWUPNodes(ProcessingPlantTopologyNode processingPlant){
        //
        // PetasosObservationsWorkshop
        PetasosObservationsWorkshopTN petasosObservationsWorkshop = new PetasosObservationsWorkshopTN(processingPlant.getParticipantName(), processingPlant.getNodeVersion());
        petasosObservationsWorkshop.loadConfigurationFromParent(processingPlant);
        // PetasosNotificationsService
        PetasosNotificationsAgentTN notificationsNode = new PetasosNotificationsAgentTN(petasosObservationsWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        notificationsNode.loadConfigurationFromParent(petasosObservationsWorkshop);
        petasosObservationsWorkshop.setNotificationsService(notificationsNode);
        // PetasosMetricsService
        PetasosMetricsAgentTN metricsNode = new PetasosMetricsAgentTN(petasosObservationsWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        metricsNode.loadConfigurationFromParent(petasosObservationsWorkshop);
        petasosObservationsWorkshop.setMetricsService(metricsNode);
        // PetasosAbnormalEventsService
        PetasosAbnormalEventsAgentTN abnormalEventsNode = new PetasosAbnormalEventsAgentTN(petasosObservationsWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        abnormalEventsNode.loadConfigurationFromParent(petasosObservationsWorkshop);
        petasosObservationsWorkshop.setAbnormalEventsService(abnormalEventsNode);
        // PetasosConfigurationReportsService
        PetasosConfigurationReportsAgentTN configurationReportsNode = new PetasosConfigurationReportsAgentTN(petasosObservationsWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        configurationReportsNode.loadConfigurationFromParent(petasosObservationsWorkshop);
        petasosObservationsWorkshop.setConfigurationReportService(configurationReportsNode);
        // Done
        return(petasosObservationsWorkshop);
    }
        
    public PetasosIPCWorkshopTN createPetasosIPCWorkshopAndWUPNodes(ProcessingPlantTopologyNode processingPlant){    
        // PetasosIPCWorkshop
        PetasosIPCWorkshopTN petasosIPCWorkshop = new PetasosIPCWorkshopTN(processingPlant.getParticipantName(), processingPlant.getNodeVersion());
        petasosIPCWorkshop.loadConfigurationFromParent(processingPlant);
        createAllJGroupsEndpoints(processingPlant, petasosIPCWorkshop);
        // PetasosInternalMessagingIngresProxy
        InternalMessagingEgressProxyTN egressProxy = new InternalMessagingEgressProxyTN(petasosIPCWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        egressProxy.loadConfigurationFromParent(petasosIPCWorkshop);
        petasosIPCWorkshop.setEgressProxy(egressProxy);
        // PetasosInternalMessagingEgressProxy
        InternalMessagingIngressProxyTN ingresProxy = new InternalMessagingIngressProxyTN(petasosIPCWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        ingresProxy.loadConfigurationFromParent(petasosIPCWorkshop);
        petasosIPCWorkshop.setIngresProxy(ingresProxy);
        // PetasosEdgeAsk
        PetasosEdgeAskTN edgeAsk = new PetasosEdgeAskTN(petasosIPCWorkshop.getParticipantName(), processingPlant.getNodeVersion());
        edgeAsk.loadConfigurationFromParent(petasosIPCWorkshop);
        petasosIPCWorkshop.setEdgeAsk(edgeAsk);
        // Done
        return(petasosIPCWorkshop);
    }
    
    
    /**
     * 
     * @param componentType
     * @param wup
     * @return 
     */
    public WorkUnitProcessorSubComponentTN createWorkUnitProcessorComponent(ComponentTypeEnum componentType, WorkUnitProcessorTopologyNode wup){
        getLogger().debug(".createWorkUnitProcessorComponent(): Entry");
        WorkUnitProcessorSubComponentTN wupComponent = new WorkUnitProcessorSubComponentTN();
        wupComponent.constructNodeID(componentType.getDisplayName());
        wupComponent.setSubsystemParticipantName(wup.getSubsystemParticipantName());
        wupComponent.setNodeVersion(wup.getNodeVersion());
        wupComponent.setSiteNode(wup.getSiteNode());
        wupComponent.setInfrastructureNode(wup.getInfrastructureNode());
        wupComponent.setPlatformNode(wup.getPlatformNode());
        wupComponent.setConcurrencyMode(getConcurrenceMode());
        ComponentTypeDefinition componentTypeDef = new ComponentTypeDefinition();
        componentTypeDef.setComponentArchetype(componentType);
        componentTypeDef.setTypeName(componentType.getDisplayName());
        componentTypeDef.setDisplayTypeName(componentType.getDisplayName());
        wupComponent.setNodeType(componentTypeDef);
        wupComponent.setParentNode(wup.getNodeId());
        wupComponent.setNetworkZone(wup.getNetworkZone());
        wupComponent.setSubsystemParticipantName(wup.getSubsystemParticipantName());
        wupComponent.setParticipantName(wup.getParticipantName() + "." + componentType.getDisplayName());
        wupComponent.setParticipantDisplayName(componentType.getDisplayName());
        wupComponent.setReplicationCount(wup.getReplicationCount());
//
//        getLogger().trace(".createWorkUnitProcessorComponent(): Add the WorkUnitProcessor Component to the Topology Cache");
//        getTopologyIM().addTopologyNode(wup.getNodeId(), wupComponent);
        getLogger().debug(".createWorkUnitProcessorComponent(): Exit");
        return(wupComponent);
    }
    
    //
    // Read Petasos Topology Configuration File
    //
    
    protected void loadPetasosPropertyFile(){
        getLogger().warn(".loadPetasosPropertyFile(): Entry");
        String petasosConfigFilename ="Unknwon";
        try {
            getLogger().trace(".loadPetasosPropertyFile(): Establish YAML ObjectMapper");
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            getLogger().warn(".loadPetasosPropertyFile(): [Openning Configuration File] Start");
            petasosConfigFilename = getSubsystemPropertyFile().getConfigFiles().getPetasosConfigFilename();
            getLogger().warn(".loadPetasosPropertyFile(): [Openning Configuration File] petasosConfigurationFilename->{}", petasosConfigFilename);
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Path path = Paths.get(petasosConfigFilename);
            File file = path.toFile();
            getLogger().warn(".loadPetasosPropertyFile(): [Openning Configuration File] End");
            getLogger().warn(".loadPetasosPropertyFile(): [Importing Configuration File] Start");
            setPetasosPropertyFile(yamlMapper.readValue(file, PetasosClusterPropertyFile.class));
            getLogger().warn(".loadPetasosPropertyFile(): [Read YAML Configuration File] Finish");
            getLogger().debug(".loadPetasosPropertyFile(): Exit, file loaded, petasosPropertyFile->{}",getPetasosPropertyFile());
            return;
        } catch(FileNotFoundException noFile){
            getLogger().error(".loadPetasosPropertyFile(): Configuration File->{} is not found, error->{}", petasosConfigFilename, noFile.getMessage());
        } catch(IOException ioError){
            getLogger().error(".loadPetasosPropertyFile(): Configuration File->{} could not be loaded, error->{}", petasosConfigFilename, ioError.getMessage());
        }
        getLogger().debug(".loadPetasosPropertyFile(): failed to load file");
    }
}
