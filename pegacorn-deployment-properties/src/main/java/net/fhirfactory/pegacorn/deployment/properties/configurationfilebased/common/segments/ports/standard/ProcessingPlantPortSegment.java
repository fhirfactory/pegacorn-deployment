package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.standard;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.common.StandardPortSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessingPlantPortSegment extends StandardPortSegment {
    private static Logger LOG = LoggerFactory.getLogger(ProcessingPlantPortSegment.class);

    @Override
    protected Logger specifyLogger() {
        return (LOG);
    }
}
