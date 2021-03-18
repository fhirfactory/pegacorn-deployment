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
package net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.common;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.names.common.SiteKeyNames;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.StandardProcessingPlantPort;
import net.fhirfactory.pegacorn.deployment.topology.model.common.SystemIdentificationInterface;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.EndpointTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.PegacornHTTPProcessingPlantPort;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.*;
import org.slf4j.Logger;

import javax.inject.Inject;

public abstract class PegacornTopologyFactoryBase {

    @Inject
    private SystemIdentificationInterface systemIdentification;

    abstract protected SubsystemBaseNames specifySubsystemBaseNames();

    @Inject
    private SiteKeyNames siteKeyNames;

    protected abstract Logger getLogger();

    protected SiteKeyNames getSiteKeyNames(){return(siteKeyNames);}
    protected SubsystemBaseNames getSubsystemBaseNames(){return(specifySubsystemBaseNames());}
    protected SystemIdentificationInterface getSystemIdentification(){return(systemIdentification);}

    protected TopologyNodeRDN createNodeRDN( String nodeName, String nodeVersion, TopologyNodeTypeEnum nodeType){
        TopologyNodeRDN newRDN = new TopologyNodeRDN(nodeType, nodeName, nodeVersion);
        return (newRDN);
    }



    /**
     * Subsystem Node Builder
     *
     * @param propertyFile
     * @param solution
     * @return
     */

    public SubsystemTopologyNode addSubsystemNode(BaseSubsystemPropertyFile propertyFile, SolutionTopologyNode solution){
        getLogger().debug(".buildSubsystemNode(): Entry");
        SubsystemTopologyNode subsystem = new SubsystemTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(propertyFile.getSubsystemInstant().getSubsystemName(), propertyFile.getSubsystemInstant().getSubsystemVersion(), TopologyNodeTypeEnum.SUBSYSTEM);
        subsystem.constructFDN(solution.getNodeFDN(), nodeRDN);
        subsystem.constructFunctionFDN(solution.getNodeFunctionFDN(), nodeRDN);
        subsystem.setNodeRDN(nodeRDN);
        subsystem.setConcurrencyMode(getConcurrenceMode(propertyFile));
        subsystem.setResilienceMode(getResilienceMode(propertyFile));
        subsystem.setSiteCount(propertyFile.getDeploymentSites().getSiteCount());
        solution.getSubsystemList().put(nodeRDN, subsystem);
        getLogger().debug(".buildSubsystemNode(): Exit");
        return(subsystem);
    }

    /**
     * Business Service Node Set Builder
     *
     * @param propertyFile
     * @param subsystem
     * @return
     */

    public BusinessServiceTopologyNode addBusinessServiceNode(BaseSubsystemPropertyFile propertyFile, SubsystemTopologyNode subsystem){
        getLogger().debug(".buildBusinessServiceNode(): Entry");
        BusinessServiceTopologyNode businessService = new BusinessServiceTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(propertyFile.getSubsystemInstant().getExternalisedServiceName(), propertyFile.getSubsystemInstant().getSubsystemVersion(), TopologyNodeTypeEnum.EXTERNALISED_SERVICE);
        businessService.constructFDN(subsystem.getNodeFDN(),nodeRDN);
        businessService.constructFunctionFDN(subsystem.getNodeFunctionFDN(),nodeRDN);
        businessService.setNodeRDN(nodeRDN);
        subsystem.getBusinessServices().put(nodeRDN, businessService);
        getLogger().debug(".buildBusinessServiceNode(): Exit");
        return(businessService);
    }

    /**
     * Deployment Site Node Builder
     *
     * @param propertyFile
     * @param businessService
     * @return
     */
    public DeploymentSiteTopologyNode addDeploymentSiteNode(BaseSubsystemPropertyFile propertyFile, BusinessServiceTopologyNode businessService){
        getLogger().debug(".addDeploymentSiteNode(): Entry");
        DeploymentSiteTopologyNode site = new DeploymentSiteTopologyNode();
        String siteName = propertyFile.getDeploymentSites().getSite1Name();
        TopologyNodeRDN nodeRDN = createNodeRDN(propertyFile.getDeploymentSites().getSite1Name(), propertyFile.getSubsystemInstant().getSubsystemVersion(), TopologyNodeTypeEnum.SITE);
        site.constructFDN(businessService.getNodeFDN(), nodeRDN);
        site.constructFunctionFDN(businessService.getNodeFunctionFDN(), nodeRDN);
        site.setNodeRDN(nodeRDN);
        site.setInstanceCount(propertyFile.getDeploymentSites().getSiteCount());
        businessService.getDeploymentSites().put(nodeRDN, site);
        getLogger().debug(".addDeploymentSiteNode(): Exit");
        return(site);
    }

    /**
     * Build Cluster Service Node
     *
     * @param propertyFile
     * @param site
     * @return
     */
    public ClusterServiceTopologyNode addClusterServiceNode(BaseSubsystemPropertyFile propertyFile, DeploymentSiteTopologyNode site){
        getLogger().debug(".addClusterServiceNode(): Entry");
        ClusterServiceTopologyNode clusterService = new ClusterServiceTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(propertyFile.getSubsystemInstant().getClusterServiceName(), propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.CLUSTER_SERVICE);
        clusterService.constructFDN(site.getNodeFDN(),nodeRDN);
        clusterService.constructFunctionFDN(site.getNodeFunctionFDN(),nodeRDN);
        clusterService.setNodeRDN(nodeRDN);
        clusterService.setResilienceMode(getResilienceMode(propertyFile));
        clusterService.setConcurrencyMode(getConcurrenceMode(propertyFile));
        clusterService.setDefaultDNSName(propertyFile.getSubsystemInstant().getClusterServiceDNSName());
        clusterService.setInternalTrafficEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        site.getClusterServices().put(nodeRDN, clusterService);
        getLogger().debug(".addClusterServiceNode(): Exit");
        return(clusterService);
    }

    /**
     * Platform Node Set Builder
     *
     * @param propertyFile
     * @param service
     * @return
     */
    public PlatformNode addPlatformNode(BaseSubsystemPropertyFile propertyFile, ClusterServiceTopologyNode service){
        getLogger().debug(".addPlatformNode(): Entry");
        PlatformNode node = new PlatformNode();
        String nodeName = "PlatformNode0"; // TODO Fix This --> lookup node namde for kubernets or do a reverse lookup DNS
        TopologyNodeRDN nodeRDN = createNodeRDN(nodeName, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.PLATFORM);
        node.constructFDN(service.getNodeFDN(), nodeRDN);
        node.constructFunctionFDN(service.getNodeFunctionFDN(), nodeRDN);
        node.setNodeRDN(nodeRDN);
        node.setInstanceCount(propertyFile.getDeploymentMode().getProcessingPlantReplicationCount());
        service.getPlatformNodes().put(nodeRDN, node);
        getLogger().debug(".addPlatformNode(): Exit");
        return(node);
    }

    /**
     *
     * @param propertyFile
     * @param node
     * @return
     */
    public ProcessingPlantTopologyNode addPegacornProcessingPlant(BaseSubsystemPropertyFile propertyFile, PlatformNode node){
        getLogger().debug(".addPegacornProcessingPlant(): Entry");
        ProcessingPlantTopologyNode processingPlant = new ProcessingPlantTopologyNode();
        String name = propertyFile.getSubsystemInstant().getProcessingPlantName();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.PROCESSING_PLANT);
        processingPlant.constructFDN(node.getNodeFDN(), nodeRDN);
        processingPlant.constructFunctionFDN(node.getNodeFunctionFDN(), nodeRDN);
        processingPlant.setNodeRDN(nodeRDN);
        processingPlant.setResilienceMode(getResilienceMode(propertyFile));
        processingPlant.setConcurrencyMode(getConcurrenceMode(propertyFile));
        processingPlant.setDefaultDNSName(propertyFile.getSubsystemInstant().getProcessingPlantDNSName());
        processingPlant.setInternalTrafficEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        processingPlant.setInstanceCount(propertyFile.getDeploymentMode().getProcessingPlantReplicationCount());
        node.getProcessingPlants().put(nodeRDN, processingPlant);
        getLogger().debug(".addPegacornProcessingPlant(): Exit");
        return(processingPlant);
    }

    /**
     *
     * @param name
     * @param version
     * @param processingPlant
     */
    public WorkshopTopologyNode addWorkshop(String name, String version, ProcessingPlantTopologyNode processingPlant, TopologyNodeTypeEnum nodeType){
        getLogger().debug(".addWorkshop(): Entry");
        WorkshopTopologyNode workshop = new WorkshopTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, version,nodeType);
        workshop.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        workshop.constructFunctionFDN(processingPlant.getNodeFunctionFDN(), nodeRDN);
        workshop.setNodeRDN(nodeRDN);
        processingPlant.getWorkshops().put(nodeRDN, workshop);
        getLogger().debug(".addWorkshop(): Exit");
        return(workshop);
    }

    /**
     *
     * @param name
     * @param version
     * @param workshop
     */
    public WorkUnitProcessorTopologyNode addWorkUnitProcessor(String name, String version, WorkshopTopologyNode workshop, TopologyNodeTypeEnum nodeType){
        getLogger().debug(".addWorkshop(): Entry");
        WorkUnitProcessorTopologyNode wup = new WorkUnitProcessorTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, version, nodeType);
        wup.constructFDN(workshop.getNodeFDN(), nodeRDN);
        wup.constructFunctionFDN(workshop.getNodeFunctionFDN(), nodeRDN);
        wup.setNodeRDN(nodeRDN);
        workshop.getWupSet().put(nodeRDN, wup);
        getLogger().debug(".addWorkshop(): Exit");
        return(wup);
    }

    /**
     *
     * @param name
     * @param topologyType
     * @param wup
     */
    public WorkUnitProcessorComponentTopologyNode addWorkUnitProcessorComponent(String name, TopologyNodeTypeEnum topologyType, WorkUnitProcessorTopologyNode wup){
        getLogger().debug(".addWorkUnitProcessorComponent(): Entry");
        WorkUnitProcessorComponentTopologyNode wupComponent = new WorkUnitProcessorComponentTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, wup.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.WUP);
        wupComponent.constructFDN(wup.getNodeFDN(), nodeRDN);
        wupComponent.constructFunctionFDN(wup.getNodeFunctionFDN(), nodeRDN);
        wupComponent.setNodeRDN(nodeRDN);
        wup.getWupComponents().put(nodeRDN, wupComponent);
        getLogger().debug(".addWorkUnitProcessorComponent(): Exit");
        return(wupComponent);
    }

    /**
     *
     * @param name
     * @param topologyNodeType
     * @param wup
     */
    public WorkUnitProcessorInterchangeComponentTopologyNode addWorkUnitProcessingInterchangeComponent(String name, TopologyNodeTypeEnum topologyNodeType, WorkUnitProcessorTopologyNode wup){
        getLogger().debug(".addWorkUnitProcessingInterchangeComponent(): Entry");
        WorkUnitProcessorInterchangeComponentTopologyNode wupInterchangeComponent = new WorkUnitProcessorInterchangeComponentTopologyNode();
        TopologyNodeRDN nodeRDN = createNodeRDN(name, wup.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.WUP);
        wupInterchangeComponent.constructFDN(wup.getNodeFDN(), nodeRDN);
        wupInterchangeComponent.constructFunctionFDN(wup.getNodeFunctionFDN(), nodeRDN);
        wupInterchangeComponent.setNodeRDN(nodeRDN);
        wup.getWupInterchangeComponents().put(nodeRDN, wupInterchangeComponent);
        getLogger().debug(".addWorkUnitProcessingInterchangeComponent(): Entry");
        return(wupInterchangeComponent);
    }


    /**
     *
     * @param propertyFile
     * @return
     */

    public Boolean getSubsystemInternalTrafficEncrypt(BaseSubsystemPropertyFile propertyFile){
        getLogger().debug(".getSubsystemInternalTrafficEncrypt(): Entry");
        Boolean encryptTraffic = propertyFile.getDeploymentMode().isUsingInternalEncryption();
        getLogger().debug(".getSubsystemInternalTrafficEncrypt(): Exit, Encrypt Internal Traffic? --> {}", encryptTraffic);
        return(encryptTraffic);
    }

    /*
    protected void loadPropertyFile() {
        getLogger().debug(".loadPropertyFile(): Entry");
        String configFilePath;
        if(specifyPropertiesFileName().contains(getPropertiesFileNameExtension())){
            configFilePath = specifyPropertiesFileName();
        } else {
            configFilePath = specifyPropertiesFileName() + getPropertiesFileNameExtension();
        }
        try {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            propertyFile = (BaseSubsystemPropertyFile) yamlMapper.readValue(new File(configFilePath), getPropertyFileClass());
        } catch(FileNotFoundException noFile){
            getLogger().error(".loadPropertyFile(): Configuraiton File --> {} is not found", configFilePath);
        } catch(IOException ioError){
            getLogger().error(".loadPropertyFile(): Configuration File --> {} could not be loaded!", configFilePath);
        }
        getLogger().debug(".loadPropertyFile(): Exit");
    }
    */


    //
    // Build Prometheus Port (if there)
    //

    private void addPrometheusPort(BaseSubsystemPropertyFile propertyFile, ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addPrometheusPort(): Entry");
        PegacornHTTPProcessingPlantPort prometheusPort = new PegacornHTTPProcessingPlantPort();
        StandardProcessingPlantPort port = propertyFile.getPrometheusPort();
        if(port == null){
            getLogger().debug(".addPrometheusPort(): Exit, no port to add");
            return;
        }
        prometheusPort.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNamePrometheus()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        prometheusPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        prometheusPort.setPortType(port.getPortType());
        prometheusPort.setEndpointType(EndpointTypeEnum.HTTP_API_SERVER);
        prometheusPort.setPortValue(port.getPortValue());
        prometheusPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN );
        prometheusPort.setBasePath("/");
        prometheusPort.setaServer(true);
        processingPlant.getEnpoints().put(nodeRDN, prometheusPort);
        getLogger().debug(".addPrometheusPort(): Exit, endpoint added");
    }

    //
    // Build Jolokia Port (if there)
    //

    private void addJolokiaPort(BaseSubsystemPropertyFile propertyFile, ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addJolokiaPort(): Entry");
        PegacornHTTPProcessingPlantPort jolokiaPort = new PegacornHTTPProcessingPlantPort();
        StandardProcessingPlantPort port = propertyFile.getJolokiaPort();
        if(port == null){
            getLogger().debug(".addJolokiaPort(): Exit, no port to add");
            return;
        }
        jolokiaPort.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameJolokia()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        jolokiaPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        jolokiaPort.setPortType(port.getPortType());
        jolokiaPort.setEndpointType(EndpointTypeEnum.HTTP_API_SERVER);
        jolokiaPort.setPortValue(port.getPortValue());
        jolokiaPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN );
        jolokiaPort.setBasePath("/");
        jolokiaPort.setaServer(true);
        processingPlant.getEnpoints().put(nodeRDN, jolokiaPort);
        getLogger().debug(".addJolokiaPort(): Exit, endpoint added");
    }

    //
    // Build KubeLiveliness Port (if there)
    //

    private void addKubeLivelinessPort(BaseSubsystemPropertyFile propertyFile, ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addKubeLivelinessPort(): Entry");
        PegacornHTTPProcessingPlantPort kubeLivelinessPort = new PegacornHTTPProcessingPlantPort();
        StandardProcessingPlantPort port = propertyFile.getKubeLivelinessProbe();
        if(port == null){
            getLogger().debug(".addKubeLivelinessPort(): Exit, no port to add");
            return;
        }
        kubeLivelinessPort.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameKubeLiveliness()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        kubeLivelinessPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        kubeLivelinessPort.setPortType(port.getPortType());
        kubeLivelinessPort.setEndpointType(EndpointTypeEnum.HTTP_API_SERVER);
        kubeLivelinessPort.setPortValue(port.getPortValue());
        kubeLivelinessPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN );
        kubeLivelinessPort.setBasePath("/");
        kubeLivelinessPort.setaServer(true);
        processingPlant.getEnpoints().put(nodeRDN, kubeLivelinessPort);
        getLogger().debug(".addKubeLivelinessPort(): Exit, endpoint added");
    }

    //
    // Build KubeReadiness Port (if there)
    //

    private void addKubeReadinessPort(BaseSubsystemPropertyFile propertyFile, ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".addKubeReadinessPort(): Entry");
        PegacornHTTPProcessingPlantPort kubeReadinessPort = new PegacornHTTPProcessingPlantPort();
        StandardProcessingPlantPort port = propertyFile.getKubeReadinessProbe();
        if(port == null){
            getLogger().debug(".addKubeReadinessPort(): Exit, no port to add");
            return;
        }
        kubeReadinessPort.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        String name = getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getEndpointServerName(getSubsystemBaseNames().getFunctionNameKubeReadiness()));
        TopologyNodeRDN nodeRDN = createNodeRDN(name, processingPlant.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        kubeReadinessPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN);
        kubeReadinessPort.setPortType(port.getPortType());
        kubeReadinessPort.setEndpointType(EndpointTypeEnum.HTTP_API_SERVER);
        kubeReadinessPort.setPortValue(port.getPortValue());
        kubeReadinessPort.constructFDN(processingPlant.getNodeFDN(), nodeRDN );
        kubeReadinessPort.setNodeRDN(nodeRDN);
        kubeReadinessPort.setBasePath("/");
        kubeReadinessPort.setaServer(true);
        processingPlant.getEnpoints().put(nodeRDN, kubeReadinessPort);
        getLogger().debug(".addKubeReadinessPort(): Exit, endpoint added");
    }

    //
    // Resilience Mode Calculation
    //

    private ResilienceModeEnum getResilienceMode(BaseSubsystemPropertyFile propertyFile){
        boolean clustered= false;
        boolean kubernetes = false;
        boolean multisite = false;

        if(propertyFile.getDeploymentSites().getSiteCount() < 2){
            multisite = false;
        }

        if(propertyFile.getDeploymentMode().getProcessingPlantReplicationCount() < 2){
            clustered = true;
        }

        if(propertyFile.getDeploymentMode().isKubernetes()){
            kubernetes = true;
        }

        if(!clustered && !kubernetes && !multisite){
            return(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        }
        if(!clustered && kubernetes && !multisite){
            return(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        }
        if(clustered && !kubernetes && !multisite){
            return(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        }
        if(clustered && kubernetes && !multisite){
            return(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        }
        if(!clustered && !kubernetes && multisite){
            return(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        }
        if(clustered && !kubernetes && multisite){
            return(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        }
        if(!clustered && kubernetes && multisite){
            return(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        }
        if(clustered && kubernetes && multisite){
            return(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        }
        return(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
    }

    //
    // Concurrency Mode Calculation
    //

    private ConcurrencyModeEnum getConcurrenceMode(BaseSubsystemPropertyFile propertyFile){
        boolean standalone = false;
        boolean concurrent = false;

        if(getResilienceMode(propertyFile).equals(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE)){
            standalone=true;
        }
        if(propertyFile.getDeploymentMode().isConcurrent()){
            concurrent=true;
        }
        if(!standalone && !concurrent){
            return(ConcurrencyModeEnum.CONCURRENCY_MODE_ONDEMAND);
        }
        if(!standalone && concurrent){
            return(ConcurrencyModeEnum.CONCURRENCY_MODE_CONCURRENT);
        }
        return(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
    }
}
