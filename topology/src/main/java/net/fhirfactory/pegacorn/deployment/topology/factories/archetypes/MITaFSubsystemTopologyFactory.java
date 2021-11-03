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

import net.fhirfactory.pegacorn.core.model.componentid.ComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusteredInteractServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractClientPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.common.PetasosEnabledSubsystemTopologyFactory;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCInterfaceDefinition;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.petasos.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.ClusteredInteractServerTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.StandardInteractClientTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.mllp.InteractMLLPClientAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.mllp.InteractMLLPServerAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.mllp.datatype.MLLPPort;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.core.model.topology.nodes.external.ConnectedExternalSystemTopologyNode;

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
        InteractMLLPServerAdapter mllpServerTopologyNode = new InteractMLLPServerAdapter();
        if(mllpServerPort == null){
            getLogger().debug(".createMLLPServerEndpoint(): Exit, no port to add");
            return(null);
        }
        mllpServerTopologyNode.setEncrypted(mllpServerPort.isEncrypted());
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.MLLP_SERVER, endpointFunctionName);
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        mllpServerTopologyNode.setComponentRDN(nodeRDN);
        mllpServerTopologyNode.setActualHostIP(getActualHostIP());
        mllpServerTopologyNode.setActualPodIP(getActualPodIP());
        mllpServerTopologyNode.setName(endpointFunctionName);
        mllpServerTopologyNode.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        mllpServerTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.MLLP_SERVER);
        mllpServerTopologyNode.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        mllpServerTopologyNode.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        mllpServerTopologyNode.setComponentRDN(nodeRDN);
        mllpServerTopologyNode.setConnectedSystemName(mllpServerPort.getConnectedSystem().getSubsystemName());
        mllpServerTopologyNode.setContainingNodeFDN(endpointProvider.getComponentFDN());
        MLLPPort port = new MLLPPort();
        port.setPort(Integer.toString(mllpServerPort.getPortValue()));
        port.setServer(true);
        port.setHostName(mllpServerPort.getHostDNSEntry());
        for(InterfaceDefinitionSegment currentSegment: mllpServerPort.getSupportedInterfaceProfiles()) {
            IPCInterfaceDefinition currentInterfaceDefinition = new IPCInterfaceDefinition();
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
        InteractMLLPClientAdapter mllpClientTopologyNode = new InteractMLLPClientAdapter();
        if(mllpClientPort == null){
            getLogger().debug(".newMLLPClientEndpoint(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.MLLP_CLIENT, endpointFunctionName);
        TopologyNodeRDN nodeRDN = createNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), ComponentTypeTypeEnum.ENDPOINT);
        mllpClientTopologyNode.setComponentRDN(nodeRDN);
        mllpClientTopologyNode.setActualPodIP(getActualPodIP());
        mllpClientTopologyNode.setActualHostIP(getActualHostIP());
        mllpClientTopologyNode.setName(endpointFunctionName);
        mllpClientTopologyNode.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        mllpClientTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.MLLP_CLIENT);
        mllpClientTopologyNode.setComponentType(ComponentTypeTypeEnum.ENDPOINT);
        mllpClientTopologyNode.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN );
        mllpClientTopologyNode.setComponentRDN(nodeRDN);
        mllpClientTopologyNode.setContainingNodeFDN(endpointProvider.getComponentFDN());
        ConnectedSystemProperties connectedSystem = mllpClientPort.getConnectedSystem();

        mllpClientTopologyNode.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTopologyNode externalSystem = new ConnectedExternalSystemTopologyNode();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
        ConnectedSystemPort targetPort1 = connectedSystem.getTargetPort1();
        MLLPPort systemEndpointPort1 = newExternalSystemIPCEndpoint(targetPort1, mllpClientTopologyNode.isEncrypted());
        externalSystem.getTargetPorts().add(systemEndpointPort1);
        if(connectedSystem.getTargetPort2() != null)
        {
            ConnectedSystemPort targetPort2 = connectedSystem.getTargetPort2();
            MLLPPort systemEndpointPort2 = newExternalSystemIPCEndpoint(targetPort2, mllpClientTopologyNode.isEncrypted());
            externalSystem.getTargetPorts().add(systemEndpointPort2);
        }
        if(connectedSystem.getTargetPort3() != null)
        {
            ConnectedSystemPort targetPort3 = connectedSystem.getTargetPort3();
            MLLPPort systemEndpointPort3 = newExternalSystemIPCEndpoint(targetPort3, mllpClientTopologyNode.isEncrypted());
            externalSystem.getTargetPorts().add(systemEndpointPort3);
        }
        mllpClientTopologyNode.setTargetSystem(externalSystem);
        endpointProvider.addEndpoint(mllpClientTopologyNode.getComponentFDN());
        getLogger().warn(".newMLLPClientEndpoint(): Add the {}/{} Port to the Topology Cache", mllpClientTopologyNode.getComponentRDN().getNodeName(), endpointFunctionName);
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), mllpClientTopologyNode);
        getLogger().debug(".newMLLPClientEndpoint(): Exit, endpoint added");
        return(mllpClientTopologyNode);
    }

    protected MLLPPort newExternalSystemIPCEndpoint(ConnectedSystemPort connectedSystemPort, boolean defaultEncryption) {
        getLogger().debug(".newExternalSystemIPCEndpoint(): Entry, connectedSystemPort->{}", connectedSystemPort);
        MLLPPort systemEndpointPort = new MLLPPort();
        boolean encryptionRequired = defaultEncryption;
        if(connectedSystemPort.getEncryptionRequired() != null){
            encryptionRequired = connectedSystemPort.getEncryptionRequired();
        }
        systemEndpointPort.setEncrypted(encryptionRequired);
        systemEndpointPort.setHostName(connectedSystemPort.getTargetPortDNSName());
        systemEndpointPort.setPort(Integer.toString(connectedSystemPort.getTargetPortValue()));
        IPCInterfaceDefinition currentInterfaceDefinition = new IPCInterfaceDefinition();
        currentInterfaceDefinition.setInterfaceFormalName(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionName());
        currentInterfaceDefinition.setInterfaceFormalVersion(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionVersion());
        systemEndpointPort.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        getLogger().debug(".newExternalSystemIPCEndpoint(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }

}
