package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFDN;
import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.common.EndpointProviderInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ProcessingPlantTopologyNode extends TopologyNode implements EndpointProviderInterface {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessingPlantTopologyNode.class);

    private ArrayList<PetasosNodeFDN> workshops;
    private ArrayList<PetasosNodeFDN> endpoints;
    private ArrayList<PetasosNodeFDN> connections;
    private String nameSpace;
    private String interZoneIPCStackConfigFile;
    private String interZoneOAMStackConfigFile;
    private String intraZoneIPCStackConfigFile;
    private String intraZoneOAMStackConfigFile;

    private String defaultDNSName;
    private boolean internalTrafficEncrypted;
    private Integer instanceCount;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public ProcessingPlantTopologyNode(){
        this.workshops = new ArrayList<>();
        this.endpoints = new ArrayList<>();
        this.connections = new ArrayList<>();

        this.nameSpace = null;
        this.defaultDNSName = null;
        this.internalTrafficEncrypted = false;

        this.interZoneIPCStackConfigFile = null;
        this.interZoneOAMStackConfigFile = null;
        this.intraZoneIPCStackConfigFile = null;
        this.intraZoneOAMStackConfigFile = null;
    }

    public String getInterZoneIPCStackConfigFile() {
        return interZoneIPCStackConfigFile;
    }

    public void setInterZoneIPCStackConfigFile(String interZoneIPCStackConfigFile) {
        this.interZoneIPCStackConfigFile = interZoneIPCStackConfigFile;
    }

    public String getInterZoneOAMStackConfigFile() {
        return interZoneOAMStackConfigFile;
    }

    public void setInterZoneOAMStackConfigFile(String interZoneOAMStackConfigFile) {
        this.interZoneOAMStackConfigFile = interZoneOAMStackConfigFile;
    }

    public boolean isInternalTrafficEncrypted() {
        return internalTrafficEncrypted;
    }

    public void setInternalTrafficEncrypted(boolean internalTrafficEncrypted) {
        this.internalTrafficEncrypted = internalTrafficEncrypted;
    }

    public ArrayList<PetasosNodeFDN> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(ArrayList<PetasosNodeFDN> workshops) {
        this.workshops = workshops;
    }

    public ArrayList<PetasosNodeFDN> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(ArrayList<PetasosNodeFDN> endpoints) {
        this.endpoints = endpoints;
    }

    public ArrayList<PetasosNodeFDN> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<PetasosNodeFDN> connections) {
        this.connections = connections;
    }

    public String getDefaultDNSName() {
        return defaultDNSName;
    }

    public void setDefaultDNSName(String defaultDNSName) {
        this.defaultDNSName = defaultDNSName;
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    @Override
    public void addEndpoint(PetasosNodeFDN endpointFDN) {
        endpoints.add(endpointFDN);
    }

    public String getIntraZoneIPCStackConfigFile() {
        return intraZoneIPCStackConfigFile;
    }

    public void setIntraZoneIPCStackConfigFile(String intraZoneIPCStackConfigFile) {
        this.intraZoneIPCStackConfigFile = intraZoneIPCStackConfigFile;
    }

    public String getIntraZoneOAMStackConfigFile() {
        return intraZoneOAMStackConfigFile;
    }

    public void setIntraZoneOAMStackConfigFile(String intraZoneOAMStackConfigFile) {
        this.intraZoneOAMStackConfigFile = intraZoneOAMStackConfigFile;
    }

    @JsonIgnore
    public String getSubsystemName(){
        PetasosNodeFDN nodeFDN = getNodeFDN();
        PetasosNodeRDN subsystemRDN = nodeFDN.extractRDNForNodeType(TopologyNodeTypeEnum.SUBSYSTEM);
        String subsystemName = subsystemRDN.getNodeName();
        return(subsystemName);
    }

    @JsonIgnore
    public String getClusterServiceName(){
        PetasosNodeFDN nodeFDN = getNodeFDN();
        PetasosNodeRDN subsystemRDN = nodeFDN.extractRDNForNodeType(TopologyNodeTypeEnum.CLUSTER_SERVICE);
        String subsystemName = subsystemRDN.getNodeName();
        return(subsystemName);
    }

    @Override
    public String toString() {
        return "ProcessingPlantTopologyNode{" +
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
                ", workshops=" + workshops +
                ", endpoints=" + endpoints +
                ", connections=" + connections +
                ", nameSpace='" + nameSpace + '\'' +
                ", interZoneIPCStackConfigFile='" + interZoneIPCStackConfigFile + '\'' +
                ", interZoneOAMStackConfigFile='" + interZoneOAMStackConfigFile + '\'' +
                ", intraZoneIPCStackConfigFile='" + intraZoneIPCStackConfigFile + '\'' +
                ", intraZoneOAMStackConfigFile='" + intraZoneOAMStackConfigFile + '\'' +
                ", defaultDNSName='" + defaultDNSName + '\'' +
                ", internalTrafficEncrypted=" + internalTrafficEncrypted +
                ", instanceCount=" + instanceCount +
                ", subsystemName='" + getSubsystemName() + '\'' +
                ", clusterServiceName='" + getClusterServiceName() + '\'' +
                '}';
    }
}
