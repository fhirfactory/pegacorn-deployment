package net.fhirfactory.pegacorn.deployment.topology.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.TopologyEndpointTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class IPCTopologyEndpoint extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(IPCTopologyEndpoint.class);
    private TopologyEndpointTypeEnum endpointType;
    private int portValue;
    private String portType;
    private Boolean aServer;
    private Boolean encrypted;
    private String interfaceDNSName;
    private String interfaceName;
    private Integer servicePortValue;
    private String servicePortName;
    private Integer servicePortOffset;
    private String serviceDNSName;
    private ArrayList<IPCInterface> supportedInterfaceSet;

    @JsonIgnore
    private String toStringString;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public IPCTopologyEndpoint(){
        super();
        encrypted = false;
        this.supportedInterfaceSet = new ArrayList<>();
        generateToString();
    }

    public TopologyEndpointTypeEnum getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(TopologyEndpointTypeEnum endpointType) {
        this.endpointType = endpointType;
        generateToString();
    }

    public ArrayList<IPCInterface> getSupportedInterfaceSet() {
        return supportedInterfaceSet;
    }

    public void setSupportedInterfaceSet(ArrayList<IPCInterface> supportedInterfaceSet) {
        ArrayList<IPCInterface> newSet = new ArrayList<>();
        newSet.addAll(supportedInterfaceSet);
        this.supportedInterfaceSet = newSet;
        generateToString();
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
        generateToString();
    }

    public int getPortValue() {
        return portValue;
    }

    public void setPortValue(int portValue) {
        this.portValue = portValue;
        generateToString();
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
        generateToString();
    }

    public Boolean getaServer() {
        return aServer;
    }

    public void setaServer(Boolean aServer) {
        this.aServer = aServer;
        generateToString();
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
        generateToString();
    }

    public String getInterfaceDNSName() {
        return interfaceDNSName;
    }

    public void setInterfaceDNSName(String interfaceDNSName) {
        this.interfaceDNSName = interfaceDNSName;
        generateToString();
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        generateToString();
    }

    public Integer getServicePortValue() {
        return servicePortValue;
    }

    public void setServicePortValue(Integer servicePortValue) {
        this.servicePortValue = servicePortValue;
        generateToString();
    }

    public String getServicePortName() {
        return servicePortName;
    }

    public void setServicePortName(String servicePortName) {
        this.servicePortName = servicePortName;
        generateToString();
    }

    public Integer getServicePortOffset() {
        return servicePortOffset;
    }

    public void setServicePortOffset(Integer servicePortOffset) {
        this.servicePortOffset = servicePortOffset;
        generateToString();
    }

    public String getServiceDNSName() {
        return serviceDNSName;
    }

    public void setServiceDNSName(String serviceDNSName) {
        this.serviceDNSName = serviceDNSName;
        generateToString();
    }

    @Override
    public String toString() {
        return (toStringString);
    }

    @JsonIgnore
    public String fallbackToString() {
        return "IPCTopologyEndpoint{" +
                "endpointType=" + endpointType +
                ", portValue=" + portValue +
                ", portType='" + portType + '\'' +
                ", aServer=" + aServer +
                ", encrypted=" + encrypted +
                ", interfaceDNSName='" + interfaceDNSName + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", servicePortValue=" + servicePortValue +
                ", servicePortName='" + servicePortName + '\'' +
                ", servicePortOffset=" + servicePortOffset +
                ", serviceDNSName='" + serviceDNSName + '\'' +
                ", supportedInterfaceSet=" + supportedInterfaceSet +
                "," + super.toString() +
                '}';
    }

    @JsonIgnore
    public void generateToString() {
        try{
            JsonMapper jsonMapper = new JsonMapper();
            String tmpString = jsonMapper.writeValueAsString(this);
            this.toStringString = tmpString;
        } catch(JsonProcessingException jsonError){
            this.toStringString = "Fallback-ToString():"+fallbackToString();
        }
    }
}
