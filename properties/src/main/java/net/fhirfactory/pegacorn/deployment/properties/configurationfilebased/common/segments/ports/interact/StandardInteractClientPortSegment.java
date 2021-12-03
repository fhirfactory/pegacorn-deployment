package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes.ParameterNameValuePairType;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.StandardClientPortSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StandardInteractClientPortSegment extends StandardExternalFacingPort {
    private static Logger LOG = LoggerFactory.getLogger(StandardInteractClientPortSegment.class);

    private int defaultRetryCount;
    private int defaultRetryWait;
    private int defaultTimeout;

    public int getDefaultRetryCount() {
        return defaultRetryCount;
    }

    public void setDefaultRetryCount(int defaultRetryCount) {
        this.defaultRetryCount = defaultRetryCount;
    }

    public int getDefaultRetryWait() {
        return defaultRetryWait;
    }

    public void setDefaultRetryWait(int defaultRetryWait) {
        this.defaultRetryWait = defaultRetryWait;
    }

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    @Override
    protected Logger specifyLogger() {
        return (LOG);
    }

    @Override
    public String toString() {
        return "StandardInteractClientPortSegment{" +
                "portType=" + getPortType() +
                ", connectedSystem=" + getConnectedSystem() +
                ", startupDelay=" + getStartupDelay() +
                ", name=" + getName() +
                ", portParameters=" + getPortParameters() +
                ", defaultRetryCount=" + defaultRetryCount +
                ", defaultRetryWait=" + defaultRetryWait +
                ", defaultTimeout=" + defaultTimeout +
                '}';
    }
}
