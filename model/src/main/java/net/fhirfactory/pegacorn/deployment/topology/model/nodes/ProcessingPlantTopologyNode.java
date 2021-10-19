/*
 * Copyright (c) 2020 Mark A. Hunter (ACT Health)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.fhirfactory.pegacorn.common.model.componentid.ComponentIdType;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.common.EndpointProviderInterface;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ProcessingPlantTopologyNode extends TopologyNode implements EndpointProviderInterface {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessingPlantTopologyNode.class);

    private ArrayList<ComponentIdType> workshops;
    private ArrayList<ComponentIdType> endpoints;
    private ArrayList<ComponentIdType> connections;
    private String nameSpace;
    private String interZoneIPCStackConfigFile;
    private String interZoneOAMStackConfigFile;
    private String intraZoneIPCStackConfigFile;
    private String intraZoneOAMStackConfigFile;
    private String defaultDNSName;
    private boolean internalTrafficEncrypted;
    private Integer instanceCount;

    //
    // Constructor(s)
    //

    public ProcessingPlantTopologyNode(){
        super();
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

    public ProcessingPlantTopologyNode(ProcessingPlantTopologyNode ori){
        super(ori);
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

        if(ori.hasWorkshops()){
            for(ComponentIdType currentId: ori.getWorkshops()){
                getWorkshops().add(SerializationUtils.clone(currentId));
            }
        }
        if(ori.hasEndpoints()){
            for(ComponentIdType currentId: ori.getEndpoints()){
                getEndpoints().add(SerializationUtils.clone(currentId));
            }
        }
        if (ori.hasConnections()) {
            for(ComponentIdType currentId: ori.getConnections()){
                getConnections().add(SerializationUtils.clone(currentId));
            }
        }
        if(ori.hasNameSpace()){
            setNameSpace(SerializationUtils.clone(ori.getNameSpace()));
        }
        if(ori.hasDefaultDNSName()){
            setDefaultDNSName(SerializationUtils.clone(ori.getDefaultDNSName()));
        }
        setInternalTrafficEncrypted(ori.isInternalTrafficEncrypted());
        if(ori.hasInterZoneIPCStackConfigFile()){
            setInterZoneIPCStackConfigFile(SerializationUtils.clone(ori.getInterZoneIPCStackConfigFile()));
        }
        if(ori.hasInterZoneOAMStackConfigFile()){
            setInterZoneOAMStackConfigFile(SerializationUtils.clone(ori.getInterZoneOAMStackConfigFile()));
        }
        if(ori.hasIntraZoneIPCStackConfigFile()){
            setIntraZoneIPCStackConfigFile(SerializationUtils.clone(ori.getIntraZoneIPCStackConfigFile()));
        }
        if(ori.hasIntraZoneOAMStackConfigFile()){
            setIntraZoneOAMStackConfigFile(SerializationUtils.clone(ori.getIntraZoneOAMStackConfigFile()));
        }
    }

    //
    // Getters and Setters
    //

    @Override @JsonIgnore
    protected Logger getLogger() {
        return (LOG);
    }

    @JsonIgnore
    public boolean hasWorkshops(){
        boolean hasValue = this.workshops != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(ArrayList<ComponentIdType> workshops) {
        this.workshops = workshops;
    }

    @JsonIgnore
    public boolean hasEndpoints(){
        boolean hasValue = this.endpoints != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(ArrayList<ComponentIdType> endpoints) {
        this.endpoints = endpoints;
    }

    @JsonIgnore
    public boolean hasConnections(){
        boolean hasValue = this.connections != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<ComponentIdType> connections) {
        this.connections = connections;
    }

    @JsonIgnore
    public boolean hasInterZoneIPCStackConfigFile(){
        boolean hasValue = this.interZoneIPCStackConfigFile != null;
        return(hasValue);
    }

    public String getInterZoneIPCStackConfigFile() {
        return interZoneIPCStackConfigFile;
    }

    public void setInterZoneIPCStackConfigFile(String interZoneIPCStackConfigFile) {
        this.interZoneIPCStackConfigFile = interZoneIPCStackConfigFile;
    }

    @JsonIgnore
    public boolean hasInterZoneOAMStackConfigFile(){
        boolean hasValue = this.interZoneOAMStackConfigFile != null;
        return(hasValue);
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

    @JsonIgnore
    public boolean hasDefaultDNSName(){
        boolean hasValue = this.defaultDNSName != null;
        return(hasValue);
    }

    public String getDefaultDNSName() {
        return defaultDNSName;
    }

    public void setDefaultDNSName(String defaultDNSName) {
        this.defaultDNSName = defaultDNSName;
    }

    @JsonIgnore
    public boolean hasInstanceCount(){
        boolean hasValue = this.instanceCount != null;
        return(hasValue);
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
    }

    @JsonIgnore
    public boolean hasNameSpace(){
        boolean hasValue = this.nameSpace != null;
        return(hasValue);
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    @Override
    public void addEndpoint(ComponentIdType endpointFDN) {
        endpoints.add(endpointFDN);
    }

    @JsonIgnore
    public boolean hasIntraZoneIPCStackConfigFile(){
        boolean hasValue = this.intraZoneIPCStackConfigFile != null;
        return(hasValue);
    }

    public String getIntraZoneIPCStackConfigFile() {
        return intraZoneIPCStackConfigFile;
    }

    public void setIntraZoneIPCStackConfigFile(String intraZoneIPCStackConfigFile) {
        this.intraZoneIPCStackConfigFile = intraZoneIPCStackConfigFile;
    }

    @JsonIgnore
    public boolean hasIntraZoneOAMStackConfigFile(){
        boolean hasValue = this.intraZoneOAMStackConfigFile != null;
        return(hasValue);
    }

    public String getIntraZoneOAMStackConfigFile() {
        return intraZoneOAMStackConfigFile;
    }

    public void setIntraZoneOAMStackConfigFile(String intraZoneOAMStackConfigFile) {
        this.intraZoneOAMStackConfigFile = intraZoneOAMStackConfigFile;
    }

    //
    // To String
    //

    @Override
    public String toString() {
        return "ProcessingPlantTopologyNode{" +
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
                '}';
    }
}
