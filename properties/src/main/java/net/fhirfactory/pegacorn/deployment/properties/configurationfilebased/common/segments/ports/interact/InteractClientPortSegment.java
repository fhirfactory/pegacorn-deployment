package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.StandardClientPortSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InteractClientPortSegment extends StandardClientPortSegment {
    private static Logger LOG = LoggerFactory.getLogger(InteractClientPortSegment.class);


    @Override
    protected Logger specifyLogger() {
        return (LOG);
    }

    @Override
    public String toString() {
        return "InteractClientPortSegment{" +
                "otherConfigurationParameters=" + getOtherConfigurationParameters() +
                ", defaultRetryCount=" + getDefaultRetryCount() +
                ", defaultRetryWait=" + getDefaultRetryWait() +
                ", defaultTimeout=" + getDefaultTimeout() +
                ", connectedSystem=" + getConnectedSystem() +
                ", portType='" + getPortType() + '\'' +
                ", server=" + isServer() +
                ", encrypted=" + isEncrypted() +
                ", startupDelay=" + getStartupDelay() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
