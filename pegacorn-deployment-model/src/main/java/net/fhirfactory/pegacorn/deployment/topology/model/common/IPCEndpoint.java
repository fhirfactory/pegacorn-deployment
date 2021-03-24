package net.fhirfactory.pegacorn.deployment.topology.model.common;

import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.EndpointTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class IPCEndpoint extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(IPCEndpoint.class);
    private EndpointTypeEnum endpointType;
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

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public IPCEndpoint(){
        super();
        encrypted = false;
        this.supportedInterfaceSet = new ArrayList<>();
    }

    public EndpointTypeEnum getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(EndpointTypeEnum endpointType) {
        this.endpointType = endpointType;
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

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Integer getServicePortValue() {
        return servicePortValue;
    }

    public void setServicePortValue(Integer servicePortValue) {
        this.servicePortValue = servicePortValue;
    }

    public String getServicePortName() {
        return servicePortName;
    }

    public void setServicePortName(String servicePortName) {
        this.servicePortName = servicePortName;
    }

    public Integer getServicePortOffset() {
        return servicePortOffset;
    }

    public void setServicePortOffset(Integer servicePortOffset) {
        this.servicePortOffset = servicePortOffset;
    }

    public String getServiceDNSName() {
        return serviceDNSName;
    }

    public void setServiceDNSName(String serviceDNSName) {
        this.serviceDNSName = serviceDNSName;
    }
}
