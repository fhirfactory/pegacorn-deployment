package net.fhirfactory.pegacorn.deployment.topology.model.endpoints.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import net.fhirfactory.pegacorn.deployment.topology.model.common.IPCInterface;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.common.valuesets.AdditionalParametersListEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.TopologyEndpointTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IPCServerTopologyEndpoint extends IPCTopologyEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(IPCServerTopologyEndpoint.class);

    private int portValue;
    private String portType;
    private Boolean aServer;
    private Boolean encrypted;
    private String interfaceDNSName;


    private ArrayList<IPCInterface> supportedInterfaceSet;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public IPCServerTopologyEndpoint(){
        super();
        encrypted = false;
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

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
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

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getInterfaceDNSName() {
        return interfaceDNSName;
    }

    public void setInterfaceDNSName(String interfaceDNSName) {
        this.interfaceDNSName = interfaceDNSName;
    }

    @Override
    public String toString() {
        return "IPCServerTopologyEndpoint{" +
                "nodeRDN=" + getNodeRDN() +
                ", nodeFDN=" + getNodeFDN() +
                ", componentType=" + getComponentType() +
                ", containingNodeFDN=" + getContainingNodeFDN() +
                ", nodeKey='" + getNodeKey() + '\'' +
                ", nodeFunctionFDN=" + getNodeFunctionFDN() +
                ", concurrencyMode=" + getConcurrencyMode() +
                ", resilienceMode=" + getResilienceMode() +
                ", securityZone=" + getSecurityZone() +
                ", kubernetesDeployed=" + isKubernetesDeployed() +
                ", portValue=" + portValue +
                ", portType='" + portType + '\'' +
                ", aServer=" + aServer +
                ", encrypted=" + encrypted +
                ", interfaceDNSName='" + interfaceDNSName + '\'' +
                ", supportedInterfaceSet=" + supportedInterfaceSet +
                ", additionalParameters=" + getAdditionalParameters() +
                ", name='" + getName() + '\'' +
                ", endpointType=" + getEndpointType() +
                '}';
    }
}
