/*
 * The MIT License
 *
 * Copyright 2020 Mark A. Hunter.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes;

import net.fhirfactory.pegacorn.core.model.componentid.PegacornSystemComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.base.IPCAdapterDefinition;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.petasos.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.ClusteredInteractServerTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.StandardInteractClientTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.mllp.InteractMLLPClientEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.mllp.InteractMLLPServerEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.mllp.adapters.MLLPClientAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.mllp.adapters.MLLPServerAdapter;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.core.model.topology.nodes.external.ConnectedExternalSystemTopologyNode;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusteredInteractServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractClientPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common.PetasosEnabledSubsystemTopologyFactory;

/**
 * 
 * @author Mark A Hunter
 *
 */
public abstract class MITaFSubsystemTopologyFactory extends PetasosEnabledSubsystemTopologyFactory {

    //
    // Build an MLLP Server Endpoint
    //

    protected ClusteredInteractServerTopologyEndpointPort newMLLPServerEndpoint(EndpointProviderInterface endpointProvider, String endpointFunctionName, ClusteredInteractServerPortSegment mllpServerPort){
        getLogger().debug(".createMLLPServerEndpoint(): Entry, endpointProvider->{}, mllpServerPort->{}", endpointProvider, mllpServerPort);
        InteractMLLPServerEndpoint mllpServerTopologyNode = new InteractMLLPServerEndpoint();
        if(mllpServerPort == null){
            getLogger().debug(".createMLLPServerEndpoint(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.INTERACT_MLLP_SERVER, endpointFunctionName);
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        mllpServerTopologyNode.setComponentRDN(nodeRDN);
        mllpServerTopologyNode.setEndpointConfigurationName(mllpServerPort.getName());
        mllpServerTopologyNode.setActualHostIP(getActualHostIP());
        mllpServerTopologyNode.setActualPodIP(getActualPodIP());
        mllpServerTopologyNode.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        mllpServerTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.INTERACT_MLLP_SERVER);
        mllpServerTopologyNode.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        mllpServerTopologyNode.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        mllpServerTopologyNode.setComponentRDN(nodeRDN);
        mllpServerTopologyNode.setConnectedSystemName(mllpServerPort.getConnectedSystem().getSubsystemName());
        mllpServerTopologyNode.setContainingNodeFDN(endpointProvider.getComponentFDN());
        mllpServerTopologyNode.setServer(true);
        MLLPServerAdapter port = new MLLPServerAdapter();
        port.setPortNumber(mllpServerPort.getPortValue());
        port.setHostName(mllpServerPort.getHostDNSEntry());
        port.setServiceDNSName(mllpServerPort.getServiceDNSEntry());
        port.setServicePortValue(mllpServerPort.getServicePortValue());
        for(InterfaceDefinitionSegment currentSegment: mllpServerPort.getSupportedInterfaceProfiles()) {
            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
        }
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);

        endpointProvider.addEndpoint(mllpServerTopologyNode.getComponentFDN());
        getLogger().warn(".createMLLPServerEndpoint(): Add the {}/{} Port to the Topology Cache", mllpServerTopologyNode.getComponentRDN().getNodeName(), endpointFunctionName);
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), mllpServerTopologyNode);
        getLogger().debug(".createMLLPServerEndpoint(): Exit, endpoint added");
        return(mllpServerTopologyNode);
    }

    //
    // Build an MLLP Client Endpoint
    //

    protected StandardInteractClientTopologyEndpointPort newMLLPClientEndpoint(EndpointProviderInterface endpointProvider, String endpointFunctionName, StandardInteractClientPortSegment mllpClientPort){
        getLogger().debug(".newMLLPClientEndpoint(): Entry, endpointProvider->{}, mllpClientPort->{}", endpointProvider, mllpClientPort);
        InteractMLLPClientEndpoint mllpClientTopologyNode = new InteractMLLPClientEndpoint();
        if(mllpClientPort == null){
            getLogger().debug(".newMLLPClientEndpoint(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.INTERACT_MLLP_CLIENT, endpointFunctionName);
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        mllpClientTopologyNode.setComponentRDN(nodeRDN);
        mllpClientTopologyNode.setEndpointConfigurationName(mllpClientPort.getName());
        mllpClientTopologyNode.setActualPodIP(getActualPodIP());
        mllpClientTopologyNode.setActualHostIP(getActualHostIP());
        mllpClientTopologyNode.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        mllpClientTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.INTERACT_MLLP_CLIENT);
        mllpClientTopologyNode.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        mllpClientTopologyNode.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        mllpClientTopologyNode.setComponentRDN(nodeRDN);
        mllpClientTopologyNode.setContainingNodeFDN(endpointProvider.getComponentFDN());
        ConnectedSystemProperties connectedSystem = mllpClientPort.getConnectedSystem();

        mllpClientTopologyNode.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTopologyNode externalSystem = new ConnectedExternalSystemTopologyNode();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
        ConnectedSystemPort targetPort1 = connectedSystem.getTargetPort1();
        MLLPClientAdapter systemEndpointPort1 = newMLLPClientAdapter(targetPort1);
        externalSystem.getTargetPorts().add(systemEndpointPort1);
        if(connectedSystem.getTargetPort2() != null)
        {
            ConnectedSystemPort targetPort2 = connectedSystem.getTargetPort2();
            MLLPClientAdapter systemEndpointPort2 = newMLLPClientAdapter(targetPort2);
            externalSystem.getTargetPorts().add(systemEndpointPort2);
        }
        if(connectedSystem.getTargetPort3() != null)
        {
            ConnectedSystemPort targetPort3 = connectedSystem.getTargetPort3();
            MLLPClientAdapter systemEndpointPort3 = newMLLPClientAdapter(targetPort3);
            externalSystem.getTargetPorts().add(systemEndpointPort3);
        }
        mllpClientTopologyNode.setTargetSystem(externalSystem);
        endpointProvider.addEndpoint(mllpClientTopologyNode.getComponentFDN());
        getLogger().warn(".newMLLPClientEndpoint(): Add the {}/{} Port to the Topology Cache", mllpClientTopologyNode.getComponentRDN().getNodeName(), endpointFunctionName);
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), mllpClientTopologyNode);
        getLogger().debug(".newMLLPClientEndpoint(): Exit, endpoint added");
        return(mllpClientTopologyNode);
    }

    protected MLLPClientAdapter newMLLPClientAdapter(ConnectedSystemPort connectedSystemPort) {
        getLogger().debug(".newExternalSystemIPCEndpoint(): Entry, connectedSystemPort->{}", connectedSystemPort);
        MLLPClientAdapter systemEndpointPort = new MLLPClientAdapter();
        boolean encryptionRequired = false;
        if(connectedSystemPort.getEncryptionRequired() != null){
            encryptionRequired = connectedSystemPort.getEncryptionRequired();
        }
        systemEndpointPort.setEncrypted(encryptionRequired);
        systemEndpointPort.setHostName(connectedSystemPort.getTargetPortDNSName());
        systemEndpointPort.setPortNumber(connectedSystemPort.getTargetPortValue());
        IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
        currentInterfaceDefinition.setInterfaceFormalName(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionName());
        currentInterfaceDefinition.setInterfaceFormalVersion(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionVersion());
        systemEndpointPort.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        getLogger().debug(".newExternalSystemIPCEndpoint(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }

}
