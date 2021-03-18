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
package net.fhirfactory.pegacorn.deployment.topology.map.helpers;

import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCInterface;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCInterfaceDefinition;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.IPCEndpoint;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.Iterator;

@ApplicationScoped
public class TopologyMapTraversalHelpers {
    private static final Logger LOG = LoggerFactory.getLogger(TopologyMapTraversalHelpers.class);

    /**
     *
     * @param subsystemNode
     * @param requiredInterface
     * @return
     */
    public BusinessServiceTopologyNode selectBusinessService(SubsystemTopologyNode subsystemNode, IPCInterfaceDefinition requiredInterface){
        if(subsystemNode == null){
            return(null);
        }
        for(BusinessServiceTopologyNode businessServiceNode: subsystemNode.getBusinessServices().values()){
            for(IPCEndpoint endpointNode: businessServiceNode.getExternalisedServices().values()){
                for(IPCInterface currentInterface: endpointNode.getSupportedInterfaceSet()){
                    for(IPCInterfaceDefinition currentInterfaceDefinition: currentInterface.getSupportedInterfaceDefinitions()){
                        boolean interfaceNameSame = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalName());
                        boolean interfaceNameVersion = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalVersion());
                        if(interfaceNameSame && interfaceNameVersion){
                            return(businessServiceNode);
                        }
                    }
                }
            }
        }
        return(null);
    }

    /**
     *
     * @param businessServiceTopologyNode
     * @param siteName
     * @return
     */
    public DeploymentSiteTopologyNode selectSite(BusinessServiceTopologyNode businessServiceTopologyNode, String siteName){
        if(businessServiceTopologyNode == null){
            return(null);
        }
        for(DeploymentSiteTopologyNode currentSiteNode: businessServiceTopologyNode.getDeploymentSites().values()){
            boolean siteNameSame = currentSiteNode.getNodeRDN().getNodeName().contentEquals(siteName);
            if(siteNameSame){
                return(currentSiteNode);
            }
        }
        return(null);
    }

    /**
     *
     * @param siteNode
     * @param requiredInterface
     * @return
     */
    public ClusterServiceTopologyNode selectClusterService(DeploymentSiteTopologyNode siteNode, IPCInterfaceDefinition requiredInterface){
        if(siteNode == null){
            return(null);
        }
        for(ClusterServiceTopologyNode serviceNode: siteNode.getClusterServices().values()){
            for(IPCEndpoint endpointNode: serviceNode.getClusterServiceServerEndpoints().values()){
                for(IPCInterface currentInterface: endpointNode.getSupportedInterfaceSet()){
                    for(IPCInterfaceDefinition currentInterfaceDefinition: currentInterface.getSupportedInterfaceDefinitions()){
                        boolean interfaceNameSame = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalName());
                        boolean interfaceNameVersion = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalVersion());
                        if(interfaceNameSame && interfaceNameVersion){
                            return(serviceNode);
                        }
                    }
                }
            }
        }
        return(null);
    }

    /**
     *
     * @param clusterService
     * @param requiredInterface
     * @return
     */
    public IPCEndpoint selectClusterServiceEndpoint(ClusterServiceTopologyNode clusterService, IPCInterfaceDefinition requiredInterface){
        if(clusterService == null){
            return(null);
        }
        for(IPCEndpoint endpointNode: clusterService.getClusterServiceServerEndpoints().values()){
            for(IPCInterface currentInterface: endpointNode.getSupportedInterfaceSet()){
                for(IPCInterfaceDefinition currentInterfaceDefinition: currentInterface.getSupportedInterfaceDefinitions()){
                    boolean interfaceNameSame = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalName());
                    boolean interfaceNameVersion = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalVersion());
                    if(interfaceNameSame && interfaceNameVersion){
                        return(endpointNode);
                    }
                }
            }
        }
        return(null);
    }

    /**
     *
     * @param clusterService
     * @return
     */
    public PlatformNode selectPlatform(ClusterServiceTopologyNode clusterService){
        if(clusterService == null){
            return(null);
        }
        int platformNodeCount = clusterService.getPlatformNodes().size();
        if(platformNodeCount <= 0) {
            return (null);
        }
        if(platformNodeCount == 1) {
            Collection<PlatformNode> platformNodeSet = clusterService.getPlatformNodes().values();
            Iterator<PlatformNode> platformNodeIterator = platformNodeSet.iterator();
            PlatformNode platformNode = platformNodeIterator.next();
            return (platformNode);
        }
        Long randomEntryNumberLong = Math.round((Math.random()*platformNodeCount));
        int randomEntryNumber = Math.toIntExact(randomEntryNumberLong);
        int position = 0;
        Collection<PlatformNode> platformNodeSet = clusterService.getPlatformNodes().values();
        Iterator<PlatformNode> platformNodeIterator = platformNodeSet.iterator();
        PlatformNode selectedPlatformNode = platformNodeIterator.next();
        while(position < randomEntryNumber) {
            selectedPlatformNode = platformNodeIterator.next();
        }
        return(selectedPlatformNode);
    }

    /**
     *
     * @param platformNode
     * @param requiredInterface
     * @return
     */
    public ProcessingPlantTopologyNode selectProcessingPlant(PlatformNode platformNode, IPCInterfaceDefinition requiredInterface){
        if(platformNode == null){
            return(null);
        }
        for(ProcessingPlantTopologyNode currentProcessingPlant: platformNode.getProcessingPlants().values()){
            for(IPCEndpoint endpointNode: currentProcessingPlant.getEnpoints().values()){
                for(IPCInterface currentInterface: endpointNode.getSupportedInterfaceSet()){
                    for(IPCInterfaceDefinition currentInterfaceDefinition: currentInterface.getSupportedInterfaceDefinitions()){
                        boolean interfaceNameSame = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalName());
                        boolean interfaceNameVersion = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalVersion());
                        if(interfaceNameSame && interfaceNameVersion){
                            return(currentProcessingPlant);
                        }
                    }
                }
            }
        }
        return(null);
    }

    /**
     *
     * @param processingPlantNode
     * @param requiredInterface
     * @return
     */
    public IPCEndpoint selectProcessingPlantEndpoint(ProcessingPlantTopologyNode processingPlantNode, IPCInterfaceDefinition requiredInterface){
        if(processingPlantNode == null){
            return(null);
        }
        for(IPCEndpoint endpointNode: processingPlantNode.getEnpoints().values()){
            for(IPCInterface currentInterface: endpointNode.getSupportedInterfaceSet()){
                for(IPCInterfaceDefinition currentInterfaceDefinition: currentInterface.getSupportedInterfaceDefinitions()){
                    boolean interfaceNameSame = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalName());
                    boolean interfaceNameVersion = currentInterface.getInstanceName().contentEquals(requiredInterface.getInterfaceFormalVersion());
                    if(interfaceNameSame && interfaceNameVersion){
                        return(endpointNode);
                    }
                }
            }
        }
        return(null);
    }

}
