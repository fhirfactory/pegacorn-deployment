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
package net.fhirfactory.pegacorn.deployment.topology.map.communicate;

import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.CommunicateIrisComponentNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.CommunicateRoomServerComponentNames;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.roomserver.im.CommunicateRoomServerIMPropertyFile;
import net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.common.PetasosEnabledSubsystemTopologyFactory;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCEndpoint;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.EndpointTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.PegacornHTTPProcessingPlantPort;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.ClusterServiceTopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.ProcessingPlantTopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public  class CommunicateRoomServerIMTopologyFactory extends PetasosEnabledSubsystemTopologyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(CommunicateRoomServerIMTopologyFactory.class);

    @Inject
    private CommunicateRoomServerComponentNames roomServerComponentNames;

    @Override
    protected SubsystemBaseNames specifySubsystemBaseNames() {
        return (roomServerComponentNames);
    }

    @Inject
    private CommunicateIrisComponentNames communicateIrisComponentNames;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public void buildClusterServiceEndpoints(CommunicateRoomServerIMPropertyFile propertyFile, ClusterServiceTopologyNode serviceNode) {
        LOG.debug(".buildClusterServiceEndpoints(): Entry");
        IPCEndpoint matrixClientServicesServer = new IPCEndpoint();
        String name = roomServerComponentNames.getEndpointServerName(roomServerComponentNames.getFunctionNameMatrixClientServices());
        TopologyNodeRDN serverRDN = createNodeRDN(name, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.ENDPOINT);
        matrixClientServicesServer.constructFDN(serviceNode.getNodeFDN(),serverRDN);
        matrixClientServicesServer.constructFunctionFDN(serviceNode.getNodeFunctionFDN(),serverRDN);
        matrixClientServicesServer.setNodeRDN(serverRDN);
        matrixClientServicesServer.setEndpointType(EndpointTypeEnum.HTTP_API_SERVER);
        matrixClientServicesServer.setaServer(true);
        matrixClientServicesServer.setInterfaceDNSName(propertyFile.getMatrixClientServicesAPI().getInterfaceDNSName());
        matrixClientServicesServer.setPortType("HTTPS");
        matrixClientServicesServer.setServicePortOffset(propertyFile.getDefaultServicePortLowerBound());
        matrixClientServicesServer.setPortValue(propertyFile.getMatrixClientServicesAPI().getPortValue());
        matrixClientServicesServer.setServicePortValue(propertyFile.getMatrixClientServicesAPI().getServicePortValue());
        matrixClientServicesServer.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        serviceNode.getClusterServiceServerEndpoints().put(serverRDN, matrixClientServicesServer);
        LOG.debug(".buildClusterServiceEndpoints(): Exit");
    }

    public void buildProcessingPlantEndpoints(CommunicateRoomServerIMPropertyFile propertyFile, ProcessingPlantTopologyNode processingPlantNode) {
        LOG.debug(".buildProcessingPlantEndpoints(): Entry");
        PegacornHTTPProcessingPlantPort matrixApplicationServicesClient = new PegacornHTTPProcessingPlantPort();
        String clientName = communicateIrisComponentNames.getEndpointClientName(communicateIrisComponentNames.getFunctionNameInteractMatrixApplicationServices());
        TopologyNodeRDN clientRDN = createNodeRDN(clientName, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.ENDPOINT);
        matrixApplicationServicesClient.constructFDN(processingPlantNode.getNodeFDN(),clientRDN);
        matrixApplicationServicesClient.constructFunctionFDN(processingPlantNode.getNodeFunctionFDN(),clientRDN);
        matrixApplicationServicesClient.setNodeRDN(clientRDN);
        matrixApplicationServicesClient.setEndpointType(EndpointTypeEnum.HTTP_API_CLIENT);
        matrixApplicationServicesClient.setaServer(false);
        matrixApplicationServicesClient.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        processingPlantNode.getEnpoints().put(clientRDN, matrixApplicationServicesClient);

        PegacornHTTPProcessingPlantPort matrixClientServicesServer = new PegacornHTTPProcessingPlantPort();
        String appServicesName = roomServerComponentNames.getEndpointServerName(roomServerComponentNames.getFunctionNameMatrixClientServices());
        matrixClientServicesServer.setaServer(true);
        matrixClientServicesServer.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        matrixClientServicesServer.setPortType("HTTPS");
        matrixClientServicesServer.setEndpointType(EndpointTypeEnum.HTTP_API_SERVER);
        TopologyNodeRDN serverRDN = createNodeRDN(appServicesName, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.ENDPOINT);
        matrixClientServicesServer.constructFDN(processingPlantNode.getNodeFDN(),clientRDN);
        matrixClientServicesServer.constructFunctionFDN(processingPlantNode.getNodeFunctionFDN(),clientRDN);
        matrixClientServicesServer.setNodeRDN(clientRDN);
        matrixClientServicesServer.setInterfaceDNSName(roomServerComponentNames.getDefaultInterfaceNameForBinding());
        matrixClientServicesServer.setPortValue(propertyFile.getMatrixClientServicesAPI().getPortValue());
        processingPlantNode.getEnpoints().put(serverRDN, matrixClientServicesServer);
        LOG.debug(".buildProcessingPlantEndpoints(): Exit");
    }
}