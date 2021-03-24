package net.fhirfactory.pegacorn.deployment.topology.model.connector.external;

import net.fhirfactory.pegacorn.deployment.topology.model.connector.common.ConnectionTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.connector.common.IPCConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PegacornAsClientExternalIPCConnection extends IPCConnection {
    private static final Logger LOG = LoggerFactory.getLogger(PegacornAsClientExternalIPCConnection.class);
    private ConnectionTypeEnum connectionType;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public ConnectionTypeEnum getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionTypeEnum connectionType) {
        this.connectionType = connectionType;
    }
}
