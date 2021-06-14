package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.iris.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.SecurityCredentialSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.HTTPClusteredServiceInteractPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.common.CommunicateSubsystemPropertyFile;

public class CommunicateIrisIMPropertyFile extends CommunicateSubsystemPropertyFile {


    private HTTPClusteredServiceInteractPortSegment interactMatrixApplicationServicesServer;
    private HTTPClusteredServiceInteractPortSegment interactPegacornVoIPServicesServer;
    private SecurityCredentialSegment wildflyUser;

    public CommunicateIrisIMPropertyFile(){
        super();
        wildflyUser = new SecurityCredentialSegment();
        interactMatrixApplicationServicesServer = new HTTPClusteredServiceInteractPortSegment();
        interactPegacornVoIPServicesServer = new HTTPClusteredServiceInteractPortSegment();
    }

    public HTTPClusteredServiceInteractPortSegment getInteractMatrixApplicationServicesServer() {
        return interactMatrixApplicationServicesServer;
    }

    public void setInteractMatrixApplicationServicesServer(HTTPClusteredServiceInteractPortSegment interactMatrixApplicationServicesServer) {
        this.interactMatrixApplicationServicesServer = interactMatrixApplicationServicesServer;
    }

    public HTTPClusteredServiceInteractPortSegment getInteractPegacornVoIPServicesServer() {
        return interactPegacornVoIPServicesServer;
    }

    public void setInteractPegacornVoIPServicesServer(HTTPClusteredServiceInteractPortSegment interactPegacornVoIPServicesServer) {
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
