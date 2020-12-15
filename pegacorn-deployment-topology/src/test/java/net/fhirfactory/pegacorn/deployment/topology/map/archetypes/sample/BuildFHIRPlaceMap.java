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
package net.fhirfactory.pegacorn.deployment.topology.map.archetypes.sample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.fhirfactory.pegacorn.deployment.topology.map.archetypes.FHIRPlacePegacornSubsystem;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElementTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapNodeElement;

/**
 *
 * @author Mark A. Hunter
 */
public class BuildFHIRPlaceMap extends FHIRPlacePegacornSubsystem {

    String nodeFHIRPlaceText = "FHIRPlace";
    String nodeFHIRPlaceGen0Text = "gen0-fhirplace";

    @Override
    public void buildSubsystemNode(DeploymentMapNodeElement solutionNode) {
        DeploymentMapNodeElement fhirplaceNode = new DeploymentMapNodeElement();
        fhirplaceNode.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        fhirplaceNode.setElementVersion("0.0.1");
        fhirplaceNode.setInstanceName("FHIRPlace");
        fhirplaceNode.setFunctionName("FHIRPlace");
        fhirplaceNode.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        fhirplaceNode.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        fhirplaceNode.getContainedElements().add(fhirplaceNode);
        buildExternalisedServiceNode(fhirplaceNode);
    }

    public void buildExternalisedServiceNode(DeploymentMapNodeElement fhirplaceNode) {
        DeploymentMapNodeElement gen0FHIRPlaceServiceNode = new DeploymentMapNodeElement();
        gen0FHIRPlaceServiceNode.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        gen0FHIRPlaceServiceNode.setElementVersion("0.0.1");
        gen0FHIRPlaceServiceNode.setInstanceName(nodeFHIRPlaceGen0Text);
        gen0FHIRPlaceServiceNode.setFunctionName(nodeFHIRPlaceGen0Text);
        gen0FHIRPlaceServiceNode.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        gen0FHIRPlaceServiceNode.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        gen0FHIRPlaceServiceNode.setContainedElements(new ArrayList<DeploymentMapNodeElement>());
        fhirplaceNode.getContainedElements().add(gen0FHIRPlaceServiceNode);
    }

    @Override
    public Set<DeploymentMapNodeElement> buildConnectedSystemSet() {
        return(new HashSet<DeploymentMapNodeElement>());
    }

}
