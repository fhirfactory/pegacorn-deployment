package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.roomserver.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.common.CommunicateSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.PegacornHTTPServerClusterServicePort;

public abstract class CommunicateRoomServerIMPropertyFile extends CommunicateSubsystemPropertyFile {

    private PegacornHTTPServerClusterServicePort matrixClientServicesAPI;

    public PegacornHTTPServerClusterServicePort getMatrixClientServicesAPI() {
        return matrixClientServicesAPI;
    }

    public void setMatrixClientServicesAPI(PegacornHTTPServerClusterServicePort matrixClientServicesAPI) {
        this.matrixClientServicesAPI = matrixClientServicesAPI;
    }
}
