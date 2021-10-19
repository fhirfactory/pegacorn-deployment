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
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class WorkUnitProcessorTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(WorkUnitProcessorTopologyNode.class);

    private ArrayList<ComponentIdType> wupComponents;
    private ArrayList<ComponentIdType> wupInterchangeComponents;
    private ArrayList<ComponentIdType> endpoints;

    //
    // Constructor(s)
    //

    public WorkUnitProcessorTopologyNode(){
        super();
        this.wupComponents = new ArrayList<>();
        this.wupInterchangeComponents = new ArrayList<>();
        this.endpoints = new ArrayList<>();
    }

    public WorkUnitProcessorTopologyNode(WorkUnitProcessorTopologyNode ori){
        super(ori);
        this.wupComponents = new ArrayList<>();
        this.wupInterchangeComponents = new ArrayList<>();
        this.endpoints = new ArrayList<>();
        if (ori.hasWupComponents()) {
            for(ComponentIdType currentId: ori.getWupComponents()){
                getWupComponents().add(SerializationUtils.clone(currentId));
            }
        }
        if(ori.hasWupInterchangeComponents()){
            for(ComponentIdType currentId: ori.getWupInterchangeComponents()){
                getWupInterchangeComponents().add(SerializationUtils.clone(currentId));
            }
        }
        if(ori.hasEndpoints()){
            for(ComponentIdType currentId: ori.getEndpoints()){
                getEndpoints().add(SerializationUtils.clone(currentId));
            }
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
    public boolean hasWupComponents(){
        boolean hasValue = this.wupComponents != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getWupComponents() {
        return wupComponents;
    }

    public void setWupComponents(ArrayList<ComponentIdType> wupComponents) {
        this.wupComponents = wupComponents;
    }

    @JsonIgnore
    public boolean hasWupInterchangeComponents(){
        boolean hasValue = this.wupInterchangeComponents != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getWupInterchangeComponents() {
        return wupInterchangeComponents;
    }

    public void setWupInterchangeComponents(ArrayList<ComponentIdType> wupInterchangeComponents) {
        this.wupInterchangeComponents = wupInterchangeComponents;
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

    //
    // To String
    //

    @Override
    public String toString() {
        return "WorkUnitProcessorTopologyNode{" +
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
                ", wupComponents=" + wupComponents +
                ", wupInterchangeComponents=" + wupInterchangeComponents +
                ", endpoints=" + endpoints +
                '}';
    }
}
