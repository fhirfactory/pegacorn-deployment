/*
 * Copyright (c) 2020 Mark A. Hunter (ACT Health)
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
package net.fhirfactory.pegacorn.deployment.topology.manager;

import net.fhirfactory.pegacorn.deployment.topology.manager.cache.TopologyNodesDM;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.IPCEndpoint;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * This class WILL do more in the future, but it is for now just a proxy to the
 * TopologyDM.
 */
@ApplicationScoped
public class TopologyIM {

    private static final Logger LOG = LoggerFactory.getLogger(TopologyIM.class);

    @Inject
    private TopologyNodesDM topologyDataManager;

    @PostConstruct
    public void initialise(){

    }

    public void setDeploymentSolution(SolutionTopologyNode solution){
        topologyDataManager.setDeploymentSolution(solution);
    }

    public SolutionTopologyNode getSolutionTopology(){
        SolutionTopologyNode solution = (SolutionTopologyNode) topologyDataManager.getDeploymentSolution();
        return(solution);
    }

    public void addTopologyNode(TopologyNode parentNodeElement, TopologyNode newNodeElement) {
        LOG.debug(".registerNode(): Entry, newElement --> {}", newNodeElement);

        newNodeElement.setContainingComponent(parentNodeElement);
        topologyDataManager.addTopologyNode(newNodeElement);
        switch(newNodeElement.getComponentType()){
            case SUBSYSTEM: {
                SolutionTopologyNode solution = (SolutionTopologyNode) parentNodeElement;
                SubsystemTopologyNode subsystem = (SubsystemTopologyNode) newNodeElement;
                solution.getSubsystemList().putIfAbsent(subsystem.getNodeRDN(), subsystem);
                break;
            }
            case EXTERNALISED_SERVICE: {
                SubsystemTopologyNode subsystem = (SubsystemTopologyNode) parentNodeElement;
                BusinessServiceTopologyNode businessService = (BusinessServiceTopologyNode) newNodeElement;
                businessService.setContainingComponent(subsystem);
                subsystem.getBusinessServices().putIfAbsent(newNodeElement.getNodeRDN(), businessService);
                break;
            }
            case SITE: {
                BusinessServiceTopologyNode businessService = (BusinessServiceTopologyNode) parentNodeElement;
                DeploymentSiteTopologyNode deploymentSite = (DeploymentSiteTopologyNode) newNodeElement;
                businessService.getDeploymentSites().putIfAbsent(deploymentSite.getNodeRDN(), deploymentSite);
                break;
            }
            case CLUSTER_SERVICE:{
                DeploymentSiteTopologyNode deploymentSite = (DeploymentSiteTopologyNode) parentNodeElement;
                ClusterServiceTopologyNode clusterService = (ClusterServiceTopologyNode) newNodeElement;
                deploymentSite.getClusterServices().putIfAbsent(clusterService.getNodeRDN(), clusterService);
                break;
            }
            case PLATFORM:{
                ClusterServiceTopologyNode clusterService = (ClusterServiceTopologyNode) parentNodeElement;
                PlatformNode platformNode = (PlatformNode) newNodeElement;
                clusterService.getPlatformNodes().putIfAbsent(platformNode.getNodeRDN(), platformNode);
                break;
            }
            case PROCESSING_PLANT:{
                PlatformNode platformNode = (PlatformNode) parentNodeElement;
                ProcessingPlantTopologyNode processingPlant = (ProcessingPlantTopologyNode) newNodeElement;
                platformNode.getProcessingPlants().putIfAbsent(processingPlant.getNodeRDN(), processingPlant);
                break;
            }
            case WORKSHOP:{
                ProcessingPlantTopologyNode processingPlant = (ProcessingPlantTopologyNode) parentNodeElement;
                WorkshopTopologyNode workshop = (WorkshopTopologyNode) newNodeElement;
                processingPlant.getWorkshops().putIfAbsent(workshop.getNodeRDN(), workshop);
                break;
            }
            case WUP:{
                WorkshopTopologyNode workshop = (WorkshopTopologyNode) parentNodeElement;
                WorkUnitProcessorTopologyNode wup = (WorkUnitProcessorTopologyNode) newNodeElement;
                workshop.getWupSet().putIfAbsent(wup.getNodeRDN(), wup);
                WorkUnitProcessorComponentTopologyNode wupCore =  new WorkUnitProcessorComponentTopologyNode();
                wupCore.setContainingComponent(wup);
                wupCore.setComponentType(TopologyNodeTypeEnum.WUP_CORE);
                TopologyNodeFDN newFDN = new TopologyNodeFDN(wup.getNodeFDN());
                newFDN.appendTopologyNodeRDN(wup.getNodeRDN());
                wupCore.setNodeFDN(newFDN);
                wupCore.constructFunctionFDN(wup.getNodeFunctionFDN(), wup.getNodeRDN());
                topologyDataManager.addTopologyNode(wupCore);
                break;
            }
            case WUP_INTERCHANGE_ROUTER:
            case WUP_INTERCHANGE_PAYLOAD_TRANSFORMER:{
                WorkUnitProcessorTopologyNode wup = (WorkUnitProcessorTopologyNode) parentNodeElement;
                WorkUnitProcessorInterchangeComponentTopologyNode wupInterchangeComponent = (WorkUnitProcessorInterchangeComponentTopologyNode) newNodeElement;
                wup.getWupInterchangeComponents().putIfAbsent(wupInterchangeComponent.getNodeRDN(), wupInterchangeComponent);
            }
            case WUP_CORE:
            case WUP_CONTAINER_EGRESS_CONDUIT:
            case WUP_CONTAINER_INGRES_CONDUIT:
            case WUP_CONTAINER_EGRESS_PROCESSOR:
            case WUP_CONTAINER_INGRES_PROCESSOR:
            case WUP_CONTAINER_EGRESS_GATEKEEPER:
            case WUP_CONTAINER_INGRES_GATEKEEPER:{
                WorkUnitProcessorTopologyNode wup = (WorkUnitProcessorTopologyNode) parentNodeElement;
                WorkUnitProcessorComponentTopologyNode wupComponent = (WorkUnitProcessorComponentTopologyNode) newNodeElement;
                wup.getWupComponents().putIfAbsent(wupComponent.getNodeRDN(), wupComponent);
            }
            case ENDPOINT:{
                switch(parentNodeElement.getComponentType()){
                    case EXTERNALISED_SERVICE:{
                        BusinessServiceTopologyNode businessService = (BusinessServiceTopologyNode) parentNodeElement;
                        IPCEndpoint endpoint = (IPCEndpoint) newNodeElement;
                        businessService.getExternalisedServices().putIfAbsent(endpoint.getNodeRDN(), endpoint);
                        break;
                    }
                    case CLUSTER_SERVICE:{
                        ClusterServiceTopologyNode clusterService = (ClusterServiceTopologyNode) parentNodeElement;
                        IPCEndpoint endpoint = (IPCEndpoint) newNodeElement;
                        clusterService.getClusterServiceServerEndpoints().putIfAbsent(endpoint.getNodeRDN(), endpoint );
                        break;
                    }
                    case PROCESSING_PLANT:{
                        ProcessingPlantTopologyNode processingPlant = (ProcessingPlantTopologyNode) parentNodeElement;
                        IPCEndpoint endpoint = (IPCEndpoint) newNodeElement;
                        processingPlant.getEnpoints().putIfAbsent(endpoint.getNodeRDN(), endpoint);
                        break;
                    }
                    default:
                        // Do nothing
                }

            }
            case SOLUTION:
            default:{
                // Do nothing
            }
        }
    }

    public void removeNode(TopologyNodeFDN elementID) {
        LOG.debug(".unregisterNode(): Entry, elementID --> {}", elementID);
        topologyDataManager.deleteTopologyNode(elementID);
    }

    public Set<TopologyNode> getNodeElementSet() {
        LOG.debug(".getNodeSet(): Entry");
        return (topologyDataManager.getTopologyNodeSet());
    }

    public TopologyNode getNode(TopologyNodeFDN nodeID) {
        LOG.debug(".getNode(): Entry, nodeID --> {}", nodeID);
        TopologyNode retrievedNode = topologyDataManager.nodeSearch(nodeID);
        LOG.debug(".getNode(): Exit, retrievedNode --> {}", retrievedNode);
        return (retrievedNode);
    }

    public List<TopologyNode> nodeSearch(TopologyNodeTypeEnum nodeType, String nodeName,  String nodeVersion ){
        List<TopologyNode> nodeList = topologyDataManager.nodeSearch(nodeType, nodeName, nodeVersion);
        return(nodeList);
    }
}
