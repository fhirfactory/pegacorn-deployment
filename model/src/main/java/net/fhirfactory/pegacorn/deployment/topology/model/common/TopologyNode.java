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
package net.fhirfactory.pegacorn.deployment.topology.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.fhirfactory.pegacorn.common.model.componentid.*;
import net.fhirfactory.pegacorn.deployment.topology.model.common.valuesets.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class TopologyNode extends ConfigurableNode implements Serializable {
    abstract protected Logger getLogger();
    private TopologyNodeRDN nodeRDN;
    private ComponentTypeType componentType;
    private ComponentIdType componentId;
    private TopologyNode parentNode;
    private ConcurrencyModeEnum concurrencyMode;
    private ResilienceModeEnum resilienceMode;
    private NetworkSecurityZoneEnum securityZone;
    private ConcurrentHashMap<String, String> otherConfigurationParameters;
    private String actualHostIP;
    private String actualPodIP;

    //
    // Constructor
    //

    public TopologyNode(){
        super();
        this.nodeRDN = null;
        this.componentType = null;
        this.componentId = null;
        this.parentNode = null;
        this.concurrencyMode = null;
        this.resilienceMode = null;
        this.securityZone = null;
        this.actualHostIP = null;
        this.actualPodIP = null;
        this.otherConfigurationParameters = new ConcurrentHashMap<>();
    }

    public TopologyNode(TopologyNode ori){
        super(ori);
        this.nodeRDN = null;
        this.componentType = null;
        this.componentId = null;
        this.parentNode = null;
        this.concurrencyMode = null;
        this.resilienceMode = null;
        this.securityZone = null;
        this.actualHostIP = null;
        this.actualPodIP = null;
        if(ori.hasNodeRDN()){
            setNodeRDN(SerializationUtils.clone(ori.getNodeRDN()));
        }
        if(ori.hasComponentType()){
            setComponentType(SerializationUtils.clone(ori.getComponentType()));
        }
        if(ori.hasComponentId()){
            setComponentId(SerializationUtils.clone(ori.getComponentId()));
        }
        if (ori.hasParentNode()) {
            setParentNode(SerializationUtils.clone(ori.getParentNode()));
        }
        if(ori.hasConcurrencyMode()){
            setConcurrencyMode(ori.getConcurrencyMode());
        }
        if(ori.hasResilienceMode()){
            setResilienceMode(ori.getResilienceMode());
        }
        if(ori.hasSecurityZone()){
            setSecurityZone(ori.getSecurityZone());
        }
        if(ori.hasActualHostIP()){
            setActualHostIP(SerializationUtils.clone(ori.getActualHostIP()));
        }
        if(ori.hasActualPodIP()){
            setActualPodIP(SerializationUtils.clone(ori.getActualPodIP()));
        }
    }

    //
    // Some Helper Functions
    //

    @JsonIgnore
    public boolean isKubernetesDeployed(){
        if(getResilienceMode() == null){
            return(false);
        }
        switch(getResilienceMode()){
            case RESILIENCE_MODE_KUBERNETES_CLUSTERED:
            case RESILIENCE_MODE_KUBERNETES_MULTISITE:
            case RESILIENCE_MODE_KUBERNETES_STANDALONE:
            case RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED:
                return(true);
            case RESILIENCE_MODE_CLUSTERED:
            case RESILIENCE_MODE_MULTISITE:
            case RESILIENCE_MODE_STANDALONE:
            case RESILIENCE_MODE_MULTISITE_CLUSTERED:
            default:
                return(false);
        }
    }

    @JsonIgnore
    public void addOtherConfigurationParameter(String key, String value){
        if(this.otherConfigurationParameters.containsKey(key)){
            this.otherConfigurationParameters.remove(key);
        }
        this.otherConfigurationParameters.put(key,value);
    }

    public String getOtherConfigurationParameter(String key){
        if(this.otherConfigurationParameters.containsKey(key)){
            String value = this.otherConfigurationParameters.get(key);
            return(value);
        }
        return(null);
    }

    //
    // Getters (and Setters)
    //

    @JsonIgnore
    public boolean hasNodeRDN(){
        boolean hasValue = this.nodeRDN != null;
        return(hasValue);
    }

    public TopologyNodeRDN getNodeRDN() {
        return nodeRDN;
    }

    public void setNodeRDN(TopologyNodeRDN nodeRDN) {
        this.nodeRDN = nodeRDN;
    }

    @JsonIgnore
    public boolean hasComponentType(){
        boolean hasValue = this.componentType != null;
        return(hasValue);
    }

    public ComponentTypeType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentTypeType componentType) {
        this.componentType = componentType;
    }

    @JsonIgnore
    public boolean hasComponentId(){
        boolean hasValue = this.componentId != null;
        return(hasValue);
    }

    public ComponentIdType getComponentId() {
        return componentId;
    }

    public void setComponentId(ComponentIdType componentId) {
        this.componentId = componentId;
    }

    @JsonIgnore
    public boolean hasParentNode(){
        boolean hasValue = this.parentNode != null;
        return(hasValue);
    }

    public TopologyNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TopologyNode parentNode) {
        this.parentNode = parentNode;
    }

    @JsonIgnore
    public boolean hasConcurrencyMode(){
        boolean hasValue = this.concurrencyMode != null;
        return(hasValue);
    }

    public ConcurrencyModeEnum getConcurrencyMode() {
        return concurrencyMode;
    }

    public void setConcurrencyMode(ConcurrencyModeEnum concurrencyMode) {
        this.concurrencyMode = concurrencyMode;
    }

    @JsonIgnore
    public boolean hasResilienceMode(){
        boolean hasValue = this.resilienceMode != null;
        return(hasValue);
    }

    public ResilienceModeEnum getResilienceMode() {
        return resilienceMode;
    }

    public void setResilienceMode(ResilienceModeEnum resilienceMode) {
        this.resilienceMode = resilienceMode;
    }

    @JsonIgnore
    public boolean hasSecurityZone(){
        boolean hasValue = this.securityZone != null;
        return(hasValue);
    }

    public NetworkSecurityZoneEnum getSecurityZone() {
        return securityZone;
    }

    public void setSecurityZone(NetworkSecurityZoneEnum securityZone) {
        this.securityZone = securityZone;
    }

    @JsonIgnore
    public boolean hasOtherConfigurationParameters(){
        boolean hasValue = this.otherConfigurationParameters != null;
        return(hasValue);
    }

    public ConcurrentHashMap<String, String> getOtherConfigurationParameters() {
        return otherConfigurationParameters;
    }

    public void setOtherConfigurationParameters(ConcurrentHashMap<String, String> otherConfigurationParameters) {
        this.otherConfigurationParameters = otherConfigurationParameters;
    }

    @JsonIgnore
    public boolean hasActualHostIP(){
        boolean hasValue = this.actualHostIP != null;
        return(hasValue);
    }

    public String getActualHostIP() {
        return actualHostIP;
    }

    public void setActualHostIP(String actualHostIP) {
        this.actualHostIP = actualHostIP;
    }

    @JsonIgnore
    public boolean hasActualPodIP(){
        boolean hasValue = this.actualPodIP != null;
        return(hasValue);
    }

    public String getActualPodIP() {
        return actualPodIP;
    }

    public void setActualPodIP(String actualPodIP) {
        this.actualPodIP = actualPodIP;
    }

    //
    // To String
    //

    @Override
    public String toString() {
        return "TopologyNode{" +
                "otherConfigParameters=" + getOtherConfigParameters() +
                ", nodeRDN=" + nodeRDN +
                ", componentType=" + componentType +
                ", componentId=" + componentId +
                ", parentNode=" + parentNode +
                ", concurrencyMode=" + concurrencyMode +
                ", resilienceMode=" + resilienceMode +
                ", securityZone=" + securityZone +
                ", otherConfigurationParameters=" + otherConfigurationParameters +
                ", actualHostIP=" + actualHostIP +
                ", actualPodIP=" + actualPodIP +
                ", kubernetesDeployed=" + isKubernetesDeployed() +
                '}';
    }
}

