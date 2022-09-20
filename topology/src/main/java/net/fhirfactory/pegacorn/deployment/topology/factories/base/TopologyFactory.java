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
package net.fhirfactory.pegacorn.deployment.topology.factories.base;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.fhirfactory.dricats.configuration.defaults.dricats.systemwide.DefaultInterfaceNameSet;
import net.fhirfactory.dricats.model.component.datatypes.ComponentTypeDefinition;
import net.fhirfactory.dricats.model.component.valuesets.ComponentTypeEnum;
import net.fhirfactory.dricats.model.configuration.filebased.segments.datatypes.ParameterNameValuePairType;
import net.fhirfactory.dricats.model.topology.nodes.SolutionTopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.physical.DeploymentSiteTN;
import net.fhirfactory.dricats.model.topology.nodes.physical.InfrastructureTN;
import net.fhirfactory.dricats.model.topology.nodes.physical.PlatformTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.processingplants.base.ProcessingPlantTopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.subsystems.SubsystemTopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workshops.base.WorkshopTopologyNodeBase;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.base.WorkUnitProcessorTopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.valuesets.TopologyNodeConcurrencyModeEnum;
import net.fhirfactory.dricats.model.topology.nodes.valuesets.TopologyNodeNetworkSecurityZoneEnum;
import net.fhirfactory.dricats.model.topology.nodes.valuesets.TopologyNodeResilienceModeEnum;
import net.fhirfactory.dricats.util.SystemPropertyHelper;
import net.fhirfactory.pegacorn.deployment.topology.factories.base.base.TopologyFactoryBase;
import net.fhirfactory.pegacorn.deployment.topology.solution.LocalSolution;

public abstract class TopologyFactory extends TopologyFactoryBase {

    private static final String PROPERTY_FILENAME_EXTENSION = ".yaml";

    private ProcessingPlantTopologyNode processingPlantNode;
    private boolean initialised;
 
    @Inject
    private LocalSolution solutionMap;

    @Inject
    private DefaultInterfaceNameSet interfaceNames;

    @Inject
    private SystemPropertyHelper propertyHelper;

    //
    // Default Constructor
    //

    public TopologyFactory(){
        this.initialised = false;
    }

    //
    // Abstract Methods
    //

    abstract protected ProcessingPlantTopologyNode buildSubsystemTopology();

    //
    // PostConstruct function
    //

    @PostConstruct
    public void initialise(){
        getLogger().debug(".initialise(): Entry");
        if(!initialised) {
            getLogger().info(".initialise(): Initalising......");
            getLogger().info(".initialise(): [Property File Loading]: Start");
            readSolutionPropertyFile();
            readSubsystemPropertyFile();
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

    public LocalSolution getSolutionMap() {
        return solutionMap;
    }

    protected DefaultInterfaceNameSet getInterfaceNames(){
        return(interfaceNames);
    }

    /**
     * Subsystem Node Builder
     *
     * @param solution
     * @return
     */

    public SubsystemTopologyNode createSubsystemTopologyNode(SolutionTopologyNode solution){
        getLogger().debug(".addSubsystemNode(): Entry, solution->{}", solution);
        SubsystemTopologyNode subsystem = new SubsystemTopologyNode();
        subsystem.constructNodeID(ComponentTypeEnum.SUBSYSTEM.getDisplayName());
        getLogger().trace(".addSubsystemNode(): Set the ParticipantName --> Start");
        subsystem.setParticipantName(getSubsystemPropertyFile().getSubsystemInstant().getSubsystemParticipantDisplayName());
        subsystem.setParticipantDisplayName(getSubsystemPropertyFile().getSubsystemInstant().getSubsystemParticipantDisplayName());
        getLogger().trace(".addSubsystemNode(): Set the ParticipantName --> Finish, subsystem.getParticipantName()->{}", subsystem.getParticipantName());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Concurrency Mode");
        subsystem.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Resilience Mode");
        subsystem.setResilienceMode(getResilienceMode());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Site Count");
        subsystem.setReplicationCount(getSolutionPropertyFile().getSites().getSiteList().size());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem's Parent Solution");
        subsystem.setParentNode(solution.getNodeId());
        getLogger().trace(".addSubsystemNode(): Add the subsystem to the Solution subsystem list");
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.SUBSYSTEM);
        componentType.setTypeName("Subsytem");
        componentType.setDisplayTypeName("Subsystem-->"+subsystem.getParticipantName());
        subsystem.setNodeType(componentType);
        getLogger().trace(".addSubsystemNode(): Add the subsystem to the Topology Cache");
        getSolutionMap().addTopologyNode(subsystem);
        getLogger().debug(".addSubsystemNode(): Exit");
        return(subsystem);
    }


    /**
     * Deployment Site Node Builder
     *
     * @param solution
     * @return
     */
    public DeploymentSiteTN addDeploymentSiteNode(SolutionTopologyNode solution){
        getLogger().debug(".addDeploymentSiteNode(): Entry, solution->{}", solution);
        DeploymentSiteTN site = new DeploymentSiteTN();
        site.constructNodeID(ComponentTypeEnum.SITE.getDisplayName());
        String siteName = getSubsystemPropertyFile().getDeploymentMode().getSiteName();
        site.setParticipantName(siteName);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.SITE);
        componentType.setTypeName("Site");
        componentType.setDisplayTypeName("Site-->"+site.getParticipantName());
        site.setNodeType(componentType);
        site.setInstanceCount(getSolutionPropertyFile().getSites().getSiteList().size());
        site.setParentNode(solution.getNodeId());
        solution.addSite(site);
        getLogger().trace(".addDeploymentSiteNode(): Add the DeploymentSite to the Topology Cache");
        getSolutionMap().addTopologyNode( site);
        getLogger().debug(".addDeploymentSiteNode(): Exit");
        return(site);
    }
    
    /**
     * Platform Node Set Builder
     *
     * @param site
     * @return
     */
    public InfrastructureTN addInfrastructureNode(DeploymentSiteTN site){
        getLogger().debug(".addInfrastructureNode(): Entry");
        InfrastructureTN node = new InfrastructureTN();
        node.constructNodeID(ComponentTypeEnum.INFRASTRUCTURE_NODE.getDisplayName());
        node.setActualHostIP(getActualHostIP());
        node.setParticipantName(getActualHostIP());
        node.setParticipantDisplayName(getActualHostIP());
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.INFRASTRUCTURE_NODE);
        componentType.setTypeName("InfrastructureNode");
        componentType.setDisplayTypeName("InfrastructureNode-->"+getActualHostIP());
        node.setNodeType(componentType);
        site.addInfrastructureNode(node);
        getLogger().trace(".addInfrastructureNode(): Add the PlatformNode to the Topology Cache");
        getSolutionMap().addTopologyNode( node);
        getLogger().debug(".addInfrastructureNode(): Exit");
        return(node);
    }
    
       /**
     * Platform Node Set Builder
     *
     * @param infrastructureNode
     * @return
     */
    public PlatformTN addPlatformNode(InfrastructureTN infrastructureNode){
        getLogger().debug(".addPlatformNode(): Entry");
        PlatformTN node = new PlatformTN();
        String hostName = propertyHelper.getProperty("MY_POD_NAME", getActualPodIP());
        node.constructNodeID(ComponentTypeEnum.PLATFORM.getDisplayName());
        node.setParticipantName(hostName);
        node.setParticipantDisplayName(hostName);
        node.setActualIP(getActualPodIP());
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.PLATFORM);
        componentType.setTypeName("Platform");
        componentType.setDisplayTypeName("PlatformNode-->"+hostName);
        node.setNodeType(componentType);
        node.setParentNode(infrastructureNode.getNodeId());
        infrastructureNode.addPlatformNode(node);
        getLogger().trace(".addPlatformNode(): Add the PlatformNode to the Topology Cache");
        getSolutionMap().addTopologyNode(node);
        getLogger().debug(".addPlatformNode(): Exit");
        return(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public ProcessingPlantTopologyNode addProcessingPlant(ProcessingPlantTopologyNode processingPlant, String name, SubsystemTopologyNode subsystem, DeploymentSiteTN site, InfrastructureTN infrastructureNode, PlatformTN platform){
        getLogger().debug(".addProcessingPlant(): Entry");
        processingPlant.constructNodeID(ComponentTypeEnum.PROCESSING_PLANT.getDisplayName());
        processingPlant.setParticipantName(getSubsystemPropertyFile().getSubsystemInstant().getSubsystemParticipantDisplayName());
        processingPlant.setParticipantDisplayName(getSubsystemPropertyFile().getSubsystemInstant().getSubsystemParticipantDisplayName());
        processingPlant.setSubsystemParticipantName(getSubsystemPropertyFile().getSubsystemInstant().getSubsystemParticipantDisplayName());
        processingPlant.setNodeVersion(getSubsystemPropertyFile().getSubsystemInstant().getSubsystemVersion());
        processingPlant.setSiteNode(site);
        processingPlant.setInfrastructureNode(infrastructureNode);
        processingPlant.setPlatformNode(platform);
        processingPlant.setActualHostIP(getActualHostIP());
        processingPlant.setActualPodIP(getActualPodIP());
        processingPlant.setResilienceMode(getResilienceMode());
        processingPlant.setConcurrencyMode(getConcurrenceMode());
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.PROCESSING_PLANT);
        componentType.setTypeName("ProcessingPlant");
        componentType.setDisplayTypeName("ProcessingPlant");
        processingPlant.setNodeType(componentType);
        processingPlant.setReplicationCount(getSubsystemPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());

        processingPlant.setReplicationCount(getSubsystemPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        processingPlant.setParentNode(subsystem.getNodeId());
        TopologyNodeNetworkSecurityZoneEnum zone = TopologyNodeNetworkSecurityZoneEnum.fromDisplayName(getSubsystemPropertyFile().getDeploymentZone().getSecurityZoneName());
        if(zone == null){
            getLogger().error(".addPegacornProcessingPlant(): Cannot resolve Network Security Zone for component, provided->{}", getSubsystemPropertyFile().getDeploymentZone().getSecurityZoneName());
            StringBuilder possibleValues = new StringBuilder();
            for(TopologyNodeNetworkSecurityZoneEnum currentValue: TopologyNodeNetworkSecurityZoneEnum.values()){
                possibleValues.append(currentValue.getDisplayName() + " ");
            }
            getLogger().error(".addPegacornProcessingPlant(): Possible Values Are->{}", possibleValues.toString());
        }
        processingPlant.setNetworkZone(zone);
        setProcessingPlantNode(processingPlant);
        subsystem.addProcessingPlant(processingPlant);
        populateOtherDeploymentProperties(processingPlant, getSubsystemPropertyFile().getDeploymentMode().getSubsystemConfigurationParameters());
        getLogger().trace(".addPegacornProcessingPlant(): Add the ProcessingPlant to the Topology Cache");

        getSolutionMap().addTopologyNode(processingPlant);

        //
        // Create Petasos Participant for Processing Plant
//        PetasosParticipant processingPlantPetasosParticipant = new PetasosParticipant(processingPlant);
//        processingPlantPetasosParticipant.setParticipantStatus(PetasosParticipantStatusEnum.PETASOS_PARTICIPANT_STARTING);
//        participantHolder.setMyProcessingPlantPetasosParticipant(processingPlantPetasosParticipant);
        //
        // All done
        getLogger().debug(".addProcessingPlant(): Exit");
        return(processingPlant);
    }

    /**
     *
     * @param name
     * @param version
     * @param processingPlant
     */

    public WorkshopTopologyNodeBase createWorkshop(WorkshopTopologyNodeBase workshop, String name, String version, ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".createWorkshop(): Entry");
        workshop.constructNodeID(ComponentTypeEnum.WORKSHOP.getDisplayName());
        String participantName = getProcessingPlantNode().getParticipantName() + "." + name;
        workshop.setParticipantName(participantName);
        workshop.setParticipantDisplayName(name);
        workshop.setSubsystemParticipantName(processingPlant.getSubsystemParticipantName());
        workshop.setNodeVersion(version);
        workshop.setSiteNode(processingPlant.getSiteNode());
        workshop.setInfrastructureNode(processingPlant.getInfrastructureNode());
        workshop.setPlatformNode(processingPlant.getPlatformNode());
        workshop.setResilienceMode(getResilienceMode());
        workshop.setConcurrencyMode(getConcurrenceMode());
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.WORKSHOP);
        componentType.setTypeName("ProcessingPlant");
        componentType.setDisplayTypeName("ProcessingPlant");
        workshop.setNodeType(componentType);
        workshop.setParentNode(processingPlant.getNodeId());
        workshop.setNetworkZone(processingPlant.getNetworkZone());
        processingPlant.addWorkshop(workshop);
        getLogger().trace(".createWorkshop(): Add the Workshop to the Topology Cache");
        getSolutionMap().addTopologyNode(workshop);
        getLogger().debug(".createWorkshop(): Exit");
        return(workshop);
    }

    /**
     * 
     * @param name
     * @param version
     * @param participantName
     * @param className
     * @param workshop
     * @return 
     */
    public WorkUnitProcessorTopologyNode createWorkUnitProcessor(WorkUnitProcessorTopologyNode wup, String version, String participantName, String className, WorkshopTopologyNodeBase workshop){
        getLogger().debug(".createWorkUnitProcessor(): Entry, version->{}, participantName->{}", version, participantName);
        if(StringUtils.isEmpty(className) || StringUtils.isEmpty(participantName) || StringUtils.isEmpty(version)){
            getLogger().error(".createWorkUnitProcessor(): name or version are emtpy!!!!");
        }
        wup.constructNodeID(ComponentTypeEnum.WUP.getDisplayName());
        wup.setSubsystemParticipantName(workshop.getSubsystemParticipantName());
        wup.setNodeVersion(version);
        wup.setSiteNode(workshop.getSiteNode());
        wup.setInfrastructureNode(workshop.getInfrastructureNode());
        wup.setPlatformNode(workshop.getPlatformNode());
        wup.setResilienceMode(getResilienceMode());
        wup.setConcurrencyMode(getConcurrenceMode());
        ComponentTypeDefinition componentTypeDef = new ComponentTypeDefinition();
        componentTypeDef.setComponentArchetype(ComponentTypeEnum.WUP);
        componentTypeDef.setTypeName(className);
        componentTypeDef.setDisplayTypeName(ComponentTypeEnum.WUP.getDisplayName() + "("+className+")");
        wup.setNodeType(componentTypeDef);
        wup.setParentNode(workshop.getNodeId());
        wup.setNetworkZone(workshop.getNetworkZone());
        wup.setSubsystemParticipantName(workshop.getSubsystemParticipantName());
        wup.setParticipantName(participantName);
        wup.setParticipantDisplayName(participantName);
        wup.setReplicationCount(workshop.getReplicationCount());
        getLogger().trace(".createWorkUnitProcessor(): Add the WorkUnitProcessor to the Topology Cache");
        getSolutionMap().addTopologyNode(wup);
        getLogger().debug(".createWorkUnitProcessor(): Exit");
        return(wup);
    }

    /**
     *
     * @return
     */

    public Boolean getSubsystemInternalTrafficEncrypt(){
        getLogger().debug(".getSubsystemInternalTrafficEncrypt(): Entry");
        Boolean encryptTraffic = getSubsystemPropertyFile().getDeploymentMode().isUsingInternalEncryption();
        getLogger().debug(".getSubsystemInternalTrafficEncrypt(): Exit, Encrypt Internal Traffic? --> {}", encryptTraffic);
        return(encryptTraffic);
    }

    //
    // Resilience Mode Calculation
    //
    public TopologyNodeResilienceModeEnum getResilienceMode() {
        getLogger().debug(".getResilienceMode(): Entry");
        boolean clustered = false;
        boolean kubernetes = false;
        boolean multisite = false;

        if (getSolutionPropertyFile().getSites().getSiteList().size() < 2) {
            multisite = false;
        }

        if (getSubsystemPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount() < 2) {
            clustered = true;
        }

        if (getSubsystemPropertyFile().getDeploymentMode().isKubernetes()) {
            kubernetes = true;
        }

        if (!clustered && !kubernetes && !multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
            return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        }
        if (!clustered && kubernetes && !multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
            return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        }
        if (clustered && !kubernetes && !multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
            return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        }
        if (clustered && kubernetes && !multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED );
            return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        }
        if (!clustered && !kubernetes && multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
            return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        }
        if (clustered && !kubernetes && multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
            return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        }
        if (!clustered && kubernetes && multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
            return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        }
        if (clustered && kubernetes && multisite) {
            getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
            return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        }
        getLogger().debug(".getResilienceMode(): Exit, returning->{}", TopologyNodeResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        return (TopologyNodeResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
    }

    //
    // Concurrency Mode Calculation
    //

    public TopologyNodeConcurrencyModeEnum getConcurrenceMode() {
        getLogger().debug(".getConcurrenceMode(): Entry");
        boolean standalone = false;
        boolean concurrent = false;

        if (getResilienceMode().equals(TopologyNodeResilienceModeEnum.RESILIENCE_MODE_STANDALONE)) {
            standalone = true;
        }
        if (getSubsystemPropertyFile().getDeploymentMode().isConcurrent()) {
            concurrent = true;
        }
        if (!standalone && !concurrent) {
            getLogger().debug(".getConcurrenceMode(): Exit, returning->{}", TopologyNodeConcurrencyModeEnum.CONCURRENCY_MODE_ONDEMAND);
            return (TopologyNodeConcurrencyModeEnum.CONCURRENCY_MODE_ONDEMAND);
        }
        if (!standalone && concurrent) {
            getLogger().debug(".getConcurrenceMode(): Exit, returning->{}", TopologyNodeConcurrencyModeEnum.CONCURRENCY_MODE_CONCURRENT);
            return (TopologyNodeConcurrencyModeEnum.CONCURRENCY_MODE_CONCURRENT);
        }
        getLogger().debug(".getConcurrenceMode(): Exit, returning->{}", TopologyNodeConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        return (TopologyNodeConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
    }


    public ProcessingPlantTopologyNode getProcessingPlantNode() {
        return processingPlantNode;
    }

    public void setProcessingPlantNode(ProcessingPlantTopologyNode processingPlantNode) {
        this.processingPlantNode = processingPlantNode;
    }

    public void populateOtherDeploymentProperties(ProcessingPlantTopologyNode node, List<ParameterNameValuePairType> otherDeploymentPropertiesMap){
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
