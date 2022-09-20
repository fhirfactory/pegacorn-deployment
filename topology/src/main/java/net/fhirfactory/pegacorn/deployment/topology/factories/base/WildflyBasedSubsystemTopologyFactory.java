/*
 * The MIT License
 *
 * Copyright 2022 Mark A. Hunter.
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
package net.fhirfactory.pegacorn.deployment.topology.factories.base;

import javax.inject.Inject;
import net.fhirfactory.dricats.configuration.defaults.dricats.systemwide.base.WildflyManagementIPCEndpointConstants;
import net.fhirfactory.dricats.model.component.datatypes.ComponentTypeDefinition;
import net.fhirfactory.dricats.model.component.valuesets.ComponentTypeEnum;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.common.WildflyBasedServerPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.http.HTTPServerEndpointSegment;
import net.fhirfactory.dricats.model.topology.endpoints.http.adapters.HTTPServerAdapter;
import net.fhirfactory.dricats.model.topology.endpoints.base.valuesets.EndpointTopologyNodeTypeEnum;
import net.fhirfactory.dricats.model.topology.endpoints.http.HTTPServerETN;
import net.fhirfactory.dricats.model.topology.nodes.base.TopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.processingplants.base.ProcessingPlantTopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workshops.WildflyManagementWorkshopTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.wildfly.WildflyJolokiaServiceTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.wildfly.WildflyLivelinessServiceTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.wildfly.WildflyPrometheusServiceTN;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.wildfly.WildflyReadinessServiceTN;

/**
 *
 * @author mhunter
 */
public abstract class WildflyBasedSubsystemTopologyFactory extends TopologyFactory{
    
    @Inject
    private WildflyManagementIPCEndpointConstants wildflyIPCNames;
    
    //
    // Getters (and Setters)
    //
    
    protected WildflyManagementIPCEndpointConstants getWildflyIPCNames(){
        return(wildflyIPCNames);
    }
    
    //
    // Wildfly Management Ports
    //
    
    public WildflyManagementWorkshopTN createWildflyManagementServices(ProcessingPlantTopologyNode processingPlant){
        getLogger().debug(".createWildflyManagementServices(): Entry, processingPlant->{}", processingPlant);
        
        //
        // Wildfly Management Workshop (note, this will create the contained WUP Topology Nodes as well)
        WildflyManagementWorkshopTN wildflyManagementWorkshop = new WildflyManagementWorkshopTN(processingPlant.getParticipantName(), processingPlant.getNodeVersion());
        
        // Add Endpoints
        createPrometheusPort(wildflyManagementWorkshop.getPrometheusServiceNode());
        createLivelinessPort(wildflyManagementWorkshop.getLivelinessServiceNode());
        createReadinessPort(wildflyManagementWorkshop.getReadinessServiceNode());
        createJolokiaPort(wildflyManagementWorkshop.getJolokiaServiceNode());
        
        getLogger().debug(".createWildflyManagementServices(): Exit");
        return(wildflyManagementWorkshop);
    }
    
    
    //
    // Build Prometheus Port (if there)
    //

    protected void createPrometheusPort( WildflyPrometheusServiceTN wupNode){
        getLogger().debug(".addPrometheusPort(): Entry, wupNode->{}", wupNode);
        HTTPServerEndpointSegment portSegment = ((WildflyBasedServerPropertyFile)getSubsystemPropertyFile()).getPrometheusPort();
        if(portSegment == null){
            getLogger().debug(".addPrometheusPort(): Exit, no port to add");
            return;
        }

        String participantDisplayName = getWildflyIPCNames().getPrometheusEndpointName();
        String participantName = wupNode.getParticipantName() + "." + participantDisplayName;
        HTTPServerETN prometheusPort = createWildflyHTTPServerPort(participantName, participantDisplayName, wupNode, portSegment);
        
        // Add the Prometheus Endpoint to the WUP
        wupNode.setPrometheusServerEndpoint(prometheusPort);

        getLogger().trace(".addPrometheusPort(): Add the Prometheus Port to the Topology Cache");
        getSolutionMap().addTopologyNode(prometheusPort);
        getLogger().debug(".addPrometheusPort(): Exit, endpoint added");
    }

    //
    // Build Jolokia Port (if there)
    //

    protected void createJolokiaPort(WildflyJolokiaServiceTN wupNode){
        getLogger().debug(".createJolokiaPort(): Entry, wupNode->{}", wupNode);
        HTTPServerEndpointSegment portConfigurationSegment = ((WildflyBasedServerPropertyFile)getSubsystemPropertyFile()).getJolokiaPort();
        if(portConfigurationSegment == null){
            getLogger().debug(".createJolokiaPort(): Exit, no port to add");
            return;
        }

        String participantDisplayName = getWildflyIPCNames().getJolokiaEndpointName();
        String participantName = wupNode.getParticipantName() + "." + participantDisplayName;
        HTTPServerETN jolokiaPort = createWildflyHTTPServerPort(participantName, participantDisplayName, wupNode, portConfigurationSegment);
        
        // Add the Jolokia Endpoint to the WUP
        wupNode.setJolokiaServerEndpoint(jolokiaPort);

        getLogger().trace(".createJolokiaPort(): Add the Jolokia Port to the Topology Cache");
        getSolutionMap().addTopologyNode(jolokiaPort);
        getLogger().debug(".createJolokiaPort(): Exit, endpoint added");
    }

    //
    // Build KubeLiveliness Port (if there)
    //

    protected void createLivelinessPort(WildflyLivelinessServiceTN wupNode){
        getLogger().debug(".addKubeLivelinessPort(): Entry, wupNode->{}", wupNode);
        HTTPServerEndpointSegment portConfigurationSegment = ((WildflyBasedServerPropertyFile)getSubsystemPropertyFile()).getKubeLivelinessProbe();
        if(portConfigurationSegment == null){
            getLogger().debug(".addKubeLivelinessPort(): Exit, no port to add");
            return;
        }
        
        String participantDisplayName = getWildflyIPCNames().getLivelinessEndpointName();
        String participantName = wupNode.getParticipantName() + "." + participantDisplayName;
        HTTPServerETN livelinessPort = createWildflyHTTPServerPort(participantName, participantDisplayName, wupNode, portConfigurationSegment);

        
        // Add the Liveliness Endpoint to the WUP
        wupNode.setLivelinessServerEndpoint(livelinessPort);

        getLogger().trace(".addKubeLivelinessPort(): Add the KubeLivelinessPort Port to the Topology Cache");
        getSolutionMap().addTopologyNode(livelinessPort);
        getLogger().debug(".addKubeLivelinessPort(): Exit, endpoint added");
    }

    //
    // Build KubeReadiness Port (if there)
    //

    protected void createReadinessPort(WildflyReadinessServiceTN wupNode){
        getLogger().debug(".createReadinessPort(): Entry, wupNode->{}", wupNode);

        HTTPServerEndpointSegment portConfigurationSegment = ((WildflyBasedServerPropertyFile)getSubsystemPropertyFile()).getKubeReadinessProbe();
        if(portConfigurationSegment == null){
            getLogger().debug(".createReadinessPort(): Exit, no port to add");
            return;
        }
        
        String participantDisplayName = getWildflyIPCNames().getReadinessEndpointName();
        String participantName = wupNode.getParticipantName() + "." + participantDisplayName;
        HTTPServerETN readinessPort = createWildflyHTTPServerPort(participantName, participantDisplayName, wupNode, portConfigurationSegment);
        
        // Add the Readiness Endpoint to the WUP
        wupNode.setReadinessServerEndpoint(readinessPort);
        
        getLogger().trace(".createReadinessPort(): Add the Readiness Port to the Topology Cache");
        getSolutionMap().addTopologyNode(readinessPort);
        getLogger().debug(".createReadinessPort(): Exit, endpoint added");
    }
    
    //
    // Wildfly Port Creation
    //
    
    protected HTTPServerETN createWildflyHTTPServerPort(String participantName, String participantDisplayName, TopologyNode wupNode, HTTPServerEndpointSegment portConfigurationSegment){
        getLogger().debug(".createWildflyHTTPServerPort(): Entry, participantName->{}, participantDisplayName->{}, wupNode->{}, portConfigurationSegment->{}", participantName, participantDisplayName, wupNode, portConfigurationSegment);
        
        if(portConfigurationSegment == null){
            getLogger().debug(".createWildflyHTTPServerPort(): Exit, no port to add");
            return(null);
        }
         HTTPServerETN wildflyAdminPort = new HTTPServerETN();
        
        wildflyAdminPort.constructNodeID(participantName);

        wildflyAdminPort.setParticipantName(participantName);
        wildflyAdminPort.setParticipantDisplayName(participantDisplayName);
        wildflyAdminPort.setEndpointConfigurationName(portConfigurationSegment.getName());
        wildflyAdminPort.setEndpointType(EndpointTopologyNodeTypeEnum.HTTP_API_SERVER);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("Endpoint(HTTP.Server)");
        componentType.setDisplayTypeName("Endpoint(HTTP.Server)");
        wildflyAdminPort.setNodeType(componentType);
        wildflyAdminPort.setServer(true);
        wildflyAdminPort.setParentNode(wupNode.getNodeId());

        HTTPServerAdapter httpServerAdapter = new HTTPServerAdapter();
        httpServerAdapter.setEncrypted(getSubsystemPropertyFile().getDeploymentMode().isUsingInternalEncryption());
        httpServerAdapter.setPortNumber(portConfigurationSegment.getServerPort());
        httpServerAdapter.setContextPath(portConfigurationSegment.getContextPath());
        httpServerAdapter.setHostName(portConfigurationSegment.getServerHostname());
        wildflyAdminPort.setHTTPServerAdapter(httpServerAdapter);
        
        getLogger().debug(".createWildflyHTTPServerPort(): Exit, wildflyAdminPort->{}",wildflyAdminPort);
        return(wildflyAdminPort);
    }
   
}
