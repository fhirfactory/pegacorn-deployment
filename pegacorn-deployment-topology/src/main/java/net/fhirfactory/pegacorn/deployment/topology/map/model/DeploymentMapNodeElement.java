/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deployment.topology.map.model;

import java.util.ArrayList;
import java.util.List;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElementTypeEnum;

/**
 *
 * @author mhunter
 */
public class DeploymentMapNodeElement {

    private NodeElementTypeEnum topologyElementType;
    private String instanceName;
    private String functionName;
    private List<DeploymentMapLinkElement> links;
    private List<DeploymentMapEndpointElement> endpoints;
    private List<DeploymentMapNodeElement> containedElements;
    private String elementVersion;
    private ResilienceModeEnum resilienceMode;
    private ConcurrencyModeEnum concurrencyMode;
    
    public DeploymentMapNodeElement(){
        endpoints = new ArrayList<DeploymentMapEndpointElement>();
        containedElements = new ArrayList<DeploymentMapNodeElement>();
        links = new ArrayList<DeploymentMapLinkElement>();
    }

    public NodeElementTypeEnum getTopologyElementType() {
        return topologyElementType;
    }

    public void setTopologyElementType(NodeElementTypeEnum topologyElementType) {
        this.topologyElementType = topologyElementType;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<DeploymentMapEndpointElement> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<DeploymentMapEndpointElement> endpoints) {
        this.endpoints = endpoints;
    }

    public List<DeploymentMapNodeElement> getContainedElements() {
        return containedElements;
    }

    public void setContainedElements(List<DeploymentMapNodeElement> containedElements) {
        this.containedElements = containedElements;
    }

    public String getElementVersion() {
        return elementVersion;
    }

    public void setElementVersion(String elementVersion) {
        this.elementVersion = elementVersion;
    }

    public ResilienceModeEnum getResilienceMode() {
        return resilienceMode;
    }

    public void setResilienceMode(ResilienceModeEnum resilienceMode) {
        this.resilienceMode = resilienceMode;
    }

    public ConcurrencyModeEnum getConcurrencyMode() {
        return concurrencyMode;
    }

    public void setConcurrencyMode(ConcurrencyModeEnum concurrencyMode) {
        this.concurrencyMode = concurrencyMode;
    }

    public List<DeploymentMapLinkElement> getLinks() {
        return links;
    }

    public void setLinks(List<DeploymentMapLinkElement> links) {
        this.links = links;
    }
}
