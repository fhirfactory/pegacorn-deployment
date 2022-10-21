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
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.FileShareSinkAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.FileShareSourceAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.file.FileShareSinkTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.file.FileShareSourceTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.core.model.topology.nodes.external.ConnectedExternalSystemTopologyNode;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.InteractFileShareEndpointSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common.TopologyFactoryHelpersBase;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FileShareTopologyEndpointFactory extends TopologyFactoryHelpersBase {

    private static final Logger LOG = LoggerFactory.getLogger(FileShareTopologyEndpointFactory.class);

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
    protected Logger getLogger() {
        return (LOG);
    }

    protected PegacornCommonInterfaceNames getInterfaceNames() {
        return (interfaceNames);
    }

    protected TopologyIM getTopologyIM() {
        return (topologyIM);
    }

    //
    // Business Methods
    //

    //
    // Build a FILE Source Endpoint
    //
    public FileShareSourceTopologyEndpoint newFileShareSourceEndpoint(BaseSubsystemPropertyFile propertyFile, EndpointProviderInterface endpointProvider, String endpointFunctionName, InteractFileShareEndpointSegment fileShareSource) {
        getLogger().debug(".newFileShareSourceEndpoint(): Entry, endpointProvider->{}, fileShareSource->{}", endpointProvider, fileShareSource);
        FileShareSourceTopologyEndpoint fileShareSourceTopologyNode = new FileShareSourceTopologyEndpoint();
        if (fileShareSource == null) {
            getLogger().debug(".newFileShareSourceEndpoint(): Exit, no endpoint to add");
            return (null);
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.FILE_SHARE_SOURCE, endpointFunctionName);
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        fileShareSourceTopologyNode.setComponentId(componentId);
        fileShareSourceTopologyNode.setParentComponent(endpointProvider.getComponentId());
        fileShareSourceTopologyNode.setVersion(endpointProvider.getVersion());
        fileShareSourceTopologyNode.setEndpointConfigurationName(fileShareSource.getName());
        fileShareSourceTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.FILE_SHARE_SOURCE);
        fileShareSourceTopologyNode.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        fileShareSourceTopologyNode.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_INTERACT_INGRES);
        fileShareSourceTopologyNode.setConnectedSystemName(fileShareSource.getConnectedSystem().getSubsystemName());
        fileShareSourceTopologyNode.setServer(true);

        FileShareSourceAdapter systemEndpoint = newFileShareSourceAdapter(fileShareSource);
        systemEndpoint.setTargetSystemName(fileShareSource.getConnectedSystem().getExternalisedServiceName());
        systemEndpoint.setEncrypted(fileShareSource.isEncrypted());
        systemEndpoint.setEnablingTopologyEndpoint(fileShareSourceTopologyNode.getComponentId());
        systemEndpoint.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        systemEndpoint.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        systemEndpoint.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        systemEndpoint.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        systemEndpoint.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        systemEndpoint.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        systemEndpoint.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        systemEndpoint.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);

        fileShareSourceTopologyNode.setFileShareSourceAdapter(systemEndpoint);
        fileShareSourceTopologyNode.setEndpointDescription("Server-->FILE:" + systemEndpoint.getFilePath());

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
        participant.getParticipantId().setName(endpointProvider.getParticipantId().getName() + "." + "FILE.Source." + systemEndpoint.getFilePathAlias());
        participant.getParticipantId().setDisplayName("FILE.Source:" + systemEndpoint.getFilePathAlias());
        participant.getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        participant.getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + "." + "FILE.Source." + systemEndpoint.getFilePathAlias());
        fileShareSourceTopologyNode.setParticipant(participant);

        endpointProvider.addEndpoint(fileShareSourceTopologyNode.getComponentId());
        getLogger().warn(".newFileShareSourceEndpoint(): Add the {}/{} Port to the Topology Cache", fileShareSourceTopologyNode.getComponentId().getDisplayName(), endpointFunctionName);
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), fileShareSourceTopologyNode);
        getLogger().debug(".newFileShareSourceEndpoint(): Exit, endpoint added->{}", fileShareSourceTopologyNode);

        return (fileShareSourceTopologyNode);
    }

    //
    // Build a FILE Sink Endpoint
    //
    public FileShareSinkTopologyEndpoint newFileShareSinkEndpoint(BaseSubsystemPropertyFile propertyFile, EndpointProviderInterface endpointProvider, String endpointFunctionName, InteractFileShareEndpointSegment fileShareSink) {
        getLogger().debug(".newFileShareSinkEndpoint(): Entry, endpointProvider->{}, mllpClientPort->{}", endpointProvider, fileShareSink);
        FileShareSinkTopologyEndpoint fileShareSinkTopologyNode = new FileShareSinkTopologyEndpoint();
        if (fileShareSink == null) {
            getLogger().debug(".newFileShareSinkEndpoint(): Exit, no endpoint to add");
            return (null);
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.FILE_SHARE_SINK, endpointFunctionName);
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        fileShareSinkTopologyNode.setComponentId(componentId);
        fileShareSinkTopologyNode.setVersion(endpointProvider.getVersion());
        fileShareSinkTopologyNode.setEndpointConfigurationName(fileShareSink.getName());
        fileShareSinkTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.FILE_SHARE_SINK);
        fileShareSinkTopologyNode.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        fileShareSinkTopologyNode.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_INTERACT_EGRESS);
        ConnectedSystemProperties connectedSystem = fileShareSink.getConnectedSystem();
        fileShareSinkTopologyNode.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTopologyNode externalSystem = new ConnectedExternalSystemTopologyNode();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());

        FileShareSinkAdapter systemEndpoint = newFileShareSinkAdapter(fileShareSink);
        externalSystem.getTargetPorts().add(systemEndpoint);
        fileShareSinkTopologyNode.setEndpointDescription("Client-->FILE:" + systemEndpoint.getFilePath());
        fileShareSinkTopologyNode.setTargetSystem(externalSystem);

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
        participant.getParticipantId().setName(endpointProvider.getParticipantId().getName() + "." + "FILE.Sink." + systemEndpoint.getFilePathAlias());
        participant.getParticipantId().setDisplayName("FILE.Sink:" + systemEndpoint.getFilePathAlias());
        participant.getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        participant.getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + "." + "FILE.Sink." + systemEndpoint.getFilePathAlias());
        participant.getParticipantId().setSubsystemName(endpointProvider.getParticipantId().getSubsystemName());
        fileShareSinkTopologyNode.setParticipant(participant);

        endpointProvider.addEndpoint(fileShareSinkTopologyNode.getComponentId());
        getLogger().warn(".newFileShareSinkEndpoint(): Add the {}/{} Port to the Topology Cache", fileShareSinkTopologyNode.getComponentId().getDisplayName(), endpointFunctionName);
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), fileShareSinkTopologyNode);
        getLogger().debug(".newFileShareSinkEndpoint(): Exit, endpoint added");
        return (fileShareSinkTopologyNode);
    }

    public FileShareSourceAdapter newFileShareSourceAdapter(InteractFileShareEndpointSegment connectedSystem) {
        getLogger().debug(".newFileShareSourceAdapter(): Entry, connectedSystemPort->{}", connectedSystem);
        FileShareSourceAdapter systemEndpointPort = new FileShareSourceAdapter();
        if (StringUtils.isEmpty(connectedSystem.getFileSharePath())) {
            throw (new IllegalArgumentException("fileSharePath is empty"));
        }
        if (StringUtils.isEmpty(connectedSystem.getFileSharePathAlias())) {
            throw (new IllegalArgumentException("fileSharePathAlias is empty"));
        }
        systemEndpointPort.setFilePath(connectedSystem.getFileSharePath());
        systemEndpointPort.setFilePathAlias(connectedSystem.getFileSharePathAlias());
        getLogger().info(".newFileShareSourceAdapter(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }

    public FileShareSinkAdapter newFileShareSinkAdapter(InteractFileShareEndpointSegment connectedSystem) {
        getLogger().debug(".newFileShareSinkAdapter(): Entry, connectedSystemPort->{}", connectedSystem);
        FileShareSinkAdapter systemEndpointPort = new FileShareSinkAdapter();
        if (StringUtils.isEmpty(connectedSystem.getFileSharePath())) {
            throw (new IllegalArgumentException("fileSharePath is empty"));
        }
        if (StringUtils.isEmpty(connectedSystem.getFileSharePathAlias())) {
            throw (new IllegalArgumentException("fileSharePathAlias is empty"));
        }
        systemEndpointPort.setFilePath(connectedSystem.getFileSharePath());
        systemEndpointPort.setFilePathAlias(connectedSystem.getFileSharePathAlias());
        getLogger().info(".newFileShareSinkAdapter(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }
}
