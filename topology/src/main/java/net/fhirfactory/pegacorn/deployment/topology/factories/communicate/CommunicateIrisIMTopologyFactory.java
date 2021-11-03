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
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.iris.im.CommunicateIrisIMPropertyFile;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common.PetasosEnabledSubsystemTopologyFactory;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCInterface;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCInterfaceDefinition;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.petasos.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.technologies.HTTPProcessingPlantTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.technologies.HTTPServerClusterServiceTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;

import javax.inject.Inject;

public abstract class CommunicateIrisIMTopologyFactory extends PetasosEnabledSubsystemTopologyFactory {


    public CommunicateIrisIMTopologyFactory(){
        super();
    }

    private CommunicateIrisIMPropertyFile getCommunicateIrisPropertyFile(){
        return((CommunicateIrisIMPropertyFile)getPropertyFile());
    }

    @Inject
    private CommunicateIrisSubsystemFunctionalityNames communicateIrisComponentNames;

    @Inject
    private CommunicateRoomServerComponentNames communicateRoomServerComponentNames;

    public void addApplicationServicesServerEndpoint( EndpointProviderInterface endpointProvider) {
        getLogger().debug(".addMatrixServicesServerEndpoint(): Entry");
        HTTPServerClusterServiceTopologyEndpointPort matrixApplicationServicesServer = new HTTPServerClusterServiceTopologyEndpointPort();
        String name = communicateIrisComponentNames.getEndpointServerName(communicateIrisComponentNames.getFunctionNameInteractMatrixApplicationServices());
        TopologyNodeRDN nodeRDN = createNodeRDN(name, getCommunicateIrisPropertyFile().getSubsystemInstant().getProcessingPlantVersion(), ComponentTypeTypeEnum.ENDPOINT);
        matrixApplicationServicesServer.setComponentRDN(nodeRDN);
        matrixApplicationServicesServer.constructFDN(endpointProvider.getComponentFDN(),nodeRDN);
        matrixApplicationServicesServer.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(),nodeRDN);
        matrixApplicationServicesServer.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        matrixApplicationServicesServer.setaServer(true);
        if(endpointProvider.getComponentType().equals(ComponentTypeTypeEnum.CLUSTER_SERVICE)) {
            matrixApplicationServicesServer.setHostDNSName(getCommunicateIrisPropertyFile().getInteractMatrixApplicationServicesServer().getServiceDNSEntry());
        } else {
            matrixApplicationServicesServer.setHostDNSName(getCommunicateIrisPropertyFile().getInteractMatrixApplicationServicesServer().getHostDNSEntry());
        }
        matrixApplicationServicesServer.setPortType("HTTPS");
        matrixApplicationServicesServer.setServicePortOffset(getCommunicateIrisPropertyFile().getInteractMatrixApplicationServicesServer().getClusterServicePortOffsetValue());
        matrixApplicationServicesServer.setPortValue(getCommunicateIrisPropertyFile().getInteractMatrixApplicationServicesServer().getPortValue());
        matrixApplicationServicesServer.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        Integer portValue = getCommunicateIrisPropertyFile().getDefaultServicePortLowerBound() + getCommunicateIrisPropertyFile().getInteractMatrixApplicationServicesServer().getClusterServicePortOffsetValue();
        matrixApplicationServicesServer.setServicePortValue(portValue);
        matrixApplicationServicesServer.setEncrypted(getCommunicateIrisPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        IPCInterface ipcInterface = new IPCInterface();
        ipcInterface.setEnablingTopologyEndpoint(matrixApplicationServicesServer.getComponentFDN());
        ipcInterface.setGroupName(name);
        for(InterfaceDefinitionSegment currentInterface: getCommunicateIrisPropertyFile().getInteractMatrixApplicationServicesServer().getSupportedInterfaceProfiles()){
            IPCInterfaceDefinition interfaceDef = new IPCInterfaceDefinition();
            interfaceDef.setInterfaceFormalVersion(currentInterface.getInterfaceDefinitionVersion());
            interfaceDef.setInterfaceFormalName(currentInterface.getInterfaceDefinitionName());
            ipcInterface.getSupportedInterfaceDefinitions().add(interfaceDef);
            ipcInterface.getSupportedDeploymentModes().add(endpointProvider.getResilienceMode());
        }
        matrixApplicationServicesServer.getSupportedInterfaceSet().add(ipcInterface);
        matrixApplicationServicesServer.setContainingNodeFDN(endpointProvider.getComponentFDN());
        endpointProvider.addEndpoint(matrixApplicationServicesServer.getComponentFDN());
        getLogger().trace(".addMatrixServicesServerEndpoint(): Add the MatrixApplicationServicesServer Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), matrixApplicationServicesServer);
        getLogger().debug(".addMatrixServicesServerEndpoint(): Exit");
    }

    public void addApplicationServicesClientEndpoint( EndpointProviderInterface endpointProvider) {
        getLogger().debug(".addApplicationServicesClientEndpoint(): Entry");
        HTTPProcessingPlantTopologyEndpointPort matrixClientServicesAPIClient = new HTTPProcessingPlantTopologyEndpointPort();
        String clientName = communicateIrisComponentNames.getEndpointClientName(communicateRoomServerComponentNames.getFunctionNameMatrixClientServices());
        TopologyNodeRDN nodeRDN = createNodeRDN(clientName, getCommunicateIrisPropertyFile().getSubsystemInstant().getProcessingPlantVersion(), ComponentTypeTypeEnum.ENDPOINT);
        matrixClientServicesAPIClient.setComponentRDN(nodeRDN);
        matrixClientServicesAPIClient.constructFDN(endpointProvider.getComponentFDN(),nodeRDN);
        matrixClientServicesAPIClient.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(),nodeRDN);
        matrixClientServicesAPIClient.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_CLIENT);
        matrixClientServicesAPIClient.setaServer(false);
        matrixClientServicesAPIClient.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        matrixClientServicesAPIClient.setEncrypted(getCommunicateIrisPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        endpointProvider.addEndpoint(matrixClientServicesAPIClient.getComponentFDN());
        getLogger().trace(".addApplicationServicesClientEndpoint(): Add the MatrixApplicationServicesClientEndpoint Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), matrixClientServicesAPIClient);
        getLogger().debug(".addApplicationServicesClientEndpoint(): Exit");
    }
}
