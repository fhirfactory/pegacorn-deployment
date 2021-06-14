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
package net.fhirfactory.pegacorn.deployment.topology.factories.common.archetypes.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFunctionFDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.components.interfaces.topology.PegacornTopologyFactoryInterface;
import net.fhirfactory.pegacorn.deployment.names.common.SiteKeyNames;
import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.common.StandardPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.HTTPProcessingPlantTopologyEndpointPort;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.TopologyEndpointTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.*;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class PegacornTopologyFactoryBase implements PegacornTopologyFactoryInterface {

    private static final String PROPERTY_FILENAME_EXTENSION = ".yaml";

    private BaseSubsystemPropertyFile propertyFile;
    private boolean initialised;

    abstract protected SubsystemBaseNames specifySubsystemBaseNames();
    abstract protected String specifyPropertyFileName();
    abstract protected Class specifyPropertyFileClass();

    @Inject
    private SiteKeyNames siteKeyNames;

    @Inject
    private TopologyIM topologyIM;

    protected abstract Logger getLogger();

    protected SiteKeyNames getSiteKeyNames(){return(siteKeyNames);}
    protected SubsystemBaseNames getSubsystemBaseNames(){return(specifySubsystemBaseNames());}

    public PegacornTopologyFactoryBase(){
        this.initialised = false;
    }

    @Override
    public TopologyNodeRDN createNodeRDN(String nodeName, String nodeVersion, TopologyNodeTypeEnum nodeType){
        getLogger().debug(".createNodeRDN: Entry, nodeName->{}, nodeVersion->{}, nodeType->{}", nodeName, nodeVersion, nodeType);
        TopologyNodeRDN newRDN = new TopologyNodeRDN(nodeType, nodeName, nodeVersion);
        getLogger().debug(".createNodeRDN: Exit, newRDN->{}", newRDN);
        return (newRDN);
    }

    @PostConstruct
    public void initialise(){
        if(!initialised) {
            readPropertyFile(specifyPropertyFileName(), specifyPropertyFileClass());
            this.initialised = true;
        }
    }

    public BaseSubsystemPropertyFile getPropertyFile() {
        return (this.propertyFile);
    }

    public TopologyIM getTopologyIM() {
        return topologyIM;
    }

    /**
     * Subsystem Node Builder
     *
     * @param solution
     * @return
     */

    public SubsystemTopologyNode addSubsystemNode(SolutionTopologyNode solution){
        getLogger().debug(".addSubsystemNode(): Entry");
        SubsystemTopologyNode subsystem = new SubsystemTopologyNode();
        getLogger().trace(".addSubsystemNode(): Create the Subsystem RDN (createNodeRDN()) --> Start");
        TopologyNodeRDN nodeRDN = createNodeRDN(getPropertyFile().getSubsystemInstant().getSubsystemName(), getPropertyFile().getSubsystemInstant().getSubsystemVersion(), TopologyNodeTypeEnum.SUBSYSTEM);
        getLogger().trace(".addSubsystemNode(): Create the Subsystem RDN (createNodeRDN()) --> Finish, nodeRDN->{}", nodeRDN);
        getLogger().trace(".addSubsystemNode(): Create the Subsystem FDN (constructFDN()) --> Start");
        subsystem.constructFDN(solution.getNodeFDN(), nodeRDN);
        getLogger().trace(".addSubsystemNode(): Create the Subsystem FDN (constructFDN()) --> Finish, nodeFDN->{}", subsystem.getNodeFDN());
        getLogger().trace(".addSubsystemNode(): Create the Subsystem Function FDN (constructFunctionFDN()) --> Start");
        subsystem.constructFunctionFDN(solution.getNodeFunctionFDN(), nodeRDN);
        getLogger().trace(".addSubsystemNode(): Create the Subsystem FDN (constructFunctionFDN()) --> Finish, nodeFunctionFDN->{}", subsystem.getNodeFunctionFDN());
        subsystem.setNodeRDN(nodeRDN);
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Concurrency Mode");
        subsystem.setConcurrencyMode(getConcurrenceMode());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Resilience Mode");
        subsystem.setResilienceMode(getResilienceMode());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem Site Count");
        subsystem.setSiteCount(getPropertyFile().getDeploymentSites().getSiteCount());
        getLogger().trace(".addSubsystemNode(): Set the Subsystem's Parent Solution");
        subsystem.setContainingNodeFDN(solution.getNodeFDN());
        getLogger().trace(".addSubsystemNode(): Add the subsystem to the Solution subsystem list");
        solution.getSubsystemList().add(subsystem.getNodeFDN());
        subsystem.setComponentType(TopologyNodeTypeEnum.SUBSYSTEM);
        getLogger().trace(".addSubsystemNode(): Add the subsystem to the Topology Cache");
        getTopologyIM().addTopologyNode(solution.getNodeFDN(), subsystem);
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
        TopologyNodeRDN nodeRDN = createNodeRDN(getPropertyFile().getSubsystemInstant().getExternalisedServiceName(), getPropertyFile().getSubsystemInstant().getSubsystemVersion(), TopologyNodeTypeEnum.EXTERNALISED_SERVICE);
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service RDN (createNodeRDN()) --> Finish, nodeRDN->{}", nodeRDN);
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service FDN (constructFDN()) --> Start");
        businessService.constructFDN(subsystem.getNodeFDN(),nodeRDN);
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service FDN (constructFDN()) --> Finish, nodeFDN->{}", businessService.getNodeFDN());
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service Function FDN (constructFunctionFDN()) --> Start");
        businessService.constructFunctionFDN(subsystem.getNodeFunctionFDN(),nodeRDN);
        getLogger().trace(".addBusinessServiceNode(): Create the Business Service FDN (constructFunctionFDN()) --> Finish, nodeFunctionFDN->{}", businessService.getNodeFunctionFDN());
        businessService.setNodeRDN(nodeRDN);
        businessService.setComponentType(TopologyNodeTypeEnum.EXTERNALISED_SERVICE);
        businessService.setContainingNodeFDN(subsystem.getNodeFDN());
        subsystem.getBusinessServices().add(businessService.getNodeFDN());
        getLogger().trace(".addBusinessServiceNode(): Add the BusinessService to the Topology Cache");
        getTopologyIM().addTopologyNode(subsystem.getNodeFDN(), businessService);
        getLogger().debug(".addBusinessServiceNode(): Exit");
        return(businessService);
    }

    /**
     * Deployment Site Node Builder
     *
     * @param businessService
     * @return
     */
    public DeploymentSiteTopologyNode addDeploymentSiteNode( BusinessServiceTopologyNode businessService){
        getLogger().debug(".addDeploymentSiteNode(): Entry, businessService->{}", businessService);
        DeploymentSiteTopologyNode site = new DeploymentSiteTopologyNode();
        String siteName = getPropertyFile().getDeploymentSites().getSite1Name();
        TopologyNodeFDN businessServiceFDN = businessService.getNodeFDN();
        TopologyNodeFunctionFDN businessServiceFunctionFDN = businessService.getNodeFunctionFDN();
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site RDN (createNodeRDN()) --> Start");
        TopologyNodeRDN nodeRDN = createNodeRDN(getPropertyFile().getDeploymentSites().getSite1Name(), getPropertyFile().getSubsystemInstant().getSubsystemVersion(), TopologyNodeTypeEnum.SITE);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site RDN (createNodeRDN()) --> Finish, nodeRDN->{}", nodeRDN);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site FDN (constructFDN()) --> Start, businessServiceFDN->{}, nodeRDN->{}", businessServiceFDN, nodeRDN);
        site.constructFDN(businessServiceFDN, nodeRDN);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site FDN (constructFDN()) --> Finish, nodeFDN->{}", businessServiceFDN);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site Function FDN (constructFunctionFDN()) --> Start, businessServiceFunctionFDN->{}, nodeRDN->{}", businessServiceFunctionFDN, nodeRDN);
        site.constructFunctionFDN(businessServiceFunctionFDN, nodeRDN);
        getLogger().trace(".addDeploymentSiteNode(): Create the Deployment Site FDN (constructFunctionFDN()) --> Finish, nodeFunctionFDN->{}", businessServiceFunctionFDN);
        site.setNodeRDN(nodeRDN);
        site.setComponentType(TopologyNodeTypeEnum.SITE);
        site.setInstanceCount(getPropertyFile().getDeploymentSites().getSiteCount());
        site.setContainingNodeFDN(businessService.getNodeFDN());
        businessService.getDeploymentSites().add(site.getNodeFDN());
        getLogger().trace(".addDeploymentSiteNode(): Add the DeploymentSite to the Topology Cache");
        getTopologyIM().addTopologyNode(businessService.getNodeFDN(), site);
        getLogger().debug(".addDeploymentSiteNode(): Exit");
        return(site);
    }

    /**
     * Build Cluster Service Node
     *
     * @param site
     * @return
     */
    public ClusterServiceTopologyNode addClusterServiceNode( DeploymentSiteTopologyNode site){
        getLogger().debug(".addClusterServiceNode(): Entry");
        ClusterServiceTopologyNode clusterService = new ClusterServiceTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(getPropertyFile().getSubsystemInstant().getClusterServiceName(), getPropertyFile().getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.CLUSTER_SERVICE);
        clusterService.constructFDN(site.getNodeFDN(),nodeRDN);
        clusterService.constructFunctionFDN(site.getNodeFunctionFDN(),nodeRDN);
        clusterService.setNodeRDN(nodeRDN);
        clusterService.setComponentType(TopologyNodeTypeEnum.CLUSTER_SERVICE);
        clusterService.setResilienceMode(getResilienceMode());
        clusterService.setConcurrencyMode(getConcurrenceMode());
        clusterService.setDefaultDNSName(getPropertyFile().getSubsystemInstant().getClusterServiceDNSName());
        clusterService.setInternalTrafficEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        clusterService.setContainingNodeFDN(site.getNodeFDN());
        site.getClusterServices().add(clusterService.getNodeFDN());
        getLogger().trace(".addClusterServiceNode(): Add the ClusterService to the Topology Cache");
        getTopologyIM().addTopologyNode(site.getNodeFDN(), clusterService);
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
        String nodeName = "PlatformNode0"; // TODO Fix This --> lookup node namde for kubernets or do a reverse lookup DNS
        TopologyNodeRDN nodeRDN = createNodeRDN(nodeName, getPropertyFile().getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.PLATFORM);
        node.constructFDN(clusterService.getNodeFDN(), nodeRDN);
        node.setComponentType(TopologyNodeTypeEnum.PLATFORM);
        node.constructFunctionFDN(clusterService.getNodeFunctionFDN(), nodeRDN);
        node.setNodeRDN(nodeRDN);
        node.setInstanceCount(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        node.setContainingNodeFDN(clusterService.getNodeFDN());
        clusterService.getPlatformNodes().add(node.getNodeFDN());
        getLogger().trace(".addPlatformNode(): Add the PlatformNode to the Topology Cache");
        getTopologyIM().addTopologyNode(clusterService.getNodeFDN(), node);
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
        TopologyNodeRDN nodeRDN = createNodeRDN(name, version, TopologyNodeTypeEnum.PROCESSING_PLANT);
        processingPlant.setNodeRDN(nodeRDN);
        processingPlant.constructFDN(node.getNodeFDN(), nodeRDN);
        processingPlant.constructFunctionFDN(node.getNodeFunctionFDN(), nodeRDN);
        processingPlant.setResilienceMode(getResilienceMode());
        processingPlant.setConcurrencyMode(getConcurrenceMode());
        processingPlant.setComponentType(TopologyNodeTypeEnum.PROCESSING_PLANT);
        processingPlant.setDefaultDNSName(getPropertyFile().getSubsystemInstant().getProcessingPlantDNSName());
        processingPlant.setInternalTrafficEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        processingPlant.setInstanceCount(getPropertyFile().getDeploymentMode().getProcessingPlantReplicationCount());
        processingPlant.setContainingNodeFDN(node.getNodeFDN());
        node.getProcessingPlants().add(processingPlant.getNodeFDN());
        getLogger().trace(".addPegacornProcessingPlant(): Add the ProcessingPlant to the Topology Cache");
        getTopologyIM().addTopologyNode(node.getNodeFDN(), processingPlant);
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
    public WorkshopTopologyNode addWorkshop(String name, String version, ProcessingPlantTopologyNode processingPlant, TopologyNodeTypeEnum nodeType){
        getLogger().debug(".addWorkshop(): Entry");
        WorkshopTopologyNode workshop = new WorkshopTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, version,nodeType);
        workshop.setNodeRDN(nodeRDN);
        workshop.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        workshop.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN);
        workshop.setComponentType(nodeType);
        workshop.setContainingNodeFDN(processingPlant.getNodeFDN());
        processingPlant.getWorkshops().add(workshop.getNodeFDN());
        getLogger().trace(".addWorkshop(): Add the Workshop to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getNodeFDN(), workshop);
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
    public WorkUnitProcessorTopologyNode addWorkUnitProcessor(String name, String version, WorkshopTopologyNode workshop, TopologyNodeTypeEnum nodeType){
        getLogger().debug(".addWorkUnitProcessor(): Entry");
        WorkUnitProcessorTopologyNode wup = new WorkUnitProcessorTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, version, nodeType);
        wup.setNodeRDN(nodeRDN);
        wup.constructFDN(workshop.getNodeFDN(), nodeRDN);
        wup.constructFunctionFDN(workshop.getNodeFunctionFDN(), nodeRDN);
        wup.setContainingNodeFDN(workshop.getNodeFDN());
        wup.setComponentType(nodeType);
        workshop.getWupSet().add(wup.getNodeFDN());
        getLogger().trace(".addWorkUnitProcessor(): Add the WorkUnitProcessor to the Topology Cache");
        getTopologyIM().addTopologyNode(workshop.getNodeFDN(), wup);
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
    public WorkUnitProcessorComponentTopologyNode addWorkUnitProcessorComponent(String name, TopologyNodeTypeEnum topologyType, WorkUnitProcessorTopologyNode wup){
        getLogger().debug(".addWorkUnitProcessorComponent(): Entry");
        WorkUnitProcessorComponentTopologyNode wupComponent = new WorkUnitProcessorComponentTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, wup.getNodeRDN().getNodeVersion(), topologyType);
        wupComponent.constructFDN(wup.getNodeFDN(), nodeRDN);
        wupComponent.constructFunctionFDN(wup.getNodeFunctionFDN(), nodeRDN);
        wupComponent.setNodeRDN(nodeRDN);
        wupComponent.setComponentType(topologyType);
        wupComponent.setContainingNodeFDN(wup.getNodeFDN());
        wup.getWupComponents().add(wupComponent.getNodeFDN());
        getLogger().trace(".addWorkUnitProcessorComponent(): Add the WorkUnitProcessor Component to the Topology Cache");
        getTopologyIM().addTopologyNode(wup.getNodeFDN(), wupComponent);
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
    public WorkUnitProcessorInterchangeComponentTopologyNode addWorkUnitProcessingInterchangeComponent(String name, TopologyNodeTypeEnum topologyNodeType, WorkUnitProcessorTopologyNode wup){
        getLogger().debug(".addWorkUnitProcessingInterchangeComponent(): Entry");
        WorkUnitProcessorInterchangeComponentTopologyNode wupInterchangeComponent = new WorkUnitProcessorInterchangeComponentTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, wup.getNodeRDN().getNodeVersion(), topologyNodeType);
        wupInterchangeComponent.constructFDN(wup.getNodeFDN(), nodeRDN);
        wupInterchangeComponent.constructFunctionFDN(wup.getNodeFunctionFDN(), nodeRDN);
        wupInterchangeComponent.setNodeRDN(nodeRDN);
        wupInterchangeComponent.setComponentType(topologyNodeType);
        wupInterchangeComponent.setContainingNodeFDN(wup.getNodeFDN());
        wup.getWupInterchangeComponents().add(wupInterchangeComponent.getNodeFDN());
        getLogger().trace(".addWorkUnitProcessorComponent(): Add the WorkUnitProcessor Interchange Component to the Topology Cache");
        getTopologyIM().addTopologyNode(wup.getNodeFDN(), wupInterchangeComponent);
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
        StandardPortSegment port = getPropertyFile().getPrometheusPort();
        if(port == null){
            getLogger().debug(".addPrometheusPort(): Exit, no port to add");
            return;
        }
        prometheusPort.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNamePrometheus()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        prometheusPort.setNodeRDN(nodeRDN);
        prometheusPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        prometheusPort.setPortType(port.getPortType());
        prometheusPort.setEndpointType(TopologyEndpointTypeEnum.HTTP_API_SERVER);
        prometheusPort.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        prometheusPort.setPortValue(port.getPortValue());
        prometheusPort.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN );
        prometheusPort.setBasePath("/");
        prometheusPort.setaServer(true);
        prometheusPort.setContainingNodeFDN(processingPlant.getNodeFDN());
        processingPlant.getEndpoints().add(prometheusPort.getNodeFDN());
        getLogger().trace(".addPrometheusPort(): Add the Prometheus Port to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getNodeFDN(), prometheusPort);
        getLogger().debug(".addPrometheusPort(): Exit, endpoint added");
    }

    //
    // Build Jolokia Port (if there)
    //

    protected void addJolokiaPort(ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addJolokiaPort(): Entry, processingPlant->{}", processingPlant);
        HTTPProcessingPlantTopologyEndpointPort jolokiaPort = new HTTPProcessingPlantTopologyEndpointPort();
        StandardPortSegment port = getPropertyFile().getJolokiaPort();
        if(port == null){
            getLogger().debug(".addJolokiaPort(): Exit, no port to add");
            return;
        }
        jolokiaPort.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameJolokia()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        jolokiaPort.setNodeRDN(nodeRDN);
        jolokiaPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        jolokiaPort.setPortType(port.getPortType());
        jolokiaPort.setEndpointType(TopologyEndpointTypeEnum.HTTP_API_SERVER);
        jolokiaPort.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        jolokiaPort.setPortValue(port.getPortValue());
        jolokiaPort.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN );
        jolokiaPort.setBasePath("/");
        jolokiaPort.setaServer(true);
        jolokiaPort.setContainingNodeFDN(processingPlant.getNodeFDN());
        processingPlant.getEndpoints().add(jolokiaPort.getNodeFDN());
        getLogger().trace(".addJolokiaPort(): Add the Jolokia Port to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getNodeFDN(), jolokiaPort);
        getLogger().debug(".addJolokiaPort(): Exit, endpoint added");
    }

    //
    // Build KubeLiveliness Port (if there)
    //

    protected void addKubeLivelinessPort(ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addKubeLivelinessPort(): Entry, processingPlant->{}", processingPlant);
        HTTPProcessingPlantTopologyEndpointPort kubeLivelinessPort = new HTTPProcessingPlantTopologyEndpointPort();
        StandardPortSegment port = getPropertyFile().getKubeLivelinessProbe();
        if(port == null){
            getLogger().debug(".addKubeLivelinessPort(): Exit, no port to add");
            return;
        }
        kubeLivelinessPort.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameKubeLiveliness()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        kubeLivelinessPort.setNodeRDN(nodeRDN);
        kubeLivelinessPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        kubeLivelinessPort.setPortType(port.getPortType());
        kubeLivelinessPort.setEndpointType(TopologyEndpointTypeEnum.HTTP_API_SERVER);
        kubeLivelinessPort.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        kubeLivelinessPort.setPortValue(port.getPortValue());
        kubeLivelinessPort.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN );
        kubeLivelinessPort.setBasePath("/");
        kubeLivelinessPort.setaServer(true);
        kubeLivelinessPort.setContainingNodeFDN(processingPlant.getNodeFDN());
        processingPlant.getEndpoints().add(kubeLivelinessPort.getNodeFDN());
        getLogger().trace(".addKubeLivelinessPort(): Add the KubeLivelinessPort Port to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getNodeFDN(), kubeLivelinessPort);
        getLogger().debug(".addKubeLivelinessPort(): Exit, endpoint added");
    }

    //
    // Build KubeReadiness Port (if there)
    //

    protected void addKubeReadinessPort( ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addKubeReadinessPort(): Entry, processingPlant->{}", processingPlant);
        HTTPProcessingPlantTopologyEndpointPort kubeReadinessPort = new HTTPProcessingPlantTopologyEndpointPort();
        StandardPortSegment port = getPropertyFile().getKubeReadinessProbe();
        if(port == null){
            getLogger().debug(".addKubeReadinessPort(): Exit, no port to add");
            return;
        }
        kubeReadinessPort.setEncrypted(getPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameKubeReadiness()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        kubeReadinessPort.setNodeRDN(nodeRDN);
        kubeReadinessPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        kubeReadinessPort.setPortType(port.getPortType());
        kubeReadinessPort.setEndpointType(TopologyEndpointTypeEnum.HTTP_API_SERVER);
        kubeReadinessPort.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        kubeReadinessPort.setPortValue(port.getPortValue());
        kubeReadinessPort.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN );
        kubeReadinessPort.setNodeRDN(nodeRDN);
        kubeReadinessPort.setBasePath("/");
        kubeReadinessPort.setaServer(true);
        kubeReadinessPort.setContainingNodeFDN(processingPlant.getNodeFDN());
        processingPlant.getEndpoints().add(kubeReadinessPort.getNodeFDN());
        getLogger().trace(".addKubeReadinessPort(): Add the KubeReadinessPort Port to the Topology Cache");
        getTopologyIM().addTopologyNode(processingPlant.getNodeFDN(), kubeReadinessPort);
        getLogger().debug(".addKubeReadinessPort(): Exit, endpoint added");
    }

    //
    // Resilience Mode Calculation
    //
    public ResilienceModeEnum getResilienceMode() {
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
            return (ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        }
        if (!clustered && kubernetes && !multisite) {
            return (ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        }
        if (clustered && !kubernetes && !multisite) {
            return (ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        }
        if (clustered && kubernetes && !multisite) {
            return (ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        }
        if (!clustered && !kubernetes && multisite) {
            return (ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        }
        if (clustered && !kubernetes && multisite) {
            return (ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        }
        if (!clustered && kubernetes && multisite) {
            return (ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        }
        if (clustered && kubernetes && multisite) {
            return (ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        }
        return (ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
    }

    //
    // Concurrency Mode Calculation
    //

    public ConcurrencyModeEnum getConcurrenceMode() {
        boolean standalone = false;
        boolean concurrent = false;

        if (getResilienceMode().equals(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE)) {
            standalone = true;
        }
        if (getPropertyFile().getDeploymentMode().isConcurrent()) {
            concurrent = true;
        }
        if (!standalone && !concurrent) {
            return (ConcurrencyModeEnum.CONCURRENCY_MODE_ONDEMAND);
        }
        if (!standalone && concurrent) {
            return (ConcurrencyModeEnum.CONCURRENCY_MODE_CONCURRENT);
        }
        return (ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
    }

    protected BaseSubsystemPropertyFile readPropertyFile(String fileName, Class propertyFileClass){
        getLogger().debug(".readPropertyFile(): Entry, fileName->{}", fileName);
        String configFilePath;
        String propertyFileName = null;
        try {
            getLogger().trace(".readPropertyFile(): Establish YAML ObjectMapper");
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
            getLogger().trace(".readPropertyFile(): Establishing InputStream");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream propertyYAMLFile = classLoader.getResourceAsStream(fileName);
            getLogger().trace(".readPropertyFile(): Reading YAML configuration file");
            propertyFile = (BaseSubsystemPropertyFile) yamlMapper.readValue(propertyYAMLFile, propertyFileClass);
            getLogger().trace(".loadPropertyFile(): Exit, file loaded, propertyFile->{}", propertyFile);
            return(propertyFile);
        } catch(FileNotFoundException noFile){
            getLogger().error(".loadPropertyFile(): Configuration File->{} is not found", propertyFileName);
        } catch(IOException ioError){
            getLogger().error(".loadPropertyFile(): Configuration File->{} could not be loaded!", propertyFileName);
        }
        getLogger().debug(".loadPropertyFile(): failed to load file");
        return(null);
    }



}
