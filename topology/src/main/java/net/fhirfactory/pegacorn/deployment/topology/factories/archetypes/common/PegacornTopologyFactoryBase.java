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
package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.fhirfactory.pegacorn.core.interfaces.topology.PegacornTopologyFactoryInterface;
import net.fhirfactory.pegacorn.core.model.petasos.ipc.PegacornCommonInterfaceNames;
import net.fhirfactory.pegacorn.core.model.topology.nodes.*;
import net.fhirfactory.pegacorn.core.model.componentid.ComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeFunctionFDN;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.deployment.names.sites.SiteKeyNames;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.standard.HTTPProcessingPlantServerPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.interfaces.SolutionNodeFactoryInterface;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.core.model.topology.mode.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.petasos.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.technologies.HTTPProcessingPlantTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PegacornTopologyFactoryBase implements PegacornTopologyFactoryInterface {

    private static final String PROPERTY_FILENAME_EXTENSION = ".yaml";

    private BaseSubsystemPropertyFile propertyFile;
    private ProcessingPlantTopologyNode processingPlantNode;
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
    abstract protected ProcessingPlantTopologyNode buildSubsystemTopology();

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


    @Override
    public TopologyNodeRDN createNodeRDN(String nodeName, String nodeVersion, ComponentTypeTypeEnum nodeType){
        getLogger().debug(".createNodeRDN: Entry, nodeName->{}, nodeVersion->{}, nodeType->{}", nodeName, nodeVersion, nodeType);
        TopologyNodeRDN newRDN = new TopologyNodeRDN(nodeType, nodeName, nodeVersion);
        getLogger().debug(".createNodeRDN: Exit, newRDN->{}", newRDN);
        return (newRDN);
    }

    /**
     * Subsystem Node Builder
     *
     * @param solution
     * @return
     */

    public SubsystemTopologyNode addSubsystemNode(SolutionTopologyNode solution){
        getLogger().debug(".addSubsystemNode(): Entry, solution->{}", solution);
        SubsystemTopologyNode subsystem = new SubsystemTopologyNode();
        getLogger().trace(".addSubsystemNode(): Create the Subsystem RDN (createNodeRDN()) --> Start");
        TopologyNodeRDN nodeRDN = createNodeRDN(getPropertyFile().getSubsystemInstant().getSubsystemName(), getPropertyFile().getSubsystemInstant().getSubsystemVersion(), ComponentTypeTypeEnum.SUBSYSTEM);
        getLogger().trace(".addSubsystemNode(): Create the Subsystem RDN (createNodeRDN()) --> Finish, nodeRDN->{}", nodeRDN);
        getLogger().trace(".addSubsystemNode(): Create the Subsystem FDN (constructFDN()) --> Start");
        subsystem.constructFDN(solution.getComponentFDN(), nodeRDN);
        getLogger().trace(".addSubsystemNode(): Create the Subsystem FDN (constructFDN()) --> Finish, nodeFDN->{}", subsystem.getComponentFDN());
        getLogger().trace(".addSubsystemNode(): Create the Subsystem Function FDN (constructFunctionFDN()) --> Start");
        subsystem.constructFunctionFDN(solution.getNodeFunctionFDN(), nodeRDN);
        getLogger().trace(".addSubsystemNode(): Create the Subsystem FDN (constructFunctionFDN()) --> Finish, nodeFunctionFDN->{}", subsystem.getNodeFunctionFDN());
        subsystem.setComponentRDN(nodeRDN);
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Concurrency Mode");
        subsystem.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Resilience Mode");
        subsystem.setResilienceMode(getResilienceMode());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Site Count");
        subsystem.setSiteCount(getPropertyFile().getDeploymentSites().getSiteCount());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem's Parent Solution");
        subsystem.setContainingNodeFDN(solution.getComponentFDN());
        getLogger().trace(".addSubsystemNode(): Add the subsystem to the Solution subsystem list");
        solution.getSubsystemList().add(subsystem.getComponentFDN());
        subsystem.setComponentType(ComponentTypeTypeEnum.SUBSYSTEM);
        getLogger().trace(".addSubsystemNode(): Add the subsystem to the Topology Cache");
        getTopologyIM().addTopologyNode(solution.getComponentFDN(), subsystem);
        getLogger().debug(".addSubsystemNode(): Exit");
        return(subsystem);
    }

    /**
     * Business Service Node Set Builder
     *
     * @param subsystem
     * @return
     */

    public BusinessServiceTopologyNode addBusinessServiceNode( SubsystemTopologyNode subsystem){
        getLogger().debug(".addBusinessServiceNode(): Entry");
        BusinessServiceTopologyNode businessService = new BusinessServiceTopologyNode();
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service RDN (createNodeRDN()) --> Start");
        TopologyNodeRDN nodeRDN = createNodeRDN(getPropertyFile().getSubsystemInstant().getExternalisedServiceName(), getPropertyFile().getSubsystemInstant().getSubsystemVersion(), ComponentTypeTypeEnum.EXTERNALISED_SERVICE);
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service RDN (createNodeRDN()) --> Finish, nodeRDN->{}", nodeRDN);
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service FDN (constructFDN()) --> Start");
        businessService.constructFDN(subsystem.getComponentFDN(),nodeRDN);
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service FDN (constructFDN()) --> Finish, nodeFDN->{}", businessService.getComponentFDN());
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service Function FDN (constructFunctionFDN()) --> Start");
        businessService.constructFunctionFDN(subsystem.getNodeFunctionFDN(),nodeRDN);
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service FDN (constructFunctionFDN()) --> Finish, nodeFunctionFDN->{}", businessService.getNodeFunctionFDN());
        businessService.setComponentRDN(nodeRDN);
        businessService.setComponentType(ComponentTypeTypeEnum.EXTERNALISED_SERVICE);
        businessService.setContainingNodeFDN(subsystem.getComponentFDN());
        subsystem.getBusinessServices().add(businessService.getComponentFDN());
        getLogger().trace(".addBusinessServiceNode(): Add the BusinessService to the Topology Cache");
        getTopologyIM().addTopologyNode(subsystem.getComponentFDN(), businessService);
        getLogger().debug(".addBusinessServiceNode(): Exit");
        return(businessService);
    }

    /**
     * Deployment Site Node Builder
     *
     * @param businessService
     * @return
     */
    public DeploymentSiteTopologyNode addDeploymentSiteNode(BusinessServiceTopologyNode businessService){
        getLogger().debug(".addDeploymentSiteNode(): Entry, businessService->{}", businessService);
        DeploymentSiteTopologyNode site = new DeploymentSiteTopologyNode();
        String siteName = getPropertyFile().getDeploymentSites().getSite1Name();
        TopologyNodeFDN businessServiceFDN = businessService.getComponentFDN();
        TopologyNodeFunctionFDN businessServiceFunctionFDN = businessService.getNodeFunctionFDN();
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site RDN (createNodeRDN()) --> Start");
        TopologyNodeRDN nodeRDN = createNodeRDN(getPropertyFile().getDeploymentSites().getSite1Name(), getPropertyFile().getSubsystemInstant().getSubsystemVersion(), ComponentTypeTypeEnum.SITE);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site RDN (createNodeRDN()) --> Finish, nodeRDN->{}", nodeRDN);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site FDN (constructFDN()) --> Start, businessServiceFDN->{}, nodeRDN->{}", businessServiceFDN, nodeRDN);
        site.constructFDN(businessServiceFDN, nodeRDN);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site FDN (constructFDN()) --> Finish, nodeFDN->{}", businessServiceFDN);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site Function FDN (constructFunctionFDN()) --> Start, businessServiceFunctionFDN->{}, nodeRDN->{}", businessServiceFunctionFDN, nodeRDN);
        site.constructFunctionFDN(businessServiceFunctionFDN, nodeRDN);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site FDN (constructFunctionFDN()) --> Finish, nodeFunctionFDN->{}", businessServiceFunctionFDN);
        site.setComponentRDN(nodeRDN);
        site.setComponentType(ComponentTypeTypeEnum.SITE);
        site.setInstanceCount(getPropertyFile().getDeploymentSites().getSiteCount());
        site.setContainingNodeFDN(businessService.getComponentFDN());
        businessService.getDeploymentSites().add(site.getComponentFDN());
        getLogger().trace(".addDeploymentSiteNode(): Add the DeploymentSite to the Topology Cache");
        getTopologyIM().addTopologyNode(businessService.getComponentFDN(), site);
        getLogger().debug(".addDeploymentSiteNode(): Exit");
        return(site);
    }

    /**
     * Build Cluster Service Node
     *
     * @param site
     * @return
     */
    public ClusterServiceTopologyNode addClusterServiceNode(DeploymentSiteTopologyNode site){
        getLogger().debug(".addClusterServiceNode(): Entry");
        ClusterServiceTopologyNode clusterService = new ClusterServiceTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(getPropertyFile().getSubsystemInstant().getClusterServiceName(), getPropertyFile().getSubsystemInstant().getProcessingPlantVersion(), ComponentTypeTypeEnum.CLUSTER_SERVICE);
        clusterService.constructFDN(site.getComponentFDN(),nodeRDN);
        clusterService.constructFunctionFDN(site.getNodeFunctionFDN(),nodeRDN);
        clusterService.setComponentRDN(nodeRDN);
        clusterService.setComponentType(ComponentTypeTypeEnum.CLUSTER_SERVICE);
        clusterService.setResilienceMode(getResilienceMode());
        clusterService.setConcurrencyMode(getConcurrenceMode());
        clusterService.setDefaultDNSName(getPropertyFile().getSubsystemInstant().getClusterServiceDNSName());
        clusterService.setInternalTrafficEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        clusterService.setContainingNodeFDN(site.getComponentFDN());
        site.getClusterServices().add(clusterService.getComponentFDN());
        getLogger().trace(".addClusterServiceNode(): Add the ClusterService to the Topology Cache");
        getTopologyIM().addTopologyNode(site.getComponentFDN(), clusterService);
        getLogger().debug(".addClusterServiceNode(): Exit");
        return(clusterService);
    }

    /**
     * Platform Node Set Builder
     *
     * @param clusterService
     * @return
     */
    public PlatformTopologyNode addPlatformNode(ClusterServiceTopologyNode clusterService){
        getLogger().debug(".addPlatformNode(): Entry");
        PlatformTopologyNode node = new PlatformTopologyNode();
        String hostName = pegacornProperties.getProperty("MY_POD_NAME", "PlatformNode0");
//      TODO Fix This --> lookup POD Name or DNS Name
        TopologyNodeRDN nodeRDN = createNodeRDN(hostName, getPropertyFile().getSubsystemInstant().getProcessingPlantVersion(), ComponentTypeTypeEnum.PLATFORM);
        node.constructFDN(clusterService.getComponentFDN(), nodeRDN);
        node.setActualHostIP(getActualHostIP());
        node.setActualPodIP(getActualPodIP());
        node.setComponentType(ComponentTypeTypeEnum.PLATFORM);
        node.constructFunctionFDN(clusterService.getNodeFunctionFDN(), nodeRDN);
        node.setComponentRDN(nodeRDN);
        node.setInstanceCount(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        node.setContainingNodeFDN(clusterService.getComponentFDN());
        clusterService.getPlatformNodes().add(node.getComponentFDN());
        getLogger().trace(".addPlatformNode(): Add the PlatformNode to the Topology Cache");
        getTopologyIM().addTopologyNode(clusterService.getComponentFDN(), node);
        getLogger().debug(".addPlatformNode(): Exit");
        return(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public ProcessingPlantTopologyNode addPegacornProcessingPlant( PlatformTopologyNode node){
        getLogger().debug(".addPegacornProcessingPlant(): Entry");
        ProcessingPlantTopologyNode processingPlant = new ProcessingPlantTopologyNode();
        String name = getPropertyFile().getSubsystemInstant().getProcessingPlantName();
        String version = getPropertyFile().getSubsystemInstant().getProcessingPlantVersion();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, version, ComponentTypeTypeEnum.PROCESSING_PLANT);
        processingPlant.setComponentRDN(nodeRDN);
        processingPlant.setActualHostIP(getActualHostIP());
        processingPlant.setActualPodIP(getActualPodIP());
        processingPlant.constructFDN(node.getComponentFDN(), nodeRDN);
        processingPlant.constructFunctionFDN(node.getNodeFunctionFDN(), nodeRDN);
        processingPlant.setResilienceMode(getResilienceMode());
        processingPlant.setConcurrencyMode(getConcurrenceMode());
        processingPlant.setComponentType(ComponentTypeTypeEnum.PROCESSING_PLANT);
        processingPlant.setNameSpace(getPropertyFile().getDeploymentZone().getNameSpace());
        processingPlant.setInterZoneIPCStackConfigFile(getPropertyFile().getDeploymentMode().getInterNetworkIPCStackConfigFile());
        processingPlant.setInterZoneOAMStackConfigFile(getPropertyFile().getDeploymentMode().getInterNetworkOAMStackConfigFile());
        processingPlant.setIntraZoneIPCStackConfigFile(getPropertyFile().getDeploymentMode().getIntraNetworkIPCStackConfigFile());
        processingPlant.setIntraZoneOAMStackConfigFile(getPropertyFile().getDeploymentMode().getIntraNetworkOAMStackConfigFile());
        processingPlant.setDefaultDNSName(getPropertyFile().getSubsystemInstant().getProcessingPlantDNSName());
        processingPlant.setInternalTrafficEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        processingPlant.setInstanceCount(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        processingPlant.setContainingNodeFDN(node.getComponentFDN());
        processingPlant.setSecurityZone(NetworkSecurityZoneEnum.fromSecurityZoneString(getPropertyFile().getDeploymentZone().getSecurityZoneName()));
        node.getProcessingPlants().add(processingPlant.getComponentFDN());
        populateOtherDeploymentProperties(processingPlant, getPropertyFile().getDeploymentMode().getOtherDeploymentFlags());
        getLogger().trace(".addPegacornProcessingPlant(): Add the ProcessingPlant to the Topology Cache");
        getTopologyIM().addTopologyNode(node.getComponentFDN(), processingPlant);
        getLogger().debug(".addPegacornProcessingPlant(): Exit");
        return(processingPlant);
    }

    /**
     *
     * @param name
     * @param version
     * @param processingPlant
     */
    @Override
    public WorkshopTopologyNode createWorkshop(String name, String version, ProcessingPlantTopologyNode processingPlant, ComponentTypeTypeEnum nodeType){
        getLogger().debug(".addWorkshop(): Entry");
        WorkshopTopologyNode workshop = new WorkshopTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, version,nodeType);
        workshop.setComponentRDN(nodeRDN);
        workshop.constructFDN(processingPlant.getComponentFDN(), nodeRDN);
        workshop.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN);
        workshop.setComponentType(nodeType);
        workshop.setResilienceMode(getResilienceMode());
        workshop.setConcurrencyMode(getConcurrenceMode());
        workshop.setContainingNodeFDN(processingPlant.getComponentFDN());
        processingPlant.getWorkshops().add(workshop.getComponentFDN());
        getLogger().trace(".addWorkshop(): Add the Workshop to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getComponentFDN(), workshop);
        getLogger().debug(".addWorkshop(): Exit");
        return(workshop);
    }

    /**
     *
     * @param name
     * @param version
     * @param workshop
     */
    @Override
    public WorkUnitProcessorTopologyNode createWorkUnitProcessor(String name, String version, WorkshopTopologyNode workshop, ComponentTypeTypeEnum nodeType){
        getLogger().debug(".addWorkUnitProcessor(): Entry, name->{}, version->{}", name, version);
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(version)){
            getLogger().error(".createWorkUnitProcessor(): name or version are emtpy!!!!");
        }
        WorkUnitProcessorTopologyNode wup = new WorkUnitProcessorTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, version, nodeType);
        wup.setComponentRDN(nodeRDN);
        wup.constructFDN(workshop.getComponentFDN(), nodeRDN);
        wup.constructFunctionFDN(workshop.getNodeFunctionFDN(), nodeRDN);
        wup.setContainingNodeFDN(workshop.getComponentFDN());
        wup.setComponentType(nodeType);
        wup.setConcurrencyMode(getConcurrenceMode());
        wup.setResilienceMode(getResilienceMode());
        workshop.getWupSet().add(wup.getComponentFDN());
        getLogger().trace(".addWorkUnitProcessor(): Add the WorkUnitProcessor to the Topology Cache");
        getTopologyIM().addTopologyNode(workshop.getComponentFDN(), wup);
        getLogger().debug(".addWorkUnitProcessor(): Exit");
        return(wup);
    }

    /**
     *
     * @param name
     * @param topologyType
     * @param wup
     */
    @Override
    public WorkUnitProcessorComponentTopologyNode createWorkUnitProcessorComponent(String name, ComponentTypeTypeEnum topologyType, WorkUnitProcessorTopologyNode wup){
        getLogger().debug(".addWorkUnitProcessorComponent(): Entry");
        WorkUnitProcessorComponentTopologyNode wupComponent = new WorkUnitProcessorComponentTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, wup.getComponentRDN().getNodeVersion(), topologyType);
        wupComponent.constructFDN(wup.getComponentFDN(), nodeRDN);
        wupComponent.constructFunctionFDN(wup.getNodeFunctionFDN(), nodeRDN);
        wupComponent.setComponentRDN(nodeRDN);
        wupComponent.setComponentType(topologyType);
        wupComponent.setContainingNodeFDN(wup.getComponentFDN());
        wupComponent.setConcurrencyMode(getConcurrenceMode());
        wupComponent.setResilienceMode(getResilienceMode());
        wup.getWupComponents().add(wupComponent.getComponentFDN());
        getLogger().trace(".addWorkUnitProcessorComponent(): Add the WorkUnitProcessor Component to the Topology Cache");
        getTopologyIM().addTopologyNode(wup.getComponentFDN(), wupComponent);
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
    public WorkUnitProcessorInterchangeComponentTopologyNode createWorkUnitProcessingInterchangeComponent(String name, ComponentTypeTypeEnum topologyNodeType, WorkUnitProcessorTopologyNode wup){
        getLogger().debug(".addWorkUnitProcessingInterchangeComponent(): Entry");
        WorkUnitProcessorInterchangeComponentTopologyNode wupInterchangeComponent = new WorkUnitProcessorInterchangeComponentTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, wup.getComponentRDN().getNodeVersion(), topologyNodeType);
        wupInterchangeComponent.constructFDN(wup.getComponentFDN(), nodeRDN);
        wupInterchangeComponent.constructFunctionFDN(wup.getNodeFunctionFDN(), nodeRDN);
        wupInterchangeComponent.setComponentRDN(nodeRDN);
        wupInterchangeComponent.setComponentType(topologyNodeType);
        wupInterchangeComponent.setContainingNodeFDN(wup.getComponentFDN());
        wupInterchangeComponent.setConcurrencyMode(getConcurrenceMode());
        wupInterchangeComponent.setResilienceMode(getResilienceMode());
        wup.getWupInterchangeComponents().add(wupInterchangeComponent.getComponentFDN());
        getLogger().trace(".addWorkUnitProcessorComponent(): Add the WorkUnitProcessor Interchange Component to the Topology Cache");
        getTopologyIM().addTopologyNode(wup.getComponentFDN(), wupInterchangeComponent);
        getLogger().debug(".addWorkUnitProcessingInterchangeComponent(): Exit");
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

    protected void addPrometheusPort( ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addPrometheusPort(): Entry, processingPlant->{}", processingPlant);
        HTTPProcessingPlantTopologyEndpointPort prometheusPort = new HTTPProcessingPlantTopologyEndpointPort();
        HTTPProcessingPlantServerPortSegment port = getPropertyFile().getPrometheusPort();
        if(port == null){
            getLogger().debug(".addPrometheusPort(): Exit, no port to add");
            return;
        }
        prometheusPort.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        String name = interfaceNames.getEndpointServerName(interfaceNames.getFunctionNamePrometheus());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        prometheusPort.setComponentRDN(nodeRDN);
        prometheusPort.setName(interfaceNames.getFunctionNamePrometheus());
        prometheusPort.constructFDN(processingPlant.getComponentFDN(), nodeRDN);
        prometheusPort.setPortType(port.getPortType());
        prometheusPort.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        prometheusPort.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        prometheusPort.setPortValue(port.getPortValue());
        prometheusPort.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN );
        prometheusPort.setBasePath(port.getWebServicePath());
        prometheusPort.setaServer(true);
        prometheusPort.setContainingNodeFDN(processingPlant.getComponentFDN());
        processingPlant.getEndpoints().add(prometheusPort.getComponentFDN());
        getLogger().trace(".addPrometheusPort(): Add the Prometheus Port to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getComponentFDN(), prometheusPort);
        getLogger().debug(".addPrometheusPort(): Exit, endpoint added");
    }

    //
    // Build Jolokia Port (if there)
    //

    protected void addJolokiaPort(ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addJolokiaPort(): Entry, processingPlant->{}", processingPlant);
        HTTPProcessingPlantTopologyEndpointPort jolokiaPort = new HTTPProcessingPlantTopologyEndpointPort();
        HTTPProcessingPlantServerPortSegment port = getPropertyFile().getJolokiaPort();
        if(port == null){
            getLogger().debug(".addJolokiaPort(): Exit, no port to add");
            return;
        }
        jolokiaPort.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        String name = interfaceNames.getEndpointServerName(interfaceNames.getFunctionNameJolokia());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        jolokiaPort.setComponentRDN(nodeRDN);
        jolokiaPort.setName(interfaceNames.getFunctionNameJolokia());
        jolokiaPort.constructFDN(processingPlant.getComponentFDN(), nodeRDN);
        jolokiaPort.setPortType(port.getPortType());
        jolokiaPort.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        jolokiaPort.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        jolokiaPort.setPortValue(port.getPortValue());
        jolokiaPort.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN );
        jolokiaPort.setBasePath(port.getWebServicePath());
        jolokiaPort.setaServer(true);
        jolokiaPort.setContainingNodeFDN(processingPlant.getComponentFDN());
        processingPlant.getEndpoints().add(jolokiaPort.getComponentFDN());
        getLogger().trace(".addJolokiaPort(): Add the Jolokia Port to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getComponentFDN(), jolokiaPort);
        getLogger().debug(".addJolokiaPort(): Exit, endpoint added");
    }

    //
    // Build KubeLiveliness Port (if there)
    //

    protected void addKubeLivelinessPort(ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addKubeLivelinessPort(): Entry, processingPlant->{}", processingPlant);
        HTTPProcessingPlantTopologyEndpointPort kubeLivelinessPort = new HTTPProcessingPlantTopologyEndpointPort();
        HTTPProcessingPlantServerPortSegment port = getPropertyFile().getKubeLivelinessProbe();
        if(port == null){
            getLogger().debug(".addKubeLivelinessPort(): Exit, no port to add");
            return;
        }
        kubeLivelinessPort.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        String name = interfaceNames.getEndpointServerName(interfaceNames.getFunctionNameKubeLiveliness());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        kubeLivelinessPort.setComponentRDN(nodeRDN);
        kubeLivelinessPort.setName(interfaceNames.getFunctionNameKubeLiveliness());
        kubeLivelinessPort.constructFDN(processingPlant.getComponentFDN(), nodeRDN);
        kubeLivelinessPort.setPortType(port.getPortType());
        kubeLivelinessPort.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        kubeLivelinessPort.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        kubeLivelinessPort.setPortValue(port.getPortValue());
        kubeLivelinessPort.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN );
        kubeLivelinessPort.setBasePath(port.getWebServicePath());
        kubeLivelinessPort.setaServer(true);
        kubeLivelinessPort.setContainingNodeFDN(processingPlant.getComponentFDN());
        processingPlant.getEndpoints().add(kubeLivelinessPort.getComponentFDN());
        getLogger().trace(".addKubeLivelinessPort(): Add the KubeLivelinessPort Port to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getComponentFDN(), kubeLivelinessPort);
        getLogger().debug(".addKubeLivelinessPort(): Exit, endpoint added");
    }

    //
    // Build KubeReadiness Port (if there)
    //

    protected void addKubeReadinessPort( ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addKubeReadinessPort(): Entry, processingPlant->{}", processingPlant);
        HTTPProcessingPlantTopologyEndpointPort kubeReadinessPort = new HTTPProcessingPlantTopologyEndpointPort();
        HTTPProcessingPlantServerPortSegment port = getPropertyFile().getKubeReadinessProbe();
        if(port == null){
            getLogger().debug(".addKubeReadinessPort(): Exit, no port to add");
            return;
        }
        kubeReadinessPort.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        String name = interfaceNames.getEndpointServerName(interfaceNames.getFunctionNameKubeReadiness());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        kubeReadinessPort.setComponentRDN(nodeRDN);
        kubeReadinessPort.setName(interfaceNames.getFunctionNameKubeReadiness());
        kubeReadinessPort.constructFDN(processingPlant.getComponentFDN(), nodeRDN);
        kubeReadinessPort.setPortType(port.getPortType());
        kubeReadinessPort.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        kubeReadinessPort.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        kubeReadinessPort.setPortValue(port.getPortValue());
        kubeReadinessPort.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN );
        kubeReadinessPort.setComponentRDN(nodeRDN);
        kubeReadinessPort.setBasePath(port.getWebServicePath());
        kubeReadinessPort.setaServer(true);
        kubeReadinessPort.setContainingNodeFDN(processingPlant.getComponentFDN());
        processingPlant.getEndpoints().add(kubeReadinessPort.getComponentFDN());
        getLogger().trace(".addKubeReadinessPort(): Add the KubeReadinessPort Port to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getComponentFDN(), kubeReadinessPort);
        getLogger().debug(".addKubeReadinessPort(): Exit, endpoint added");
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
            InputStream propertyYAMLFile = classLoader.getResourceAsStream("./"+propertyFileName);
            getLogger().warn(".readPropertyFile(): [Openning Configuration File] End");
            getLogger().warn(".readPropertyFile(): [Importing Configuration File] Start");
            this.propertyFile = (BaseSubsystemPropertyFile) yamlMapper.readValue(propertyYAMLFile, propertyFileClass);
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



    public ProcessingPlantTopologyNode getProcessingPlantNode() {
        return processingPlantNode;
    }

    public void setProcessingPlantNode(ProcessingPlantTopologyNode processingPlantNode) {
        this.processingPlantNode = processingPlantNode;
    }

    public void populateOtherDeploymentProperties(ProcessingPlantTopologyNode node, String otherDeploymentPropertiesString){
        ConcurrentHashMap<String, String> propertiesMap = new ConcurrentHashMap<>();
        if(StringUtils.isEmpty(otherDeploymentPropertiesString)){
            node.setOtherConfigurationParameters(propertiesMap);
            return;
        }
        String[] properties = otherDeploymentPropertiesString.split(",");
        if(properties.length <= 0){
            node.setOtherConfigurationParameters(propertiesMap);
            return;
        }
        for(int counter = 0; counter < properties.length; counter += 1){
            String currentProperty = properties[counter];
            String[] currentPropertyPair = currentProperty.split("=");
            if(currentPropertyPair.length == 2){
                propertiesMap.put(currentPropertyPair[0], currentPropertyPair[1]);
            }
        }
        node.setOtherConfigurationParameters(propertiesMap);
    }
}
