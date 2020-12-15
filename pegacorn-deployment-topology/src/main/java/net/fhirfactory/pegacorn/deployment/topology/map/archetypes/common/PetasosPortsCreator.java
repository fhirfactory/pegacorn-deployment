package net.fhirfactory.pegacorn.deployment.topology.map.archetypes.common;

import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapEndpointElement;
import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapNodeElement;
import net.fhirfactory.pegacorn.petasos.model.topology.EndpointElementTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PetasosPortsCreator {
    private static final Logger LOG = LoggerFactory.getLogger(PetasosPortsCreator.class);

    public void addPetasosServerPorts(DeploymentMapNodeElement processingPlant, Integer offset, String version){
        LOG.debug(".addEdgeReceiverPorts(): Entry, processingPlant --> {}, offset --> {}", processingPlant, offset);
        String[] portNames = {
                "Port:Petasos-Resilience-Sync-Server",
                "Port:Petasos-Resilience-Distribution-Server",
                "Port:Petasos-Watchdog-Reporting-Server",
                "Port:Petasos-Watchdog-Heartbeat-Server"
        };

        Integer portValue = offset;
        for(String portName: portNames) {
            LOG.trace(".addEdgeReceiverPorts(): Adding endpoint --> {}", portName);
            DeploymentMapEndpointElement receiver = new DeploymentMapEndpointElement();
            receiver.setEndpointInstanceID(portName);
            receiver.setEndpointFunctionID(portName);
            receiver.setEndpointType(EndpointElementTypeEnum.API_SERVER);
            receiver.setExternalDNSEntry("___");
            receiver.setExternalPortNumber(Integer.toString(portValue));
            receiver.setIsServer(true);
            receiver.setRequiresEncryption(true);
            receiver.setVersion(version);
            processingPlant.getEndpoints().add(receiver);
            portValue += 1;
        }
    }
}
