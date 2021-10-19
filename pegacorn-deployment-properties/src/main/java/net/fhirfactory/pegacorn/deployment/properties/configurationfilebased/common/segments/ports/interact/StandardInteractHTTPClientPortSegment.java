package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardInteractHTTPClientPortSegment extends StandardInteractClientPortSegment {
    private static Logger LOG = LoggerFactory.getLogger(StandardInteractHTTPClientPortSegment.class);

    private String contextPath;

    //
    // Constructor(s)
    //

    public StandardInteractHTTPClientPortSegment(){
        super();
         this.contextPath = null;
    }

    public StandardInteractHTTPClientPortSegment(StandardInteractClientPortSegment clientPortSegment){

    }

    //
    // Getters and Setters
    //

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
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
        return "StandardInteractHTTPClientPortSegment{" +
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
                ", defaultRetryCount=" + getDefaultRetryCount() +
                ", defaultRetryWait=" + getDefaultRetryWait() +
                ", defaultTimeout=" + getDefaultTimeout() +
                ", contextPath='" + contextPath + '\'' +
                '}';
    }
}
