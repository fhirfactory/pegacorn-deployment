package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.iris.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.common.CommunicateSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.PegacornHTTPServerClusterServicePort;

public class CommunicateIrisIMPropertyFile extends CommunicateSubsystemPropertyFile {

    private PegacornHTTPServerClusterServicePort interactMatrixApplicationServicesServer;
    private PegacornHTTPServerClusterServicePort interactPegacornVoIPServicesServer;

    public PegacornHTTPServerClusterServicePort getInteractMatrixApplicationServicesServer() {
        return interactMatrixApplicationServicesServer;
    }

    public void setInteractMatrixApplicationServicesServer(PegacornHTTPServerClusterServicePort interactMatrixApplicationServicesServer) {
        this.interactMatrixApplicationServicesServer = interactMatrixApplicationServicesServer;
    }

    public PegacornHTTPServerClusterServicePort getInteractPegacornVoIPServicesServer() {
        return interactPegacornVoIPServicesServer;
    }

    public void setInteractPegacornVoIPServicesServer(PegacornHTTPServerClusterServicePort interactPegacornVoIPServicesServer) {
        this.interactPegacornVoIPServicesServer = interactPegacornVoIPServicesServer;
    }
}
