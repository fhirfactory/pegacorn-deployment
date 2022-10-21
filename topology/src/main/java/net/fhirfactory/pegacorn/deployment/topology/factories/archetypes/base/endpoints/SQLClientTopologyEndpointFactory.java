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
import net.fhirfactory.pegacorn.core.model.petasos.endpoint.valuesets.PetasosEndpointTopologyTypeEnum;
import net.fhirfactory.pegacorn.core.model.petasos.ipc.PegacornCommonInterfaceNames;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipant;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipantFulfillment;
import net.fhirfactory.pegacorn.core.model.petasos.participant.PetasosParticipantFulfillmentStatusEnum;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.sql.SQLClientTopologyEndpoint;
import net.fhirfactory.pegacorn.core.model.topology.endpoints.sql.adapters.SQLClientAdapter;
import net.fhirfactory.pegacorn.core.model.topology.nodes.common.EndpointProviderInterface;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.BaseSubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes.ParameterNameValuePairType;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.interact.InteractSQLClientPortSegment;
import net.fhirfactory.pegacorn.deployment.topology.factories.archetypes.base.common.TopologyFactoryHelpersBase;
import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import net.fhirfactory.pegacorn.util.PegacornProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SQLClientTopologyEndpointFactory extends TopologyFactoryHelpersBase {
    private static final Logger LOG = LoggerFactory.getLogger(SQLClientTopologyEndpointFactory.class);

    @Inject
    private PegacornProperties pegacornProperties;

    @Inject
    private PegacornCommonInterfaceNames interfaceNames;

    @Inject
    private TopologyIM topologyIM;

    //
    // Constructor(s)
    //

    //
    // Getters and Setters
    //

    @Override
    protected Logger getLogger(){
        return(LOG);
    }

    protected PegacornCommonInterfaceNames getInterfaceNames(){
        return(interfaceNames);
    }

    protected TopologyIM getTopologyIM(){
        return(topologyIM);
    }

    //
    // Business Methods
    //

   
    // Build a SQL Client Endpoint (Helper Method)
    //

    public SQLClientTopologyEndpoint newSQLClientTopologyEndpoint(BaseSubsystemPropertyFile propertyFile, EndpointProviderInterface endpointProvider, String endpointFunctionName, InteractSQLClientPortSegment sqlClientPortConfigurationSegment){
        getLogger().debug(".newSQLClientTopologyEndpoint(): Entry, endpointProvider->{}, httpClientPortConfigurationSegment->{}", endpointProvider, sqlClientPortConfigurationSegment);
        SQLClientTopologyEndpoint sqlClientEndpoint = new SQLClientTopologyEndpoint();
        if(sqlClientPortConfigurationSegment == null){
            getLogger().debug(".newSQLClientTopologyEndpoint(): Exit, no port to add");
            return(null);
        }
        String name = getInterfaceNames().getEndpointName(PetasosEndpointTopologyTypeEnum.SQL_CLIENT, endpointFunctionName);
        ComponentIdType componentId = ComponentIdType.fromComponentName(name);
        sqlClientEndpoint.setComponentId(componentId);
        sqlClientEndpoint.setEndpointConfigurationName(sqlClientPortConfigurationSegment.getName());
        sqlClientEndpoint.setParentComponent(endpointProvider.getComponentId());
        sqlClientEndpoint.setVersion(endpointProvider.getVersion());
        sqlClientEndpoint.setEndpointType(PetasosEndpointTopologyTypeEnum.SQL_CLIENT);
        sqlClientEndpoint.setComponentType(SoftwareComponentTypeEnum.ENDPOINT);
        sqlClientEndpoint.setComponentSystemRole(SoftwareComponentConnectivityContextEnum.COMPONENT_ROLE_INTERACT_BIDIRECTIONAL);
        sqlClientEndpoint.setConnectedSystemName(sqlClientPortConfigurationSegment.getConnectedSystem().getSubsystemName());
        sqlClientEndpoint.setServer(false);

        for(ParameterNameValuePairType otherConfigurationParameter: sqlClientPortConfigurationSegment.getOtherConfigurationParameters()){
            sqlClientEndpoint.getOtherConfigurationParameters().put(otherConfigurationParameter.getParameterName(), otherConfigurationParameter.getParameterValue());
        }

        sqlClientEndpoint.setDataSourceName(sqlClientPortConfigurationSegment.getDataSourceName());
        sqlClientEndpoint.setDriverClassName(sqlClientPortConfigurationSegment.getDriverClassName());
        sqlClientEndpoint.setConnectionURL(sqlClientPortConfigurationSegment.getConnectionURL());
        sqlClientEndpoint.setQueryTemplate(sqlClientPortConfigurationSegment.getQueryTemplate());

        SQLClientAdapter sqlClientAdapter = new SQLClientAdapter();
        sqlClientAdapter.setEnablingTopologyEndpoint(componentId);
        sqlClientAdapter.setTargetSystemName(sqlClientPortConfigurationSegment.getConnectedSystem().getSubsystemName());
        sqlClientEndpoint.getSQLClientAdapters().add(sqlClientAdapter);

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
        participant.getParticipantId().setSubsystemName(endpointProvider.getParticipantId().getSubsystemName());
        participant.getParticipantId().setName(endpointProvider.getParticipantId()+"."+"SQL.Client."+sqlClientEndpoint.getDataSourceName());
        participant.getParticipantId().setDisplayName("SQL.Client:"+sqlClientEndpoint.getDataSourceName()+":"+sqlClientEndpoint.getConnectionURL());
        participant.getParticipantId().setFullName(endpointProvider.getParticipantId().getFullName() + "." + "SQL.Client."+sqlClientEndpoint.getDataSourceName());
        participant.getParticipantId().setVersion(endpointProvider.getParticipantId().getVersion());
        sqlClientEndpoint.setParticipant(participant);
       
        //
        // Add the endpoint to the provider
        endpointProvider.addEndpoint(sqlClientEndpoint.getComponentId());
        //
        // Add the endpoint to the topology cache
        getLogger().trace(".newSQLClientTopologyEndpoint(): Add the httpClient Port to the Topology Cache");
        getTopologyIM().addTopologyNode(endpointProvider.getComponentId(), sqlClientEndpoint);
        //
        // We're done
        getLogger().debug(".newSQLClientTopologyEndpoint(): Exit, endpoint added");
        return(sqlClientEndpoint);
    }
}
