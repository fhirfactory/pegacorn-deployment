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
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class BusinessServiceTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessServiceTopologyNode.class);

    private ArrayList<ComponentIdType> deploymentSites;
    private ArrayList<ComponentIdType> externalisedServices;
    private boolean preferringEncryption;
    private String defaultDNSName;

    //
    // Constructor(s)
    //

    public BusinessServiceTopologyNode(){
        super();
        this.deploymentSites = new ArrayList<>();
        this.externalisedServices = new ArrayList<>();
        this.preferringEncryption = false;
        this.defaultDNSName = null;
    }

    public BusinessServiceTopologyNode(BusinessServiceTopologyNode ori){
        super(ori);
        this.deploymentSites = new ArrayList<>();
        this.externalisedServices = new ArrayList<>();
        this.preferringEncryption = false;
        this.defaultDNSName = null;
    }

    //
    // Getters and Setters
    //

    @Override @JsonIgnore
    protected Logger getLogger() {
        return (LOG);
    }

    @JsonIgnore
    public boolean hasDeploymentSites(){
        boolean hasValue = this.deploymentSites != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getDeploymentSites() {
        return deploymentSites;
    }

    public void setDeploymentSites(ArrayList<ComponentIdType> deploymentSites) {
        this.deploymentSites = deploymentSites;
    }

    @JsonIgnore
    public boolean hasExternalisedServices(){
        boolean hasValue = this.externalisedServices != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getExternalisedServices() {
        return externalisedServices;
    }

    public void setExternalisedServices(ArrayList<ComponentIdType> externalisedServices) {
        this.externalisedServices = externalisedServices;
    }

    public boolean isPreferringEncryption() {
        return preferringEncryption;
    }

    public void setPreferringEncryption(boolean preferringEncryption) {
        this.preferringEncryption = preferringEncryption;
    }

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

    //
    // To String
    //

    @Override
    public String toString() {
        return "BusinessServiceTopologyNode{" +
                "otherConfigParameters=" + getOtherConfigParameters() +
                ", kubernetesDeployed=" + isKubernetesDeployed() +
                ", hasNodeRDN=" + hasNodeRDN() +
                ", nodeRDN=" + getNodeRDN() +
                ", hasComponentType=" + hasComponentType() +
                ", componentType=" + getComponentType() +
                ", hasComponentId=" + hasComponentId() +
                ", componentId=" + getComponentId() +
                ", hasParentNode=" + hasParentNode() +
                ", parentNode=" + getParentNode() +
                ", hasConcurrencyMode=" + hasConcurrencyMode() +
                ", concurrencyMode=" + getConcurrencyMode() +
                ", hasResilienceMode=" + hasResilienceMode() +
                ", resilienceMode=" + getResilienceMode() +
                ", hasSecurityZone=" + hasSecurityZone() +
                ", securityZone=" + getSecurityZone() +
                ", hasOtherConfigurationParameters=" + hasOtherConfigurationParameters() +
                ", otherConfigurationParameters=" + getOtherConfigurationParameters() +
                ", hasActualHostIP=" + hasActualHostIP() +
                ", actualHostIP='" + getActualHostIP() + '\'' +
                ", hasActualPodIP=" + hasActualPodIP() +
                ", actualPodIP='" + getActualPodIP() + '\'' +
                ", deploymentSites=" + deploymentSites +
                ", externalisedServices=" + externalisedServices +
                ", preferringEncryption=" + preferringEncryption +
                ", defaultDNSName='" + defaultDNSName + '\'' +
                ", hasDeploymentSites=" + hasDeploymentSites() +
                ", hasExternalisedServices=" + hasExternalisedServices() +
                ", hasDefaultDNSName=" + hasDefaultDNSName() +
                '}';
    }
}
