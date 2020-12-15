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

import net.fhirfactory.pegacorn.deployment.topology.map.archetypes.LadonExternalisedServices;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.petasos.model.resilience.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.petasos.model.topology.EndpointElementTypeEnum;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElementTypeEnum;
import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapEndpointElement;
import net.fhirfactory.pegacorn.deployment.topology.map.model.DeploymentMapNodeElement;

/**
 *
 * @author Mark A. Hunter
 */
public class BuildLadonMap extends LadonExternalisedServices {

    String nodeLadonText = "Ladon";
    String nodeLadonGen0Text = "gen0-ladon";

    @Override
    public void buildSubsystemNode(DeploymentMapNodeElement solutionNode) {
        DeploymentMapNodeElement ladonNode = new DeploymentMapNodeElement();
        ladonNode.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        ladonNode.setElementVersion("0.0.1");
        ladonNode.setInstanceName("Ladon");
        ladonNode.setFunctionName("Ladon");
        ladonNode.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        ladonNode.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        ladonNode.getContainedElements().add(ladonNode);
        buildExternalisedServiceNode(ladonNode);
    }

    public void buildExternalisedServiceNode(DeploymentMapNodeElement ladonNode) {
        DeploymentMapNodeElement gen0LadonServiceNode = new DeploymentMapNodeElement();
        gen0LadonServiceNode.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        gen0LadonServiceNode.setElementVersion("0.0.1");
        gen0LadonServiceNode.setInstanceName(nodeLadonGen0Text);
        gen0LadonServiceNode.setFunctionName(nodeLadonGen0Text);
        gen0LadonServiceNode.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        gen0LadonServiceNode.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        gen0LadonServiceNode.setContainedElements(new ArrayList<DeploymentMapNodeElement>());
        ladonNode.getContainedElements().add(gen0LadonServiceNode);
        createLadonSites(gen0LadonServiceNode);
    }

    public void createLadonSites(DeploymentMapNodeElement nodeLadonExternalService) {
        DeploymentMapNodeElement nodeSiteA = new DeploymentMapNodeElement();
        nodeSiteA.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeSiteA.setElementVersion("0.0.1");
        nodeSiteA.setInstanceName("___");
        nodeSiteA.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeSiteA.setTopologyElementType(NodeElementTypeEnum.SITE);
        nodeLadonExternalService.getContainedElements().add(nodeSiteA);
        createLadonServices(nodeSiteA);
        /*        
        ConfigMapNodeElement nodeSiteB = new ConfigMapNodeElement();
        nodeSiteB.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeSiteB.setElementVersion("0.0.1");
        nodeSiteB.setInstanceName("SiteB");
        nodeSiteB.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeSiteB.setTopologyElementType(NodeElementTypeEnum.SITE);
        irisServiceNode.getContainedElements().add(nodeSiteB);
        createIrisPlatforms(nodeSiteB); */
    }

    public void createLadonServices(DeploymentMapNodeElement nodeLadonSite) {
        DeploymentMapNodeElement nodeLadonService = new DeploymentMapNodeElement();
        nodeLadonService.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeLadonService.setElementVersion("0.0.1");
        nodeLadonService.setInstanceName("Ladon");
        nodeLadonService.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeLadonService.setTopologyElementType(NodeElementTypeEnum.SERVICE);
        nodeLadonSite.getContainedElements().add(nodeLadonService);
        createLadonPlatforms(nodeLadonService);
        createLadonServiceEndpoints(nodeLadonService);
    }

    public void createLadonServiceEndpoints(DeploymentMapNodeElement nodeLadonService) {
        DeploymentMapEndpointElement endpointPetasosTopologySync = new DeploymentMapEndpointElement();
        endpointPetasosTopologySync.setEndpointInstanceID("PetasosTopologySyncSvr");
        endpointPetasosTopologySync.setEndpointFunctionID("PetasosTopologySyncSvr");
        endpointPetasosTopologySync.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointPetasosTopologySync.setExternalDNSEntry("___");
        endpointPetasosTopologySync.setExternalPortNumber("10810");
        endpointPetasosTopologySync.setInternalPortNumber("10810");
        endpointPetasosTopologySync.setIsServer(true);
        endpointPetasosTopologySync.setRequiresEncryption(false);
        nodeLadonService.getEndpoints().add(endpointPetasosTopologySync);

        DeploymentMapEndpointElement endpointPetasosParcelSync = new DeploymentMapEndpointElement();
        endpointPetasosParcelSync.setEndpointInstanceID("PetasosTopologySyncSvr");
        endpointPetasosParcelSync.setEndpointFunctionID("PetasosTopologySyncSvr");
        endpointPetasosParcelSync.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointPetasosParcelSync.setExternalDNSEntry("___");
        endpointPetasosParcelSync.setExternalPortNumber("10811");
        endpointPetasosParcelSync.setInternalPortNumber("10811");
        endpointPetasosParcelSync.setIsServer(true);
        endpointPetasosParcelSync.setRequiresEncryption(false);
        nodeLadonService.getEndpoints().add(endpointPetasosParcelSync);

        DeploymentMapEndpointElement endpointPetasosHeartbeatSync = new DeploymentMapEndpointElement();
        endpointPetasosHeartbeatSync.setEndpointInstanceID("PetasosTopologySyncSvr");
        endpointPetasosHeartbeatSync.setEndpointFunctionID("PetasosTopologySyncSvr");
        endpointPetasosHeartbeatSync.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointPetasosHeartbeatSync.setExternalDNSEntry("___");
        endpointPetasosHeartbeatSync.setExternalPortNumber("10812");
        endpointPetasosHeartbeatSync.setInternalPortNumber("10812");
        endpointPetasosHeartbeatSync.setIsServer(true);
        endpointPetasosHeartbeatSync.setRequiresEncryption(true);
        nodeLadonService.getEndpoints().add(endpointPetasosHeartbeatSync);
        
        DeploymentMapEndpointElement endpointEndReceiveCommunication = new DeploymentMapEndpointElement();
        endpointEndReceiveCommunication.setEndpointInstanceID("EdgeReceive.CommunicationEX");
        endpointEndReceiveCommunication.setEndpointFunctionID("EdgeReceive.CommunicationEX");
        endpointEndReceiveCommunication.setEndpointType(EndpointElementTypeEnum.INGRES_MESSAGING);
        endpointEndReceiveCommunication.setExternalDNSEntry("Inherited");
        endpointEndReceiveCommunication.setExternalPortNumber("10200");
        endpointEndReceiveCommunication.setInternalPortNumber("10200");
        endpointEndReceiveCommunication.setIsServer(true);
        endpointEndReceiveCommunication.setRequiresEncryption(false);
        nodeLadonService.getEndpoints().add(endpointEndReceiveCommunication);
    }

    public void createLadonPlatforms(DeploymentMapNodeElement nodeLadonSite) {
        DeploymentMapNodeElement nodePlatformA = new DeploymentMapNodeElement();
        nodePlatformA.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodePlatformA.setElementVersion("0.0.1");
        nodePlatformA.setInstanceName("___");
        nodePlatformA.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodePlatformA.setTopologyElementType(NodeElementTypeEnum.PLATFORM);
        nodeLadonSite.getContainedElements().add(nodePlatformA);
        createLadonServiceModules(nodePlatformA);

        /*        ConfigMapNodeElement nodePlatformB = new ConfigMapNodeElement();
        nodePlatformB.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodePlatformB.setElementVersion("0.0.1");
        nodePlatformB.setInstanceName("Derived");
        nodePlatformB.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodePlatformB.setTopologyElementType(NodeElementTypeEnum.PLATFORM);
        nodeFHIRBreakTechOne.getContainedElements().add(nodePlatformB);  
        createIrisServices(nodePlatformB); */
    }

    public void createLadonServiceModules(DeploymentMapNodeElement nodeNode) {
        DeploymentMapNodeElement nodeEdgeForward = new DeploymentMapNodeElement();
        nodeEdgeForward.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeEdgeForward.setElementVersion("0.0.1");
        nodeEdgeForward.setInstanceName("EdgeForward");
        nodeEdgeForward.setFunctionName("EdgeForward");
        nodeEdgeForward.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeEdgeForward.setTopologyElementType(NodeElementTypeEnum.PROCESSING_PLANT);
        nodeNode.getContainedElements().add(nodeEdgeForward);

        DeploymentMapNodeElement nodeEdgeReceive = new DeploymentMapNodeElement();
        nodeEdgeReceive.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeEdgeReceive.setElementVersion("0.0.1");
        nodeEdgeReceive.setInstanceName("EdgeReceive");
        nodeEdgeReceive.setFunctionName("EdgeReceive");
        nodeEdgeReceive.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeEdgeReceive.setTopologyElementType(NodeElementTypeEnum.PROCESSING_PLANT);
        nodeNode.getContainedElements().add(nodeEdgeReceive);

        DeploymentMapEndpointElement endpointEndReceiveCommunication = new DeploymentMapEndpointElement();
        endpointEndReceiveCommunication.setEndpointInstanceID("EdgeReceive.CommunicationUN");
        endpointEndReceiveCommunication.setEndpointFunctionID("EdgeReceive.CommunicationUN");
        endpointEndReceiveCommunication.setEndpointType(EndpointElementTypeEnum.INGRES_MESSAGING);
        endpointEndReceiveCommunication.setExternalDNSEntry("Inherited");
        endpointEndReceiveCommunication.setExternalPortNumber("10250");
        endpointEndReceiveCommunication.setIsServer(true);
        endpointEndReceiveCommunication.setRequiresEncryption(false);
        nodeEdgeReceive.getEndpoints().add(endpointEndReceiveCommunication);
    }

    @Override
    public Set<DeploymentMapNodeElement> buildConnectedSystemSet() {
        return(new HashSet<DeploymentMapNodeElement>());
    }

}