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
package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.fhirpersistence.im;

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractClientPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common.PetasosEnabledSubsystemTopologyFactory;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCInterfaceDefinition;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.base.ExternalSystemIPCEndpoint;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.PetasosTopologyEndpointTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.interact.StandardInteractClientTopologyEndpointPort;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.external.ConnectedExternalSystemTopologyNode;

public abstract class FHIRIMSubsystemTopologyFactory extends PetasosEnabledSubsystemTopologyFactory {

    //
    // Build an HTTP Client Endpoint
    //

    protected StandardInteractClientTopologyEndpointPort newHTTPClient(EndpointProviderInterface endpointProvider, String endpointFunctionName, StandardInteractClientPortSegment mllpClientPort){
        getLogger().debug(".newHTTPClient(): Entry, endpointProvider->{}, mllpClientPort->{}", endpointProvider, mllpClientPort);
        StandardInteractClientTopologyEndpointPort httpFHIRClient = new StandardInteractClientTopologyEndpointPort();
        if(mllpClientPort == null){
            getLogger().debug(".newHTTPClient(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointServerName(endpointFunctionName);
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getNodeRDN().getNodeVersion(), TopologyNodeTypeEnum.ENDPOINT);
        httpFHIRClient.setNodeRDN(nodeRDN);
        httpFHIRClient.setName(endpointFunctionName);
        httpFHIRClient.constructFDN(endpointProvider.getNodeFDN(), nodeRDN);
        httpFHIRClient.setEndpointType(PetasosTopologyEndpointTypeEnum.HTTP_API_CLIENT);
        httpFHIRClient.setComponentType(TopologyNodeTypeEnum.ENDPOINT);
        httpFHIRClient.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        httpFHIRClient.setNodeRDN(nodeRDN);
        httpFHIRClient.setContainingNodeFDN(endpointProvider.getNodeFDN());
        ConnectedSystemProperties connectedSystem = mllpClientPort.getConnectedSystem();
        ConnectedExternalSystemTopologyNode externalSystem = new ConnectedExternalSystemTopologyNode();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
        ConnectedSystemPort targetPort1 = connectedSystem.getTargetPort1();
        ExternalSystemIPCEndpoint systemEndpointPort1 = newExternalSystemIPCEndpoint(targetPort1);
        externalSystem.getTargetPorts().add(systemEndpointPort1);
        if(connectedSystem.getTargetPort2() != null)
        {
            ConnectedSystemPort targetPort2 = connectedSystem.getTargetPort2();
            ExternalSystemIPCEndpoint systemEndpointPort2 = newExternalSystemIPCEndpoint(targetPort2);
            externalSystem.getTargetPorts().add(systemEndpointPort2);
        }
        if(connectedSystem.getTargetPort3() != null)
        {
            ConnectedSystemPort targetPort3 = connectedSystem.getTargetPort3();
            ExternalSystemIPCEndpoint systemEndpointPort3 = newExternalSystemIPCEndpoint(targetPort3);
            externalSystem.getTargetPorts().add(systemEndpointPort3);
        }
        httpFHIRClient.setTargetSystem(externalSystem);
        endpointProvider.addEndpoint(httpFHIRClient.getNodeFDN());
        getLogger().trace(".newHTTPClient(): Add the httpFHIRClient Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getNodeFDN(), httpFHIRClient);
        getLogger().debug(".newHTTPClient(): Exit, endpoint added");
        return(httpFHIRClient);
    }

    protected ExternalSystemIPCEndpoint newExternalSystemIPCEndpoint(ConnectedSystemPort connectedSystemPort) {
        getLogger().debug(".newExternalSystemIPCEndpoint(): Entry, connectedSystemPort->{}", connectedSystemPort);
        ExternalSystemIPCEndpoint systemEndpointPort = new ExternalSystemIPCEndpoint();
        systemEndpointPort.setEncryptionRequired(connectedSystemPort.getEncryptionRequired());
        systemEndpointPort.setTargetPortDNSName(connectedSystemPort.getTargetPortDNSName());
        systemEndpointPort.setTargetPortValue(connectedSystemPort.getTargetPortValue());
        IPCInterfaceDefinition currentInterfaceDefinition = new IPCInterfaceDefinition();
        currentInterfaceDefinition.setInterfaceFormalName(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionName());
        currentInterfaceDefinition.setInterfaceFormalVersion(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionVersion());
        systemEndpointPort.setSupportedInterfaceDefinition(currentInterfaceDefinition);
        getLogger().debug(".newExternalSystemIPCEndpoint(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }
}
