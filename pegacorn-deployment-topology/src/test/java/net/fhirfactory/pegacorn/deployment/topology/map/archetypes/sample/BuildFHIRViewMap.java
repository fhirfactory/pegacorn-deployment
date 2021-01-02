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

import net.fhirfactory.pegacorn.deployment.topology.map.archetypes.FHIRViewPegacornSubsystem;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElementTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapNodeElement;

/**
 *
 * @author Mark A. Hunter
 */
public class BuildFHIRViewMap extends FHIRViewPegacornSubsystem {

    String nodeFHIRView = "FHIRView";
    String nodeFHIRViewGen0 = "gen0-fhirview";
    String nodeFHIRViewGen1 = "gen1-fhirview";

    @Override
    protected ResilienceModeEnum specifyResilienceMode() {
        return (ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
    }

    @Override
    protected String getDefaultServiceDNSEntry() {
        return (nodeFHIRViewGen0);
    }

    @Override
    protected int getDefaultServiceInteractBasePort() {
        return (19408);
    }

    @Override
    protected int getDefaultServicePetasosBasePort() {
        return (19402);
    }

    @Override
    protected int getDefaultServiceEdgeReceiveBasePort() {
        return (19401);
    }

    @Override
    protected int getDefaultServiceEdgeAnswerBasePort() {
        return (19400);
    }

    @Override
    protected int getDefaultProcessingPlantInteractBasePort() {
        return (19408);
    }

    @Override
    protected int getDefaultProcessingPlantPetasosBasePort() {
        return (19402);
    }

    @Override
    protected int getDefaultProcessingPlantEdgeReceiveBasePort() {
        return (19401);
    }

    @Override
    protected int getDefaultProcessingPlantEdgeAnswerBasePort() {
        return (19400);
    }

    @Override
    protected String getDefaultProcessingPlantDNSEntry() {
        return (nodeFHIRViewGen0);
    }

    @Override
    public void buildSubsystemNode(DeploymentMapNodeElement solutionNode) {
        DeploymentMapNodeElement fhirviewNode = new DeploymentMapNodeElement();
        fhirviewNode.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        fhirviewNode.setElementVersion("0.0.1");
        fhirviewNode.setInstanceName("FHIRView");
        fhirviewNode.setFunctionName("FHIRView");
        fhirviewNode.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        fhirviewNode.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        fhirviewNode.getContainedElements().add(fhirviewNode);
        buildExternalisedServiceNode(fhirviewNode);
    }

    public void buildExternalisedServiceNode(DeploymentMapNodeElement fhirviewNode) {
        DeploymentMapNodeElement fhirvewGen0Node = new DeploymentMapNodeElement();
        fhirvewGen0Node.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        fhirvewGen0Node.setElementVersion("0.0.1");
        fhirvewGen0Node.setInstanceName(nodeFHIRViewGen0);
        fhirvewGen0Node.setFunctionName(nodeFHIRViewGen0);
        fhirvewGen0Node.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        fhirvewGen0Node.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        fhirvewGen0Node.setContainedElements(new ArrayList<DeploymentMapNodeElement>());
        fhirviewNode.getContainedElements().add(fhirvewGen0Node);

        DeploymentMapNodeElement fhirvewGen1Node = new DeploymentMapNodeElement();
        fhirvewGen1Node.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        fhirvewGen1Node.setElementVersion("0.0.1");
        fhirvewGen1Node.setInstanceName(nodeFHIRViewGen1);
        fhirvewGen1Node.setFunctionName(nodeFHIRViewGen1);
        fhirvewGen1Node.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        fhirvewGen1Node.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        fhirvewGen1Node.setContainedElements(new ArrayList<DeploymentMapNodeElement>());
        fhirviewNode.getContainedElements().add(fhirvewGen1Node);
    }

    @Override
    public Set<DeploymentMapNodeElement> buildConnectedSystemSet() {
        return(new HashSet<DeploymentMapNodeElement>());
    }

}
