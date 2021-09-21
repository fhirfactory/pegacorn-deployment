package net.fhirfactory.pegacorn.deployment.topology.model.endpoints.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.common.valuesets.AdditionalParametersListEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.common.PetasosEndpointTopologyTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class IPCTopologyEndpoint extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(IPCTopologyEndpoint.class);

    private String name;
    private PetasosEndpointTopologyTypeEnum endpointType;
    private Map<AdditionalParametersListEnum, String> additionalParameters;
    private String connectedSystemName;
    private Boolean encrypted;
    private TopologyNodeFDN implementingWUP;

    public IPCTopologyEndpoint(){
        super();
        encrypted = false;
        this.additionalParameters = new HashMap<>();
    }

    public TopologyNodeFDN getImplementingWUP() {
        return implementingWUP;
    }

    public void setImplementingWUP(TopologyNodeFDN implementingWUP) {
        this.implementingWUP = implementingWUP;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public Map<AdditionalParametersListEnum, String> getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(Map<AdditionalParametersListEnum, String> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }

    public String getConnectedSystemName() {
        return connectedSystemName;
    }

    public void setConnectedSystemName(String connectedSystemName) {
        this.connectedSystemName = connectedSystemName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetasosEndpointTopologyTypeEnum getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(PetasosEndpointTopologyTypeEnum endpointType) {
        this.endpointType = endpointType;
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    @JsonIgnore
    public String getAdditionalParameter(AdditionalParametersListEnum parameterName){
        if(this.additionalParameters.isEmpty()){
            return(null);
        }
        String value = additionalParameters.get(parameterName);
        return(value);
    }

    @Override
    public String toString() {
        return "IPCTopologyEndpoint{" +
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
                ", otherConfigurationParameters=" + getOtherConfigurationParameters() +
                ", name='" + name + '\'' +
                ", endpointType=" + endpointType +
                ", additionalParameters=" + additionalParameters +
                ", connectedSystemName='" + connectedSystemName + '\'' +
                ", encrypted=" + encrypted +
                ", implementingWUP=" + implementingWUP +
                ", actualHostIP='" + getActualHostIP() + '\'' +
                '}';
    }
}
