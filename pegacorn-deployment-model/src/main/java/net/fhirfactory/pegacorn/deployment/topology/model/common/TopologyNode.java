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

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFunctionFDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;
import org.slf4j.Logger;

public abstract class TopologyNode {
    abstract protected Logger getLogger();
    private TopologyNodeRDN nodeRDN;
    private TopologyNodeFDN nodeFDN;
    private TopologyNodeFunctionFDN nodeFunctionFDN;
    private TopologyNodeTypeEnum componentType;
    private TopologyNode containingComponent;
    private ConcurrencyModeEnum concurrencyMode;
    private ResilienceModeEnum resilienceMode;

    public TopologyNode(){
        this.nodeRDN = null;
        this.nodeFDN = null;
        this.nodeFunctionFDN = null;
        this.concurrencyMode = null;
        this.resilienceMode = null;
    }

    public TopologyNodeRDN getNodeRDN() {
        return nodeRDN;
    }

    public void setNodeRDN(TopologyNodeRDN nodeRDN) {
        this.nodeRDN = nodeRDN;
    }

    public TopologyNodeFDN getNodeFDN() {
        return nodeFDN;
    }

    public void setNodeFDN(TopologyNodeFDN nodeFDN) {
        this.nodeFDN = nodeFDN;
    }

    public TopologyNodeTypeEnum getComponentType() {
        return componentType;
    }

    public void setComponentType(TopologyNodeTypeEnum componentType) {
        this.componentType = componentType;
    }

    public TopologyNode getContainingComponent() {
        return containingComponent;
    }

    public void setContainingComponent(TopologyNode containingComponent) {
        this.containingComponent = containingComponent;
    }

    public void constructFDN(TopologyNodeFDN parentNodeFDN, TopologyNodeRDN nodeRDN){
        getLogger().debug(".constructIdentifier(): Entry");
        if(parentNodeFDN == null || nodeRDN.getNodeType().equals(TopologyNodeTypeEnum.SOLUTION)){
            TopologyNodeFDN solutionFDN = new TopologyNodeFDN();
            solutionFDN.appendTopologyNodeRDN(nodeRDN);
            this.nodeFDN = solutionFDN;
        } else {
            TopologyNodeFDN newFDN = new TopologyNodeFDN(parentNodeFDN);
            newFDN.appendTopologyNodeFDN(nodeFDN);
            newFDN.appendTopologyNodeRDN(nodeRDN);
            this.nodeFDN = newFDN;
        }
        getLogger().debug(".constructIdentifier(): Exit, created Identifier --> {}", this.getNodeFDN());
    }

    public TopologyNodeFunctionFDN getNodeFunctionFDN() {
        return nodeFunctionFDN;
    }

    public void setNodeFunctionFDN(TopologyNodeFunctionFDN nodeFunctionFDN) {
        this.nodeFunctionFDN = nodeFunctionFDN;
    }

    public void constructFunctionFDN(TopologyNodeFunctionFDN parentFunctionFDN, TopologyNodeRDN nodeRDN){
        getLogger().debug(".constructFunctionIdentifier(): Entry");
        switch(nodeRDN.getNodeType()){
            case SOLUTION: {
                TopologyNodeFunctionFDN solutionFDN = new TopologyNodeFunctionFDN();
                solutionFDN.appendTopologyNodeRDN(nodeRDN);
                this.nodeFunctionFDN = solutionFDN;
            }
            case SITE:
            case PLATFORM:{
                this.nodeFunctionFDN = parentFunctionFDN;
            }
            default:{
                TopologyNodeFunctionFDN newFunctionFDN = new TopologyNodeFunctionFDN(parentFunctionFDN);
                newFunctionFDN.appendTopologyNodeRDN(nodeRDN);
                this.nodeFunctionFDN = newFunctionFDN;
            }
        }
    }

    public ConcurrencyModeEnum getConcurrencyMode() {
        return concurrencyMode;
    }

    public void setConcurrencyMode(ConcurrencyModeEnum concurrencyMode) {
        this.concurrencyMode = concurrencyMode;
    }

    public ResilienceModeEnum getResilienceMode() {
        return resilienceMode;
    }

    public void setResilienceMode(ResilienceModeEnum resilienceMode) {
        this.resilienceMode = resilienceMode;
    }

    public boolean isKubernetesDeployed(){
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
}
