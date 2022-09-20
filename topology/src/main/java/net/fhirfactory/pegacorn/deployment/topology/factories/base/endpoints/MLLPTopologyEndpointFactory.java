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
package net.fhirfactory.pegacorn.deployment.topology.factories.base.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import net.fhirfactory.dricats.configuration.defaults.dricats.subsystems.mitaf.mllp.MLLPConfigurationDefaults;
import net.fhirfactory.dricats.configuration.defaults.dricats.systemwide.DefaultInterfaceNameSet;
import net.fhirfactory.dricats.model.component.datatypes.ComponentTypeDefinition;
import net.fhirfactory.dricats.model.component.valuesets.ComponentTypeEnum;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.subsystems.mitaf.MITaFSubsystemPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.dricats.model.configuration.filebased.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.base.InterfaceDefinitionSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.mllp.MLLPReceiverEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.mllp.MLLPSenderEndpointSegment;
import net.fhirfactory.dricats.model.topology.endpoints.base.adapters.IPCAdapterDefinition;
import net.fhirfactory.dricats.model.topology.endpoints.base.valuesets.EndpointResilienceModeEnum;
import net.fhirfactory.dricats.model.topology.endpoints.base.valuesets.EndpointTopologyNodeTypeEnum;
import net.fhirfactory.dricats.model.topology.endpoints.mllp.MLLPClientETN;
import net.fhirfactory.dricats.model.topology.endpoints.mllp.MLLPServerETN;
import net.fhirfactory.dricats.model.topology.endpoints.mllp.adapters.MLLPClientAdapter;
import net.fhirfactory.dricats.model.topology.endpoints.mllp.adapters.MLLPServerAdapter;
import net.fhirfactory.dricats.model.topology.nodes.external.ConnectedExternalSystemTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.mllp.MLLPReceiverProcessorTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.mllp.MLLPSenderProcessorTN;
import net.fhirfactory.dricats.model.topology.nodes.valuesets.TopologyNodeNetworkingContextEnum;
import net.fhirfactory.dricats.configuration.api.services.common.PlatformInformationHelper;
import net.fhirfactory.pegacorn.deployment.topology.solution.LocalSolution;

@ApplicationScoped
public class MLLPTopologyEndpointFactory extends PlatformInformationHelper {
    private static final Logger LOG = LoggerFactory.getLogger(MLLPTopologyEndpointFactory.class);

    @Inject
    private DefaultInterfaceNameSet interfaceNames;

    @Inject
    private LocalSolution solutionMap;

    //
    // Constructor(s)
    //

    //
    // Getters and Setters
    //

    protected Logger getLogger(){
        return(LOG);
    }

    protected DefaultInterfaceNameSet getInterfaceNames(){
        return(interfaceNames);
    }

    protected LocalSolution getSolutionMap(){
        return(solutionMap);
    }

    //
    // Business Methods
    //

    public MLLPServerETN newMLLPServerEndpoint(MITaFSubsystemPropertyFile propertyFile, MLLPReceiverProcessorTN parentWUP, String endpointFunctionName, MLLPReceiverEndpointSegment mllpReceiverPortSegment){
        getLogger().debug(".createMLLPServerEndpoint(): Entry, parentWUP->{}, mllpServerPort->{}", parentWUP, mllpReceiverPortSegment);
        MLLPServerETN mllpServerTopologyNode = new MLLPServerETN();
        if(mllpReceiverPortSegment == null){
            getLogger().debug(".createMLLPServerEndpoint(): Exit, no port to add");
            return(null);
        }
        mllpServerTopologyNode.constructNodeID(ComponentTypeEnum.ENDPOINT.getDisplayName());
        mllpServerTopologyNode.setEndpointConfigurationName(mllpReceiverPortSegment.getName());
        mllpServerTopologyNode.setActualHostIP(getActualHostIP());
        mllpServerTopologyNode.setEndpointType(EndpointTopologyNodeTypeEnum.MLLP_SERVER);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("MLLPServer");
        componentType.setDisplayTypeName("MLLP Server");
        mllpServerTopologyNode.setNodeType(componentType);
        mllpServerTopologyNode.setNetworkContext(TopologyNodeNetworkingContextEnum.COMPONENT_ROLE_INTERACT_INGRES);
        mllpServerTopologyNode.setConnectedSystemName(mllpReceiverPortSegment.getConnectedSystem().getSubsystemName());
        mllpServerTopologyNode.setServer(true);
        mllpServerTopologyNode.setServiceActualIP(mllpReceiverPortSegment.getServiceDNS());
        mllpServerTopologyNode.setServicePortValue(mllpReceiverPortSegment.getServicePort());
        
        MLLPServerAdapter port = new MLLPServerAdapter();
        port.setPortNumber(mllpReceiverPortSegment.getServerPort());
        port.setHostName(mllpReceiverPortSegment.getServerHostname());
        port.setServiceDNSName(mllpReceiverPortSegment.getServiceDNS());
        port.setServicePortValue(mllpReceiverPortSegment.getServicePort());
        port.setTargetSystemName(mllpReceiverPortSegment.getConnectedSystem().getExternalisedServiceName());
        port.setEncrypted(mllpReceiverPortSegment.isEncrypted());
        port.setEnablingTopologyEndpoint(mllpServerTopologyNode.getNodeId());
        for(InterfaceDefinitionSegment currentSegment: mllpReceiverPortSegment.getSupportedInterfaceProfiles()) {
            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            port.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        }
        port.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        port.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        port.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        port.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        port.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        port.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        port.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        port.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        if(mllpReceiverPortSegment.hasAcceptTimeout()){
            port.setAcceptTimeout(mllpReceiverPortSegment.getAcceptTimeout());
        } else {
            port.setAcceptTimeout(MLLPConfigurationDefaults.ACCEPT_TIMEOUT);
        }
        if(mllpReceiverPortSegment.hasBindTimeout()){
            port.setBindTimeout(mllpReceiverPortSegment.getBindTimeout());
        } else {
            port.setBindTimeout(MLLPConfigurationDefaults.BIND_TIMEOUT);
        }
        if(mllpReceiverPortSegment.hasConvertToStringPayload()){
            port.setConvertToStringPayload(mllpReceiverPortSegment.getConvertToStringPayload());
        } else {
            port.setConvertToStringPayload(MLLPConfigurationDefaults.CONVERT_TO_STRING_PAYLOAD);
        }
        if(mllpReceiverPortSegment.hasValidatePayload()){
            port.setValidatePayload(mllpReceiverPortSegment.getValidatePayload());
        } else {
            port.setValidatePayload(MLLPConfigurationDefaults.VALIDATE_PAYLOAD);
        }
        if(mllpReceiverPortSegment.hasMaximumConcurrentConsumers()){
            port.setMaximumConcurrentConsumers(mllpReceiverPortSegment.getMaximumConcurrentConsumers());
        } else {
            port.setMaximumConcurrentConsumers(MLLPConfigurationDefaults.MAXIMUM_CONCURRENT_CONSUMERS);
        }
        //
        // Add the Adapter to the endpoint
        mllpServerTopologyNode.setMLLPServerAdapter(port);
        
        mllpServerTopologyNode.setDescription("Server-->MLLP:"+propertyFile.getLoadBalancer().getIpAddress()+":"+Integer.toString(port.getServicePortValue()));
        mllpServerTopologyNode.setParticipantName(parentWUP.getParticipantName()+"."+"MLLP.Server."+propertyFile.getLoadBalancer().getIpAddress()+"."+Integer.toString(port.getServicePortValue()));
        mllpServerTopologyNode.setParticipantDisplayName("MLLP.Server:"+propertyFile.getLoadBalancer().getIpAddress()+":"+Integer.toString(port.getServicePortValue()));
        
        // Add the endpoint to the provider
        parentWUP.setIngresEndpoint(mllpServerTopologyNode);
        //
        // Add the endpoint to the topology cache
        getLogger().trace(".newHTTPServerTopologyEndpoint(): Add the HTTP Server Port to the Topology Cache");
        getSolutionMap().addTopologyNode(mllpServerTopologyNode);
        //
        // We're done
        getLogger().debug(".createMLLPServerEndpoint(): Exit, endpoint added->{}", mllpServerTopologyNode);
        return(mllpServerTopologyNode);
    }

    //
    // Build an MLLP Client Endpoint
    //

    public MLLPClientETN newMLLPClientEndpoint(MITaFSubsystemPropertyFile propertyFile, MLLPSenderProcessorTN parentWUP, String endpointFunctionName, MLLPSenderEndpointSegment mllpClientPortSegment){
        getLogger().debug(".newMLLPClientEndpoint(): Entry, parentWUP->{}, mllpClientPort->{}", parentWUP, mllpClientPortSegment);
        MLLPClientETN mllpClientTopologyNode = new MLLPClientETN();
        if(mllpClientPortSegment == null){
            getLogger().debug(".newMLLPClientEndpoint(): Exit, no port to add");
            return(null);
        }
        mllpClientTopologyNode.constructNodeID(ComponentTypeEnum.ENDPOINT.getDisplayName());
        mllpClientTopologyNode.setEndpointConfigurationName(mllpClientPortSegment.getName());
        mllpClientTopologyNode.setEndpointType(EndpointTopologyNodeTypeEnum.MLLP_CLIENT);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("MLLPClient");
        componentType.setDisplayTypeName("MLLP Client");
        mllpClientTopologyNode.setNodeType(componentType);
        mllpClientTopologyNode.setNetworkContext(TopologyNodeNetworkingContextEnum.COMPONENT_ROLE_INTERACT_EGRESS);
        ConnectedSystemProperties connectedSystem = mllpClientPortSegment.getConnectedSystem();
        mllpClientTopologyNode.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTN externalSystem = new ConnectedExternalSystemTN();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
        ConnectedSystemPort targetPort1 = connectedSystem.getTargetPort1();
        MLLPClientAdapter systemEndpointPort1 = newMLLPClientAdapter(targetPort1);
        if(mllpClientPortSegment.hasKeepAlive()){
            systemEndpointPort1.setKeepAlive(mllpClientPortSegment.getKeepAlive());
        } else {
            systemEndpointPort1.setKeepAlive(MLLPConfigurationDefaults.KEEP_ALIVE);
        }
        if(mllpClientPortSegment.hasConnectionTimeout()){
            systemEndpointPort1.setConnectTimeout(mllpClientPortSegment.getConnectionTimeout());
        } else {
            systemEndpointPort1.setConnectTimeout(MLLPConfigurationDefaults.CONNECT_TIMEOUT);
        }
        externalSystem.getTargetPorts().add(systemEndpointPort1);
        if(connectedSystem.getTargetPort2() != null)
        {
            ConnectedSystemPort targetPort2 = connectedSystem.getTargetPort2();
            MLLPClientAdapter systemEndpointPort2 = newMLLPClientAdapter(targetPort2);
            if(mllpClientPortSegment.hasKeepAlive()){
                systemEndpointPort2.setKeepAlive(mllpClientPortSegment.getKeepAlive());
            } else {
                systemEndpointPort2.setKeepAlive(MLLPConfigurationDefaults.KEEP_ALIVE);
            }
            if(mllpClientPortSegment.hasConnectionTimeout()){
                systemEndpointPort2.setConnectTimeout(mllpClientPortSegment.getConnectionTimeout());
            } else {
                systemEndpointPort2.setConnectTimeout(MLLPConfigurationDefaults.CONNECT_TIMEOUT);
            }
            externalSystem.getTargetPorts().add(systemEndpointPort2);
        }
        if(connectedSystem.getTargetPort3() != null)
        {
            ConnectedSystemPort targetPort3 = connectedSystem.getTargetPort3();
            MLLPClientAdapter systemEndpointPort3 = newMLLPClientAdapter(targetPort3);
            if(mllpClientPortSegment.hasKeepAlive()){
                systemEndpointPort3.setKeepAlive(mllpClientPortSegment.getKeepAlive());
            } else {
                systemEndpointPort3.setKeepAlive(MLLPConfigurationDefaults.KEEP_ALIVE);
            }
            if(mllpClientPortSegment.hasConnectionTimeout()){
                systemEndpointPort3.setConnectTimeout(mllpClientPortSegment.getConnectionTimeout());
            } else {
                systemEndpointPort3.setConnectTimeout(MLLPConfigurationDefaults.CONNECT_TIMEOUT);
            }
            externalSystem.getTargetPorts().add(systemEndpointPort3);
        }
        mllpClientTopologyNode.setDescription("Client-->MLLP:"+targetPort1.getTargetPortDNSName()+":"+Integer.toString(targetPort1.getTargetPortValue()));
        mllpClientTopologyNode.setParticipantName(parentWUP.getParticipantName()+"."+"MLLP.Client."+targetPort1.getTargetPortDNSName()+"."+Integer.toString(targetPort1.getTargetPortValue()));
        mllpClientTopologyNode.setParticipantDisplayName("MLLP.Client:"+targetPort1.getTargetPortDNSName()+":"+Integer.toString(targetPort1.getTargetPortValue()));
        mllpClientTopologyNode.setTargetSystem(externalSystem);
        //
        // Add the endpoint to the provider
        parentWUP.setEgressEndpoint(mllpClientTopologyNode);
        //
        // Add the endpoint to the topology cache
        getLogger().warn(".newMLLPClientEndpoint(): Add the {}/{} Port to the Topology Cache", mllpClientTopologyNode.getNodeId().getId(), endpointFunctionName);
        getSolutionMap().addTopologyNode(mllpClientTopologyNode);
        getLogger().debug(".newMLLPClientEndpoint(): Exit, endpoint added");
        return(mllpClientTopologyNode);
    }

    public MLLPClientAdapter newMLLPClientAdapter(ConnectedSystemPort connectedSystemPort) {
        getLogger().debug(".newMLLPClientAdapter(): Entry, connectedSystemPort->{}", connectedSystemPort);
        MLLPClientAdapter systemEndpointPort = new MLLPClientAdapter();
        boolean encryptionRequired = false;
        if(connectedSystemPort.getEncryptionRequired() != null){
            encryptionRequired = connectedSystemPort.getEncryptionRequired();
        }
        systemEndpointPort.setEncrypted(encryptionRequired);
        if(StringUtils.isEmpty(connectedSystemPort.getTargetPortDNSName())){
            throw(new IllegalArgumentException("targetPortDNSName is empty"));
        }
        systemEndpointPort.setHostName(connectedSystemPort.getTargetPortDNSName());
        if(connectedSystemPort.getTargetPortValue() == null){
            throw(new IllegalArgumentException("targetPortValue is empty"));
        }
        systemEndpointPort.setPortNumber(connectedSystemPort.getTargetPortValue());
        IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
        currentInterfaceDefinition.setInterfaceFormalName(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionName());
        currentInterfaceDefinition.setInterfaceFormalVersion(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionVersion());
        systemEndpointPort.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        getLogger().info(".newMLLPClientAdapter(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }
}
