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

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.names.common.SubsystemBaseNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.CommunicateIrisComponentNames;
import net.fhirfactory.pegacorn.deployment.names.subsystems.CommunicateRoomServerComponentNames;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.iris.im.CommunicateIrisIMPropertyFile;
import net.fhirfactory.pegacorn.deployment.topology.map.common.archetypes.common.PegacornTopologyFactoryBase;
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
public class CommunicateIrisIMTopologyFactory extends PegacornTopologyFactoryBase {
    private static final Logger LOG = LoggerFactory.getLogger(CommunicateIrisIMTopologyFactory.class);

    @Override
    protected SubsystemBaseNames specifySubsystemBaseNames() {
        return (communicateIrisComponentNames);
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    @Inject
    private CommunicateIrisComponentNames communicateIrisComponentNames;

    @Inject
    private CommunicateRoomServerComponentNames communicateRoomServerComponentNames;

    public void buildClusterServiceEndpoints(CommunicateIrisIMPropertyFile propertyFile, ClusterServiceTopologyNode serviceNode) {
        LOG.debug(".buildClusterServiceEndpoints(): Entry");
        IPCEndpoint matrixApplicationServicesServer = new IPCEndpoint();
        String name = communicateIrisComponentNames.getEndpointServerName(communicateIrisComponentNames.getFunctionNameInteractMatrixApplicationServices());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.ENDPOINT);
        matrixApplicationServicesServer.constructFDN(serviceNode.getNodeFDN(),nodeRDN);
        matrixApplicationServicesServer.constructFunctionFDN(serviceNode.getNodeFunctionFDN(),nodeRDN);
        matrixApplicationServicesServer.setEndpointType(EndpointTypeEnum.HTTP_API_SERVER);
        matrixApplicationServicesServer.setaServer(true);
        matrixApplicationServicesServer.setInterfaceDNSName(propertyFile.getInteractMatrixApplicationServicesServer().getInterfaceDNSName());
        matrixApplicationServicesServer.setPortType("HTTPS");
        matrixApplicationServicesServer.setServicePortOffset(propertyFile.getInteractMatrixApplicationServicesServer().getServicePortOffset());
        matrixApplicationServicesServer.setPortValue(propertyFile.getInteractMatrixApplicationServicesServer().getPortValue());
        Integer portValue = propertyFile.getDefaultServicePortLowerBound() + propertyFile.getInteractMatrixApplicationServicesServer().getServicePortOffset();
        matrixApplicationServicesServer.setServicePortValue(portValue);
        matrixApplicationServicesServer.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        serviceNode.getClusterServiceServerEndpoints().put(nodeRDN, matrixApplicationServicesServer);
        LOG.debug(".buildClusterServiceEndpoints(): Exit");
    }

    public void buildProcessingPlantEndpoints(CommunicateIrisIMPropertyFile propertyFile, ProcessingPlantTopologyNode processingPlantNode) {
        LOG.debug(".buildProcessingPlantEndpoints(): Entry");
        PegacornHTTPProcessingPlantPort matrixClientServicesAPIClient = new PegacornHTTPProcessingPlantPort();
        String clientName = communicateIrisComponentNames.getEndpointClientName(communicateRoomServerComponentNames.getFunctionNameMatrixClientServices());
        TopologyNodeRDN nodeRDN = createNodeRDN(clientName, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.ENDPOINT);
        matrixClientServicesAPIClient.constructFDN(processingPlantNode.getNodeFDN(),nodeRDN);
        matrixClientServicesAPIClient.constructFunctionFDN(processingPlantNode.getNodeFunctionFDN(),nodeRDN);
        matrixClientServicesAPIClient.setEndpointType(EndpointTypeEnum.HTTP_API_CLIENT);
        matrixClientServicesAPIClient.setaServer(false);
        matrixClientServicesAPIClient.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        processingPlantNode.getEnpoints().put(nodeRDN, matrixClientServicesAPIClient);

        PegacornHTTPProcessingPlantPort matrixApplicationServicesServer = new PegacornHTTPProcessingPlantPort();
        String appServicesName = communicateIrisComponentNames.getEndpointServerName(communicateIrisComponentNames.getFunctionNameInteractMatrixApplicationServices());
        matrixApplicationServicesServer.setaServer(true);
        matrixApplicationServicesServer.setEncrypted(propertyFile.getDeploymentMode().isUsingInternalEncryption());
        matrixApplicationServicesServer.setPortType("HTTPS");
        matrixApplicationServicesServer.setEndpointType(EndpointTypeEnum.HTTP_API_SERVER);
        TopologyNodeRDN appServerRDN = createNodeRDN(appServicesName, propertyFile.getSubsystemInstant().getProcessingPlantVersion(), TopologyNodeTypeEnum.ENDPOINT);
        matrixClientServicesAPIClient.constructFDN(processingPlantNode.getNodeFDN(),appServerRDN);
        matrixClientServicesAPIClient.constructFunctionFDN(processingPlantNode.getNodeFunctionFDN(),appServerRDN);
        matrixApplicationServicesServer.setInterfaceDNSName(communicateIrisComponentNames.getDefaultInterfaceNameForBinding());
        matrixApplicationServicesServer.setPortValue(propertyFile.getInteractMatrixApplicationServicesServer().getPortValue());
        processingPlantNode.getEnpoints().put(appServerRDN, matrixApplicationServicesServer);
        LOG.debug(".buildProcessingPlantEndpoints(): Exit");
    }


}
