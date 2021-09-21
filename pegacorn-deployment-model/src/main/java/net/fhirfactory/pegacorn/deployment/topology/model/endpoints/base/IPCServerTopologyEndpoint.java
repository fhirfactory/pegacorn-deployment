package net.fhirfactory.pegacorn.deployment.topology.model.endpoints.base;

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

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public IPCServerTopologyEndpoint(){
        super();
        this.supportedInterfaceSet = new ArrayList<>();
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

    @Override
    public String toString() {
        return "IPCServerTopologyEndpoint{" +
                "nodeRDN=" + getNodeRDN() +
                ", nodeFDN=" + getNodeFDN() +
                ", componentType=" + getComponentType() +
                ", containingNodeFDN=" + getContainingNodeFDN() +
                ", nodeKey='" + getComponentID() + '\'' +
                ", nodeFunctionFDN=" + getNodeFunctionFDN() +
                ", concurrencyMode=" + getConcurrencyMode() +
                ", resilienceMode=" + getResilienceMode() +
                ", securityZone=" + getSecurityZone() +
                ", kubernetesDeployed=" + isKubernetesDeployed() +
                ", portValue=" + portValue +
                ", portType='" + portType + '\'' +
                ", aServer=" + aServer +
                ", encrypted=" + isEncrypted() +
                ", interfaceDNSName='" + hostDNSName + '\'' +
                ", supportedInterfaceSet=" + supportedInterfaceSet +
                ", additionalParameters=" + getAdditionalParameters() +
                ", connectedSystemName='" + getConnectedSystemName() + '\'' +
                ", name='" + getName() + '\'' +
                ", endpointType=" + getEndpointType() +
                '}';
    }
}
