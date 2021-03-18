package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.properties.common;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.segments.common.LadonConnectorPropertyFileSegment;
import org.slf4j.Logger;

public abstract class LadonConnectorProperties {
    abstract protected Logger getLogger();

    private String subsystemName;
    private LadonConnectorPropertyFileSegment propertiesSegmentBase;
    Integer lowerClusterServicePortBound;

    public LadonConnectorProperties(String subsystemName, Integer lowerClusterServicePortBound, LadonConnectorPropertyFileSegment propertiesSegmentBase){
        this.subsystemName = subsystemName;
        this.propertiesSegmentBase = propertiesSegmentBase;
        this.lowerClusterServicePortBound = lowerClusterServicePortBound;
    }

    public String getEndpointNamePetasosFinalisationServer(){
        return("endpoint:petasos-finalisation-server-for-"+subsystemName);
    }
    public String getEndpointNamePetasosHeartbeatServer(){
        return("endpoint:petasos-heartbeat-server-for-"+subsystemName);
    }
    public String getEndpointNamePetasosFinalisationClient(){
        return("endpoint:petasos-finalisation-client-to-"+subsystemName);
    }
    public String getEndpointNamePetasosHeartbeatClient(){
        return("endpoint:petasos-heartbeat-client-to-"+subsystemName);
    }

    public String getEndpointNameEdgeForwardBundle(){
        return("endpoint:edge-forward-fhir-bundle-client-to-"+ subsystemName);
    }
    public String getEndpointNameEdgeReceiveBundle(){
        return("endpoint:edge-receive-fhir-bundle-server-for-"+ subsystemName);
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public LadonConnectorPropertyFileSegment getPropertiesSegmentBase() {
        return propertiesSegmentBase;
    }

    public void setPropertiesSegmentBase(LadonConnectorPropertyFileSegment propertiesSegmentBase) {
        this.propertiesSegmentBase = propertiesSegmentBase;
    }

    public Integer getLowerClusterServicePortBound() {
        return lowerClusterServicePortBound;
    }

    public void setLowerClusterServicePortBound(Integer lowerClusterServicePortBound) {
        this.lowerClusterServicePortBound = lowerClusterServicePortBound;
    }

    //
    // Property Names
    //
    public String getEndpointNamePetasosFinalisationServerForLadon(){return("endpoint:petasos-finalisation-server-for-ladon");}
    public String getEndpointNamePetasosHeartbeatServerForLadon(){return("endpoint:petasos-heartbeat-server-for-ladon");}

    public String getEndpointNamePetasosFinalisationClientToLadon(){return("endpoint:petasos-finalisation-client-to-ladon");}
    public String getEndpointNamePetasosHeartbeatClientToLadon(){return("endpoint:petasos-heartbeat-client-to-ladon");}

    public String getEndpointNameEdgeForwardFHIRBundleClientToLadon(){return("endpoint:edge-forward-fhir-bundle-client-to-ladon");}
    public String getEndpointNameEdgeReceiveFHIRBundleServerForLadon(){return("endpoint:edge-forward-fhir-bundle-server-for-ladon");}

    //
    // Property Resolvers
    //
    public String getPetasosFinalisationClusterServicePort(){
        getLogger().debug(".getPetasosFinalisationServicePort(): Entry");
        PegacornStandardIPCPort petasosPort = propertiesSegmentBase.getPetasosFinalisation();
        Integer portValue =  lowerClusterServicePortBound + petasosPort.getClusterServicePortOffsetValue();
        String portValueAsString = Integer.toString(portValue);
        getLogger().debug(".getPetasosFinalisationServicePort(): Exit, PetasosFinalisationServicePort --> {}", portValueAsString);
        return(portValueAsString);
    }

    public String getPetasosHeartbeatClusterServicePort(){
        getLogger().debug(".getPetasosHeartbeatServicePort(): Entry");
        PegacornStandardIPCPort petasosPort = propertiesSegmentBase.getPetasosHeartbeat();
        Integer portValue = lowerClusterServicePortBound + petasosPort.getClusterServicePortOffsetValue();
        String portValueAsString = Integer.toString(portValue);
        getLogger().debug(".getPetasosHeartbeatServicePort(): Exit, ServicePetasosHeartbeatPort --> {}", portValueAsString);
        return(portValueAsString);
    }

    public String getPetasosFinalisationProcessingPlantPort(){
        getLogger().debug(".getPetasosFinalisationProcessingPlantPort(): Entry");
        PegacornStandardIPCPort petasosPort = propertiesSegmentBase.getPetasosFinalisation();
        Integer portValue = petasosPort.getPortValue();
        String portValueAsString = Integer.toString(portValue);
        getLogger().debug(".getPetasosFinalisationProcessingPlantPort(): Exit, processingPlantPort --> {}", portValueAsString);
        return(portValueAsString);
    }

    public String getPetasosHeartbeatProcessingPlantPort(){
        getLogger().debug(".getPetasosHeartbeatProcessingPlantPort(): Entry");
        PegacornStandardIPCPort petasosPort = propertiesSegmentBase.getPetasosHeartbeat();
        Integer portValue = petasosPort.getPortValue();
        String portValueAsString = Integer.toString(portValue);
        getLogger().debug(".getPetasosHeartbeatProcessingPlantPort(): Exit, processingPlantPort --> {}", portValueAsString);
        return(portValueAsString);
    }

    public String getEdgeReceiveBundleClusterServicePort(){
        getLogger().debug(".getEdgeReceiveBundleServicePort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertiesSegmentBase.getEdgeReceiveBundle();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveBundleServicePort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = lowerClusterServicePortBound + servicePegacornStandardIPCPort.getClusterServicePortOffsetValue();
        getLogger().debug(".getEdgeReceiveBundleServicePort(): Exit, Service Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

    public String getEdgeReceiveBundleProcessingPlantPort(){
        getLogger().debug(".getEdgeReceiveBundleProcessingPlantPort(): Entry");
        PegacornStandardIPCPort servicePegacornStandardIPCPort = propertiesSegmentBase.getEdgeReceiveBundle();
        if(servicePegacornStandardIPCPort == null){
            getLogger().error(".getEdgeReceiveBundleProcessingPlantPort(): Not such port type defined");
            return(Integer.toString(0));
        }
        Integer portValue = servicePegacornStandardIPCPort.getPortValue();
        getLogger().debug(".getEdgeReceiveBundleProcessingPlantPort(): Exit, ProcessingPlant Port Number --> {}", portValue);
        return(Integer.toString(portValue));
    }

}

