package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.connectedsystems.ConnectedSystemProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.StandardServerPortSegment;
import org.slf4j.Logger;

public abstract class StandardExternalFacingPort extends StandardServerPortSegment{

    protected abstract Logger specifyLogger();

    protected Logger getLogger(){
        return(specifyLogger());
    }

    private ConnectedSystemProperties connectedSystem;

    public StandardExternalFacingPort(){
        super();
        this.connectedSystem = new ConnectedSystemProperties();
    }

    public ConnectedSystemProperties getConnectedSystem() {
        return connectedSystem;
    }

    public void setConnectedSystem(ConnectedSystemProperties connectedSystem) {
        this.connectedSystem = connectedSystem;
    }
}
