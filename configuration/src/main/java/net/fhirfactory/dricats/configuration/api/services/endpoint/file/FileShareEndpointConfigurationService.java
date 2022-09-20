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
package net.fhirfactory.dricats.configuration.api.services.endpoint.file;

import net.fhirfactory.dricats.configuration.api.services.common.PlatformInformationHelper;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.common.WildflyBasedServerPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.fileshare.FileShareEndpointSegment;
import net.fhirfactory.dricats.util.SystemPropertyHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileShareEndpointConfigurationService extends PlatformInformationHelper {
    private static final Logger LOG = LoggerFactory.getLogger(FileShareEndpointConfigurationService.class);

    private WildflyBasedServerPropertyFile propertyFile;

    //
    // Constructor(s)
    //

    public FileShareEndpointConfigurationService(SystemPropertyHelper propertyHelper, WildflyBasedServerPropertyFile propertyFile){
        super(propertyHelper);
        this.propertyFile = propertyFile;
    }


    //
    // Getters and Setters
    //
    protected Logger getLogger() {
        return (LOG);
    }

    protected WildflyBasedServerPropertyFile getPropertyFile(){
        return(propertyFile);
    }

    //
    // Business Methods
    //

    public FileShareEndpointSegment getFileShareEndpoint(String instanceName){
        return(null);
    }







    /*

    public FileShareSourceETN newFileShareSourceEndpoint(WildflyBasedServerPropertyFile propertyFile, FileReaderProcessorTN parentWUP, String endpointFunctionName, FileShareEndpointSegment fileShareSource) {
        getLogger().debug(".newFileShareSourceEndpoint(): Entry, parentWUP->{}, fileShareSource->{}", parentWUP, fileShareSource);
        FileShareSourceETN fileShareSourceTopologyNode = new FileShareSourceETN();
        if (fileShareSource == null) {
            getLogger().debug(".newFileShareSourceEndpoint(): Exit, no endpoint to add");
            return (null);
        }
        fileShareSourceTopologyNode.constructNodeID(ComponentTypeEnum.ENDPOINT.getDisplayName());
        fileShareSourceTopologyNode.setEndpointConfigurationName(fileShareSource.getName());
        fileShareSourceTopologyNode.setEndpointType(EndpointTopologyNodeTypeEnum.FILE_SHARE_SOURCE);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("FileShareSourceEndpoint");
        componentType.setDisplayTypeName("FileShareSourceEndpoint");
        fileShareSourceTopologyNode.setNodeType(componentType);
        fileShareSourceTopologyNode.setNetworkContext(TopologyNodeNetworkingContextEnum.COMPONENT_ROLE_INTERACT_INGRES);
        fileShareSourceTopologyNode.setConnectedSystemName(fileShareSource.getConnectedSystem().getSubsystemName());
        fileShareSourceTopologyNode.setServer(true);
        fileShareSourceTopologyNode.setParentNode(parentWUP.getNodeId());
        FileShareSourceAdapter systemEndpoint = newFileShareSourceAdapter(fileShareSource);
        systemEndpoint.setTargetSystemName(fileShareSource.getConnectedSystem().getExternalisedServiceName());
        systemEndpoint.setEncrypted(fileShareSource.isEncrypted());
        systemEndpoint.setEnablingTopologyEndpoint(fileShareSourceTopologyNode.getNodeId());
        systemEndpoint.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        systemEndpoint.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        systemEndpoint.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        systemEndpoint.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        systemEndpoint.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        systemEndpoint.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        systemEndpoint.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        systemEndpoint.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);

        fileShareSourceTopologyNode.setFileShareSourceAdapter(systemEndpoint);
        fileShareSourceTopologyNode.setDescription("Server-->FILE:" + systemEndpoint.getFilePath());
        fileShareSourceTopologyNode.setParticipantName(parentWUP.getParticipantName() + "." + "FILE.Source." + systemEndpoint.getFilePathAlias());
        fileShareSourceTopologyNode.setParticipantDisplayName("FILE.Source:" + systemEndpoint.getFilePathAlias());

        parentWUP.setIngresEndpoint(fileShareSourceTopologyNode);
        getLogger().warn(".newFileShareSourceEndpoint(): Add the {}/{} Port to the Topology Cache", fileShareSourceTopologyNode.getNodeId().getId(), fileShareSourceTopologyNode.getParticipantName());
        getSolutionMap().addTopologyNode(fileShareSourceTopologyNode);
        getLogger().debug(".newFileShareSourceEndpoint(): Exit, endpoint added->{}", fileShareSourceTopologyNode);

        return (fileShareSourceTopologyNode);
    }

    //
    // Build a FILE Sink Endpoint
    //
    public FileShareSinkETN newFileShareSinkEndpoint(WildflyBasedServerPropertyFile propertyFile, FileWriterProcessorTN parentWUP, String endpointFunctionName, FileShareEndpointSegment fileShareSink) {
        getLogger().debug(".newFileShareSinkEndpoint(): Entry, parentWUP->{}, mllpClientPort->{}", parentWUP, fileShareSink);
        FileShareSinkETN fileShareSinkTopologyNode = new FileShareSinkETN();
        if (fileShareSink == null) {
            getLogger().debug(".newFileShareSinkEndpoint(): Exit, no endpoint to add");
            return (null);
        }
        fileShareSinkTopologyNode.constructNodeID(ComponentTypeEnum.ENDPOINT.getDisplayName());
        fileShareSinkTopologyNode.setEndpointConfigurationName(fileShareSink.getName());
        fileShareSinkTopologyNode.setEndpointType(EndpointTopologyNodeTypeEnum.FILE_SHARE_SINK);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("FileShareSinkEndpoint");
        componentType.setDisplayTypeName("FileShareSinkSendpoint");
        fileShareSinkTopologyNode.setNodeType(componentType);
        fileShareSinkTopologyNode.setNetworkContext(TopologyNodeNetworkingContextEnum.COMPONENT_ROLE_INTERACT_EGRESS);
        fileShareSinkTopologyNode.setParentNode(parentWUP.getNodeId());
        ConnectedSystemProperties connectedSystem = fileShareSink.getConnectedSystem();
        fileShareSinkTopologyNode.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTN externalSystem = new ConnectedExternalSystemTN();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
        FileShareSinkAdapter systemEndpoint = newFileShareSinkAdapter(fileShareSink);
        externalSystem.getTargetPorts().add(systemEndpoint);

        fileShareSinkTopologyNode.setDescription("Client-->FILE:" + systemEndpoint.getFilePath());
        fileShareSinkTopologyNode.setParticipantName(parentWUP.getParticipantName() + "." + "FILE.Sink." + systemEndpoint.getFilePathAlias());
        fileShareSinkTopologyNode.setParticipantDisplayName("FILE.Sink:" + systemEndpoint.getFilePathAlias());

        fileShareSinkTopologyNode.setTargetSystem(externalSystem);
        parentWUP.setEgressEndpoint(fileShareSinkTopologyNode);
        getLogger().warn(".newFileShareSinkEndpoint(): Add the {}/{} Port to the Topology Cache", fileShareSinkTopologyNode.getNodeId().getId(), fileShareSinkTopologyNode.getParticipantName());
        getSolutionMap().addTopologyNode(fileShareSinkTopologyNode);
        getLogger().debug(".newFileShareSinkEndpoint(): Exit, endpoint added");
        return (fileShareSinkTopologyNode);
    }

    public FileShareSourceAdapter newFileShareSourceAdapter(FileShareEndpointSegment connectedSystem) {
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

    public FileShareSinkAdapter newFileShareSinkAdapter(FileShareEndpointSegment connectedSystem) {
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

     */
}
