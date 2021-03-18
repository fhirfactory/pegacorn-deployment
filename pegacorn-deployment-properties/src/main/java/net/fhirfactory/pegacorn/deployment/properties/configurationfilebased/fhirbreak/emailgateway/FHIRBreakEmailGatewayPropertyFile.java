package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.emailgateway;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornClusterServiceInteractPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.fhirbreak.common.FHIRBreakSubsystemPropertyFile;

public class FHIRBreakEmailGatewayPropertyFile extends FHIRBreakSubsystemPropertyFile {

    private PegacornClusterServiceInteractPort smtpGateway;
    private PegacornClusterServiceInteractPort imapGateway;

    private PegacornStandardIPCPort edgeReceiveCommunication;

    public PegacornClusterServiceInteractPort getSmtpGateway() {
        return smtpGateway;
    }

    public void setSmtpGateway(PegacornClusterServiceInteractPort smtpGateway) {
        this.smtpGateway = smtpGateway;
    }

    public PegacornClusterServiceInteractPort getImapGateway() {
        return imapGateway;
    }

    public void setImapGateway(PegacornClusterServiceInteractPort imapGateway) {
        this.imapGateway = imapGateway;
    }

    public PegacornStandardIPCPort getEdgeReceiveCommunication() {
        return edgeReceiveCommunication;
    }

    public void setEdgeReceiveCommunication(PegacornStandardIPCPort edgeReceiveCommunication) {
        this.edgeReceiveCommunication = edgeReceiveCommunication;
    }
}
