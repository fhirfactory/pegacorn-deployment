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

import net.fhirfactory.pegacorn.deployment.topology.map.archetypes.FHIRBreakPegacornSubsystem;
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
public class BuildFHIRBreakMap extends FHIRBreakPegacornSubsystem {

    String nodeFHIRBreak = "FHIRBreak";
    String nodeFHIRBreakGen0 = "gen0-fhirbreak";
    String nodeFHIRBreakGen1 = "gen1-fhirbreak";

    @Override
    public void buildSubsystemNode(DeploymentMapNodeElement solutionNode) {
        DeploymentMapNodeElement fhirbreakNode = new DeploymentMapNodeElement();
        fhirbreakNode.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        fhirbreakNode.setElementVersion("0.0.1");
        fhirbreakNode.setInstanceName("FHIRBreak");
        fhirbreakNode.setFunctionName("FHIRBreak");
        fhirbreakNode.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        fhirbreakNode.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        fhirbreakNode.getContainedElements().add(fhirbreakNode);
        buildExternalisedServiceNode(fhirbreakNode);
    }

    public void buildExternalisedServiceNode(DeploymentMapNodeElement fhirbreakNode) {
        DeploymentMapNodeElement fhirBreakGen0Instance = new DeploymentMapNodeElement();
        fhirBreakGen0Instance.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        fhirBreakGen0Instance.setElementVersion("0.0.1");
        fhirBreakGen0Instance.setInstanceName(nodeFHIRBreakGen0);
        fhirBreakGen0Instance.setFunctionName(nodeFHIRBreakGen0);
        fhirBreakGen0Instance.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        fhirBreakGen0Instance.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        fhirBreakGen0Instance.setContainedElements(new ArrayList<DeploymentMapNodeElement>());
        fhirbreakNode.getContainedElements().add(fhirBreakGen0Instance);

        DeploymentMapNodeElement fhirBreakGen1Instance = new DeploymentMapNodeElement();
        fhirBreakGen1Instance.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        fhirBreakGen1Instance.setElementVersion("0.0.1");
        fhirBreakGen1Instance.setInstanceName(nodeFHIRBreakGen1);
        fhirBreakGen1Instance.setFunctionName(nodeFHIRBreakGen1);
        fhirBreakGen1Instance.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        fhirBreakGen1Instance.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        fhirBreakGen1Instance.setContainedElements(new ArrayList<DeploymentMapNodeElement>());
        fhirbreakNode.getContainedElements().add(fhirBreakGen1Instance);

        DeploymentMapNodeElement externalServiceFHIRBreakTechOne = new DeploymentMapNodeElement();
        externalServiceFHIRBreakTechOne.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        externalServiceFHIRBreakTechOne.setElementVersion("0.0.1");
        externalServiceFHIRBreakTechOne.setInstanceName("aether-fhirbreak-techone");
        externalServiceFHIRBreakTechOne.setFunctionName("aether-fhirbreak-techone");
        externalServiceFHIRBreakTechOne.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        externalServiceFHIRBreakTechOne.setTopologyElementType(NodeElementTypeEnum.EXTERNALISED_SERVICE);
        externalServiceFHIRBreakTechOne.setContainedElements(new ArrayList<DeploymentMapNodeElement>());
        fhirbreakNode.getContainedElements().add(externalServiceFHIRBreakTechOne);

        DeploymentMapEndpointElement endpointMatrixNotificationReceiver = new DeploymentMapEndpointElement();
        endpointMatrixNotificationReceiver.setEndpointInstanceID("TechOneAPI");
        endpointMatrixNotificationReceiver.setEndpointFunctionID("TechOneAPI");
        endpointMatrixNotificationReceiver.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointMatrixNotificationReceiver.setExternalDNSEntry("aether-fhirbreak-technone");
        endpointMatrixNotificationReceiver.setExternalPortNumber("32410");
        endpointMatrixNotificationReceiver.setIsServer(true);
        endpointMatrixNotificationReceiver.setRequiresEncryption(false);
        externalServiceFHIRBreakTechOne.getEndpoints().add(endpointMatrixNotificationReceiver);
    }

    public void createFHIRBreakSites(DeploymentMapNodeElement nodeFHIRBreakExternalService) {
        DeploymentMapNodeElement nodeSiteA = new DeploymentMapNodeElement();
        nodeSiteA.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeSiteA.setElementVersion("0.0.1");
        nodeSiteA.setInstanceName("___");
        nodeSiteA.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeSiteA.setTopologyElementType(NodeElementTypeEnum.SITE);
        nodeFHIRBreakExternalService.getContainedElements().add(nodeSiteA);
        createFHIRBReakTechOneServices(nodeSiteA);
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

    public void createFHIRBReakTechOneServices(DeploymentMapNodeElement nodeFHIRBreakSite) {
        DeploymentMapNodeElement nodeFHIRBreakTechoneService = new DeploymentMapNodeElement();
        nodeFHIRBreakTechoneService.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeFHIRBreakTechoneService.setElementVersion("0.0.1");
        nodeFHIRBreakTechoneService.setInstanceName("TechnologyOneAPI");
        nodeFHIRBreakTechoneService.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeFHIRBreakTechoneService.setTopologyElementType(NodeElementTypeEnum.SERVICE);
        nodeFHIRBreakSite.getContainedElements().add(nodeFHIRBreakTechoneService);
        createFHIRBreakTechOnePlatforms(nodeFHIRBreakTechoneService);
        createFHIRBreakTechOneEndpoints(nodeFHIRBreakTechoneService);
    }

    public void createFHIRBreakTechOneEndpoints(DeploymentMapNodeElement nodeTechOneService) {
        DeploymentMapEndpointElement endpointTechoneAPIPort = new DeploymentMapEndpointElement();
        endpointTechoneAPIPort.setEndpointInstanceID("TechnologyOneAPI");
        endpointTechoneAPIPort.setEndpointFunctionID("MatrixNotificationsReceiver");
        endpointTechoneAPIPort.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointTechoneAPIPort.setExternalDNSEntry("___");
        endpointTechoneAPIPort.setExternalPortNumber("12410");
        endpointTechoneAPIPort.setInternalPortNumber("8443");
        endpointTechoneAPIPort.setIsServer(true);
        endpointTechoneAPIPort.setRequiresEncryption(false);
        nodeTechOneService.getEndpoints().add(endpointTechoneAPIPort);

        DeploymentMapEndpointElement endpointPetasosTopologySync = new DeploymentMapEndpointElement();
        endpointPetasosTopologySync.setEndpointInstanceID("PetasosTopologySyncSvr");
        endpointPetasosTopologySync.setEndpointFunctionID("PetasosTopologySyncSvr");
        endpointPetasosTopologySync.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointPetasosTopologySync.setExternalDNSEntry("___");
        endpointPetasosTopologySync.setExternalPortNumber("12860");
        endpointPetasosTopologySync.setInternalPortNumber("12860");
        endpointPetasosTopologySync.setIsServer(true);
        endpointPetasosTopologySync.setRequiresEncryption(false);
        nodeTechOneService.getEndpoints().add(endpointPetasosTopologySync);

        DeploymentMapEndpointElement endpointPetasosParcelSync = new DeploymentMapEndpointElement();
        endpointPetasosParcelSync.setEndpointInstanceID("PetasosResilienceParcelSyncSvr");
        endpointPetasosParcelSync.setEndpointFunctionID("PetasosResilienceParcelSyncSvr");
        endpointPetasosParcelSync.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointPetasosParcelSync.setExternalDNSEntry("___");
        endpointPetasosParcelSync.setExternalPortNumber("12861");
        endpointPetasosParcelSync.setInternalPortNumber("12861");
        endpointPetasosParcelSync.setIsServer(true);
        endpointPetasosParcelSync.setRequiresEncryption(false);
        nodeTechOneService.getEndpoints().add(endpointPetasosParcelSync);

        DeploymentMapEndpointElement endpointPetasosHeartbeatSync = new DeploymentMapEndpointElement();
        endpointPetasosHeartbeatSync.setEndpointInstanceID("PetasosHeartbeatSvr");
        endpointPetasosHeartbeatSync.setEndpointFunctionID("PetasosHeartbeatSvr");
        endpointPetasosHeartbeatSync.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointPetasosHeartbeatSync.setExternalDNSEntry("___");
        endpointPetasosHeartbeatSync.setExternalPortNumber("12862");
        endpointPetasosHeartbeatSync.setInternalPortNumber("12862");
        endpointPetasosHeartbeatSync.setIsServer(true);
        endpointPetasosHeartbeatSync.setRequiresEncryption(true);
        nodeTechOneService.getEndpoints().add(endpointPetasosHeartbeatSync);

        DeploymentMapEndpointElement endpointEndReceiveCommunication = new DeploymentMapEndpointElement();
        endpointEndReceiveCommunication.setEndpointInstanceID("EdgeReceive.CommunicationEX");
        endpointEndReceiveCommunication.setEndpointFunctionID("EdgeReceive.CommunicationEX");
        endpointEndReceiveCommunication.setEndpointType(EndpointElementTypeEnum.INGRES_MESSAGING);
        endpointEndReceiveCommunication.setExternalDNSEntry("Inherited");
        endpointEndReceiveCommunication.setExternalPortNumber("12450");
        endpointEndReceiveCommunication.setInternalPortNumber("12450");
        endpointEndReceiveCommunication.setIsServer(true);
        endpointEndReceiveCommunication.setRequiresEncryption(false);
        nodeTechOneService.getEndpoints().add(endpointEndReceiveCommunication);

    }

    public void createFHIRBreakTechOnePlatforms(DeploymentMapNodeElement nodeFHIRBreakTechOne) {
        DeploymentMapNodeElement nodePlatformA = new DeploymentMapNodeElement();
        nodePlatformA.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodePlatformA.setElementVersion("0.0.1");
        nodePlatformA.setInstanceName("___");
        nodePlatformA.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodePlatformA.setTopologyElementType(NodeElementTypeEnum.PLATFORM);
        nodeFHIRBreakTechOne.getContainedElements().add(nodePlatformA);
        createFHIRBreakTechOneServiceModules(nodePlatformA);

        /*        ConfigMapNodeElement nodePlatformB = new ConfigMapNodeElement();
        nodePlatformB.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodePlatformB.setElementVersion("0.0.1");
        nodePlatformB.setInstanceName("Derived");
        nodePlatformB.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodePlatformB.setTopologyElementType(NodeElementTypeEnum.PLATFORM);
        nodeFHIRBreakTechOne.getContainedElements().add(nodePlatformB);  
        createIrisServices(nodePlatformB); */
    }

    public void createFHIRBreakTechOneServiceModules(DeploymentMapNodeElement nodeNode) {
        DeploymentMapNodeElement nodeTechOneAPIServer = new DeploymentMapNodeElement();
        nodeTechOneAPIServer.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeTechOneAPIServer.setElementVersion("0.0.1");
        nodeTechOneAPIServer.setInstanceName("TechologyOneAPIServer");
        nodeTechOneAPIServer.setFunctionName("TechologyOneAPIServer");
        nodeTechOneAPIServer.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeTechOneAPIServer.setTopologyElementType(NodeElementTypeEnum.PROCESSING_PLANT);
        nodeNode.getContainedElements().add(nodeTechOneAPIServer);

        DeploymentMapEndpointElement endpointTechOneAPIReceiver = new DeploymentMapEndpointElement();
        endpointTechOneAPIReceiver.setEndpointInstanceID("TechologyOneAPIServerPort");
        endpointTechOneAPIReceiver.setEndpointFunctionID("TechologyOneAPIServerPort");
        endpointTechOneAPIReceiver.setEndpointType(EndpointElementTypeEnum.API_SERVER);
        endpointTechOneAPIReceiver.setExternalDNSEntry("Inherited");
        endpointTechOneAPIReceiver.setExternalPortNumber("Default");
        endpointTechOneAPIReceiver.setIsServer(true);
        endpointTechOneAPIReceiver.setRequiresEncryption(false);
        nodeTechOneAPIServer.getEndpoints().add(endpointTechOneAPIReceiver);

        /*  
        ConfigMapNodeElement nodeMatrixRoomIM2FHIRCommsTransformer = new ConfigMapNodeElement();
        nodeMatrixRoomIM2FHIRCommsTransformer.setConcurrencyMode(ConcurrencyModeEnum.CONCURRENCY_MODE_STANDALONE);
        nodeMatrixRoomIM2FHIRCommsTransformer.setElementVersion("0.0.1");
        nodeMatrixRoomIM2FHIRCommsTransformer.setInstanceName("MatrixRoomIM2FHIRCommunicationTransformer");
        nodeMatrixRoomIM2FHIRCommsTransformer.setFunctionName("MatrixRoomIM2FHIRCommunicationTransformer");
        nodeMatrixRoomIM2FHIRCommsTransformer.setResilienceMode(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        nodeMatrixRoomIM2FHIRCommsTransformer.setTopologyElementType(NodeElementTypeEnum.SERVICE_MODULE);
        nodeFHIRBreakTechoneService.getContainedElements().add(nodeMatrixRoomIM2FHIRCommsTransformer); */
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
        endpointEndReceiveCommunication.setEndpointInstanceID("EdgeReceive.CommunicationEX");
        endpointEndReceiveCommunication.setEndpointFunctionID("EdgeReceive.CommunicationEX");
        endpointEndReceiveCommunication.setEndpointType(EndpointElementTypeEnum.INGRES_MESSAGING);
        endpointEndReceiveCommunication.setExternalDNSEntry("Inherited");
        endpointEndReceiveCommunication.setExternalPortNumber("12450");
        endpointEndReceiveCommunication.setIsServer(true);
        endpointEndReceiveCommunication.setRequiresEncryption(false);
        nodeEdgeReceive.getEndpoints().add(endpointEndReceiveCommunication);
    }

    @Override
    public Set<DeploymentMapNodeElement> buildConnectedSystemSet() {
        return(new HashSet<DeploymentMapNodeElement>());
    }

}
