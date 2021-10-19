package net.fhirfactory.pegacorn.deployment.topology.model.endpoints.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPCClusteredServerTopologyEndpoint extends IPCServerTopologyEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(IPCClusteredServerTopologyEndpoint.class);

    private Integer servicePortValue;
    private String servicePortName;
    private Integer servicePortOffset;
    private String serviceDNSName;

    //
    // Constructor(s)
    //

    public IPCClusteredServerTopologyEndpoint(){
        super();
        this.servicePortName = null;
        this.servicePortOffset = null;
        this.servicePortOffset = null;
        this.serviceDNSName = null;
    }

    //
    // Getters and Setters
    //

    @Override @JsonIgnore
    protected Logger getLogger() {
        return (LOG);
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

    //
    // To String
    //

    @Override
    public String toString() {
        return "IPCClusteredServerTopologyEndpoint{" +
                "otherConfigParameters=" + getOtherConfigParameters() +
                ", kubernetesDeployed=" + isKubernetesDeployed() +
                ", nodeRDN=" + getNodeRDN() +
                ", componentType=" + getComponentType() +
                ", componentId=" + getComponentId() +
                ", parentNode=" + getParentNode() +
                ", concurrencyMode=" + getConcurrencyMode() +
                ", resilienceMode=" + getResilienceMode() +
                ", securityZone=" + getSecurityZone() +
                ", otherConfigurationParameters=" + getOtherConfigurationParameters() +
                ", actualHostIP='" + getActualHostIP() + '\'' +
                ", actualPodIP='" + getActualPodIP() + '\'' +
                ", servicePortValue=" + servicePortValue +
                ", servicePortName='" + servicePortName + '\'' +
                ", servicePortOffset=" + servicePortOffset +
                ", serviceDNSName='" + serviceDNSName + '\'' +
                ", supportedInterfaceSet=" + getSupportedInterfaceSet() +
                ", portValue=" + getPortValue() +
                ", portType='" + getPortType() + '\'' +
                ", aServer=" + getaServer() +
                ", hostDNSName='" + getHostDNSName() + '\'' +
                ", implementingWUP=" + getImplementingWUP() +
                ", encrypted=" + isEncrypted() +
                ", additionalParameters=" + getAdditionalParameters() +
                ", connectedSystemName='" + getConnectedSystemName() + '\'' +
                ", name='" + getName() + '\'' +
                ", endpointType=" + getEndpointType() +
                '}';
    }
}
