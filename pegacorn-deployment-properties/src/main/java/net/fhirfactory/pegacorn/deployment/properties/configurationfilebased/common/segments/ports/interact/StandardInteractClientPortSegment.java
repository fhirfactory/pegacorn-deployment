package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.StandardClientPortSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardInteractClientPortSegment extends StandardExternalFacingPort {
    private static Logger LOG = LoggerFactory.getLogger(StandardInteractClientPortSegment.class);

    private int defaultRetryCount;
    private int defaultRetryWait;
    private int defaultTimeout;

    //
    // Constructor(s)
    //

    public StandardInteractClientPortSegment(){
        super();
        this.defaultRetryCount = 3;
        this.defaultRetryWait = 30;
        this.defaultTimeout = 30;
    }

    //
    // Getters and Setters
    //

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

    //
    // To String
    //


    @Override
    public String toString() {
        return "StandardInteractClientPortSegment{" +
                "otherConfigurationParameters=" + getOtherConfigurationParameters() +
                ", portValue=" + getPortValue() +
                ", portType='" + getPortType() + '\'' +
                ", server=" + isServer() +
                ", encrypted=" + isEncrypted() +
                ", supportedInterfaceProfiles=" + getSupportedInterfaceProfiles() +
                ", hostDNSEntry='" + getHostDNSEntry() + '\'' +
                ", startupDelay=" + getStartupDelay() +
                ", name='" + getName() + '\'' +
                ", connectedSystem=" + getConnectedSystem() +
                ", defaultRetryCount=" + defaultRetryCount +
                ", defaultRetryWait=" + defaultRetryWait +
                ", defaultTimeout=" + defaultTimeout +
                '}';
    }
}
