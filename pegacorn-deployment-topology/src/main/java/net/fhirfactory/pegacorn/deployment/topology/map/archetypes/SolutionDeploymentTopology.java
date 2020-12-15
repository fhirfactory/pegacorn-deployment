/*
 * The MIT License
 *
 * Copyright 2020 Mark A. Hunter.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.fhirfactory.pegacorn.deployment.topology.map.archetypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import net.fhirfactory.pegacorn.deployment.topology.initialiser.TopologyMapElementTransformationServices;
import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapNodeElement;
import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentTopologyInitialisationInterface;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.petasos.model.topology.EndpointElementIdentifier;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElement;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElementTypeEnum;
import net.fhirfactory.pegacorn.petasos.topology.manager.TopologyIM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mark A Hunter
 *
 */
public abstract class SolutionDeploymentTopology implements DeploymentTopologyInitialisationInterface {

    private static final Logger LOG = LoggerFactory.getLogger(SolutionDeploymentTopology.class);

    protected DeploymentMapNodeElement solutionNode;
    private String solutionName;
    private String solutionVersion;
    private ResilienceModeEnum solutionMode;
    protected Set<DeploymentMapNodeElement> connectedSubsystems;
    private boolean isInitialised;

    @Inject
    private TopologyMapElementTransformationServices topologyMapService;

    @Inject
    private TopologyIM topologyServer;

    public SolutionDeploymentTopology() {
        LOG.debug(".SolutionDeploymentTopology(): Entry, Constructor called!");
        this.solutionName = specifySolutionName();
        this.solutionVersion = specifySolutionVersion();
        this.solutionMode = specifyResilienceMode();
        this.connectedSubsystems = new HashSet<DeploymentMapNodeElement>();
        isInitialised = false;
        LOG.debug(".SolutionDeploymentTopology(): Exit, this.solutionName --> {}", this.solutionName);
    }

    @PostConstruct
    private void initialise() {
        LOG.debug(".initialise(): Entry");
        if (!isInitialised) {
            LOG.trace(".initialise(): Doing initialisation!");
            this.solutionNode = buildCoreSolution();
            LOG.trace(".initialise(): Core Solution added, Adding Connected System Nodes");
            this.connectedSubsystems.addAll(specifyConnectedSystems());
            LOG.trace(".initialise(): Connected System Nodes added, Installing Pegacorn Solution into Topology Server");
            topologyMapService.registerDeploymentNodeAsTopologyNodeElement(this.getSolution(), null, null);
            LOG.trace(".initialise(): Installed Pegacorn Solution into Topology Server, Adding Connected Systems to Topology Server");
            for (DeploymentMapNodeElement node : connectedSubsystems) {
                topologyMapService.registerDeploymentNodeAsTopologyNodeElement(node, null, null);
            }
            LOG.trace(".initialise(): Installed Connected Systems to Topology Server, done...");
            this.isInitialised = true;
            if(LOG.isWarnEnabled()){
                printTopologySummary();
            }
        }
        LOG.debug(".initialise(): Exit");
    }

    protected void initialiseServices(){
        initialise();
    }

    protected boolean getIsInitialised(){
        return(this.isInitialised);
    }

    abstract protected String specifySolutionName();

    abstract protected String specifySolutionVersion();

    abstract protected ResilienceModeEnum specifyResilienceMode();

    abstract protected void specifySubsystems(DeploymentMapNodeElement solutionNode);

    abstract protected Set<DeploymentMapNodeElement> specifyConnectedSystems();

    public DeploymentMapNodeElement getSolution() {
        return solutionNode;
    }

    public void setSolution(DeploymentMapNodeElement solution) {
        this.solutionNode = solution;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getSolutionVersion() {
        return solutionVersion;
    }

    public void setSolutionVersion(String solutionVersion) {
        this.solutionVersion = solutionVersion;
    }

    public Set<DeploymentMapNodeElement> getConnectedSubsystems() {
        return connectedSubsystems;
    }

    public void setConnectedSubsystems(Set<DeploymentMapNodeElement> containedSubsystems) {
        this.connectedSubsystems = containedSubsystems;
    }

    public ResilienceModeEnum getSolutionMode() {
        return solutionMode;
    }

    public void setSolutionMode(ResilienceModeEnum solutionMode) {
        this.solutionMode = solutionMode;
    }

    private DeploymentMapNodeElement buildCoreSolution() {
        DeploymentMapNodeElement solution = new DeploymentMapNodeElement();
        solution.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        solution.setElementVersion(this.getSolutionVersion());
        solution.setInstanceName(this.getSolutionName());
        solution.setFunctionName(this.getSolutionName());
        solution.setResilienceMode(this.getSolutionMode());
        solution.setTopologyElementType(NodeElementTypeEnum.SOLUTION);
        solution.setContainedElements(new ArrayList<DeploymentMapNodeElement>());

        specifySubsystems(solution);

        return (solution);
    }

    private void printTopologySummary(){
        LOG.warn(".printTopologySummary(): Entry");
        Set<NodeElement> nodeSet = topologyServer.getNodeSet();
        for(NodeElement node: nodeSet){
            LOG.warn("Node: Type {}, InstanceId {}", node.getNodeArchetype() ,node.getNodeInstanceID().toTag());
            for(EndpointElementIdentifier endpoint: node.getEndpoints()){
                LOG.warn("      Endpoint: {}", endpoint);
            }
        }
    }

}
