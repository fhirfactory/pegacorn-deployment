package net.fhirfactory.pegacorn.deployment.topology.model.endpoints.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class IPCServerTopologyEndpoint extends IPCTopologyEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(IPCServerTopologyEndpoint.class);

    private int portValue;
    private String portType;
    private Boolean aServer;
    private String hostDNSName;
    private ArrayList<IPCInterface> supportedInterfaceSet;

    //
    // Constructor(s)
    //

    public IPCServerTopologyEndpoint(){
        super();
        this.supportedInterfaceSet = new ArrayList<>();
        this.portValue = 0;
        this.portType = null;
        this.aServer = false;
        this.hostDNSName = null;
    }

    //
    // Getters and Setters
    //

    @Override @JsonIgnore
    protected Logger getLogger() {
        return (LOG);
    }

    public ArrayList<IPCInterface> getSupportedInterfaceSet() {
        return supportedInterfaceSet;
    }

    public void setSupportedInterfaceSet(ArrayList<IPCInterface> supportedInterfaceSet) {
        ArrayList<IPCInterface> newSet = new ArrayList<>();
        newSet.addAll(supportedInterfaceSet);
        this.supportedInterfaceSet = newSet;
    }

    public int getPortValue() {
        return portValue;
    }

    public void setPortValue(int portValue) {
        this.portValue = portValue;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }

    public Boolean getaServer() {
        return aServer;
    }

    public void setaServer(Boolean aServer) {
        this.aServer = aServer;
    }

    public String getHostDNSName() {
        return hostDNSName;
    }

    public void setHostDNSName(String interfaceDNSName) {
        this.hostDNSName = interfaceDNSName;
    }

    //
    // To String
    //
}
