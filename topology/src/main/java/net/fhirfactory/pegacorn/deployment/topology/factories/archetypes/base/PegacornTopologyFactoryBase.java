/*
 * Copyright (c) 2021 Mark A. Hunter (ACT Health)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.fhirfactory.pegacorn.core.interfaces.topology.PegacornTopologyFactoryInterface;
import net.fhirfactory.pegacorn.core.model.componentid.ComponentIdType;
import net.fhirfactory.pegacorn.core.model.componentid.SoftwareComponentTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.JGroupsIntegrationPointNamingUtilities;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.ipc.PegacornCommonInterfaceNames;
import net.fhirfactory.pegacorn.core.model.petasos.participant.*;
import net.fhirfactory.pegacorn.core.model.petasos.participant.id.PetasosParticipantId;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.HTTPServerAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.http.HTTPServerTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.mode.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.*;
import net.fhirfactory.pegacorn.deployment.names.sites.SiteKeyNames;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes.ParameterNameValuePairType;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.http.HTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common.TopologyFactoryHelpersBase;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.interfaces.SolutionNodeFactoryInterface;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PegacornTopologyFactoryBase extends TopologyFactoryHelpersBase implements PegacornTopologyFactoryInterface {

    private static final String PROPERTY_FILENAME_EXTENSION = ".yaml";

    private static final Integer ID_VALIDITY_RANGE_IN_YEARS = 100;

    private BaseSubsystemPropertyFile propertyFile;
    private ProcessingPlantSoftwareComponent processingPlantNode;
    private boolean initialised;

    @Inject
    private SiteKeyNames siteKeyNames;

    @Inject
    private TopologyIM topologyIM;

    @Inject
    private SolutionNodeFactoryInterface solutionNodeInterface;

    @Inject
    private PegacornCommonInterfaceNames interfaceNames;

    @Inject
    private PegacornProperties pegacornProperties;

    @Inject
    private JGroupsIntegrationPointNamingUtilities componentNameingUtilities;

    @Inject
    private ProcessingPlantPetasosParticipantHolder participantHolder;

    @Inject
    private ProcessingPlantPetasosParticipantNameHolder participantNameHolder;

    //
    // Default Constructor
    //

    public PegacornTopologyFactoryBase(){
        this.initialised = false;
    }

    //
    // Abstract Methods
    //

    abstract protected Logger specifyLogger();
    abstract protected String specifyPropertyFileName();
    abstract protected Class specifyPropertyFileClass();
    abstract protected ProcessingPlantSoftwareComponent buildSubsystemTopology();

    //
    // PostConstruct function
    //

    @PostConstruct
    public void initialise(){
        getLogger().debug(".initialise(): Entry");
        if(!initialised) {
            getLogger().info(".initialise(): Initalising......");
            getLogger().info(".initialise(): [Solution Node Factory Initialisation]: Start");
            this.solutionNodeInterface.initialise();
            getLogger().info(".initialise(): [Solution Node Factory Initialisation]: Finish");
            getLogger().info(".initialise(): [Property File Loading]: Start");
            this.propertyFile = readPropertyFile();
            getLogger().info(".initialise(): [Property File Loading]: Finish");
            getLogger().info(".initialise(): [Subsystem Topology Building]: Start");
            this.processingPlantNode = buildSubsystemTopology();
            getLogger().info(".initialise(): [Subsystem Topology Building]: Finish");
            this.initialised = true;
            getLogger().info(".initialise(): Done.");
        } else {
            getLogger().info(".initialise(): Already initialised, nothing to do....");
        }
        getLogger().debug(".initialise(): Exit");
    }

    //
    // Getters (and Setters)
    //

    protected Logger getLogger(){
        return(specifyLogger());
    }

    public BaseSubsystemPropertyFile getPropertyFile() {
        return (this.propertyFile);
    }

    public TopologyIM getTopologyIM() {
        return topologyIM;
    }

    protected SiteKeyNames getSiteKeyNames(){
        return(siteKeyNames);
    }

    public SolutionNodeFactoryInterface getSolutionNodeInterface() {
        return solutionNodeInterface;
    }

    protected PegacornCommonInterfaceNames getInterfaceNames(){
        return(interfaceNames);
    }

    //
    // Node Builders
    //

    protected String getActualHostIP(){
        String actualHostIP = pegacornProperties.getProperty("MY_HOST_IP", "Unknown");
        return(actualHostIP);
    }

    protected String getActualPodIP(){
        String actualHostIP = pegacornProperties.getProperty("MY_POD_IP", "Unknown");
        return(actualHostIP);
    }

    /**
     * Subsystem Node Builder
     *
     * @param
     * @return
     */

    public SubsystemTopologyNode buildSubsystemNodeFromConfigurationFile(){
        getLogger().debug(".addSubsystemNode(): Entry");
        SubsystemTopologyNode subsystem = new SubsystemTopologyNode();

        getLogger().trace(".addSubsystemNode(): [Create the Subsystem ComponentId] Start");
        ComponentIdType componentId = ComponentIdType.fromComponentName(getPropertyFile().getSubsystemInstant().getSubsystemName());
        subsystem.setComponentID(componentId);
        getLogger().trace(".addSubsystemNode(): [Create the Subsystem ComponentId] id->{}", subsystem.getComponentId());
        getLogger().trace(".addSubsystemNode(): [Create the Subsystem ComponentId] Finish");

        getLogger().trace(".addSubsystemNode(): [Create the Subsystem ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        String participantName = getPropertyFile().getSubsystemInstant().getParticipantName();
        if(StringUtils.isEmpty(participantName)){
            participantName = componentId.getName();
        }
        participantId.setName(participantName);
        participantId.setDisplayName(participantName);
        participantId.setSubsystemName(participantName);
        participantId.setFullName(getSolutionNodeInterface().getSolutionTopologyNode().getParticipantId().getName() + "." + participantName);
        String subsystemVersion = getPropertyFile().getSubsystemInstant().getSubsystemVersion();
        if(StringUtils.isEmpty(subsystemVersion)){
            subsystemVersion = "1.0.0";
        }
        participantId.setVersion(subsystemVersion);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(1);
        subsystem.setParticipant(participant);
        getLogger().trace(".addSubsystemNode(): [Create the Subsystem ParticipantId] id->{}", subsystem.getParticipant());
        getLogger().trace(".addSubsystemNode(): [Create the Subsystem ParticipantId] Finish");

        getLogger().trace(".addSubsystemNode(): [Set the Subsystem Concurrency Mode] Start");
        subsystem.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".addSubsystemNode(): [Set the Subsystem Concurrency Mode] Finish");

        getLogger().trace(".addSubsystemNode(): [Set the Subsystem Resilience Mode] Start");
        subsystem.setResilienceMode(getResilienceMode());
        getLogger().trace(".addSubsystemNode(): [Set the Subsystem Resilience Mode] Finish");

        getLogger().trace(".addSubsystemNode(): [Set the Subsystem Site Count] Start");
        subsystem.setSiteCount(getPropertyFile().getDeploymentSites().getSiteCount());
        getLogger().trace(".addSubsystemNode(): [Set the Subsystem Site Count] Finish");

        getLogger().trace(".addSubsystemNode(): [Set the Subsystem's Parent Solution] Start");
        subsystem.setParentComponent(getSolutionNodeInterface().getSolutionTopologyNode().getComponentId());
        getLogger().trace(".addSubsystemNode(): [Set the Subsystem's Parent Solution] Finish");

        getLogger().trace(".addSubsystemNode(): [Add the subsystem to the Solution subsystem list] Start");
        getSolutionNodeInterface().getSolutionTopologyNode().getSubsystemList().add(subsystem.getComponentId());
        getLogger().trace(".addSubsystemNode(): [Add the subsystem to the Solution subsystem list] Start");

        getLogger().trace(".addSubsystemNode(): [Set subsystem type] Start");
        subsystem.setComponentType(SoftwareComponentTypeEnum.SUBSYSTEM);
        getLogger().trace(".addSubsystemNode(): [Set subsystem type] Finish");

        getLogger().trace(".addSubsystemNode(): [Add the subsystem to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(getSolutionNodeInterface().getSolutionTopologyNode().getComponentId(), subsystem);
        getLogger().trace(".addSubsystemNode(): [Add the subsystem to the Topology Cache] Finish");

        getLogger().debug(".addSubsystemNode(): Exit");
        return(subsystem);
    }

    /**
     * Business Service Node Set Builder
     *
     * @param subsystem
     * @return
     */

    public BusinessServiceTopologyNode buildBusinessServiceNode(SubsystemTopologyNode subsystem){
        getLogger().debug(".buildBusinessServiceNode(): Entry, subsystem->{}", subsystem);
        BusinessServiceTopologyNode businessService = new BusinessServiceTopologyNode();

        businessService.setParentComponent(subsystem.getComponentId());

        getLogger().trace(".buildBusinessServiceNode(): [Create the Subsystem ComponentId] Start");
        String businessServiceName = getPropertyFile().getSubsystemInstant().getExternalisedServiceName();
        if(StringUtils.isEmpty(businessServiceName)){
            businessServiceName = "ExtSvc." + subsystem.getComponentId().getName();
        }
        ComponentIdType componentId = ComponentIdType.fromComponentName(businessServiceName);
        businessService.setComponentID(componentId);
        getLogger().trace(".buildBusinessServiceNode(): [Create the Subsystem ComponentId] id->{}", businessService.getComponentId());
        getLogger().trace(".buildBusinessServiceNode(): [Create the Subsystem ComponentId] Finish");

        getLogger().trace(".buildBusinessServiceNode(): [Create the Subsystem ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        String participantName = getPropertyFile().getSubsystemInstant().getExternalisedServiceName();
        if(StringUtils.isEmpty(participantName)){
            participantName =  componentId.getName();
        }
        participantId.setName(participantName);
        participantId.setDisplayName(participantName);
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        participantId.setFullName(subsystem.getParticipant().getParticipantId().getFullName() + "." + participantName);
        String subsystemVersion = getPropertyFile().getSubsystemInstant().getSubsystemVersion();
        if(StringUtils.isEmpty(subsystemVersion)){
            subsystemVersion = "1.0.0";
        }
        participantId.setVersion(subsystemVersion);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(1);
        businessService.setParticipant(participant);
        getLogger().trace(".buildBusinessServiceNode(): [Create the Subsystem ParticipantId] Start");

        getLogger().trace(".addSubsystemNode(): [Set subsystem type] Start");
        businessService.setComponentType(SoftwareComponentTypeEnum.EXTERNALISED_SERVICE);
        getLogger().trace(".addSubsystemNode(): [Set subsystem type] Finish");

        getLogger().trace(".addSubsystemNode(): [Add the businesService to the Subsystem] Start");
        subsystem.getBusinessServices().add(businessService.getComponentId());
        getLogger().trace(".addSubsystemNode(): [Add the businesService to the Subsystem] Start");

        getLogger().trace(".addSubsystemNode(): [Add the businesService to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(subsystem.getComponentId(), businessService);
        getLogger().trace(".addSubsystemNode(): [Add the businesService to the Topology Cache] Finish");

        getLogger().debug(".buildBusinessServiceNode(): Exit");
        return(businessService);
    }

    /**
     * Deployment Site Node Builder
     *
     * @param businessService
     * @return
     */
    public DeploymentSiteTopologyNode buildDeploymentSiteNode(BusinessServiceTopologyNode businessService){
        getLogger().debug(".buildDeploymentSiteNode(): Entry, businessService->{}", businessService);

        DeploymentSiteTopologyNode site = new DeploymentSiteTopologyNode();

        getLogger().trace(".buildDeploymentSiteNode(): [Set the deploymentSite's Parent BusinessService] Start");
        site.setParentComponent(businessService.getComponentId());
        getLogger().trace(".buildDeploymentSiteNode(): [Set the deploymentSite's Parent BusinessService] Finish");

        getLogger().trace(".buildDeploymentSiteNode(): [Create the Site ComponentId] Start");
        String siteName = getPropertyFile().getSubsystemInstant().getSite();
        ComponentIdType componentId = ComponentIdType.fromComponentName(siteName);
        site.setComponentID(componentId);
        getLogger().trace(".buildDeploymentSiteNode(): [Create the Site ComponentId] Finish");

        getLogger().trace(".buildDeploymentSiteNode(): [Create the Site ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        String participantName = componentId.getName();
        participantId.setName(participantName);
        participantId.setDisplayName(participantName);
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        participantId.setFullName(businessService.getParticipant().getParticipantId().getFullName() + "." + participantName);
        String siteVersion = getPropertyFile().getSubsystemInstant().getSubsystemVersion();
        if(StringUtils.isEmpty(siteVersion)){
            siteVersion = "1.0.0";
        }
        participantId.setVersion(siteVersion);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(1);
        site.setParticipant(participant);
        getLogger().trace(".buildDeploymentSiteNode(): [Create the Site ParticipantId] Finish");

        getLogger().trace(".buildDeploymentSiteNode(): [Set component type] Start");
        site.setComponentType(SoftwareComponentTypeEnum.SITE);
        getLogger().trace(".buildDeploymentSiteNode(): [Set component type] Finish");

        getLogger().trace(".buildDeploymentSiteNode(): [Set site instance count] Start");
        site.setInstanceCount(getPropertyFile().getDeploymentSites().getSiteCount());
        getLogger().trace(".buildDeploymentSiteNode(): [Set site instance count] Finish");

        getLogger().trace(".buildDeploymentSiteNode(): [Add the DeploymentSite to the Business Service] Start");
        businessService.getDeploymentSites().add(site.getComponentId());
        getLogger().trace(".buildDeploymentSiteNode(): [Add the DeploymentSite to the Business Service] Finish");

        getLogger().trace(".buildDeploymentSiteNode(): [Add the DeploymentSite to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(businessService.getComponentId(), site);
        getLogger().trace(".buildDeploymentSiteNode(): [Add the DeploymentSite to the Topology Cache] Finish");

        getLogger().debug(".buildDeploymentSiteNode(): Exit");
        return(site);
    }

    /**
     * Build Cluster Service Node
     *
     * @param site
     * @return
     */
    public ClusterServiceTopologyNode buildClusterServiceNode(DeploymentSiteTopologyNode site, BusinessServiceTopologyNode businessService){
        getLogger().debug(".buildClusterServiceNode(): Entry");
        ClusterServiceTopologyNode clusterService = new ClusterServiceTopologyNode();

        getLogger().trace(".buildClusterServiceNode(): [Set the node's Parent site] Start");
        site.setParentComponent(site.getComponentId());
        getLogger().trace(".buildClusterServiceNode(): [Set the node's Parent site] Finish");

        getLogger().trace(".buildClusterServiceNode(): [Create the Node ComponentId] Start");
        String clusterServiceName = getPropertyFile().getSubsystemInstant().getClusterServiceName();
        ComponentIdType componentId = ComponentIdType.fromComponentName(clusterServiceName);
        clusterService.setComponentID(componentId);
        getLogger().trace(".buildClusterServiceNode(): [Create the ClusterService ComponentId] Finish");

        getLogger().trace(".buildClusterServiceNode(): [Create the ClusterService ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        String participantName = componentId.getName();
        participantId.setName(participantName);
        participantId.setDisplayName(participantName);
        participantId.setFullName(businessService.getParticipant().getParticipantId().getFullName() + "." + participantName);
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        String siteVersion = getPropertyFile().getSubsystemInstant().getProcessingPlantVersion();
        if(StringUtils.isEmpty(siteVersion)){
            siteVersion = "1.0.0";
        }
        participantId.setVersion(siteVersion);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(getPropertyFile().getDeploymentSites().getSiteCount());
        clusterService.setParticipant(participant);
        getLogger().trace(".buildClusterServiceNode(): [Create the ClusterService ParticipantId] Finish");

        getLogger().trace(".buildClusterServiceNode(): [Set component type] Start");
        clusterService.setComponentType(SoftwareComponentTypeEnum.CLUSTER_SERVICE);
        getLogger().trace(".buildClusterServiceNode(): [Set component type] Finish");

        getLogger().trace(".buildClusterServiceNode(): [Set Resilience Mode] Start");
        clusterService.setResilienceMode(getResilienceMode());
        getLogger().trace(".buildClusterServiceNode(): [Set Resilience Mode] Finish");

        getLogger().trace(".buildClusterServiceNode(): [Set Concurrency Mode] Start");
        clusterService.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".buildClusterServiceNode(): [Set Concurrency Mode] Finish");

        getLogger().trace(".buildClusterServiceNode(): [Set Default DNS Name] Start");
        clusterService.setDefaultDNSName(getPropertyFile().getSubsystemInstant().getClusterServiceDNSName());
        getLogger().trace(".buildClusterServiceNode(): [Set Default DNS Name] Finish");

        getLogger().trace(".buildClusterServiceNode(): [Set Encryption Status] Start");
        clusterService.setInternalTrafficEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        getLogger().trace(".buildClusterServiceNode(): [Set Encryption Status] Finish");

        getLogger().trace(".addDeploymentSiteNode(): [Add the ClusterService to Site] Start");
        site.getClusterServices().add(clusterService.getComponentId());
        getLogger().trace(".addDeploymentSiteNode(): [Add the ClusterService to Site] Finish");

        getLogger().trace(".buildClusterServiceNode(): [Add the ClusterService to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(site.getComponentId(), clusterService);
        getLogger().trace(".buildClusterServiceNode(): [Add the ClusterService to the Topology Cache] Finish");

        getLogger().debug(".buildClusterServiceNode(): Exit");
        return(clusterService);
    }

    /**
     * Platform Node Set Builder
     *
     * @param clusterService
     * @return
     */
    public PlatformTopologyNode buildPlatformNode(ClusterServiceTopologyNode clusterService){
        getLogger().debug(".addPlatformNode(): Entry");

        PlatformTopologyNode node = new PlatformTopologyNode();

        getLogger().trace(".addPlatformNode(): [Set the node's Parent site] Start");
        node.setParentComponent(clusterService.getComponentId());
        getLogger().trace(".addPlatformNode(): [Set the node's Parent site] Finish");

        getLogger().trace(".addPlatformNode(): [Create the ComponentId] Start");
        String hostName = pegacornProperties.getProperty("MY_POD_NAME", "PlatformNode0");
        ComponentIdType componentId = ComponentIdType.fromComponentName(hostName);
        node.setComponentID(componentId);
        getLogger().trace(".addPlatformNode(): [Create the ComponentId] Finish");

        getLogger().trace(".addPlatformNode(): [Create the ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        String participantDisplayName = clusterService.getParticipant().getParticipantId().getName() + "." + componentId.getName();
        participantId.setName(componentId.getName());
        participantId.setDisplayName( participantDisplayName);
        participantId.setFullName(clusterService.getParticipant().getParticipantId().getFullName() + "." + componentId.getName());
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        String siteVersion = getPropertyFile().getSubsystemInstant().getProcessingPlantVersion();
        if(StringUtils.isEmpty(siteVersion)){
            siteVersion = "1.0.0";
        }
        participantId.setVersion(siteVersion);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(1);
        node.setParticipant(participant);
        getLogger().trace(".addPlatformNode(): [Create the ParticipantId] Finish");

        getLogger().trace(".addPlatformNode(): [Set IP Address Detail] Start");
        node.setActualHostIP(getActualHostIP());
        node.setActualPodIP(getActualPodIP());
        getLogger().trace(".addPlatformNode(): [Set IP Address Detail] Finish");

        getLogger().trace(".addPlatformNode(): [Set component type] Start");
        node.setComponentType(SoftwareComponentTypeEnum.PLATFORM);
        getLogger().trace(".addPlatformNode(): [Set component type] Finish");

        getLogger().trace(".addPlatformNode(): [Set Resilience Mode] Start");
        node.setResilienceMode(getResilienceMode());
        getLogger().trace(".addPlatformNode(): [Set Resilience Mode] Finish");

        getLogger().trace(".addPlatformNode(): [Set Concurrency Mode] Start");
        node.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".addPlatformNode(): [Set Concurrency Mode] Finish");

        getLogger().trace(".addPlatformNode(): [Add the Node to Cluster Service] Start");
        clusterService.getPlatformNodes().add(node.getComponentId());
        getLogger().trace(".addPlatformNode(): [Add the Node to Cluster Service] Finish");

        getLogger().trace(".addPlatformNode(): [Add the PlatformNode to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(clusterService.getComponentId(), node);
        getLogger().trace(".addPlatformNode(): [Add the PlatformNode to the Topology Cache] Start");

        getLogger().debug(".addPlatformNode(): Exit");
        return(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public ProcessingPlantSoftwareComponent buildProcessingPlant(PlatformTopologyNode node, ClusterServiceTopologyNode clusterService){
        getLogger().debug(".buildProcessingPlant(): Entry");

        ProcessingPlantSoftwareComponent processingPlant = new ProcessingPlantSoftwareComponent();

        getLogger().trace(".buildProcessingPlant(): [Set Parent] Start");
        processingPlant.setParentComponent(node.getComponentId());
        getLogger().trace(".buildProcessingPlant(): [Set Parent] Finish");

        getLogger().trace(".buildProcessingPlant(): [Create ComponentId] Start");
        String name = getPropertyFile().getSubsystemInstant().getParticipantName();
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        processingPlant.setComponentID(componentId);
        getLogger().trace(".buildProcessingPlant(): [Create ComponentId] Finish");

        getLogger().trace(".buildProcessingPlant(): [Create ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        String participantName = getPropertyFile().getSubsystemInstant().getParticipantName();
        participantId.setName(participantName);
        participantId.setDisplayName(participantName);
        participantId.setFullName(getSolutionNodeInterface().getSolutionTopologyNode().getParticipantId().getName() + "." + participantName);
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        String siteVersion = getPropertyFile().getSubsystemInstant().getProcessingPlantVersion();
        if(StringUtils.isEmpty(siteVersion)){
            siteVersion = "1.0.0";
        }
        participantId.setVersion(siteVersion);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        processingPlant.setParticipant(participant);
        getLogger().trace(".buildProcessingPlant(): [Create ParticipantId] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set Location Metadata Detail] Start");
        processingPlant.setActualHostIP(getActualHostIP());
        processingPlant.setActualPodIP(getActualPodIP());
        processingPlant.setPodName(node.getComponentId().getName());
        String siteName = getPropertyFile().getSubsystemInstant().getSite();
        processingPlant.setSiteName(siteName);
        getLogger().trace(".buildProcessingPlant(): [Set Location Metadata Detail] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set Resilience Mode] Start");
        processingPlant.setResilienceMode(getResilienceMode());
        getLogger().trace(".buildProcessingPlant(): [Set Resilience Mode] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set Concurrency Mode] Start");
        processingPlant.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".buildProcessingPlant(): [Set Concurrency Mode] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set component type] Start");
        processingPlant.setComponentType(SoftwareComponentTypeEnum.PROCESSING_PLANT);
        getLogger().trace(".buildProcessingPlant(): [Set component type] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set deployment zone namespace] Start");
        processingPlant.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        getLogger().trace(".buildProcessingPlant(): [Set deployment zone namespace] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set component site] Start");
        processingPlant.setDeploymentSite(getPropertyFile().getSubsystemInstant().getSite());
        getLogger().trace(".buildProcessingPlant(): [Set component site] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set Replication Count] Start");
        processingPlant.setReplicationCount(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        getLogger().trace(".buildProcessingPlant(): [Set Replication Count] Finish");

        participantNameHolder.setSubsystemParticipantName(getPropertyFile().getSubsystemInstant().getParticipantName());

        //
        // Assign the Configuration File Names (InterZone)
        getLogger().trace(".buildProcessingPlant(): [Set Petasos Configuration Files] Start");
        processingPlant.setPetasosIPCStackConfigFile(getPropertyFile().getDeploymentMode().getPetasosIPCStackConfigFile());
        processingPlant.setPetasosTopologyStackConfigFile(getPropertyFile().getDeploymentMode().getPetasosTopologyStackConfigFile());
        processingPlant.setMultiZoneInfinispanStackConfigFile(getPropertyFile().getDeploymentMode().getMultiuseInfinispanStackConfigFile());
        processingPlant.setPetasosTaskingStackConfigFile(getPropertyFile().getDeploymentMode().getPetasosTaskingStackConfigFile());
        processingPlant.setPetasosAuditStackConfigFile(getPropertyFile().getDeploymentMode().getPetasosAuditStackConfigFile());
        processingPlant.setPetasosInterceptionStackConfigFile(getPropertyFile().getDeploymentMode().getPetasosInterceptionStackConfigFile());
        processingPlant.setPetasosMetricsStackConfigFile(getPropertyFile().getDeploymentMode().getPetasosMetricsStackConfigFile());
        processingPlant.setPetasosSubscriptionsStackConfigFile(getPropertyFile().getDeploymentMode().getPetasosSubscriptionStackConfigFile());
        getLogger().trace(".buildProcessingPlant(): [Set Petasos Configuration Files] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set Assigned DNS Name] Start");
        processingPlant.setAssignedDNSName(getPropertyFile().getSubsystemInstant().getProcessingPlantDNSName());
        getLogger().trace(".buildProcessingPlant(): [Set Assigned DNS Name] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set Encryption Status] Start");
        processingPlant.setInternalTrafficEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        getLogger().trace(".buildProcessingPlant(): [Set Encryption Status] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set Network Zone] Start");
        NetworkSecurityZoneEnum zone = NetworkSecurityZoneEnum.fromDisplayName(getPropertyFile().getDeploymentZone().getSecurityZoneName());
        if(zone == null){
            getLogger().error(".buildProcessingPlant(): [Set Network Zone] Cannot resolve Network Security Zone for component, provided->{}", getPropertyFile().getDeploymentZone().getSecurityZoneName());
            StringBuilder possibleValues = new StringBuilder();
            for(NetworkSecurityZoneEnum currentValue: NetworkSecurityZoneEnum.values()){
                possibleValues.append(currentValue.getDisplayName() + " ");
            }
            getLogger().error(".buildProcessingPlant(): [Set Network Zone] Possible Values Are->{}", possibleValues.toString());
        }
        processingPlant.setSecurityZone(zone);
        getLogger().trace(".buildProcessingPlant(): [Set Network Zone] Finish");

        getLogger().trace(".buildProcessingPlant(): [Set Local Default ProcessingPlant] Start");
        setProcessingPlantNode(processingPlant);
        getLogger().trace(".buildProcessingPlant(): [Set Local Default ProcessingPlant] Finish");

        getLogger().trace(".buildProcessingPlant(): [Populate OtherDeploymentProperties] Start");
        populateOtherDeploymentProperties(processingPlant, getPropertyFile().getDeploymentMode().getOtherDeploymentParameters());
        getLogger().trace(".buildProcessingPlant(): [Populate OtherDeploymentProperties] Start");

        getLogger().trace(".buildProcessingPlant(): [Add the Node to Cluster Service] Start");
        node.getProcessingPlants().add(processingPlant.getComponentId());
        getLogger().trace(".buildProcessingPlant(): [Add the Node to Cluster Service] Finish");

        getLogger().trace(".buildProcessingPlant(): [Add the ProcessingPlant to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(node.getComponentId(), processingPlant);
        getLogger().trace(".buildProcessingPlant(): [Add the ProcessingPlant to the Topology Cache] Finish");

        //
        // Create Petasos Participant for Processing Plant
        getLogger().trace(".buildProcessingPlant(): [Create Petasos Participant for Processing Plant] Start");
        getProcessingPlantNode().getParticipant().setParticipantStatus(PetasosParticipantStatusEnum.PARTICIPANT_IS_NOT_READY);
        participantHolder.setMeAsPetasosParticipant(getProcessingPlantNode().getParticipant());
        getLogger().trace(".buildProcessingPlant(): [Create Petasos Participant for Processing Plant] Finish");
        //
        // All done
        getLogger().debug(".buildProcessingPlant(): Exit");
        return(processingPlant);
    }

    /**
     *
     * @param name
     * @param version
     * @param processingPlant
     */
    @Override
    public WorkshopSoftwareComponent buildWorkshop(String name, String version, ProcessingPlantSoftwareComponent processingPlant, SoftwareComponentTypeEnum nodeType){
        getLogger().debug(".buildWorkshop(): Entry");

        WorkshopSoftwareComponent workshop = new WorkshopSoftwareComponent();

        getLogger().trace(".buildWorkshop(): [Set Parent] Start");
        workshop.setParentComponent(processingPlant.getComponentId());
        getLogger().trace(".buildWorkshop(): [Set Parent] Finish");

        getLogger().trace(".buildWorkshop(): [Create ComponentId] Start");
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        workshop.setComponentID(componentId);
        getLogger().trace(".buildWorkshop(): [Create ComponentId] Finish");

        getLogger().trace(".buildWorkshop(): [Create ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        participantId.setName(processingPlant.getParticipant().getParticipantId().getName() + "." + name);
        participantId.setDisplayName(name);
        participantId.setFullName(processingPlant.getParticipant().getParticipantId().getFullName() + "." + name);
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        participantId.setVersion(version);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        workshop.setParticipant(participant);
        getLogger().trace(".buildWorkshop(): [Create ParticipantId] Finish");

        getLogger().trace(".buildWorkshop(): [Set Resilience Mode] Start");
        workshop.setResilienceMode(getResilienceMode());
        getLogger().trace(".buildWorkshop(): [Set Resilience Mode] Finish");

        getLogger().trace(".buildWorkshop(): [Set Concurrency Mode] Start");
        workshop.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".buildWorkshop(): [Set Concurrency Mode] Finish");

        getLogger().trace(".buildWorkshop(): [Set component type] Start");
        workshop.setComponentType(nodeType);
        getLogger().trace(".buildWorkshop(): [Set component type] Finish");

        getLogger().trace(".buildWorkshop(): [Set component site] Start");
        workshop.setDeploymentSite(getPropertyFile().getSubsystemInstant().getSite());
        getLogger().trace(".buildWorkshop(): [Set component site] Finish");

        getLogger().trace(".buildWorkshop(): [Set Network Zone] Start");
        workshop.setSecurityZone(processingPlant.getSecurityZone());
        getLogger().trace(".buildWorkshop(): [Set Network Zone] Finish");

        getLogger().trace(".buildWorkshop(): [Add the Workshop to ProcessingPlant] Start");
        processingPlant.getWorkshops().add(workshop.getComponentId());
        getLogger().trace(".buildWorkshop(): [Add the Workshop to ProcessingPlant] Finish");

        getLogger().trace(".buildWorkshop(): [Add the Workshop to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(processingPlant.getComponentId(), workshop);
        getLogger().trace(".buildWorkshop(): [Add the Workshop to the Topology Cache] Finish");

        getLogger().debug(".buildWorkshop(): Exit");
        return(workshop);
    }

    /**
     *
     * @param name
     * @param version
     * @param workshop
     */
    @Override
    public WorkUnitProcessorSoftwareComponent buildWUP(String name, String version, String participantDisplayNameOverride, WorkshopSoftwareComponent workshop, SoftwareComponentTypeEnum nodeType){
        getLogger().debug(".buildWUP(): Entry, name->{}, version->{}", name, version);
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(version)){
            getLogger().error(".buildWUP(): name or version are emtpy!!!!");
        }
        WorkUnitProcessorSoftwareComponent wup = new WorkUnitProcessorSoftwareComponent();

        getLogger().trace(".buildWUP(): [Set Parent] Start");
        wup.setParentComponent(workshop.getComponentId());
        getLogger().trace(".buildWUP(): [Set Parent] Finish");

        getLogger().trace(".buildWUP(): [Create ComponentId] Start");
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        wup.setComponentID(componentId);
        getLogger().trace(".buildWUP(): [Create ComponentId] Finish");

        getLogger().trace(".buildWUP(): [Create ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        participantId.setName(workshop.getParticipant().getParticipantId().getName() + "." + name);
        if(StringUtils.isNotEmpty(participantDisplayNameOverride)) {
            participantId.setDisplayName(participantDisplayNameOverride);
        } else {
            participantId.setDisplayName(name);
        }
        participantId.setFullName(workshop.getParticipant().getParticipantId().getFullName() + "." + name);
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        participantId.setVersion(version);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        wup.setParticipant(participant);
        getLogger().trace(".buildWUP(): [Create ParticipantId] Finish");

        getLogger().trace(".buildWUP(): [Set Resilience Mode] Start");
        wup.setResilienceMode(getResilienceMode());
        getLogger().trace(".buildWUP(): [Set Resilience Mode] Finish");

        getLogger().trace(".buildWUP(): [Set Concurrency Mode] Start");
        wup.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".buildWUP(): [Set Concurrency Mode] Finish");

        getLogger().trace(".buildWUP(): [Set component type] Start");
        wup.setComponentType(nodeType);
        getLogger().trace(".buildWUP(): [Set component type] Finish");

        getLogger().trace(".buildWUP(): [Set component site] Start");
        wup.setDeploymentSite(getPropertyFile().getSubsystemInstant().getSite());
        getLogger().trace(".buildWUP(): [Set component site] Finish");

        getLogger().trace(".buildWUP(): [Set Network Zone] Start");
        wup.setSecurityZone(workshop.getSecurityZone());
        getLogger().trace(".buildWUP(): [Set Network Zone] Finish");

        getLogger().trace(".buildWUP(): [Set Replication Count] Start");
        wup.setReplicationCount(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        getLogger().trace(".buildWUP(): [Set Replication Count] Finish");

        getLogger().trace(".buildWUP(): [Add the WUP to Workshop] Start");
        workshop.getWupSet().add(wup.getComponentId());
        getLogger().trace(".buildWUP(): [Add the WUP to Workshop] Finish");

        getLogger().trace(".buildWUP(): [Add the WUP to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(workshop.getComponentId(), wup);
        getLogger().trace(".buildWUP(): [Add the WUP to the Topology Cache] Finish");

        return(wup);
    }

    /**
     *
     * @param name
     * @param topologyType
     * @param wup
     */
    @Override
    public WorkUnitProcessorSubComponentSoftwareComponent buildWUPComponent(String name, SoftwareComponentTypeEnum topologyType, WorkUnitProcessorSoftwareComponent wup){
        getLogger().debug(".buildWUPComponent(): Entry");
        WorkUnitProcessorSubComponentSoftwareComponent wupComponent = new WorkUnitProcessorSubComponentSoftwareComponent();

        getLogger().trace(".buildWUPComponent(): [Set Parent] Start");
        wupComponent.setParentComponent(wup.getComponentId());
        getLogger().trace(".buildWUPComponent(): [Set Parent] Finish");

        getLogger().trace(".buildWUPComponent(): [Create ComponentId] Start");
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        wupComponent.setComponentID(componentId);
        getLogger().trace(".buildWUPComponent(): [Create ComponentId] Finish");

        getLogger().trace(".buildWUPComponent(): [Create ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        participantId.setName(wup.getParticipant().getParticipantId().getName() + "." + name);
        participantId.setDisplayName(wup.getParticipant().getParticipantId().getDisplayName() + "." + name);
        participantId.setFullName(wup.getParticipant().getParticipantId().getFullName() + "." + name);
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        participantId.setVersion(wup.getParticipant().getParticipantId().getVersion());
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        wupComponent.setParticipant(participant);
        getLogger().trace(".buildWUPComponent(): [Create ParticipantId] Finish");

        getLogger().trace(".buildWUP(): [Set Resilience Mode] Start");
        wupComponent.setResilienceMode(getResilienceMode());
        getLogger().trace(".buildWUP(): [Set Resilience Mode] Finish");

        getLogger().trace(".buildWUP(): [Set Concurrency Mode] Start");
        wupComponent.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".buildWUP(): [Set Concurrency Mode] Finish");

        getLogger().trace(".buildWUP(): [Set component type] Start");
        wupComponent.setComponentType(topologyType);
        getLogger().trace(".buildWUP(): [Set component type] Finish");

        getLogger().trace(".buildWUP(): [Set component site] Start");
        wupComponent.setDeploymentSite(getPropertyFile().getSubsystemInstant().getSite());
        getLogger().trace(".buildWUP(): [Set component site] Finish");

        getLogger().trace(".buildWUP(): [Set Network Zone] Start");
        wupComponent.setSecurityZone(wup.getSecurityZone());
        getLogger().trace(".buildWUP(): [Set Network Zone] Finish");

        getLogger().trace(".buildWUP(): [Add the WUPComponent to WUP] Start");
        wup.getWupComponents().add(wupComponent.getComponentId());
        getLogger().trace(".buildWUP(): [Add the WUPComponent to WUP] Finish");

        getLogger().trace(".buildWUP(): [Add the wupComponent to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(wup.getComponentId(), wupComponent);
        getLogger().trace(".buildWUP(): [Add the wupComponent to the Topology Cache] Finish");

        getLogger().debug(".addWorkUnitProcessorComponent(): Exit");
        return(wupComponent);
    }

    /**
     *
     * @param name
     * @param topologyNodeType
     * @param wup
     */
    @Override
    public WorkUnitProcessorInterchangeSoftwareComponent buildWUPInterchange(String name, SoftwareComponentTypeEnum topologyNodeType, WorkUnitProcessorSoftwareComponent wup){
        getLogger().debug(".buildWUPInterchange(): Entry");

        WorkUnitProcessorInterchangeSoftwareComponent wupInterchangeComponent = new WorkUnitProcessorInterchangeSoftwareComponent();

        getLogger().trace(".buildWUPComponent(): [Set Parent] Start");
        wupInterchangeComponent.setParentComponent(wup.getComponentId());
        getLogger().trace(".buildWUPComponent(): [Set Parent] Finish");

        getLogger().trace(".buildWUPComponent(): [Create ComponentId] Start");
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        wupInterchangeComponent.setComponentID(componentId);
        getLogger().trace(".buildWUPComponent(): [Create ComponentId] Finish");

        getLogger().trace(".buildWUPComponent(): [Create ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        participantId.setName(wup.getParticipant().getParticipantId().getName() + "." + name);
        participantId.setDisplayName(wup.getParticipant().getParticipantId().getDisplayName() + "." + name);
        participantId.setFullName(wup.getParticipant().getParticipantId().getFullName() + "." + name);
        participantId.setSubsystemName(getPropertyFile().getSubsystemInstant().getParticipantName());
        participantId.setVersion(wup.getParticipant().getParticipantId().getVersion());
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        wupInterchangeComponent.setParticipant(participant);
        getLogger().trace(".buildWUPComponent(): [Create ParticipantId] Finish");

        getLogger().trace(".buildWUP(): [Set Resilience Mode] Start");
        wupInterchangeComponent.setResilienceMode(getResilienceMode());
        getLogger().trace(".buildWUP(): [Set Resilience Mode] Finish");

        getLogger().trace(".buildWUP(): [Set Concurrency Mode] Start");
        wupInterchangeComponent.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".buildWUP(): [Set Concurrency Mode] Finish");

        getLogger().trace(".buildWUP(): [Set component type] Start");
        wupInterchangeComponent.setComponentType(topologyNodeType);
        getLogger().trace(".buildWUP(): [Set component type] Finish");

        getLogger().trace(".buildWUP(): [Set component site] Start");
        wupInterchangeComponent.setDeploymentSite(getPropertyFile().getSubsystemInstant().getSite());
        getLogger().trace(".buildWUP(): [Set component site] Finish");

        getLogger().trace(".buildWUP(): [Set Network Zone] Start");
        wupInterchangeComponent.setSecurityZone(wup.getSecurityZone());
        getLogger().trace(".buildWUP(): [Set Network Zone] Finish");

        getLogger().trace(".buildWUP(): [Add the WUPComponent to WUP] Start");
        wup.getWupInterchangeComponents().add(wupInterchangeComponent.getComponentId());
        getLogger().trace(".buildWUP(): [Add the WUPComponent to WUP] Finish");

        getLogger().trace(".buildWUP(): [Add the wupComponent to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(wup.getComponentId(), wupInterchangeComponent);
        getLogger().trace(".buildWUP(): [Add the wupComponent to the Topology Cache] Finish");

        getLogger().debug(".buildWUPInterchange(): Exit");
        return(wupInterchangeComponent);
    }


    /**
     *
     * @return
     */

    @Override
    public Boolean getSubsystemInternalTrafficEncrypt(){
        getLogger().debug(".getSubsystemInternalTrafficEncrypt(): Entry");
        Boolean encryptTraffic = getPropertyFile().getDeploymentMode().isUsingInternalEncryption();
        getLogger().debug(".getSubsystemInternalTrafficEncrypt(): Exit, Encrypt Internal Traffic? --> {}", encryptTraffic);
        return(encryptTraffic);
    }



    //
    // Build Prometheus Port (if there)
    //

    protected void addPrometheusPort( ProcessingPlantSoftwareComponent processingPlant){
        getLogger().debug(".addPrometheusPort(): Entry, processingPlant->{}", processingPlant);
        HTTPServerTopologyEndpoint prometheusPort = new HTTPServerTopologyEndpoint();
        HTTPServerPortSegment port = getPropertyFile().getPrometheusPort();
        if(port == null){
            getLogger().debug(".addPrometheusPort(): Exit, no port to add");
            return;
        }

        addWildflyOAMPort(processingPlant, interfaceNames.getPrometheusEndpointName(), port);

        getLogger().debug(".addPrometheusPort(): Exit, endpoint added");
    }

    //
    // Build Jolokia Port (if there)
    //

    protected void addJolokiaPort(ProcessingPlantSoftwareComponent processingPlant){
        getLogger().debug(".addJolokiaPort(): Entry, processingPlant->{}", processingPlant);
        HTTPServerTopologyEndpoint jolokiaPort = new HTTPServerTopologyEndpoint();
        HTTPServerPortSegment port = getPropertyFile().getJolokiaPort();
        if(port == null){
            getLogger().debug(".addJolokiaPort(): Exit, no port to add");
            return;
        }

        addWildflyOAMPort(processingPlant, interfaceNames.getJolokiaEndpointName(), port);

        getLogger().debug(".addJolokiaPort(): Exit, endpoint added");
    }

    //
    // Build KubeLiveliness Port (if there)
    //

    protected void addKubeLivelinessPort(ProcessingPlantSoftwareComponent processingPlant){
        getLogger().debug(".addKubeLivelinessPort(): Entry, processingPlant->{}", processingPlant);
        HTTPServerTopologyEndpoint kubeLivelinessPort = new HTTPServerTopologyEndpoint();
        HTTPServerPortSegment port = getPropertyFile().getKubeLivelinessProbe();
        if(port == null){
            getLogger().debug(".addKubeLivelinessPort(): Exit, no port to add");
            return;
        }
        addWildflyOAMPort(processingPlant, interfaceNames.getKubeLivelinessEndpointName(), port);
    }

    //
    // Build KubeReadiness Port (if there)
    //

    protected void addKubeReadinessPort( ProcessingPlantSoftwareComponent processingPlant){
        getLogger().debug(".addKubeReadinessPort(): Entry, processingPlant->{}", processingPlant);
        HTTPServerTopologyEndpoint kubeReadinessPort = new HTTPServerTopologyEndpoint();
        HTTPServerPortSegment port = getPropertyFile().getKubeReadinessProbe();
        if(port == null){
            getLogger().debug(".addKubeReadinessPort(): Exit, no port to add");
            return;
        }

        addWildflyOAMPort(processingPlant, interfaceNames.getKubeReadinessEndpointName(), port);

        getLogger().debug(".addKubeReadinessPort(): Exit, endpoint added");
    }

    protected void addWildflyOAMPort(ProcessingPlantSoftwareComponent processingPlant, String interfaceName, HTTPServerPortSegment port){
        getLogger().debug(".addWildflyOAMPort(): Entry, processingPlant->{}", processingPlant);
        HTTPServerTopologyEndpoint oamPort = new HTTPServerTopologyEndpoint();
        if(port == null){
            getLogger().debug(".addWildflyOAMPort(): Exit, no port to add");
            return;
        }

        String name = interfaceNames.getEndpointServerName(interfaceName);
        String uniqueName = componentNameingUtilities.buildUniqueComponentName(name);

        getLogger().trace(".addWildflyOAMPort(): [Set Parent] Start");
        oamPort.setParentComponent(processingPlant.getComponentId());
        getLogger().trace(".addWildflyOAMPort(): [Set Parent] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Create ComponentId] Start");
        ComponentIdType componentId = new ComponentIdType();
        componentId.setName(uniqueName);
        componentId.setDisplayName(name);
        componentId.setIdValidityEndInstant(ZonedDateTime.now().plusYears(ID_VALIDITY_RANGE_IN_YEARS).toInstant());
        componentId.setIdValidityStartInstant(Instant.now());
        componentId.setId(uniqueName);
        oamPort.setComponentID(componentId);
        getLogger().trace(".addWildflyOAMPort(): [Create ComponentId] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Create ParticipantId] Start");
        PetasosParticipantId participantId = new PetasosParticipantId();
        String participantName = processingPlant.getParticipant() + interfaceName;
        participantId.setName(participantName);
        participantId.setDisplayName(getProcessingPlantNode().getParticipant().getParticipantId().getDisplayName() +"." + participantName);
        participantId.setFullName(processingPlant.getParticipant().getParticipantId().getFullName() + "." + interfaceName);
        participantId.setSubsystemName(processingPlant.getParticipant().getParticipantId().getSubsystemName());
        participantId.setVersion(processingPlant.getParticipant().getParticipantId().getVersion());
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        oamPort.setParticipant(participant);
        getLogger().trace(".addWildflyOAMPort(): [Create ParticipantId] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Set Endpoint Type] Start");
        oamPort.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        getLogger().trace(".addWildflyOAMPort(): [Set Endpoint Type] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Set Component Type] Start");
        oamPort.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        getLogger().trace(".addWildflyOAMPort(): [Set Component Type] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Set Endpoint Configuration Name] Start");
        oamPort.setEndpointConfigurationName(interfaceName);
        getLogger().trace(".addWildflyOAMPort(): [Set Endpoint Configuration Name] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Set Server Status] Start");
        oamPort.setServer(true);
        getLogger().trace(".addWildflyOAMPort(): [Set Server Status] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Set Adapter] Start");
        HTTPServerAdapter httpServerAdapter = new HTTPServerAdapter();
        httpServerAdapter.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        httpServerAdapter.setPortNumber(port.getServerPort());
        httpServerAdapter.setContextPath(port.getContextPath());
        httpServerAdapter.setHostName(port.getServerHostname());
        oamPort.setHTTPServerAdapter(httpServerAdapter);
        getLogger().trace(".addWildflyOAMPort(): [Set Adapter] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Add the OAM Port to the Processing Plant] Start");
        processingPlant.getEndpoints().add(oamPort.getComponentId());
        getLogger().trace(".addWildflyOAMPort(): [Add the OAM Port to the Processing Plant] Finish");

        getLogger().trace(".addWildflyOAMPort(): [Add the OAM Port to the Topology Cache] Start");
        getTopologyIM().addTopologyNode(processingPlant.getComponentId(), oamPort);
        getLogger().trace(".addWildflyOAMPort(): [Add the OAM Port to the Topology Cache] Finish");

        getLogger().debug(".addWildflyOAMPort(): Exit, endpoint added");
    }



    //
    // Resilience Mode Calculation
    //
    public ResilienceModeEnum getResilienceMode() {
        getLogger().debug(".getResilienceMode(): Entry");
        boolean clustered = false;
        boolean kubernetes = false;
        boolean multisite = false;

        if (getPropertyFile().getDeploymentSites().getSiteCount() < 2) {
            multisite = false;
        }

        if (getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount() < 2) {
            clustered = true;
        }

        if (getPropertyFile().getDeploymentMode().isKubernetes()) {
            kubernetes = true;
        }

        if (!clustered && !kubernetes && !multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
            return (ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        }
        if (!clustered && kubernetes && !multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
            return (ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        }
        if (clustered && !kubernetes && !multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
            return (ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        }
        if (clustered && kubernetes && !multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED );
            return (ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        }
        if (!clustered && !kubernetes && multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
            return (ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        }
        if (clustered && !kubernetes && multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
            return (ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        }
        if (!clustered && kubernetes && multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
            return (ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        }
        if (clustered && kubernetes && multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
            return (ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        }
        getLogger().debug(".getResilienceMode(): Exit, returning->{}", ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        return (ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
    }

    //
    // Concurrency Mode Calculation
    //

    public ConcurrencyModeEnum getConcurrenceMode() {
        getLogger().debug(".getConcurrenceMode(): Entry");
        boolean standalone = false;
        boolean concurrent = false;

        if (getResilienceMode().equals(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE)) {
            standalone = true;
        }
        if (getPropertyFile().getDeploymentMode().isConcurrent()) {
            concurrent = true;
        }
        if (!standalone && !concurrent) {
            getLogger().debug(".getConcurrenceMode(): Exit, returning->{}", ConcurrencyModeEnum.CONCURRENCY_MODE_ONDEMAND);
            return (ConcurrencyModeEnum.CONCURRENCY_MODE_ONDEMAND);
        }
        if (!standalone && concurrent) {
            getLogger().debug(".getConcurrenceMode(): Exit, returning->{}", ConcurrencyModeEnum.CONCURRENCY_MODE_CONCURRENT);
            return (ConcurrencyModeEnum.CONCURRENCY_MODE_CONCURRENT);
        }
        getLogger().debug(".getConcurrenceMode(): Exit, returning->{}", ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        return (ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
    }


    protected BaseSubsystemPropertyFile readPropertyFile(){
        String propertyFileName = specifyPropertyFileName();
        Class propertyFileClass = specifyPropertyFileClass();
        getLogger().warn(".readPropertyFile(): Entry, propertyFileName->{}", propertyFileName);
        try {
            getLogger().trace(".readPropertyFile(): Establish YAML ObjectMapper");
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            getLogger().warn(".readPropertyFile(): [Openning Configuration File] Start");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//            InputStream propertyYAMLFile = classLoader.getResourceAsStream("./"+propertyFileName);
            Path path = Paths.get(propertyFileName);
            File file = path.toFile();
            getLogger().warn(".readPropertyFile(): [Openning Configuration File] End");
            getLogger().warn(".readPropertyFile(): [Importing Configuration File] Start");
            this.propertyFile = (BaseSubsystemPropertyFile) yamlMapper.readValue(file, propertyFileClass);
            getLogger().warn(".readPropertyFile(): [Read YAML Configuration File] Finish");
            getLogger().debug(".loadPropertyFile(): Exit, file loaded, propertyFile->{}", this.propertyFile);
            return(this.propertyFile);
        } catch(FileNotFoundException noFile){
            getLogger().error(".loadPropertyFile(): Configuration File->{} is not found, error->{}", propertyFileName, noFile.getMessage());
        } catch(IOException ioError){
            getLogger().error(".loadPropertyFile(): Configuration File->{} could not be loaded, error->{}", propertyFileName, ioError.getMessage());
        }
        getLogger().debug(".loadPropertyFile(): failed to load file");
        return(null);
    }

    public ProcessingPlantSoftwareComponent getProcessingPlantNode() {
        return processingPlantNode;
    }

    public void setProcessingPlantNode(ProcessingPlantSoftwareComponent processingPlantNode) {
        this.processingPlantNode = processingPlantNode;
    }

    public void populateOtherDeploymentProperties(ProcessingPlantSoftwareComponent node, List<ParameterNameValuePairType> otherDeploymentPropertiesMap){
        ConcurrentHashMap<String, String> propertiesMap = new ConcurrentHashMap<>();
        if(otherDeploymentPropertiesMap.isEmpty()){
            node.setOtherConfigurationParameters(propertiesMap);
            return;
        }
        for(ParameterNameValuePairType currentNameValuePair: otherDeploymentPropertiesMap){
            propertiesMap.put(currentNameValuePair.getParameterName(), currentNameValuePair.getParameterValue());
        }
        node.setOtherConfigurationParameters(propertiesMap);
    }


}
