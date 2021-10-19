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
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import net.fhirfactory.pegacorn.common.model.componentid.ComponentIdType;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class DeploymentSiteTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(DeploymentSiteTopologyNode.class);

    private ArrayList<ComponentIdType> clusterServices;
    private Integer instanceCount;

    //
    // Constructor(s)
    //

    public DeploymentSiteTopologyNode(){
        super();
        this.clusterServices = new ArrayList<>();
        this.instanceCount = null;
    }

    public DeploymentSiteTopologyNode(DeploymentSiteTopologyNode ori){
        super(ori);
        this.clusterServices = new ArrayList<>();
        this.instanceCount = null;

        if(ori.hasClusterServices()){
            for(ComponentIdType currentId: ori.getClusterServices()){
                getClusterServices().add(SerializationUtils.clone(currentId));
            }
        }
        if(ori.hasInstanceCount()){
            setInstanceCount(ori.getInstanceCount());
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
    public boolean hasClusterServices(){
        boolean hasValue = this.clusterServices != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getClusterServices() {
        return clusterServices;
    }

    public void setClusterServices(ArrayList<ComponentIdType> clusterServices) {
        this.clusterServices = clusterServices;
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

    //
    // to String
    //

    @Override
    public String toString() {
        return "DeploymentSiteTopologyNode{" +
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
                ", clusterServices=" + clusterServices +
                ", instanceCount=" + instanceCount +
                '}';
    }
}
