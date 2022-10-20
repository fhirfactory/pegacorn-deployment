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
package net.fhirfactory.pegacorn.deployment.topology.manager.cache;

import net.fhirfactory.pegacorn.core.constants.systemwide.DeploymentSystemIdentificationInterface;
import net.fhirfactory.pegacorn.core.model.component.SoftwareComponent;
import net.fhirfactory.pegacorn.core.model.componentid.ComponentIdType;
import net.fhirfactory.pegacorn.core.model.componentid.SoftwareComponentTypeEnum;
import net.fhirfactory.pegacorn.core.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.core.model.topology.mode.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.SolutionTopologyNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Mark A. Hunter
 * @since 2020-07-01
 */
@ApplicationScoped
public class TopologyNodesDM implements DeploymentSystemIdentificationInterface {

    private static final Logger LOG = LoggerFactory.getLogger(TopologyNodesDM.class);

    private SolutionTopologyNode deploymentSolution;
    private ConcurrentHashMap<ComponentIdType, SoftwareComponent> nodeSet;
    private Object nodeSetLock;

    //
    // Constructor(s)
    //

    public TopologyNodesDM() {
        LOG.debug(".TopologyDM(): Constructor initialisation");
        this.deploymentSolution = null;
        this.nodeSet = new ConcurrentHashMap<>();
        this.nodeSetLock = new Object();
    }

    //
    // Getters (and Setters)
    //

    protected ConcurrentHashMap<ComponentIdType, SoftwareComponent> getNodeSet(){
        return(this.nodeSet);
    }

    protected Object getNodeSetLock(){
        return(this.nodeSetLock);
    }

    public SolutionTopologyNode getDeploymentSolution() {
        return deploymentSolution;
    }

    //
    // Business Methods
    //

    private void insertOrOverwriteTopologyNode(SolutionTopologyNode node){
        LOG.debug(".insertOrOverwriteTopologyNode(): Entry");
        synchronized (getNodeSetLock()) {
            if (getNodeSet().containsKey(node.getComponentId())) {
                getNodeSet().remove(node.getComponentId());
            }
            getNodeSet().put(node.getComponentId(), node);
        }
        LOG.debug(".insertOrOverwriteTopologyNode(): Exit");
    }

    public void setDeploymentSolution(SolutionTopologyNode deploymentSolution) {
        LOG.debug(".setDeploymentSolution(): Entry");
        this.deploymentSolution = deploymentSolution;
        insertOrOverwriteTopologyNode(deploymentSolution);
        LOG.debug(".setDeploymentSolution(): Exit");
    }

    @Override
    public String getSystemName() {
        return (getDeploymentSolution().getParticipant().getParticipantId().getName());
    }

    @Override
    public String getSystemVersion() {
        return (getDeploymentSolution().getVersion());
    }

    public String getSystemIdentifier() {
        return (getDeploymentSolution().getComponentId().getDisplayName());
    }

    @Override
    public String getSystemOwnerName() {
        return (getDeploymentSolution().getSystemOwner());
    }

    /**
     * This function adds an entry to the Element Set.
     * <p>
     * Note that the default behaviour is to UPDATE the values with the set if
     * there already exists an instance for the specified FDNToken (identifier).
     *
     * Note, we have to do a deep inspection of the ConcurrentHashMap key (FDNToken) content,
     * as the default only only looks for equivalence with respect to the action Object instance.
     *
     * @param newElement The NodeElement to be added to the Set
     */
    public void addTopologyNode(SoftwareComponent newElement) {
        LOG.debug(".addTopologyNode(): Entry, newElement --> {}", newElement);
        if (newElement == null) {
            throw (new IllegalArgumentException(".addTopologyNode(): newElement is null"));
        }
        if (newElement.getComponentId() == null) {
            throw (new IllegalArgumentException(".addTopologyNode(): bad elementID within newElement"));
        }
        synchronized (getNodeSetLock()) {
            ComponentIdType componentId = null;
            if(getNodeSet().containsKey(newElement.getComponentId())){
                SoftwareComponent node = getNodeSet().get(newElement.getComponentId());
                if(node != null){
                    componentId = node.getComponentId();
                }
                getNodeSet().remove(newElement.getComponentId());
            }
            if(componentId != null){
                if(this.nodeSet.containsKey(componentId)) {
                    this.nodeSet.remove(componentId);
                }
            }
            getNodeSet().put(newElement.getComponentId(), newElement);
        }
        LOG.debug(".addTopologyNode(): Exit");
    }

    /**
     *
     * @param componentId
     */
    public void deleteTopologyNode(ComponentIdType componentId) {
        LOG.debug(".deleteTopologyNode(): Entry, componentId --> {}", componentId);
        if (componentId == null) {
            throw (new IllegalArgumentException(".removeNode(): elementID is null"));
        }
        synchronized (getNodeSetLock()){
            TopologyNodeFDN topologyNodeFDN = null;
            if(this.nodeSet.containsKey(componentId)){
                this.nodeSet.remove(componentId);
            }
        }
        LOG.debug(".deleteTopologyNode(): Exit");
    }

    /**
     *
     * @return
     */
    public Set<SoftwareComponent> getTopologyNodeSet() {
        LOG.debug(".getTopologyNodeSet(): Entry");
        LinkedHashSet<SoftwareComponent> elementSet = new LinkedHashSet<SoftwareComponent>();
        if (getNodeSet().isEmpty()) {
            LOG.debug(".getTopologyNodeSet(): Exit, The module map is empty, returning null");
            return (null);
        }
        synchronized (getNodeSetLock()) {
            elementSet.addAll(getNodeSet().values());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(".getTopologyNodeSet(): Exit, returning an element set, size --> {}", elementSet.size());
        }
        return (elementSet);
    }

    public SoftwareComponent getSoftwareComponent(ComponentIdType nodeId) {
        LOG.debug(".getSoftwareComponent(): Entry, nodeId --> {}", nodeId);
        if (nodeId == null) {
            LOG.debug(".getSoftwareComponent(): Exit, provided a null nodeId , so returning null");
            return (null);
        }
        SoftwareComponent retrievedNode = null;
        synchronized (getNodeSetLock()) {
            retrievedNode = getNodeSet().get(nodeId);
        }
        LOG.debug(".getSoftwareComponent(): Exit, retrievedNode->{}", retrievedNode);
        return (retrievedNode);
    }


    private boolean stringValuesMatch(String stringA, String stringB){
        if(stringA == null && stringB == null){
            return(true);
        }
        if(stringA == null || stringB == null){
            return(false);
        }
        boolean stringsAreEqual = stringA.contentEquals(stringB);
        return(stringsAreEqual);
    }

    public List<SoftwareComponent> getSoftwareComponent(SoftwareComponentTypeEnum nodeType, String participantName, String participantVersion){
        LOG.debug(".getSoftwareComponent(): Entry, nodeType->{}, participantName->{}, participantVersion->{}", nodeType, participantName, participantVersion);

        ArrayList<SoftwareComponent> nodeList = new ArrayList<>();
        Collection<SoftwareComponent> topologyNodes= new ArrayList<>();

        if(nodeType == null){
            LOG.debug(".getSoftwareComponent(): Exit, provided nodeType is null");
            return(nodeList);
        }
        if(StringUtils.isEmpty(participantName)){
            LOG.debug(".getSoftwareComponent(): Exit, provided participantName is empty");
            return (nodeList);
        }

        LOG.trace(".getSoftwareComponent(): [Get the set of existing SoftwareComponents] Start");
        synchronized (nodeSetLock) {
            topologyNodes.addAll(getNodeSet().values());
        }
        LOG.trace(".getSoftwareComponent(): [Get the set of existing SoftwareComponents] Finish");

        LOG.trace(".getSoftwareComponent(): [Iterating through SoftwareComponents List for Matching Name/Version] Start");
        for (SoftwareComponent currentNode: topologyNodes) {
            boolean nodeNameMatches = false;
            boolean nodeVersionMatches = false;
            boolean nodeTypeMatches = false;
            LOG.trace(".getSoftwareComponent(): Comparing nodeType: Start");
            nodeTypeMatches = nodeType.equals(currentNode.getComponentType());
            LOG.trace(".getSoftwareComponent(): Comparing nodeType: Finish, result->{}", nodeTypeMatches);
            if(nodeTypeMatches) {
                LOG.trace(".getSoftwareComponent(): Comparing nodeName: Start");
                nodeNameMatches = participantName.contentEquals(currentNode.getParticipant().getParticipantId().getName());
                LOG.trace(".getSoftwareComponent(): Comparing nodeName: Finish, result->{}", nodeNameMatches);
                LOG.trace(".getSoftwareComponent(): Comparing nodeName: Version");
                if(StringUtils.isNotEmpty(participantVersion)) {
                    if (currentNode.hasParticipant()) {
                        if (currentNode.getParticipant().hasParticipantId()) {
                            if(StringUtils.isNotEmpty(currentNode.getParticipant().getParticipantId().getVersion())) {
                                nodeVersionMatches = participantVersion.contentEquals(currentNode.getParticipant().getParticipantId().getVersion());
                            }
                        }
                    }
                } else {
                    nodeVersionMatches = true;
                }
                LOG.trace(".getSoftwareComponent(): Comparing nodeVersion: Finish, result->{}", nodeVersionMatches);
            }
            if (nodeTypeMatches && nodeNameMatches && nodeVersionMatches) {
                LOG.trace(".getSoftwareComponent(): Node found!!! Adding to search result!");
                nodeList.add(currentNode);
            }
        }
        LOG.trace(".getSoftwareComponent(): [Iterating through SoftwareComponents List for Matching Name/Version] Finish");

        LOG.trace(".getSoftwareComponent(): Exit");
        return(nodeList);
    }

    public NetworkSecurityZoneEnum getDeploymentNetworkSecurityZone(String subsystemName){
        Collection<SoftwareComponent> nodeCollection = new ArrayList<>();
        synchronized (nodeSetLock) {
            nodeCollection.addAll(getNodeSet().values());
        }
        for(SoftwareComponent currentNode: nodeCollection){
            boolean nameSame = currentNode.getParticipant().getParticipantId().getSubsystemName().contentEquals(subsystemName);
            boolean isProcessingPlant = currentNode.getComponentType().equals(SoftwareComponentTypeEnum.PROCESSING_PLANT);
            if(nameSame && isProcessingPlant){
                return(currentNode.getSecurityZone());
            }
        }
        return(NetworkSecurityZoneEnum.INTERNET);
    }
}
