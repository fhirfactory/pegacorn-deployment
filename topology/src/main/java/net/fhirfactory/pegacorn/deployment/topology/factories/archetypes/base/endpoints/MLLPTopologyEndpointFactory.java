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
package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.endpoints;

import net.fhirfactory.pegacorn.core.model.component.valuesets.SoftwareComponentConnectivityContextEnum;
import net.fhirfactory.pegacorn.core.model.componentid.ComponentIdType;
import net.fhirfactory.pegacorn.core.model.componentid.SoftwareComponentTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.ipc.PegacornCommonInterfaceNames;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipant;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipantFulfillment;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipantFulfillmentStatusEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.base.IPCAdapterDefinition;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.mllp.MLLPClientEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.mllp.MLLPServerEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.mllp.adapters.MLLPClientAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.mllp.adapters.MLLPServerAdapter;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.core.model.topology.nodes.external.ConnectedExternalSystemTopologyNode;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes.ParameterNameValuePairType;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.InteractClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.InteractClusteredServerPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common.TopologyFactoryHelpersBase;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MLLPTopologyEndpointFactory extends TopologyFactoryHelpersBase {
    private static final Logger LOG = LoggerFactory.getLogger(MLLPTopologyEndpointFactory.class);

    @Inject
    private PegacornProperties pegacornProperties;

    @Inject
    private PegacornCommonInterfaceNames interfaceNames;

    @Inject
    private TopologyIM topologyIM;

    //
    // Constructor(s)
    //

    //
    // Getters and Setters
    //

    @Override
    protected Logger getLogger(){
        return(LOG);
    }

    protected PegacornCommonInterfaceNames getInterfaceNames(){
        return(interfaceNames);
    }

    protected TopologyIM getTopologyIM(){
        return(topologyIM);
    }

    //
    // Business Methods
    //

    public MLLPServerEndpoint newMLLPServerEndpoint(BaseSubsystemPropertyFile propertyFile, EndpointProviderInterface endpointProvider, String endpointFunctionName, InteractClusteredServerPortSegment mllpServerPort){
        getLogger().debug(".createMLLPServerEndpoint(): Entry, endpointProvider->{}, mllpServerPort->{}", endpointProvider, mllpServerPort);
        MLLPServerEndpoint mllpServerTopologyNode = new MLLPServerEndpoint();
        if(mllpServerPort == null){
            getLogger().debug(".createMLLPServerEndpoint(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.MLLP_SERVER, endpointFunctionName);
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        mllpServerTopologyNode.setComponentID(componentId);
        mllpServerTopologyNode.setParentComponent(endpointProvider.getComponentId());
        mllpServerTopologyNode.setVersion(endpointProvider.getVersion());
        mllpServerTopologyNode.setEndpointConfigurationName(mllpServerPort.getName());
        mllpServerTopologyNode.setActualHostIP(getActualHostIP());
        mllpServerTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.MLLP_SERVER);
        mllpServerTopologyNode.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        mllpServerTopologyNode.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_INTERACT_INGRES);
        mllpServerTopologyNode.setConnectedSystemName(mllpServerPort.getConnectedSystem().getSubsystemName());
        for(ParameterNameValuePairType otherConfigurationParameter: mllpServerPort.getOtherConfigurationParameters()){
            mllpServerTopologyNode.getOtherConfigurationParameters().put(otherConfigurationParameter.getParameterName(), otherConfigurationParameter.getParameterValue());
        }
        mllpServerTopologyNode.setServer(true);
        MLLPServerAdapter port = new MLLPServerAdapter();
        port.setPortNumber(mllpServerPort.getServerPort());
        port.setHostName(mllpServerPort.getServerHostname());
        port.setServiceDNSName(mllpServerPort.getServiceDNS());
        port.setServicePortValue(mllpServerPort.getServicePort());
        port.setTargetSystemName(mllpServerPort.getConnectedSystem().getExternalisedServiceName());
        port.setEncrypted(mllpServerPort.isEncrypted());
        port.setEnablingTopologyEndpoint(mllpServerTopologyNode.getComponentId());
        for(ParameterNameValuePairType otherConfigurationParameter: mllpServerPort.getOtherConfigurationParameters()){
            port.getAdditionalParameters().put(otherConfigurationParameter.getParameterName(), otherConfigurationParameter.getParameterValue());
        }
        for(InterfaceDefinitionSegment currentSegment: mllpServerPort.getSupportedInterfaceProfiles()) {
            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            port.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        }
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        mllpServerTopologyNode.setMLLPServerAdapter(port);
        mllpServerTopologyNode.setEndpointDescription("Server-->MLLP:"+propertyFile.getLoadBalancer().getIpAddress()+":"+Integer.toString(port.getServicePortValue()));

        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(propertyFile.getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        participant.getParticipantId().setSubsystemName(endpointProvider.getParticipantId().getSubsystemName());
        participant.getParticipantId().setName(endpointProvider.getParticipantId()+"."+"MLLP.Server."+propertyFile.getLoadBalancer().getIpAddress()+"."+Integer.toString(port.getServicePortValue()));
        participant.getParticipantId().setDisplayName("MLLP.Server:"+propertyFile.getLoadBalancer().getIpAddress()+":"+Integer.toString(port.getServicePortValue()));
        participant.getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + "." + "MLLP.Server."+propertyFile.getLoadBalancer().getIpAddress()+"."+Integer.toString(port.getServicePortValue()));
        participant.getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        mllpServerTopologyNode.setParticipant(participant);

        endpointProvider.addEndpoint(mllpServerTopologyNode.getComponentId());
        getLogger().warn(".createMLLPServerEndpoint(): Add the {}/{} Port to the Topology Cache", mllpServerTopologyNode.getComponentId().getDisplayName(), endpointFunctionName);
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), mllpServerTopologyNode);
        getLogger().debug(".createMLLPServerEndpoint(): Exit, endpoint added->{}", mllpServerTopologyNode);
        return(mllpServerTopologyNode);
    }

    //
    // Build an MLLP Client Endpoint
    //

    public MLLPClientEndpoint newMLLPClientEndpoint(BaseSubsystemPropertyFile propertyFile, EndpointProviderInterface endpointProvider, String endpointFunctionName, InteractClientPortSegment mllpClientPort){
        getLogger().debug(".newMLLPClientEndpoint(): Entry, endpointProvider->{}, mllpClientPort->{}", endpointProvider, mllpClientPort);
        MLLPClientEndpoint mllpClientTopologyNode = new MLLPClientEndpoint();
        if(mllpClientPort == null){
            getLogger().debug(".newMLLPClientEndpoint(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.MLLP_CLIENT, endpointFunctionName);
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        mllpClientTopologyNode.setComponentID(componentId);
        mllpClientTopologyNode.setParentComponent(endpointProvider.getComponentId());
        mllpClientTopologyNode.setEndpointConfigurationName(mllpClientPort.getName());
        mllpClientTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.MLLP_CLIENT);
        mllpClientTopologyNode.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        mllpClientTopologyNode.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_INTERACT_EGRESS);
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
        for(ParameterNameValuePairType otherConfigurationParameter: mllpClientPort.getOtherConfigurationParameters()){
            mllpClientTopologyNode.getOtherConfigurationParameters().put(otherConfigurationParameter.getParameterName(), otherConfigurationParameter.getParameterValue());
        }
        mllpClientTopologyNode.setEndpointDescription("Client-->MLLP:"+targetPort1.getTargetPortDNSName()+":"+Integer.toString(targetPort1.getTargetPortValue()));

        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(propertyFile.getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        participant.getParticipantId().setSubsystemName(endpointProvider.getParticipantId().getSubsystemName());
        participant.getParticipantId().setName(endpointProvider.getParticipantId()+"."+"MLLP.Client."+targetPort1.getTargetPortDNSName()+"."+Integer.toString(targetPort1.getTargetPortValue()));
        participant.getParticipantId().setDisplayName("MLLP.Client:"+targetPort1.getTargetPortDNSName()+":"+Integer.toString(targetPort1.getTargetPortValue()));
        participant.getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + "." + "MLLP.Client."+targetPort1.getTargetPortDNSName()+"."+Integer.toString(targetPort1.getTargetPortValue()));
        participant.getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        mllpClientTopologyNode.setParticipant(participant);
        mllpClientTopologyNode.setTargetSystem(externalSystem);
        endpointProvider.addEndpoint(mllpClientTopologyNode.getComponentId());
        getLogger().warn(".newMLLPClientEndpoint(): Add the {}/{} Port to the Topology Cache", mllpClientTopologyNode.getComponentId().getDisplayName(), endpointFunctionName);
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), mllpClientTopologyNode);
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
