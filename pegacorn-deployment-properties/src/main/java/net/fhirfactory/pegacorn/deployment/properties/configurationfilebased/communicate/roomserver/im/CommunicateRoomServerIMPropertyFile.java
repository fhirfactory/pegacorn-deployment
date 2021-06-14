package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.roomserver.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.standard.HTTPClusteredServicePortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.communicate.common.CommunicateSubsystemPropertyFile;

public abstract class CommunicateRoomServerIMPropertyFile extends CommunicateSubsystemPropertyFile {

    private HTTPClusteredServicePortSegment matrixClientServicesAPI;

    public HTTPClusteredServicePortSegment getMatrixClientServicesAPI() {
        return matrixClientServicesAPI;
    }

    public void setMatrixClientServicesAPI(HTTPClusteredServicePortSegment matrixClientServicesAPI) {
        this.matrixClientServicesAPI = matrixClientServicesAPI;
    }
}
