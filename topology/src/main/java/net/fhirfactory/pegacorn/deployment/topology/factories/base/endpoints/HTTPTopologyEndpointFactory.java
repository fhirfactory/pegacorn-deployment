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
import net.fhirfactory.dricats.configuration.defaults.dricats.systemwide.DefaultInterfaceNameSet;
import net.fhirfactory.dricats.configuration.defaults.dricats.systemwide.ReferenceProperties;
import net.fhirfactory.dricats.model.component.datatypes.ComponentTypeDefinition;
import net.fhirfactory.dricats.model.component.valuesets.ComponentTypeEnum;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.PetasosClusterPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.common.WildflyBasedServerPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.segments.connectedsystems.ConnectedSystemPort;
import net.fhirfactory.dricats.model.configuration.filebased.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.http.HTTPClientEndpointSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.http.HTTPServerEndpointSegment;
import net.fhirfactory.dricats.model.topology.endpoints.http.adapters.HTTPClientAdapter;
import net.fhirfactory.dricats.model.topology.endpoints.http.adapters.HTTPServerAdapter;
import net.fhirfactory.dricats.model.topology.endpoints.base.adapters.IPCAdapterDefinition;
import net.fhirfactory.dricats.model.topology.endpoints.base.valuesets.EndpointTopologyNodeTypeEnum;
import net.fhirfactory.dricats.model.topology.endpoints.edge.ask.EdgeAskETN;
import net.fhirfactory.dricats.model.topology.endpoints.http.HTTPClientETN;
import net.fhirfactory.dricats.model.topology.endpoints.http.HTTPServerETN;
import net.fhirfactory.dricats.model.topology.nodes.external.ConnectedExternalSystemTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.http.HTTPClientProcessorTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.http.HTTPServerProcessorTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.ipc.PetasosEdgeAskTN;
import net.fhirfactory.dricats.model.topology.nodes.valuesets.TopologyNodeNetworkingContextEnum;
import net.fhirfactory.dricats.configuration.api.services.common.PlatformInformationHelper;
import net.fhirfactory.pegacorn.deployment.topology.solution.LocalSolution;

@ApplicationScoped
public class HTTPTopologyEndpointFactory extends PlatformInformationHelper {
    private static final Logger LOG = LoggerFactory.getLogger(HTTPTopologyEndpointFactory.class);

    @Inject
    private ReferenceProperties pegacornProperties;

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
    
  
    public HTTPServerETN newHTTPServerTopologyEndpoint(WildflyBasedServerPropertyFile propertyFile, HTTPServerProcessorTN parentWUP, HTTPServerEndpointSegment httpServerPort){
        getLogger().debug(".newHTTPServerTopologyEndpoint(): Entry, parentWUP->{}, endpointFunctionName->{}, httpServerPort->{}", parentWUP, httpServerPort);
        if(httpServerPort == null){
            getLogger().debug(".newHTTPServerTopologyEndpoint(): Exit, no port to add");
            return(null);
        }
        HTTPServerETN httpServer = new HTTPServerETN();
        httpServer.constructNodeID(ComponentTypeEnum.ENDPOINT.getDisplayName());
        httpServer.setEndpointConfigurationName(httpServerPort.getName());
        httpServer.setActualHostIP(getActualHostIP());
        httpServer.setEndpointType(EndpointTopologyNodeTypeEnum.HTTP_API_SERVER);
        httpServer.setNetworkContext(TopologyNodeNetworkingContextEnum.COMPONENT_ROLE_INTERACT_INGRES);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("HTTPServer");
        componentType.setDisplayTypeName("HTTP Server");
        httpServer.setServer(true);
        httpServer.setParentNode(parentWUP.getNodeId());
        httpServer.setConnectedSystemName(httpServerPort.getConnectedSystem().getSubsystemName());
        //
        // Create the ServerAdapter (the thing that actually holds the port number and ip address details)
        HTTPServerAdapter httpServerAdapter = new HTTPServerAdapter();
        //
        // This is the Port Number exposed via the clustering service (i.e. a load-balancer)
        if(httpServerPort.getServerPort() == null) {
            throw(new IllegalArgumentException("serverPort for " + httpServerPort.getName() + " is not defined in the configuration file"));
        }
        httpServerAdapter.setPortNumber(httpServerPort.getServerPort());
        //
        // This is the Port Number exposed via the clustering service (i.e. a load-balancer)
        if(httpServerPort.getServicePort() == null){
            throw(new IllegalArgumentException("servicePort for " + httpServerPort.getName() + " is not defined in the configuration file"));
        }
        httpServerAdapter.setServicePortValue(httpServerPort.getServicePort());
        //
        // Set the context path
        String serverPath = httpServerPort.getContextPath();
        httpServerAdapter.setContextPath(serverPath);
        //
        // This is the hostname that we actually bind our port to
        if(StringUtils.isEmpty(httpServerPort.getServerHostname())){
            throw( new IllegalArgumentException("serverHost for " + httpServerPort.getName() + "is not defined in configuration file"));
        }
        httpServerAdapter.setHostName(httpServerPort.getServerHostname());
        //
        // This is the hostname/ip-address that we are exposing via the kubernetes clustering service (i.e. load-balancer)
        if(StringUtils.isEmpty(propertyFile.getLoadBalancer().getIpAddress())){
            if(StringUtils.isEmpty(httpServerPort.getServiceDNS())) {
                throw( new IllegalArgumentException("serviceDNS and loadBalancer.ipAddress for " + httpServerPort.getName() + "are not defined in configuration file"));
            } else {
                httpServerAdapter.setServiceDNSName(httpServerPort.getServiceDNS());
            }
        } else {
            httpServerAdapter.setServiceDNSName(propertyFile.getLoadBalancer().getIpAddress());
        }
        //
        // Should encryption (https) be used?
        httpServerAdapter.setEncrypted(httpServerPort.isEncrypted());
        //
        // Add the adapter to the TopologyEndpoint
        httpServer.setHTTPServerAdapter(httpServerAdapter);
        //
        // Now build the Endpoint Description
        if(httpServerAdapter.isEncrypted()) {
            if(StringUtils.isEmpty(httpServerAdapter.getContextPath())) {
                httpServer.setDescription("Server-->HTTPS://" + httpServerAdapter.getServiceDNSName() + ":" + httpServerAdapter.getServicePortValue() + "/");
            } else {
                httpServer.setDescription("Server-->HTTPS://" + httpServerAdapter.getServiceDNSName() + ":" + httpServerAdapter.getServicePortValue() + httpServerAdapter.getContextPath());
            }
        } else {
            if(StringUtils.isEmpty(httpServerAdapter.getContextPath())) {
                httpServer.setDescription("Server-->HTTP://" + httpServerAdapter.getServiceDNSName() + ":" + httpServerAdapter.getServicePortValue() + "/");
            } else {
                httpServer.setDescription("Server-->HTTP://" + httpServerAdapter.getServiceDNSName() + ":" + httpServerAdapter.getServicePortValue() + httpServerAdapter.getContextPath());
            }
        }
        //
        // Now build the Endpoint Participant Name
        String providerParticipantName = parentWUP.getParticipantName();
        if(httpServerAdapter.isEncrypted()) {
            httpServer.setParticipantName(providerParticipantName+".Server.HTTPS." +httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
            httpServer.setParticipantDisplayName("Server.HTTPS." + httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
        } else {
            httpServer.setParticipantName(providerParticipantName+".Server.HTTP." + httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
            httpServer.setParticipantDisplayName("Server.HTTP." + httpServerAdapter.getServiceDNSName() + "." + httpServerAdapter.getServicePortValue());
        }
        //
        // Add the Adapter to the endpoint
        httpServer.setHTTPServerAdapter(httpServerAdapter);
        //
        // Add the endpoint to the provider
        parentWUP.setIngresEndpoint(httpServer);
        //
        // Add the endpoint to the topology cache
        getLogger().trace(".newHTTPServerTopologyEndpoint(): Add the HTTP Server Port to the Topology Cache");
        getSolutionMap().addTopologyNode(httpServer);
        //
        // We're done
        getLogger().debug(".newHTTPServerTopologyEndpoint(): Exit, clusteredHTTPServerPort added");
        return(httpServer);
    }

    //
    // Build an HTTP Client Endpoint (Helper Method)
    //

    public HTTPClientETN newHTTPClientTopologyEndpoint(WildflyBasedServerPropertyFile propertyFile, HTTPClientProcessorTN parentWUP, HTTPClientEndpointSegment httpClientEndpointSegment){
        getLogger().debug(".newEdgeHTTPClientTopologyEndpoint(): Entry, parentWUP->{}, httpClientPortSegment->{}", parentWUP, httpClientEndpointSegment);
        HTTPClientETN httpClient = new HTTPClientETN();
        if(httpClientEndpointSegment == null){
            getLogger().debug(".newHTTPClient(): Exit, no port to add");
            return(null);
        }
        httpClient.constructNodeID(ComponentTypeEnum.ENDPOINT.getDisplayName());
        httpClient.setEndpointConfigurationName(httpClientEndpointSegment.getName());
        httpClient.setEndpointType(EndpointTopologyNodeTypeEnum.HTTP_API_CLIENT);
        httpClient.setNetworkContext(TopologyNodeNetworkingContextEnum.COMPONENT_ROLE_INTERACT_EGRESS);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("HTTPClient");
        componentType.setDisplayTypeName("HTTP Client");
        httpClient.setNodeType(componentType);
        httpClient.setParentNode(parentWUP.getNodeId());
        ConnectedSystemProperties connectedSystem = httpClientEndpointSegment.getConnectedSystem();
        httpClient.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTN externalSystem = new ConnectedExternalSystemTN();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
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
            if(StringUtils.isEmpty(targetPort1.getTargetPath())) {
                httpClient.setDescription(parentWUP.getParticipantName()+"."+"Client.HTTPS://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + "/");
            } else {
                httpClient.setDescription(parentWUP.getParticipantName()+"."+"Client.HTTPS://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + targetPort1.getTargetPath());
            }
        } else {
            if(StringUtils.isEmpty(targetPort1.getTargetPath())) {
                httpClient.setDescription(parentWUP.getParticipantName()+"."+"Server-->HTTP://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + "/");
            } else {
                httpClient.setDescription(parentWUP.getParticipantName()+"."+"Server-->HTTP://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + targetPort1.getTargetPath());
            }
        }

        //
        // Now build the Endpoint Participant Name / Participant Display Name
        if(targetPort1.getEncryptionRequired()) {
            httpClient.setParticipantName(parentWUP.getParticipantName()+".Client.HTTPS." + targetPort1.getTargetPortDNSName() + "." + targetPort1.getTargetPortValue());
            httpClient.setParticipantDisplayName("HTTPS.Client:" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue());

        } else {
            httpClient.setParticipantName(parentWUP.getParticipantName()+".Client.HTTP." + targetPort1.getTargetPortDNSName() + "." + targetPort1.getTargetPortValue());
            httpClient.setParticipantDisplayName("HTTP.Client:" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue());
        }
        //
        // Add the endpoint to the provider
        parentWUP.setEgressEndpoint(httpClient);
        //
        // Add the endpoint to the topology cache
        getLogger().trace(".newHTTPClient(): Add the httpClient Port to the Topology Cache");
        getSolutionMap().addTopologyNode(httpClient);
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
        IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
        currentInterfaceDefinition.setInterfaceFormalName(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionName());
        currentInterfaceDefinition.setInterfaceFormalVersion(connectedSystemPort.getTargetInterfaceDefinition().getInterfaceDefinitionVersion());
        systemEndpointPort.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        getLogger().debug(".HTTPClientAdapter(): Exit, systemEndpointPort->{}", systemEndpointPort);
        return (systemEndpointPort);
    }
    
    //
    // Build an HTTP Client Endpoint for Edge Ask
    //

    public EdgeAskETN newEdgeAskEndpoint(PetasosClusterPropertyFile propertyFile, PetasosEdgeAskTN parentWUP, HTTPClientEndpointSegment httpClientEndpointSegment){
        getLogger().debug(".newEdgeHTTPClientTopologyEndpoint(): Entry, parentWUP->{}, httpClientPortSegment->{}", parentWUP, httpClientEndpointSegment);
        EdgeAskETN httpClient = new EdgeAskETN();
        if(httpClientEndpointSegment == null){
            getLogger().debug(".newHTTPClient(): Exit, no port to add");
            return(null);
        }
        httpClient.constructNodeID(ComponentTypeEnum.ENDPOINT.getDisplayName());
        httpClient.setEndpointConfigurationName(httpClientEndpointSegment.getName());
        httpClient.setEndpointType(EndpointTopologyNodeTypeEnum.HTTP_API_CLIENT);
        httpClient.setNetworkContext(TopologyNodeNetworkingContextEnum.COMPONENT_ROLE_INTERACT_EGRESS);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("HTTPClient");
        componentType.setDisplayTypeName("HTTP Client");
        httpClient.setNodeType(componentType);
        httpClient.setParentNode(parentWUP.getNodeId());
        ConnectedSystemProperties connectedSystem = httpClientEndpointSegment.getConnectedSystem();
        httpClient.setConnectedSystemName(connectedSystem.getSubsystemName());
        ConnectedExternalSystemTN externalSystem = new ConnectedExternalSystemTN();
        externalSystem.setSubsystemName(connectedSystem.getSubsystemName());
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
        //
        // Now build the Endpoint Description
        if(targetPort1.getEncryptionRequired()) {
            if(StringUtils.isEmpty(targetPort1.getTargetPath())) {
                httpClient.setDescription(parentWUP.getParticipantName()+"."+"Client.HTTPS://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + "/");
            } else {
                httpClient.setDescription(parentWUP.getParticipantName()+"."+"Client.HTTPS://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + targetPort1.getTargetPath());
            }
        } else {
            if(StringUtils.isEmpty(targetPort1.getTargetPath())) {
                httpClient.setDescription(parentWUP.getParticipantName()+"."+"Server-->HTTP://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + "/");
            } else {
                httpClient.setDescription(parentWUP.getParticipantName()+"."+"Server-->HTTP://" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue() + targetPort1.getTargetPath());
            }
        }

        //
        // Now build the Endpoint Participant Name / Participant Display Name
        if(targetPort1.getEncryptionRequired()) {
            httpClient.setParticipantName(parentWUP.getParticipantName()+".Client.HTTPS." + targetPort1.getTargetPortDNSName() + "." + targetPort1.getTargetPortValue());
            httpClient.setParticipantDisplayName("HTTPS.Client:" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue());

        } else {
            httpClient.setParticipantName(parentWUP.getParticipantName()+".Client.HTTP." + targetPort1.getTargetPortDNSName() + "." + targetPort1.getTargetPortValue());
            httpClient.setParticipantDisplayName("HTTP.Client:" + targetPort1.getTargetPortDNSName() + ":" + targetPort1.getTargetPortValue());
        }
        //
        // Add the endpoint to the provider
        parentWUP.setEdgeAskEndpoint(httpClient);
        //
        // Add the endpoint to the topology cache
        getLogger().trace(".newHTTPClient(): Add the httpClient Port to the Topology Cache");
        getSolutionMap().addTopologyNode(httpClient);
        //
        // We're done
        getLogger().debug(".newHTTPClient(): Exit, endpoint added");
        return(httpClient);
    }

}
