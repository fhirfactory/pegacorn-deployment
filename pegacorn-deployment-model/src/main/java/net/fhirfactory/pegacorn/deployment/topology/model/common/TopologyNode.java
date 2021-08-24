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
import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFDN;
import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeFunctionFDN;
import net.fhirfactory.pegacorn.common.model.componentid.PetasosNodeRDN;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.common.valuesets.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.deployment.topology.model.mode.ResilienceModeEnum;
import org.apache.commons.lang3.SerializationUtils;
import org.hl7.fhir.r4.model.Device;
import org.slf4j.Logger;

import java.util.UUID;

public abstract class TopologyNode extends Device {
    abstract protected Logger getLogger();
    private PetasosNodeRDN nodeRDN;
    private PetasosNodeFDN nodeFDN;
    private String nodeKey;
    private PetasosNodeFunctionFDN nodeFunctionFDN;
    private TopologyNodeTypeEnum componentType;
    private PetasosNodeFDN containingNodeFDN;
    private ConcurrencyModeEnum concurrencyMode;
    private ResilienceModeEnum resilienceMode;
    private NetworkSecurityZoneEnum securityZone;

    public TopologyNode(){
        this.nodeRDN = null;
        this.nodeFDN = null;
        this.nodeFunctionFDN = null;
        this.concurrencyMode = null;
        this.resilienceMode = null;
        this.nodeKey = null;
    }

    public PetasosNodeRDN getNodeRDN() {
        return nodeRDN;
    }

    public void setNodeRDN(PetasosNodeRDN nodeRDN) {
        this.nodeRDN = nodeRDN;
        constructNodeKey();
    }

    public PetasosNodeFDN getNodeFDN() {
        return nodeFDN;
    }

    public void setNodeFDN(PetasosNodeFDN nodeFDN) {
        this.nodeFDN = nodeFDN;
        setNodeRDN(nodeFDN.getLeafRDN());
        constructNodeKey();
    }

    public TopologyNodeTypeEnum getComponentType() {
        return componentType;
    }

    public void setComponentType(TopologyNodeTypeEnum componentType) {
        this.componentType = componentType;
    }

    public PetasosNodeFDN getContainingNodeFDN() {
        return containingNodeFDN;
    }

    public void setContainingNodeFDN(PetasosNodeFDN containingNodeFDN) {
        this.containingNodeFDN = containingNodeFDN;
    }

    @JsonIgnore
    public void constructFDN(PetasosNodeFDN parentNodeFDN, PetasosNodeRDN nodeRDN){
        getLogger().debug(".constructFDN(): Entry, parentNodeFDN->{}, nodeRDN->{}", parentNodeFDN, nodeRDN);
        if(parentNodeFDN == null || nodeRDN.getNodeType().equals(TopologyNodeTypeEnum.SOLUTION)){
            getLogger().trace(".constructFDN(): Is a Solution Node");
            PetasosNodeFDN solutionFDN = new PetasosNodeFDN();
            solutionFDN.appendTopologyNodeRDN(nodeRDN);
            this.nodeFDN = solutionFDN;
        } else {
            getLogger().trace(".constructFDN(): Is not a Solution Node");
            PetasosNodeFDN newFDN = (PetasosNodeFDN)SerializationUtils.clone(parentNodeFDN);
            getLogger().trace(".constructFDN(): newFDN Created");
            newFDN.appendTopologyNodeRDN(nodeRDN);
            getLogger().trace(".constructFDN(): nodeRDN appended");
            this.nodeFDN = newFDN;
            getLogger().trace(".constructFDN(): this.nodeFDN assigned->{}", this.getNodeFDN());
        }
        setNodeRDN(nodeRDN);
        constructNodeKey();
        getLogger().debug(".constructFDN(): Exit, nodeFDN->{}", this.getNodeFDN());
    }

    public void constructNodeKey(){
        String fdnValue = getNodeRDN().getNodeName() + "-" + UUID.randomUUID().toString();
        setNodeKey(fdnValue);
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public PetasosNodeFunctionFDN getNodeFunctionFDN() {
        return nodeFunctionFDN;
    }

    public void setNodeFunctionFDN(PetasosNodeFunctionFDN nodeFunctionFDN) {
        this.nodeFunctionFDN = nodeFunctionFDN;
    }

    @JsonIgnore
    public void constructFunctionFDN(PetasosNodeFunctionFDN parentFunctionFDN, PetasosNodeRDN nodeRDN){
        getLogger().debug(".constructFunctionFDN(): Entry");
        switch(nodeRDN.getNodeType()){
            case SOLUTION: {
                PetasosNodeFunctionFDN solutionFDN = new PetasosNodeFunctionFDN();
                solutionFDN.appendTopologyNodeRDN(nodeRDN);
                this.nodeFunctionFDN = solutionFDN;
                break;
            }
            case SITE:
            case PLATFORM:{
                this.nodeFunctionFDN = parentFunctionFDN;
                break;
            }
            default:{
                PetasosNodeFunctionFDN newFunctionFDN = (PetasosNodeFunctionFDN)SerializationUtils.clone(parentFunctionFDN);
                newFunctionFDN.appendTopologyNodeRDN(nodeRDN);
                this.nodeFunctionFDN = newFunctionFDN;
            }
        }
        setNodeRDN(nodeRDN);
        constructNodeKey();
        getLogger().debug(".constructFunctionFDN(): Exit, nodeFunctionFDN->{}", this.getNodeFunctionFDN());
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

    public NetworkSecurityZoneEnum getSecurityZone() {
        return securityZone;
    }

    public void setSecurityZone(NetworkSecurityZoneEnum securityZone) {
        this.securityZone = securityZone;
    }

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

    @Override
    public String toString() {
        return "TopologyNode{" +
                "nodeRDN=" + nodeRDN +
                ", nodeFDN=" + nodeFDN +
                ", nodeFunctionFDN=" + nodeFunctionFDN +
                ", componentType=" + componentType +
                ", containingComponent=" + containingNodeFDN +
                ", concurrencyMode=" + concurrencyMode +
                ", resilienceMode=" + resilienceMode +
                ", securityZone=" + securityZone +
                '}';
    }
}
