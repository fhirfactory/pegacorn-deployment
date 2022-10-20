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

import net.fhirfactory.pegacorn.core.model.componentid.ComponentIdType;
import net.fhirfactory.pegacorn.core.model.componentid.SoftwareComponentTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.ipc.PegacornCommonInterfaceNames;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipant;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipantFulfillment;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipantFulfillmentStatusEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.HTTPClientAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.HTTPServerAdapter;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.base.IPCAdapterDefinition;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.base.IPCServerTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.http.HTTPClientTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.http.HTTPServerTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.interact.StandardInteractClientTopologyEndpointPort;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.core.model.topology.nodes.external.ConnectedExternalSystemTopologyNode;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.StandardServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.http.ClusteredHTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.http.HTTPClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.http.HTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common.TopologyFactoryHelpersBase;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class HTTPTopologyEndpointFactory extends TopologyFactoryHelpersBase {
    private static final Logger LOG = LoggerFactory.getLogger(HTTPTopologyEndpointFactory.class);

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

    public IPCServerTopologyEndpoint newHTTPServerTopologyEndpoint(BaseSubsystemPropertyFile propertyFile, EndpointProviderInterface endpointProvider, String name, StandardServerPortSegment httpServerPort){
        getLogger().debug(".newHTTPServerTopologyEndpoint(): Entry, endpointProvider->{}, endpointFunctionName->{}, httpServerPort->{}", endpointProvider, httpServerPort);
        if(httpServerPort == null){
            getLogger().debug(".newHTTPServerTopologyEndpoint(): Exit, no port to add");
            return(null);
        }
        HTTPServerTopologyEndpoint httpServer = new HTTPServerTopologyEndpoint();
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        httpServer.setVersion(endpointProvider.getVersion());
        httpServer.setComponentID(componentId);
        httpServer.setEndpointConfigurationName(httpServerPort.getName());
        httpServer.setActualHostIP(getActualHostIP());
        httpServer.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_SERVER);
        httpServer.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        httpServer.setServer(true);
        httpServer.setConnectedSystemName(httpServerPort.getConnectedSystem().getSubsystemName());
        // Build Petasos Participant
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
        httpServer.setParticipant(participant);
        //
        // Create the ServerAdapter (the thing that actually holds the port number and ip address details)
        HTTPServerAdapter httpServerAdapter = new HTTPServerAdapter();
        //
        // Unfortunately, the two different HTTP endpoints don't inherent from the same superclass until way up in
        // the chain - so we need to explicitly separate the creation/assignment of parameters... :(
        if(httpServerPort instanceof ClusteredHTTPServerPortSegment) {
            //
            // This assumes that there is some type of load balancer in front of our exposed server ports
            ClusteredHTTPServerPortSegment clusteredHTTPServerPort = (ClusteredHTTPServerPortSegment) httpServerPort;
            //
            // This is the Port Number we are actually binding to
            if(clusteredHTTPServerPort.getServerPort() == null){
                throw(new IllegalArgumentException("serverPort for " + clusteredHTTPServerPort.getName() + " is not defined in configuration file"));
            }
            httpServerAdapter.setPortNumber(clusteredHTTPServerPort.getServerPort());
            //
            // This is the Port Number exposed via the clustering service (i.e. a load-balancer)
            if(clusteredHTTPServerPort.getServicePort() == null) {
                throw(new IllegalArgumentException("servicePort for " + clusteredHTTPServerPort.getName() + " is not defined in the configuration file"));
            }
            httpServerAdapter.setServicePortValue(clusteredHTTPServerPort.getServicePort());
            //
            // Set the context path
            String serverPath = clusteredHTTPServerPort.getContextPath();
            httpServerAdapter.setContextPath(serverPath);
            //
            // This is the hostname that we actually bind our port to
            if(StringUtils.isEmpty(clusteredHTTPServerPort.getServerHostname())){
                throw( new IllegalArgumentException("serverHost for " + clusteredHTTPServerPort.getName() + "is not defined in configuration file"));
            }
            httpServerAdapter.setHostName(httpServerPort.getServerHostname());
            //
            // This is the hostname/ip-address that we are exposing via the clustering service (i.e. load-balancer)
            if(StringUtils.isEmpty(propertyFile.getLoadBalancer().getIpAddress())){
                if(StringUtils.isEmpty(clusteredHTTPServerPort.getServiceDNS())) {
                    throw( new IllegalArgumentException("serviceDNS and loadBalancer.ipAddress for " + clusteredHTTPServerPort.getName() + "are not defined in configuration file"));
                } else {
                    httpServerAdapter.setServiceDNSName(clusteredHTTPServerPort.getServiceDNS());
                }
            } else {
                httpServerAdapter.setServiceDNSName(propertyFile.getLoadBalancer().getIpAddress());
            }
            //
            // Should encryption (https) be used?
            httpServerAdapter.setEncrypted(clusteredHTTPServerPort.isEncrypted());
            //
            // Add the adapter to the TopologyEndpoint
            httpServer.setHTTPServerAdapter(httpServerAdapter);
            //
            // Now build the Endpoint Description
            if(clusteredHTTPServerPort.isEncrypted()) {
                if(StringUtils.isEmpty(httpServerAdapter.getContextPath())) {
                    httpServer.setEndpointDescription("Server-->HTTPS://" + httpServerAdapter.getServiceDNSName() + ":" + httpServerAdapter.getServicePortValue() + "/");
                } else {
                    httpServer.setEndpointDescription("Server-->HTTPS://" + httpServerAdapter.getServiceDNSName() + ":" + httpServerAdapter.getServicePortValue() + httpServerAdapter.getContextPath());
                }
            } else {
                if(StringUtils.isEmpty(httpServerAdapter.getContextPath())) {
                    httpServer.setEndpointDescription("Server-->HTTP://" + httpServerAdapter.getServiceDNSName() + ":" + httpServerAdapter.getServicePortValue() + "/");
                } else {
                    httpServer.setEndpointDescription("Server-->HTTP://" + httpServerAdapter.getServiceDNSName() + ":" + httpServerAdapter.getServicePortValue() + httpServerAdapter.getContextPath());
                }
            }
            //
            // Now build the Endpoint Participant Name
            String providerParticipantName = endpointProvider.getParticipantId().getName();
            if(clusteredHTTPServerPort.isEncrypted()) {
                httpServer.getParticipant().getParticipantId().setName(providerParticipantName+".Server.HTTPS." +httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
                httpServer.getParticipant().getParticipantId().setDisplayName("Server.HTTPS." + httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
                httpServer.getParticipant().getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + ".Server.HTTPS." + httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
            } else {
                httpServer.getParticipant().getParticipantId().setName(providerParticipantName+".Server.HTTP." + httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
                httpServer.getParticipant().getParticipantId().setDisplayName("Server.HTTP." + httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
                httpServer.getParticipant().getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + ".Server.HTTP." + httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
            }
            httpServer.getParticipant().getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        } else {
            //
            // This assumes that there is nothing between our exposed ports and client connections
            HTTPServerPortSegment nonClusteredHTTPServerPort = (HTTPServerPortSegment)httpServerPort;
            //
            // This is the Port Number we are actually binding to
            if(nonClusteredHTTPServerPort.getServerPort() == null){
                throw(new IllegalArgumentException("portValue for " + nonClusteredHTTPServerPort.getName() + " is not defined in configuration file"));
            }
            httpServerAdapter.setPortNumber(nonClusteredHTTPServerPort.getServerPort());
            //
            // This is the hostname that we actually bind our port to
            if(StringUtils.isEmpty(nonClusteredHTTPServerPort.getServerHostname())){
                throw( new IllegalArgumentException("hostDNSEntry for " + nonClusteredHTTPServerPort.getName() + "is not defined in configuration file"));
            }
            httpServerAdapter.setHostName(httpServerPort.getServerHostname());
            //
            // Set the Context Path
            String serverPath = nonClusteredHTTPServerPort.getContextPath();
            httpServerAdapter.setContextPath(serverPath);
            //
            // Should encryption (https) be used?
            httpServerAdapter.setEncrypted(nonClusteredHTTPServerPort.isEncrypted());
            //
            // Now build the Endpoint Description
            if(nonClusteredHTTPServerPort.isEncrypted()) {
                if(StringUtils.isEmpty(httpServerAdapter.getContextPath())) {
                    httpServer.setEndpointDescription("Server-->HTTPS://" + httpServerAdapter.getHostName() + ":" + httpServerAdapter.getPortNumber() + "/");
                } else {
                    httpServer.setEndpointDescription("Server-->HTTPS://" + httpServerAdapter.getHostName() + ":" + httpServerAdapter.getPortNumber() + httpServerAdapter.getContextPath());
                }
            } else {
                if(StringUtils.isEmpty(httpServerAdapter.getContextPath())) {
                    httpServer.setEndpointDescription("Server-->HTTP://" + httpServerAdapter.getHostName() + ":" + httpServerAdapter.getPortNumber() + "/");
                } else {
                    httpServer.setEndpointDescription("Server-->HTTP://" + httpServerAdapter.getHostName() + ":" + httpServerAdapter.getPortNumber() + httpServerAdapter.getContextPath());
                }
            }
            //
            // Now build the Endpoint Participant Name / Participant Display Name
            if(nonClusteredHTTPServerPort.isEncrypted()) {
                httpServer.getParticipant().getParticipantId().setName(endpointProvider.getParticipantId()+"."+"Server.HTTPS." +httpServerAdapter.getHostName() + "." + httpServerAdapter.getPortNumber());
                httpServer.getParticipant().getParticipantId().setDisplayName("HTTPS.Server:" +httpServerAdapter.getHostName() + ":" + httpServerAdapter.getPortNumber());
                httpServer.getParticipant().getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + ".HTTPS.Server:" +httpServerAdapter.getHostName() + ":" + httpServerAdapter.getPortNumber());
            } else {
                httpServer.getParticipant().getParticipantId().setName(endpointProvider.getParticipantId()+"."+"Server.HTTP." + httpServerAdapter.getHostName() + "." + httpServerAdapter.getPortNumber());
                httpServer.getParticipant().getParticipantId().setDisplayName("HTTP.Server:" +httpServerAdapter.getHostName() + ":" + httpServerAdapter.getPortNumber());
                httpServer.getParticipant().getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + ".HTTP.Server:" +httpServerAdapter.getHostName() + ":" + httpServerAdapter.getPortNumber());
            }
            httpServer.getParticipant().getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        }
        //
        // Add the Adapter to the endpoint
        httpServer.setHTTPServerAdapter(httpServerAdapter);
        //
        // Add the endpoint to the provider
        endpointProvider.addEndpoint(httpServer.getComponentId());
        //
        // Add the endpoint to the topology cache
        getLogger().trace(".newHTTPServerTopologyEndpoint(): Add the HTTP Server Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), httpServer);
        //
        // We're done
        getLogger().debug(".newHTTPServerTopologyEndpoint(): Exit, clusteredHTTPServerPort added");
        return(httpServer);
    }

    //
    // Build an HTTP Client Endpoint (Helper Method)
    //

    public StandardInteractClientTopologyEndpointPort newHTTPClientTopologyEndpoint(BaseSubsystemPropertyFile propertyFile, EndpointProviderInterface endpointProvider, String endpointFunctionName, HTTPClientPortSegment httpClientPortConfigurationSegment){
        getLogger().debug(".newEdgeHTTPClientTopologyEndpoint(): Entry, endpointProvider->{}, httpClientPortConfigurationSegment->{}", endpointProvider, httpClientPortConfigurationSegment);
        HTTPClientTopologyEndpoint httpClient = new HTTPClientTopologyEndpoint();
        if(httpClientPortConfigurationSegment == null){
            getLogger().debug(".newHTTPClient(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointServerName(endpointFunctionName);
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        httpClient.setComponentID(componentId);
        httpClient.setVersion(endpointProvider.getVersion());
        httpClient.setEndpointConfigurationName(httpClientPortConfigurationSegment.getName());
        httpClient.setEndpointType(PetasosEndpointTopologyTypeEnum.HTTP_API_CLIENT);
        httpClient.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        httpClient.getParticipant().getParticipantId().setName(endpointFunctionName);
        ConnectedSystemProperties connectedSystem = httpClientPortConfigurationSegment.getConnectedSystem();
        httpClient.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTopologyNode externalSystem = new ConnectedExternalSystemTopologyNode();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
        // Build Petasos Participant
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
        httpClient.setParticipant(participant);
        // Build Ports
        ConnectedSystemPort targetPort1 = connectedSystem.getTargetPort1();
        HTTPClientAdapter systemEndpointPort1 = newHTTPClientAdapter(targetPort1);
        externalSystem.getTargetPorts().add(systemEndpointPort1);
        if(connectedSystem.getTargetPort2() != null)
        {
            ConnectedSystemPort targetPort2 = connectedSystem.getTargetPort2();
            HTTPClientAdapter systemEndpointPort2 = newHTTPClientAdapter(targetPort2);
            externalSystem.getTargetPorts().add(systemEndpointPort2);
        }
        if(connectedSystem.getTargetPort3() != null)
        {
            ConnectedSystemPort targetPort3 = connectedSystem.getTargetPort3();
            HTTPClientAdapter systemEndpointPort3 = newHTTPClientAdapter(targetPort3);
            externalSystem.getTargetPorts().add(systemEndpointPort3);
        }
        httpClient.setTargetSystem(externalSystem);
        //
        // Now build the Endpoint Description
        if(targetPort1.getEncryptionRequired()) {
            if(StringUtils.isEmpty(targetPort1.getTargetContextPath())) {
                httpClient.setEndpointDescription(endpointProvider.getParticipantId().getName() +"."+"Client.HTTPS://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + "/");
            } else {
                httpClient.setEndpointDescription(endpointProvider.getParticipantId().getName() +"."+"Client.HTTPS://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + targetPort1.getTargetContextPath());
            }
        } else {
            if(StringUtils.isEmpty(targetPort1.getTargetContextPath())) {
                httpClient.setEndpointDescription(endpointProvider.getParticipantId().getName() +"."+"Server-->HTTP://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + "/");
            } else {
                httpClient.setEndpointDescription(endpointProvider.getParticipantId().getName() + "."+"Server-->HTTP://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + targetPort1.getTargetContextPath());
            }
        }

        //
        // Now build the Endpoint Participant Name / Participant Display Name
        if(targetPort1.getEncryptionRequired()) {
            httpClient.getParticipant().getParticipantId().setName(endpointProvider.getParticipantId().getName() + ".Client.HTTPS." + targetPort1.getTargetPortDNSName() + "." + targetPort1.getTargetPortValue());
            httpClient.getParticipant().getParticipantId().setDisplayName("HTTPS.Client:" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue());
            httpClient.getParticipant().getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + "." + ".Client.HTTPS." + targetPort1.getTargetPortDNSName() + "." + targetPort1.getTargetPortValue());

        } else {
            httpClient.getParticipant().getParticipantId().setName(endpointProvider.getParticipantId().getName() + ".Client.HTTP." + targetPort1.getTargetPortDNSName() + "." + targetPort1.getTargetPortValue());
            httpClient.getParticipant().getParticipantId().setDisplayName("HTTP.Client:" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue());
            httpClient.getParticipant().getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + "." + ".Client.HTTP." + targetPort1.getTargetPortDNSName() + "." + targetPort1.getTargetPortValue());
        }
        httpClient.getParticipant().getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        //
        // Add the endpoint to the provider
        endpointProvider.addEndpoint(httpClient.getComponentId());
        //
        // Add the endpoint to the topology cache
        getLogger().trace(".newHTTPClient(): Add the httpClient Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), httpClient);
        //
        // We're done
        getLogger().debug(".newHTTPClient(): Exit, endpoint added");
        return(httpClient);
    }

    public HTTPClientAdapter newHTTPClientAdapter(ConnectedSystemPort connectedSystemPort) {
        getLogger().debug(".HTTPClientAdapter(): Entry, connectedSystemPort->{}", connectedSystemPort);
        HTTPClientAdapter systemEndpointPort = new HTTPClientAdapter();
        boolean encryptionRequired = false;
        if(connectedSystemPort.getEncryptionRequired() != null){
            encryptionRequired = connectedSystemPort.getEncryptionRequired();
        }
        systemEndpointPort.setEncrypted(encryptionRequired);
        if(StringUtils.isEmpty(connectedSystemPort.getTargetPortDNSName())){
            throw (new IllegalArgumentException("TargetPortDNSName is not defined in configuration file"));
        }
        systemEndpointPort.setHostName(connectedSystemPort.getTargetPortDNSName());
        if(connectedSystemPort.getTargetPortValue() == null){
            throw(new IllegalArgumentException("TargetPortValue is not defined in configuration file"));
        }
        systemEndpointPort.setPortNumber(connectedSystemPort.getTargetPortValue());
        systemEndpointPort.setContextPath(connectedSystemPort.getTargetContextPath());
        IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
        currentInterfaceDefinition.setInterfaceFormalName(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionName());
        currentInterfaceDefinition.setInterfaceFormalVersion(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionVersion());
        systemEndpointPort.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        getLogger().debug(".HTTPClientAdapter(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }

}
