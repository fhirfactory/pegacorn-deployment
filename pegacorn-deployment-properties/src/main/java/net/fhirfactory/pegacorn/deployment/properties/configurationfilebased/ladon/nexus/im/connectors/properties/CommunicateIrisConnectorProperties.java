/*
 * The MIT License
 *
 * Copyright 2020 Mark A. Hunter (ACT Health).
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
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.properties;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.properties.common.LadonConnectorProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.segments.CommunicateIrisPropertyFileSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommunicateIrisConnectorProperties extends LadonConnectorProperties {
    private static Logger LOG = LoggerFactory.getLogger(CommunicateIrisConnectorProperties.class);

    private CommunicateIrisPropertyFileSegment propertyFileSegment;

    public CommunicateIrisConnectorProperties(Integer lowerClusterServicePortBound, CommunicateIrisPropertyFileSegment propertyFileSegment)
    {
        super("communicate-fhirbridge", lowerClusterServicePortBound, propertyFileSegment);
        this.propertyFileSegment = propertyFileSegment;
    }

    //
    // Property Names
    //
    public String getEndpointNameEdgeForwardCareTeam(){
        return("endpoint:edge-forward-fhir-careteam-client-to-"+getSubsystemName());
    }
    public String getEndpointNameEdgeReceiveCareTeam(){
        return("endpoint:edge-receive-fhir-careteam-server-for-"+getSubsystemName());
    }

    public String getEndpointNameEdgeForwardCommunication(){
        return("endpoint:edge-forward-fhir-communication-client-to-"+getSubsystemName());
    }
    public String getEndpointNameEdgeReceiveCommunication(){
        return("endpoint:edge-receive-fhir-communication-server-for-"+getSubsystemName());
    }

    public String getEndpointNameEdgeForwardGroup(){
        return("endpoint:edge-forward-fhir-group-client-to-"+getSubsystemName());
    }
    public String getEndpointNameEdgeReceiveGroup(){
        return("endpoint:edge-receive-fhir-group-server-for-"+getSubsystemName());
    }

    public String getEndpointNameEdgeForwardPractitioner(){
        return("endpoint:edge-forward-fhir-practitioner-client-to-"+getSubsystemName());
    }
    public String getEndpointNameEdgeReceivePractitioner(){
        return("endpoint:edge-receive-fhir-practitioner-server-for-"+getSubsystemName());
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    //
    // Property Resolvers
    //




    public String getEdgeReceiveCareTeamClusterServicePort(){
        getLogger().debug(".getEdgeReceiveCareTeamClusterServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveCareTeam();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveCareTeamClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = getLowerClusterServicePortBound() + servicePegacornStandardIPCPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceiveCareTeamClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveCareTeamProcessingPlantPort(){
        getLogger().debug(".getEdgeReceiveCareTeamProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveCareTeam();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveCareTeamProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceiveCareTeamProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveEndpointClusterServicePort(){
        getLogger().debug(".getEdgeReceiveEndpointClusterServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveEndpoint();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveEndpointClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = getLowerClusterServicePortBound() + servicePegacornStandardIPCPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceiveEndpointClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveEndpointProcessingPlantPort(){
        getLogger().debug(".getEdgeReceiveEndpointProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveEndpoint();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveEndpointProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceiveEndpointProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveGroupClusterServicePort(){
        getLogger().debug(".getEdgeReceiveGroupClusterServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveGroup();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveGroupClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = getLowerClusterServicePortBound() + servicePegacornStandardIPCPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceiveGroupClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveGroupProcessingPlantPort(){
        getLogger().debug(".getEdgeReceiveGroupProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveGroup();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveGroupProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceiveGroupProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveOrganizationClusterServicePort(){
        getLogger().debug(".getEdgeReceiveOrganizationClusterServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveOrganization();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveOrganizationClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = getLowerClusterServicePortBound() + servicePegacornStandardIPCPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceiveOrganizationClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveOrganizationProcessingPlantPort(){
        getLogger().debug(".getEdgeReceiveOrganizationProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveOrganization();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveOrganizationProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceiveOrganizationProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceivePractitionerClusterServicePort(){
        getLogger().debug(".getEdgeReceivePractitionerClusterServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceivePractitioner();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceivePractitionerClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = getLowerClusterServicePortBound() + servicePegacornStandardIPCPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceivePractitionerClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceivePractitionerProcessingPlantPort(){
        getLogger().debug(".getEdgeReceivePractitionerProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceivePractitioner();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceivePractitionerProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceivePractitionerProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceivePractitionerRoleClusterServicePort(){
        getLogger().debug(".getEdgeReceivePractitionerRoleClusterServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceivePractitionerRole();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceivePractitionerRoleClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = getLowerClusterServicePortBound() + servicePegacornStandardIPCPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceivePractitionerRoleClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceivePractitionerRoleProcessingPlantPort(){
        getLogger().debug(".getEdgeReceivePractitionerRoleProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceivePractitioner();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceivePractitionerRoleProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceivePractitionerRoleProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveTaskClusterServicePort(){
        getLogger().debug(".getEdgeReceiveTaskClusterServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveTask();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveTaskClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = getLowerClusterServicePortBound() + servicePegacornStandardIPCPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceiveTaskClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveTaskProcessingPlantPort(){
        getLogger().debug(".getEdgeReceiveTaskProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveTask();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveTaskProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceiveTaskProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    //
    // Get FHIR:::Communication IPC Details
    //

    public PegacornStandardIPCPort getEdgeReceiveCommunicationClusterIPCPort(){
        getLogger().debug(".getEdgeReceiveCommunicationClusterIPCPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertyFileSegment.getEdgeReceiveCommunication();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveCommunicationClusterIPCPort(): Not such port type defined");
            return(null);
        }
        return(servicePegacornStandardIPCPort);
    }

    public String getEdgeReceiveCommunicationClusterServicePort(){
        getLogger().debug(".getEdgeReceiveCommunicationClusterServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = getEdgeReceiveCommunicationClusterIPCPort();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveCommunicationClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getClusterServicePortIncludingOffset(getLowerClusterServicePortBound());
        getLogger().debug(".getEdgeReceiveCommunicationClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveCommunicationProcessingPlantPort(){
        getLogger().debug(".getEdgeReceiveCommunicationProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = getEdgeReceiveCommunicationClusterIPCPort();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveCommunicationProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceiveCommunicationProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

}