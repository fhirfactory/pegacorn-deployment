package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.iris.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.SecurityCredentialSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.ClusteredInteractHTTPServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.common.CommunicateSubsystemPropertyFile;

public class CommunicateIrisIMPropertyFile extends CommunicateSubsystemPropertyFile {


    private ClusteredInteractHTTPServerPortSegment interactMatrixApplicationServicesServer;
    private ClusteredInteractHTTPServerPortSegment interactPegacornVoIPServicesServer;
    private SecurityCredentialSegment wildflyUser;

    public CommunicateIrisIMPropertyFile(){
        super();
        wildflyUser = new SecurityCredentialSegment();
        interactMatrixApplicationServicesServer = new ClusteredInteractHTTPServerPortSegment();
        interactPegacornVoIPServicesServer = new ClusteredInteractHTTPServerPortSegment();
    }

    public ClusteredInteractHTTPServerPortSegment getInteractMatrixApplicationServicesServer() {
        return interactMatrixApplicationServicesServer;
    }

    public void setInteractMatrixApplicationServicesServer(ClusteredInteractHTTPServerPortSegment interactMatrixApplicationServicesServer) {
        this.interactMatrixApplicationServicesServer = interactMatrixApplicationServicesServer;
    }

    public ClusteredInteractHTTPServerPortSegment getInteractPegacornVoIPServicesServer() {
        return interactPegacornVoIPServicesServer;
    }

    public void setInteractPegacornVoIPServicesServer(ClusteredInteractHTTPServerPortSegment interactPegacornVoIPServicesServer) {
        this.interactPegacornVoIPServicesServer = interactPegacornVoIPServicesServer;
    }

    public SecurityCredentialSegment getWildflyUser() {
        return wildflyUser;
    }

    public void setWildflyUser(SecurityCredentialSegment wildflyUser) {
        this.wildflyUser = wildflyUser;
    }

    @Override
    public String toString() {
        return "CommunicateIrisIMPropertyFile{" +
                "interactMatrixApplicationServicesServer=" + interactMatrixApplicationServicesServer +
                ", interactPegacornVoIPServicesServer=" + interactPegacornVoIPServicesServer +
                ", wildflyUser=" + wildflyUser +
                "," + super.toString() +
                '}';
    }
}
