package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.standard;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.common.StandardClusterServicePortSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusteredServicePortSegment extends StandardClusterServicePortSegment {
    private static Logger LOG = LoggerFactory.getLogger(ClusteredServicePortSegment.class);

    @Override
    protected Logger specifyLogger() {
        return (LOG);
    }
}
