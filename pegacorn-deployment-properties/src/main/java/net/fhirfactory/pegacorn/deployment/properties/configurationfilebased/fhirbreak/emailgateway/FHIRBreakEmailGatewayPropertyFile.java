package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.emailgateway;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusterServiceInteractPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.common.StandardPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.common.FHIRBreakSubsystemPropertyFile;

public class FHIRBreakEmailGatewayPropertyFile extends FHIRBreakSubsystemPropertyFile {

    private ClusterServiceInteractPortSegment smtpGateway;
    private ClusterServiceInteractPortSegment imapGateway;

    private StandardPortSegment edgeReceiveCommunication;

    public ClusterServiceInteractPortSegment getSmtpGateway() {
        return smtpGateway;
    }

    public void setSmtpGateway(ClusterServiceInteractPortSegment smtpGateway) {
        this.smtpGateway = smtpGateway;
    }

    public ClusterServiceInteractPortSegment getImapGateway() {
        return imapGateway;
    }

    public void setImapGateway(ClusterServiceInteractPortSegment imapGateway) {
        this.imapGateway = imapGateway;
    }

    public StandardPortSegment getEdgeReceiveCommunication() {
        return edgeReceiveCommunication;
    }

    public void setEdgeReceiveCommunication(StandardPortSegment edgeReceiveCommunication) {
        this.edgeReceiveCommunication = edgeReceiveCommunication;
    }
}
