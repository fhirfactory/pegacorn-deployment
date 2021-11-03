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
package net.fhirfactory.pegacorn.deployment.topology.factories.communicate;

import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.core.model.componentid.ComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.deployment.names.subsystems.CommunicateIrisSubsystemFunctionalityNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.CommunicateRoomServerComponentNames;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.roomserver.im.CommunicateRoomServerIMPropertyFile;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common.PetasosEnabledSubsystemTopologyFactory;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCClusteredServerTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.petasos.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.technologies.HTTPProcessingPlantTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.nodes.ClusterServiceTopologyNode;
import net.fhirfactory.pegacorn.core.model.topology.nodes.ProcessingPlantTopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public abstract class CommunicateRoomServerIMTopologyFactory extends PetasosEnabledSubsystemTopologyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(CommunicateRoomServerIMTopologyFactory.class);

    @Inject
    private CommunicateRoomServerComponentNames roomServerComponentNames;

    @Inject
    private CommunicateIrisSubsystemFunctionalityNames communicateIrisComponentNames;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public void buildClusterServiceEndpoints(CommunicateRoomServerIMPropertyFile propertyFile, ClusterServiceTopologyNode serviceNode) {
        getLogger().debug(".buildClusterServiceEndpoints(): Entry");
        IPCClusteredServerTopologyEndpoint matrixClientServicesServer = new IPCClusteredServerTopologyEndpoint();
        String name = roomServerComponentNames.getEndpointServerName(roomServerComponentNames.getFunctionNameMatrixClientServices());
        TopologyNodeRDN serverRDN = createNodeRDN(name, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), ComponentTypeTypeEnum.ENDPOINT);
        matrixClientServicesServer.constructFDN(serviceNode.getComponentFDN(),serverRDN);
        matrixClientServicesServer.constructFunctionFDN(serviceNode.getNodeFunctionFDN(),serverRDN);
        matrixClientServicesServer.setComponentRDN(serverRDN);
        matrixClientServicesServer.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        matrixClientServicesServer.setaServer(true);
        matrixClientServicesServer.setHostDNSName(propertyFile.getMatrixClientServicesAPI().getServiceDNSEntry());
        matrixClientServicesServer.setPortType("HTTPS");
        matrixClientServicesServer.setServicePortOffset(propertyFile.getDefaultServicePortLowerBound());
        matrixClientServicesServer.setPortValue(propertyFile.getMatrixClientServicesAPI().getPortValue());
        matrixClientServicesServer.setServicePortValue(propertyFile.getMatrixClientServicesAPI().getServicePortValue());
        matrixClientServicesServer.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        serviceNode.getServiceEndpoints().add(matrixClientServicesServer.getComponentFDN());
        getLogger().debug(".buildClusterServiceEndpoints(): Exit");
    }

    public void buildProcessingPlantEndpoints(CommunicateRoomServerIMPropertyFile propertyFile, ProcessingPlantTopologyNode processingPlantNode) {
        getLogger().debug(".buildProcessingPlantEndpoints(): Entry");
        HTTPProcessingPlantTopologyEndpointPort matrixApplicationServicesClient = new HTTPProcessingPlantTopologyEndpointPort();
        String clientName = communicateIrisComponentNames.getEndpointClientName(communicateIrisComponentNames.getFunctionNameInteractMatrixApplicationServices());
        TopologyNodeRDN clientRDN = createNodeRDN(clientName, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), ComponentTypeTypeEnum.ENDPOINT);
        matrixApplicationServicesClient.constructFDN(processingPlantNode.getComponentFDN(),clientRDN);
        matrixApplicationServicesClient.constructFunctionFDN(processingPlantNode.getNodeFunctionFDN(),clientRDN);
        matrixApplicationServicesClient.setComponentRDN(clientRDN);
        matrixApplicationServicesClient.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_CLIENT);
        matrixApplicationServicesClient.setaServer(false);
        matrixApplicationServicesClient.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        processingPlantNode.getEndpoints().add(matrixApplicationServicesClient.getComponentFDN());

        HTTPProcessingPlantTopologyEndpointPort matrixClientServicesServer = new HTTPProcessingPlantTopologyEndpointPort();
        String appServicesName = roomServerComponentNames.getEndpointServerName(roomServerComponentNames.getFunctionNameMatrixClientServices());
        matrixClientServicesServer.setaServer(true);
        matrixClientServicesServer.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        matrixClientServicesServer.setPortType("HTTPS");
        matrixClientServicesServer.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        TopologyNodeRDN serverRDN = createNodeRDN(appServicesName, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), ComponentTypeTypeEnum.ENDPOINT);
        matrixClientServicesServer.constructFDN(processingPlantNode.getComponentFDN(),clientRDN);
        matrixClientServicesServer.constructFunctionFDN(processingPlantNode.getNodeFunctionFDN(),clientRDN);
        matrixClientServicesServer.setComponentRDN(clientRDN);
        matrixClientServicesServer.setHostDNSName(roomServerComponentNames.getDefaultInterfaceNameForBinding());
        matrixClientServicesServer.setPortValue(propertyFile.getMatrixClientServicesAPI().getPortValue());
        processingPlantNode.getEndpoints().add(matrixClientServicesServer.getComponentFDN());
        getLogger().debug(".buildProcessingPlantEndpoints(): Exit");
    }
}
