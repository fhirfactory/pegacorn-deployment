package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.mitaf.smsgateway;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardInteractPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.mitaf.common.MITaFSubsystemPropertyFile;

public class MITaFSMSGatewayPropertyFile extends MITaFSubsystemPropertyFile {

    private PegacornStandardInteractPort smsGateway;

    private PegacornStandardIPCPort edgeReceiveCommunication;

    public PegacornStandardInteractPort getSmsGateway() {
        return smsGateway;
    }

    public void setSmsGateway(PegacornStandardInteractPort smsGateway) {
        this.smsGateway = smsGateway;
    }

    public PegacornStandardIPCPort getEdgeReceiveCommunication() {
        return edgeReceiveCommunication;
    }

    public void setEdgeReceiveCommunication(PegacornStandardIPCPort edgeReceiveCommunication) {
        this.edgeReceiveCommunication = edgeReceiveCommunication;
    }
}
