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
import net.fhirfactory.pegacorn.core.model.componentid.PegacornSystemComponentTypeTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.ipc.PegacornCommonInterfaceNames;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.base.IPCAdapterDefinition;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.mllp.MLLPServerEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.mllp.adapters.MLLPServerAdapter;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.core.model.topology.nodes.external.ConnectedExternalSystemTopologyNode;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.InteractClusteredServerPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common.TopologyFactoryHelpersBase;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.FileShareSinkAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.file.FileShareSinkTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.file.FileShareSourceTopologyEndpoint;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.InteractFileShareEndpointSegment;

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
//    public MLLPServerEndpoint newFileShareSourceEndpoint(BaseSubsystemPropertyFile propertyFile, EndpointProviderInterface endpointProvider, String endpointFunctionName, InteractFileShareEndpointSegment fileShareSource) {
//        getLogger().debug(".newFileShareSourceEndpoint(): Entry, endpointProvider->{}, fileShareSource->{}", endpointProvider, fileShareSource);
//        FileShareSourceTopologyEndpoint fileShareSourceTopologyNode = new FileShareSourceTopologyEndpoint();
//        if (fileShareSource == null) {
//            getLogger().debug(".newFileShareSourceEndpoint(): Exit, no endpoint to add");
//            return (null);
//        }
//        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.FILE_SHARE_SOURCE, endpointFunctionName);
//        TopologyNodeRDN nodeRDN = createSimpleNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
//        fileShareSourceTopologyNode.setComponentRDN(nodeRDN);
//        fileShareSourceTopologyNode.setEndpointConfigurationName(fileShareSource.getName());
//        fileShareSourceTopologyNode.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
//        fileShareSourceTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.FILE_SHARE_SOURCE);
//        fileShareSourceTopologyNode.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
//        fileShareSourceTopologyNode.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_INTERACT_INGRES);
//        fileShareSourceTopologyNode.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN);
//        fileShareSourceTopologyNode.setConnectedSystemName(fileShareSource.getConnectedSystem().getSubsystemName());
//        fileShareSourceTopologyNode.setContainingNodeFDN(endpointProvider.getComponentFDN());
//        fileShareSourceTopologyNode.setServer(true);
//        MLLPServerAdapter port = new MLLPServerAdapter();
//        port.setPortNumber(fileShareSourceTopologyNode.getServerPort());
//        port.setHostName(fileShareSourceTopologyNode.getServerHostname());
//        port.setServiceDNSName(fileShareSourceTopologyNode.getServiceDNS());
//        port.setServicePortValue(fileShareSourceTopologyNode.getServicePort());
//        port.setTargetSystemName(fileShareSourceTopologyNode.getConnectedSystem().getExternalisedServiceName());
//        port.setEncrypted(fileShareSourceTopologyNode.isEncrypted());
//        port.setEnablingTopologyEndpoint(fileShareSourceTopologyNode.getComponentID());
//        for (InterfaceDefinitionSegment currentSegment : mllpServerPort.getSupportedInterfaceProfiles()) {
//            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
//            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
//            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
//            port.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
//        }
//        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
//        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
//        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
//        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
//        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
//        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
//        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
//        port.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
//        mllpServerTopologyNode.setMLLPServerAdapter(port);
//        mllpServerTopologyNode.setEndpointDescription("Server-->MLLP:" + propertyFile.getLoadBalancer().getIpAddress() + ":" + Integer.toString(port.getServicePortValue()));
//        mllpServerTopologyNode.setParticipantName(endpointProvider.getParticipantName() + "." + "MLLP.Server." + propertyFile.getLoadBalancer().getIpAddress() + "." + Integer.toString(port.getServicePortValue()));
//        mllpServerTopologyNode.setParticipantDisplayName("MLLP.Server:" + propertyFile.getLoadBalancer().getIpAddress() + ":" + Integer.toString(port.getServicePortValue()));
//        endpointProvider.addEndpoint(mllpServerTopologyNode.getComponentFDN());
//        getLogger().warn(".createMLLPServerEndpoint(): Add the {}/{} Port to the Topology Cache", mllpServerTopologyNode.getComponentRDN().getNodeName(), endpointFunctionName);
//        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), mllpServerTopologyNode);
//        getLogger().debug(".createMLLPServerEndpoint(): Exit, endpoint added->{}", mllpServerTopologyNode);
//        return (mllpServerTopologyNode);
//    }

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
        TopologyNodeRDN nodeRDN = createSimpleNodeRDN(name, endpointProvider.getComponentRDN().getNodeVersion(), PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        fileShareSinkTopologyNode.setComponentRDN(nodeRDN);
        fileShareSinkTopologyNode.setEndpointConfigurationName(fileShareSink.getName());
        fileShareSinkTopologyNode.constructFDN(endpointProvider.getComponentFDN(), nodeRDN);
        fileShareSinkTopologyNode.setEndpointType(PetasosEndpointTopologyTypeEnum.FILE_SHARE_SINK);
        fileShareSinkTopologyNode.setComponentType(PegacornSystemComponentTypeTypeEnum.ENDPOINT);
        fileShareSinkTopologyNode.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_INTERACT_EGRESS);
        fileShareSinkTopologyNode.constructFunctionFDN(endpointProvider.getNodeFunctionFDN(), nodeRDN);
        fileShareSinkTopologyNode.setContainingNodeFDN(endpointProvider.getComponentFDN());
        ConnectedSystemProperties connectedSystem = fileShareSink.getConnectedSystem();
        fileShareSinkTopologyNode.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTopologyNode externalSystem = new ConnectedExternalSystemTopologyNode();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
        FileShareSinkAdapter systemEndpoint = newFileShareSinkAdapter(fileShareSink);
        externalSystem.getTargetPorts().add(systemEndpoint);

        fileShareSinkTopologyNode.setEndpointDescription("Client-->FILE:" + systemEndpoint.getFilePath());
        fileShareSinkTopologyNode.setParticipantName(endpointProvider.getParticipantName() + "." + "FILE.Sink." + systemEndpoint.getFilePathAlias());
        fileShareSinkTopologyNode.setParticipantDisplayName("FILE.Sink:" + systemEndpoint.getFilePathAlias());

        fileShareSinkTopologyNode.setTargetSystem(externalSystem);
        endpointProvider.addEndpoint(fileShareSinkTopologyNode.getComponentFDN());
        getLogger().warn(".newFileShareSinkEndpoint(): Add the {}/{} Port to the Topology Cache", fileShareSinkTopologyNode.getComponentRDN().getNodeName(), endpointFunctionName);
        getTopologyIM().addTopologyNode(endpointProvider.getComponentFDN(), fileShareSinkTopologyNode);
        getLogger().debug(".newFileShareSinkEndpoint(): Exit, endpoint added");
        return (fileShareSinkTopologyNode);
    }

    public FileShareSinkAdapter newFileShareSinkAdapter(InteractFileShareEndpointSegment connectedSystem) {
        getLogger().debug(".newFileShareSinkAdapter(): Entry, connectedSystemPort->{}", connectedSystem);
        FileShareSinkAdapter systemEndpointPort = new FileShareSinkAdapter();
        if (StringUtils.isEmpty(connectedSystem.getFileSharePath())) {
            throw (new IllegalArgumentException("fileSharePath is empty"));
        }
        systemEndpointPort.setFilePath(connectedSystem.getFileSharePath());
        systemEndpointPort.setFilePathAlias(connectedSystem.getFileSharePathAlias());
        getLogger().info(".newFileShareSinkAdapter(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }
}
