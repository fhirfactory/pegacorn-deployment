/*
 * Copyright (c) 2021 Mark A. Hunter (ACT Health)
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
package net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.endpoints;

import net.fhirfactory.pegacorn.core.model.component.valuesets.SoftwareComponentConnectivityContextEnum;
import net.fhirfactory.pegacorn.core.model.componentid.ComponentIdType;
import net.fhirfactory.pegacorn.core.model.componentid.SoftwareComponentTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.JGroupsIntegrationPointNamingUtilities;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointFunctionTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosIntegrationPointNameEnum;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipant;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipantFulfillment;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipantFulfillmentStatusEnum;
import net.fhirfactory.pegacorn.core.model.petasos.participant.id.PetasosParticipantId;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.adapters.base.IPCAdapterDefinition;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.jgroups.JGroupsIntegrationPoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.edge.jgroups.datatypes.JGroupsAdapter;
import net.fhirfactory.pegacorn.core.model.topology.mode.ConcurrencyModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.mode.NetworkSecurityZoneEnum;
import net.fhirfactory.pegacorn.core.model.topology.mode.ResilienceModeEnum;
import net.fhirfactory.pegacorn.core.model.topology.nodes.ProcessingPlantSoftwareComponent;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.PetasosEnabledSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.InterfaceDefinitionSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.jgroups.JGroupsInterZoneRepeaterClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.jgroups.JGroupsKubernetesPodPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common.TopologyFactoryHelpersBase;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class JGroupsTopologyEndpointFactory extends TopologyFactoryHelpersBase {
    private static final Logger LOG = LoggerFactory.getLogger(JGroupsTopologyEndpointFactory.class);

    @Inject
    private JGroupsIntegrationPointNamingUtilities jgroupNamingUtilities;

    @Inject
    private TopologyIM topologyIM;

    //
    // Constructor(s)
    //

    //
    // Getters (and Setters)
    //

    @Override
    protected Logger getLogger(){
        return(LOG);
    }

    protected JGroupsIntegrationPointNamingUtilities getJGroupsNamingUtilities(){
        return(jgroupNamingUtilities);
    }

    protected TopologyIM getTopologyIM(){
        return(topologyIM);
    }

    //
    // Business Methods
    //

    public void addJGroupsEndpoint(ProcessingPlantSoftwareComponent processingPlantNode,
                                      BaseSubsystemPropertyFile propertyFile,
                                      EndpointProviderInterface endpointProvider,
                                      JGroupsKubernetesPodPortSegment jgroupsIPCSegment,
                                      PetasosEndpointTopologyTypeEnum petasosEndpointType,
                                      PetasosEndpointFunctionTypeEnum function, String name,
                                      ResilienceModeEnum resilienceMode,
                                      ConcurrencyModeEnum concurrencyMode){
        getLogger().debug(".addJGroupsEndpoint(): Entry, endpointProvider->{}, jgroupsIPCSegment->{}, petasosEndpointType->{}, function->{}, name->{}",endpointProvider,jgroupsIPCSegment,petasosEndpointType,function,name );
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile) propertyFile;
        JGroupsIntegrationPoint jgroupsIP = new JGroupsIntegrationPoint();
        if(jgroupsIPCSegment == null){
            getLogger().debug(".addJGroupsEndpoint(): Exit, no jgroupsIPCSegment to add");
            return;
        }
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        jgroupsIP.setComponentID(componentId);
        jgroupsIP.setVersion(endpointProvider.getVersion());
        jgroupsIP.setEndpointConfigurationName(jgroupsIPCSegment.getName());
        jgroupsIP.setActualHostIP(getActualHostIP());
        jgroupsIP.setEndpointType(petasosEndpointType);
        NetworkSecurityZoneEnum securityZone = NetworkSecurityZoneEnum.fromDisplayName(petasosEnabledSubsystemPropertyFile.getDeploymentZone().getSecurityZoneName());
        jgroupsIP.setSecurityZone(securityZone);
        jgroupsIP.setNameSpace(petasosEnabledSubsystemPropertyFile.getDeploymentZone().getNameSpace());
        jgroupsIP.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_SUBSYSTEM_EDGE);
        jgroupsIP.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        jgroupsIP.setServer(true);
        jgroupsIP.setResilienceMode(resilienceMode);
        jgroupsIP.setConcurrencyMode(concurrencyMode);
        String site = petasosEnabledSubsystemPropertyFile.getSubsystemInstant().getSite();
        jgroupsIP.setDeploymentSite(site);
        jgroupsIP.setInterfaceFunction(function);
        jgroupsIP.setEnablingProcessingPlantId(processingPlantNode.getComponentId());
        jgroupsIP.setEndpointDescription(jgroupsIPCSegment.getPortType());
        // Build Petasos Participant
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(propertyFile.getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        String subsystemParticipantName = petasosEnabledSubsystemPropertyFile.getSubsystemInstant().getParticipantName();
        participant.getParticipantId().setSubsystemName(subsystemParticipantName);
        String clusterName = propertyFile.getSubsystemInstant().getClusterServiceName();
        if(StringUtils.isEmpty(clusterName)){
            getLogger().error(".addInterZoneRepeaterJGroupsIntegrationPoint(): Bad definition of Petasos IPC Endpoint->{}", jgroupsIPCSegment.getName());
            participant.getParticipantId().setName("unknown");
        } else {
            participant.getParticipantId().setName(clusterName);
        }
        participant.getParticipantId().setDisplayName(endpointProvider.getParticipantId().getDisplayName() + "." + clusterName);
        participant.getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + "." + clusterName);
        participant.getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        jgroupsIP.setParticipant(participant);
        jgroupsIP.setEndpointDescription(jgroupsIPCSegment.getPortType());

        String channelName = getJGroupsNamingUtilities().buildChannelName(site, securityZone.getDisplayName(), subsystemParticipantName, function.getDisplayName(), getJGroupsNamingUtilities().getCurrentUUID());
        jgroupsIP.setChannelName(channelName);

        JGroupsAdapter adapter = new JGroupsAdapter();
        adapter.setPortNumber(jgroupsIPCSegment.getServerPort());
        adapter.setHostName(jgroupsIPCSegment.getServerHostname());
        adapter.setEncrypted(jgroupsIPCSegment.isEncrypted());
        for(InterfaceDefinitionSegment currentSegment: jgroupsIPCSegment.getSupportedInterfaceProfiles()) {
            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            adapter.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        }
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        jgroupsIP.setJGroupsAdapter(adapter);
        endpointProvider.addEndpoint(jgroupsIP.getComponentId());
        getLogger().trace(".addJGroupsEndpoint(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), jgroupsIP);
        getLogger().debug(".addJGroupsEndpoint(): Exit, endpoint added");
    }

    /**
     * This method creates a JGroupsIntegrationPoint (JChannel) for a given integration function (e.g. tasking,
     * subscriptions, messaging, interception, etc.) - populating it with a combination of ProcessingPlant sourced
     * information and PropertyFile() based information as passed in the JGroupsInterZoneRepeaterClientPortSegment
     * data element.
     *
     * @param endpointProvider The entity to which this integrationPoint should be "added"
     * @param jgroupsIPCSegment The PropertyFile segment describing the key attributes of this integrationPoint
     * @param petasosEndpointType What sort of PetasosEndpointType should be assigned to the integrationPoint
     * @param function What is it's integration "function" (i.e. tasking, subscriptions, messaging, etc.)
     * @param name What name should be given to the create integrationPoint (SoftwareComponent)
     */
    public void newInterZoneRepeaterJGroupsIntegrationPoint(ProcessingPlantSoftwareComponent processingPlantNode,
                                                               BaseSubsystemPropertyFile propertyFile,
                                                               EndpointProviderInterface endpointProvider,
                                                               JGroupsInterZoneRepeaterClientPortSegment jgroupsIPCSegment,
                                                               PetasosEndpointTopologyTypeEnum petasosEndpointType,
                                                               PetasosEndpointFunctionTypeEnum function, String name,
                                                               ResilienceModeEnum resilienceMode,
                                                               ConcurrencyModeEnum concurrencyMode){
        getLogger().debug(".addJGroupsIntegrationPoint(): Entry, endpointProvider->{}, jgroupsIPCSegment->{}, petasosEndpointType->{}, function->{}, name->{}",endpointProvider,jgroupsIPCSegment,petasosEndpointType,function,name );
        PetasosEnabledSubsystemPropertyFile petasosEnabledSubsystemPropertyFile = (PetasosEnabledSubsystemPropertyFile)propertyFile;
        JGroupsIntegrationPoint jgroupsIP = new JGroupsIntegrationPoint();
        if(jgroupsIPCSegment == null){
            getLogger().debug(".addJGroupsIntegrationPoint(): Exit, no jgroupsIPCSegment to add");
            return;
        }
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        jgroupsIP.setComponentID(componentId);
        jgroupsIP.setVersion(endpointProvider.getVersion());
        jgroupsIP.setEndpointConfigurationName(jgroupsIPCSegment.getName());
        jgroupsIP.setActualHostIP(getActualHostIP());
        jgroupsIP.setEndpointType(petasosEndpointType);
        NetworkSecurityZoneEnum securityZone = NetworkSecurityZoneEnum.fromDisplayName(petasosEnabledSubsystemPropertyFile.getDeploymentZone().getSecurityZoneName());
        jgroupsIP.setSecurityZone(securityZone);
        jgroupsIP.setNameSpace(petasosEnabledSubsystemPropertyFile.getDeploymentZone().getNameSpace());
        jgroupsIP.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_SUBSYSTEM_EDGE);
        jgroupsIP.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        jgroupsIP.setServer(true);
        jgroupsIP.setResilienceMode(resilienceMode);
        jgroupsIP.setConcurrencyMode(concurrencyMode);

        String site = petasosEnabledSubsystemPropertyFile.getSubsystemInstant().getSite();
        jgroupsIP.setDeploymentSite(site);
        jgroupsIP.setInterfaceFunction(function);
        // Build Participant
        PetasosParticipantId participantId = new PetasosParticipantId();
        participantId.setName(name);
        participantId.setFullName(processingPlantNode.getParticipantId().getFullName() + "." + name);
        participantId.setDisplayName(name);
        participantId.setVersion(endpointProvider.getVersion());
        String subsystemParticipantName = petasosEnabledSubsystemPropertyFile.getSubsystemInstant().getParticipantName();
        participantId.setSubsystemName(subsystemParticipantName);
        PetasosParticipant participant = new PetasosParticipant();
        participant.setComponentId(componentId);
        participant.setParticipantId(participantId);
        participant.setFulfillmentState(new PetasosParticipantFulfillment());
        participant.getFulfillmentState().getFulfillerComponents().add(componentId);
        participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_FULLY_FULFILLED);
        participant.getFulfillmentState().setNumberOfActualFulfillers(1);
        participant.getFulfillmentState().setNumberOfFulfillersExpected(propertyFile.getDeploymentMode().getProcessingPlantReplicationCount());
        if(participant.getFulfillmentState().getNumberOfFulfillersExpected() > participant.getFulfillmentState().getNumberOfActualFulfillers()){
            participant.getFulfillmentState().setFulfillmentStatus(PetasosParticipantFulfillmentStatusEnum.PETASOS_PARTICIPANT_PARTIALLY_FULFILLED);
        }
        jgroupsIP.setEnablingProcessingPlantId(processingPlantNode.getComponentId());
        jgroupsIP.setEndpointDescription(jgroupsIPCSegment.getPortType());
        jgroupsIP.setParticipant(participant);

        switch(function){
            case PETASOS_MESSAGING_ENDPOINT:
                jgroupsIP.setConfigurationFileName(propertyFile.getDeploymentMode().getPetasosIPCStackConfigFile());
                break;
            case PETASOS_SUBSCRIPTIONS_ENDPOINT:
                jgroupsIP.setConfigurationFileName(propertyFile.getDeploymentMode().getPetasosSubscriptionStackConfigFile());
                break;
            case PETASOS_INTERCEPTION_ENDPOINT:
                jgroupsIP.setConfigurationFileName(propertyFile.getDeploymentMode().getPetasosInterceptionStackConfigFile());
                break;
            case PETASOS_TASKING_ENDPOINT:
                jgroupsIP.setConfigurationFileName(propertyFile.getDeploymentMode().getPetasosTaskingStackConfigFile());
                break;
            case PETASOS_AUDIT_ENDPOINT:
                jgroupsIP.setConfigurationFileName(propertyFile.getDeploymentMode().getPetasosAuditStackConfigFile());
                break;
            case PETASOS_METRICS_ENDPOINT:
                jgroupsIP.setConfigurationFileName(propertyFile.getDeploymentMode().getPetasosMetricsStackConfigFile());
                break;
            case PETASOS_TOPOLOGY_ENDPOINT:
                jgroupsIP.setConfigurationFileName(propertyFile.getDeploymentMode().getPetasosTopologyStackConfigFile());
                break;
        }

        String channelName = getJGroupsNamingUtilities().buildChannelName(site, securityZone.getDisplayName(), subsystemParticipantName, function.getDisplayName(), getJGroupsNamingUtilities().getCurrentUUID());
        jgroupsIP.setChannelName(channelName);
        getLogger().info(".addJGroupsIntegrationPoint(): channelName->{}", jgroupsIP.getChannelName());
        // Build Adapter Set
        JGroupsAdapter adapter = new JGroupsAdapter();
        adapter.setPortNumber(jgroupsIPCSegment.getTargetPortValue());
        adapter.setHostName(jgroupsIPCSegment.getTargetHostName());
        adapter.setEncrypted(jgroupsIPCSegment.isEncrypted());
        for(InterfaceDefinitionSegment currentSegment: jgroupsIPCSegment.getSupportedInterfaceProfiles()) {
            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            adapter.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        }
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(ResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        jgroupsIP.setJGroupsAdapter(adapter);
        endpointProvider.addEndpoint(jgroupsIP.getComponentId());
        getLogger().trace(".addJGroupsIntegrationPoint(): Add the InterZone JGroups IPC Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), jgroupsIP);
        getLogger().info(".addJGroupsIntegrationPoint(): Exit, integrationPoint added, jgroupsIP->{}", jgroupsIP);
    }
}
