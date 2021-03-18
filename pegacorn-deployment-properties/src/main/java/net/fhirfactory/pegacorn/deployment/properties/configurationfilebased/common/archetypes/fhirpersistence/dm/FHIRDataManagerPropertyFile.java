package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.fhirpersistence.dm;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornHTTPIPCPort;

public abstract class FHIRDataManagerPropertyFile extends BaseSubsystemPropertyFile {
    PegacornHTTPIPCPort fhirJPAServerPort;

    public PegacornHTTPIPCPort getFHIRJPAServerPort() {
        return fhirJPAServerPort;
    }

    public void getFHIRJPAServerPort(PegacornHTTPIPCPort edgeAnswer) {
        this.fhirJPAServerPort = edgeAnswer;
    }
}
