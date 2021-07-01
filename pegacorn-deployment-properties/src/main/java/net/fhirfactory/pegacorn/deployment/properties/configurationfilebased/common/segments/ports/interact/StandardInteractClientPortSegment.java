package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.StandardClientPortSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardInteractClientPortSegment extends StandardClientPortSegment {
    private static Logger LOG = LoggerFactory.getLogger(StandardInteractClientPortSegment.class);

    @Override
    protected Logger specifyLogger() {
        return (LOG);
    }

    @Override
    public String toString() {
        return "StandardInteractClientPortSegment{" +
                "portType='" + getPortType() + '\'' +
                ", portParameters='" + getPortParameters() + '\'' +
                ", connectedSystem=" + getConnectedSystem() +
                ", defaultRetryCount=" + getDefaultRetryCount() +
                ", defaultRetryWait=" + getDefaultRetryWait() +
                ", defaultTimeout=" + getDefaultTimeout() +
                '}';
    }
}
