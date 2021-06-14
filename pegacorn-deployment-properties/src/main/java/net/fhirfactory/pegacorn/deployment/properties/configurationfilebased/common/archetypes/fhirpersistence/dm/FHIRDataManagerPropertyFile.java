package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.fhirpersistence.dm;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.standard.HTTPClusteredServicePortSegment;

public abstract class FHIRDataManagerPropertyFile extends BaseSubsystemPropertyFile {
    HTTPClusteredServicePortSegment fhirJPAServerPort;

    public HTTPClusteredServicePortSegment getFHIRJPAServerPort() {
        return fhirJPAServerPort;
    }

    public void getFHIRJPAServerPort(HTTPClusteredServicePortSegment edgeAnswer) {
        this.fhirJPAServerPort = edgeAnswer;
    }
}
