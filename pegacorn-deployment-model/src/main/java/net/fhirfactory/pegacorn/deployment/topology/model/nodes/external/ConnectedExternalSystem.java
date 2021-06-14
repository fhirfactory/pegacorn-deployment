package net.fhirfactory.pegacorn.deployment.topology.model.nodes.external;

import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.external.ConnectedExternalSystemTopologyEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectedExternalSystem {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectedExternalSystem.class);

    private String owningOrganizationName;
    private String solutionName;
    private String subsystemName;
    private String subsystemVersion;
    private String externalisedServiceName;
    private String externalisedServiceEndpointName;
    private ConcurrentHashMap<Integer, ConnectedExternalSystemTopologyEndpoint> targetPorts;

    public String getOwningOrganizationName() {
        return owningOrganizationName;
    }

    public void setOwningOrganizationName(String owningOrganizationName) {
        this.owningOrganizationName = owningOrganizationName;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public String getSubsystemVersion() {
        return subsystemVersion;
    }

    public void setSubsystemVersion(String subsystemVersion) {
        this.subsystemVersion = subsystemVersion;
    }

    public String getExternalisedServiceName() {
        return externalisedServiceName;
    }

    public void setExternalisedServiceName(String externalisedServiceName) {
        this.externalisedServiceName = externalisedServiceName;
    }

    public String getExternalisedServiceEndpointName() {
        return externalisedServiceEndpointName;
    }

    public void setExternalisedServiceEndpointName(String externalisedServiceEndpointName) {
        this.externalisedServiceEndpointName = externalisedServiceEndpointName;
    }

    public ConcurrentHashMap<Integer, ConnectedExternalSystemTopologyEndpoint> getTargetPorts() {
        return targetPorts;
    }

    public void setTargetPorts(ConcurrentHashMap<Integer, ConnectedExternalSystemTopologyEndpoint> targetPorts) {
        this.targetPorts = targetPorts;
    }
}
