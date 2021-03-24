package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.properties;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.properties.common.LadonConnectorProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.segments.MITaFSMSGatewayPropertyFileSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MITaFSMSGatewayConnectorProperties extends LadonConnectorProperties {
    private static Logger LOG = LoggerFactory.getLogger(MITaFSMSGatewayConnectorProperties.class);
    private static String TARGET_QAULIFIER_STRING = "mitaf-smsgateway";

    private MITaFSMSGatewayPropertyFileSegment mitafSMSGatewayPorperties;

    public MITaFSMSGatewayConnectorProperties(Integer lowerClusterServicePortBound, MITaFSMSGatewayPropertyFileSegment mitafSMSGatewayPorperties ){
        super(TARGET_QAULIFIER_STRING, lowerClusterServicePortBound, mitafSMSGatewayPorperties);
        this.mitafSMSGatewayPorperties = mitafSMSGatewayPorperties;
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    //
    // Property Names
    //
    public String getEndpointNameEdgeForwardCommunication(){
        return("endpoint:edge-forward-fhir-communication-client-to-"+getSubsystemName());
    }
    public String getEndpointNameEdgeReceiveCommunication(){
        return("endpoint:edge-receive-fhir-communication-server-for-"+getSubsystemName());
    }

    //
    // Property Resolvers
    //
    public String getEdgeReceiveCommunicationClusterServicePort(){
        getLogger().debug(".getEdgeReceiveCommunicationClusterServicePort(): Entry");
        PegacornStandardIPCPort serviceEdgeLadonPort = mitafSMSGatewayPorperties.getEdgeReceiveCommunication();
        if(serviceEdgeLadonPort == null){
            getLogger().error(".getEdgeReceiveCommunicationClusterServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = getLowerClusterServicePortBound() + serviceEdgeLadonPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceiveCommunicationClusterServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveCommunicationProcessingPlantPort(){
        getLogger().debug(".getEdgeReceiveCommunicationProcessingPlantPort(): Entry");
        PegacornStandardIPCPort serviceEdgeLadonPort = mitafSMSGatewayPorperties.getEdgeReceiveCommunication();
        if(serviceEdgeLadonPort == null){
            getLogger().error(".getEdgeReceiveCommunicationProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = serviceEdgeLadonPort.getPortValue();
        getLogger().debug(".getEdgeReceiveCommunicationProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }
}
