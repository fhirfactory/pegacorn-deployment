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
package net.fhirfactory.pegacorn.deployment.topology.factories.base.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import net.fhirfactory.dricats.model.component.datatypes.ComponentTypeDefinition;
import net.fhirfactory.dricats.model.component.valuesets.ComponentTypeEnum;
import net.fhirfactory.dricats.model.configuration.filebased.archetypes.common.BaseSubsystemPropertyFile;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.base.InterfaceDefinitionSegment;
import net.fhirfactory.dricats.model.configuration.filebased.segments.endpoints.jgroups.JGroupsInterZoneRepeaterClientPortSegment;
import net.fhirfactory.dricats.model.topology.nodes.valuesets.TopologyNodeNetworkingContextEnum;
import net.fhirfactory.dricats.model.topology.endpoints.base.adapters.IPCAdapterDefinition;
import net.fhirfactory.dricats.model.topology.endpoints.base.valuesets.EndpointResilienceModeEnum;
import net.fhirfactory.dricats.model.topology.endpoints.base.valuesets.EndpointTopologyNodeTypeEnum;
import net.fhirfactory.dricats.model.topology.endpoints.jgroups.JGroupsClusterConnectorETN;
import net.fhirfactory.dricats.model.topology.endpoints.jgroups.adapters.JGroupsClientAdapter;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.processingplants.base.ProcessingPlantTopologyNode;
import net.fhirfactory.dricats.model.topology.nodes.softwarecomponents.workunitprocessors.petasos.ipc.base.PetasosIPCServiceAgentTopologyNode;

@ApplicationScoped
public class JGroupsTopologyEndpointFactory  {
    private static final Logger LOG = LoggerFactory.getLogger(JGroupsTopologyEndpointFactory.class);

    //
    // Constructor(s)
    //

    //
    // Getters (and Setters)
    //

    protected Logger getLogger(){
        return(LOG);
    }

    //
    // Business Methods
    //

    /**
     * This method creates a JGroupsIntegrationPoint (JChannel) for a given integration function (e.g. tasking,
     * subscriptions, messaging, interception, etc.) - populating it with a combination of ProcessingPlant sourced
     * information and PropertyFile() based information as passed in the JGroupsInterZoneRepeaterClientPortSegment
     * data element.
     *
     * @param processingPlantNode The ProcessingPlantTopologyNode representing the ProcessingPlant containing this endpoint
     * @param propertyFile The PropertyFile for this component
     * @param parentWUP The entity to which this integrationPoint should be "added"
     * @param jgroupsIPCSegment The PropertyFile segment describing the key attributes of this integrationPoint
     * @param name What name should be given to the create integrationPoint (SoftwareComponent)
     */
    public JGroupsClusterConnectorETN createJGroupsClientEndpoint(ProcessingPlantTopologyNode processingPlantNode,
                                      BaseSubsystemPropertyFile propertyFile,
                                      PetasosIPCServiceAgentTopologyNode parentWUP,
                                      JGroupsInterZoneRepeaterClientPortSegment jgroupsIPCSegment,
                                      String name, 
                                      String nameSpace){
        getLogger().debug(".addJGroupsEndpoint(): Entry, parentWUP->{}, jgroupsIPCSegment->{}, name->{}",parentWUP,jgroupsIPCSegment,name );
        JGroupsClusterConnectorETN clusterConnector = new JGroupsClusterConnectorETN();
        if(jgroupsIPCSegment == null){
            getLogger().debug(".addJGroupsEndpoint(): Exit, no jgroupsIPCSegment to add");
            return(null);
        }
        clusterConnector.constructNodeID(ComponentTypeEnum.ENDPOINT.getDisplayName());
        clusterConnector.setEndpointConfigurationName(jgroupsIPCSegment.getName());
        clusterConnector.setEndpointType(EndpointTopologyNodeTypeEnum.JGROUPS_INTEGRATION_POINT);
        clusterConnector.setNetworkZone(processingPlantNode.getNetworkZone());
        clusterConnector.setNetworkContext(TopologyNodeNetworkingContextEnum.COMPONENT_ROLE_SUBSYSTEM_EDGE);
        ComponentTypeDefinition componentType = new ComponentTypeDefinition();
        componentType.setComponentArchetype(ComponentTypeEnum.ENDPOINT);
        componentType.setTypeName("JGroupsRepeaterClient");
        componentType.setDisplayTypeName("JGroups Repeater Client");
        clusterConnector.setNodeType(componentType);
        clusterConnector.setServer(false);
        clusterConnector.setEndpointConfigurationName(jgroupsIPCSegment.getName());
        clusterConnector.setParentNode(parentWUP.getNodeId());
        clusterConnector.setParticipantName(parentWUP.getParticipantName()+"."+name+".JGroups.GossipRouterClient");
        clusterConnector.setParticipantDisplayName(name+".JGroups.GossipRouterClient");
        clusterConnector.setDescription("JGroups: Gossip Router Client"+"("+jgroupsIPCSegment.getName()+")");
        clusterConnector.setChannelName(parentWUP.getParticipantName());
        clusterConnector.setGroupName(nameSpace);
        JGroupsClientAdapter adapter = new JGroupsClientAdapter();
        adapter.setPortNumber(jgroupsIPCSegment.getTargetPort());
        adapter.setHostName(jgroupsIPCSegment.getTargetHostname());
        adapter.setEncrypted(jgroupsIPCSegment.isEncrypted());
        for(InterfaceDefinitionSegment currentSegment: jgroupsIPCSegment.getSupportedInterfaceProfiles()) {
            IPCAdapterDefinition currentInterfaceDefinition = new IPCAdapterDefinition();
            currentInterfaceDefinition.setInterfaceFormalName(currentSegment.getInterfaceDefinitionName());
            currentInterfaceDefinition.setInterfaceFormalVersion(currentSegment.getInterfaceDefinitionVersion());
            adapter.getSupportedInterfaceDefinitions().add(currentInterfaceDefinition);
        }
        adapter.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_MULTISITE);
        adapter.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_STANDALONE);
        adapter.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_KUBERNETES_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_MULTISITE);
        adapter.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_STANDALONE);
        adapter.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_CLUSTERED);
        adapter.getSupportedDeploymentModes().add(EndpointResilienceModeEnum.RESILIENCE_MODE_MULTISITE_CLUSTERED);
        clusterConnector.setJGroupsAdapter(adapter);

        getLogger().debug(".addJGroupsEndpoint(): Exit, JGroupsClusterConnectorETN create->{}", clusterConnector);
        return(clusterConnector);
    }
}
