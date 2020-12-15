package net.fhirfactory.pegacorn.deployment.topology.map.archetypes.common;

import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapEndpointElement;
import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapNodeElement;
import net.fhirfactory.pegacorn.petasos.model.topology.EndpointElementTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EdgeMessagingPortsCreator {
    private static final Logger LOG = LoggerFactory.getLogger(EdgeMessagingPortsCreator.class);

    public void addEdgeReceiverPorts(DeploymentMapNodeElement processingPlant, Integer offset, String version){
        LOG.debug(".addEdgeReceiverPorts(): Entry, processingPlant --> {}, offset --> {}", processingPlant, offset);
        String[] portNames = {
                "Port:Edge-Receive-Foundation-Terminology",
                "Port:Edge-Receive-Foundation-Other",
                "Port:Edge-Receive-Foundation-Documents",
                "Port:Edge-Receive-Base-Individuals",
                "Port:Edge-Receive-Base-Entities",
                "Port:Edge-Receive-Base-Workflow",
                "Port:Edge-Receive-Base-Management",
                "Port:Edge-Receive-Clinical-Summary",
                "Port:Edge-Receive-Clinical-Diagnostics",
                "Port:Edge-Receive-Clinical-Medications",
                "Port:Edge-Receive-Clinical-CareProvision",
                "Port:Edge-Receive-Clinical-RequestAndResponse",
                "Port:Edge-Receive-Financial-Support",
                "Port:Edge-Receive-Financial-Billing",
                "Port:Edge-Receive-Financial-Payment",
                "Port:Edge-Receive-Financial-General"
        };

        Integer portValue = offset;
        for(String portName: portNames) {
            LOG.trace(".addEdgeReceiverPorts(): Adding endpoint --> {}", portName);
            DeploymentMapEndpointElement receiver = new DeploymentMapEndpointElement();
            receiver.setEndpointInstanceID(portName);
            receiver.setEndpointFunctionID(portName);
            receiver.setEndpointType(EndpointElementTypeEnum.INGRES_MESSAGING);
            receiver.setExternalDNSEntry("___");
            receiver.setExternalPortNumber(Integer.toString(portValue));
            receiver.setIsServer(true);
            receiver.setRequiresEncryption(true);
            receiver.setVersion(version);
            processingPlant.getEndpoints().add(receiver);
            portValue += 1;
        }
    }

    public void addEdgeForwarders(DeploymentMapNodeElement processingPlant, String version){
        String[] portNames = {
                "Port:Edge-Forward-Foundation-Terminology",
                "Port:Edge-Forward-Foundation-Other",
                "Port:Edge-Forward-Foundation-Documents",
                "Port:Edge-Forward-Base-Individuals",
                "Port:Edge-Forward-Base-Entities",
                "Port:Edge-Forward-Base-Workflow",
                "Port:Edge-Forward-Base-Management",
                "Port:Edge-Forward-Clinical-Summary",
                "Port:Edge-Forward-Clinical-Diagnostics",
                "Port:Edge-Forward-Clinical-Medications",
                "Port:Edge-Forward-Clinical-CareProvision",
                "Port:Edge-Forward-Clinical-RequestAndResponse",
                "Port:Edge-Forward-Financial-Support",
                "Port:Edge-Forward-Financial-Billing",
                "Port:Edge-Forward-Financial-Payment",
                "Port:Edge-Forward-Financial-General"
        };

        for(String portName: portNames) {
            DeploymentMapEndpointElement forwarder = new DeploymentMapEndpointElement();
            forwarder.setEndpointInstanceID(portName);
            forwarder.setEndpointFunctionID(portName);
            forwarder.setEndpointType(EndpointElementTypeEnum.EGRESS_MESSAGING);
            forwarder.setExternalDNSEntry("___");
            forwarder.setIsServer(false);
            forwarder.setRequiresEncryption(true);
            forwarder.setVersion(version);
            processingPlant.getEndpoints().add(forwarder);
        }
    }
}
