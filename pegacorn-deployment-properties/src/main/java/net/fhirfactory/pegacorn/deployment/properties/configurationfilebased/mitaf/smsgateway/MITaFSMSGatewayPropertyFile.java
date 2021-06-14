package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.mitaf.smsgateway;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.common.StandardPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.StandardInteractPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.mitaf.common.MITaFSubsystemPropertyFile;

public class MITaFSMSGatewayPropertyFile extends MITaFSubsystemPropertyFile {

    private StandardInteractPortSegment smsGateway;

    private StandardPortSegment edgeReceiveCommunication;

    public StandardInteractPortSegment getSmsGateway() {
        return smsGateway;
    }

    public void setSmsGateway(StandardInteractPortSegment smsGateway) {
        this.smsGateway = smsGateway;
    }

    public StandardPortSegment getEdgeReceiveCommunication() {
        return edgeReceiveCommunication;
    }

    public void setEdgeReceiveCommunication(StandardPortSegment edgeReceiveCommunication) {
        this.edgeReceiveCommunication = edgeReceiveCommunication;
    }
}
