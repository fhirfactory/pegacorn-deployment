/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deploymentproperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/*
 * The following (JMS) queues need to be added to the Wildfly configuration 
    <jms-queue name="FHIR.Message.Raw.CommunicationBundle" entries="jms:topic:pegacorn.FHIR.message.raw.CommunicationBundle"/>
    <jms-queue name="FHIR.Message.Raw.Communication2PractitionerBundle" entries="jms:topic:pegacorn.FHIR.message.raw.CommunicationToAPractitionerBundle"/>
    <jms-queue name="FHIR.Message.Raw.Communication2GroupBundle" entries="jms:topic:pegacorn.FHIR.message.raw.CommunicationToAGroupBundle"/>
    <jms-queue name="FHIR.Message.Raw.Communication2PractitionerRoleBundle" entries="jms:topic:pegacorn.FHIR.message.raw.CommunicationToAPractitionerRoleBundle"/>
    <jms-queue name="FHIR.Message.Raw.Communication2CareTeamBundle" entries="jms:topic:pegacorn.FHIR.message.raw.CommunicationToACareTeamBundle"/>
    <jms-queue name="FHIR.Message.Raw.Communication2OrganizationBundle" entries="jms:topic:pegacorn.FHIR.message.raw.CommunicationToAOrganizationBundle"/>
    <jms-queue name="CommunicateProperties.raw.RoomServerMatrixMessages" entries="pegacorn.CommunicateProperties.raw.RoomServerMatrixMessagesBundle"/>
    <jms-queue name="FHIR.Message.Raw.GroupBundle" entries="jms:topic:pegacorn.FHIR.message.raw.GroupBundle"/>
    <jms-queue name="FHIR.Message.Raw.CareTeamBundle" entries="jms:topic:pegacorn.FHIR.message.raw.CareTeamBundle"/>
    <jms-queue name="FHIR.Message.Raw.PractitionerBunndle" entries="jms:topic:pegacorn.FHIR.message.raw.PractitionerBundle"/>
    <jms-queue name="FHIR.Message.Raw.PractitionerRoleBundle" entries="jms:topic:pegacorn.FHIR.message.raw.PractitionerRoleBundle"/>
    <jms-queue name="FHIR.Message.Raw.OrganizationBundle" entries="jms:topic:pegacorn.FHIR.message.raw.OrganizationBundle"/>
    <jms-queue name="FHIR.Message.Raw.TaskBundle" entries="jms:topic:pegacorn.FHIR.message.raw.TaskBundle"/>
    <jms-queue name="FHIR.Message.Raw.EndPointBundle" entries="jms:topic:pegacorn.FHIR.message.raw.EndPointBundle"/>
    <jms-queue name="FHIR.Message.Raw.LocationBundle" entries="jms:topic:pegacorn.FHIR.message.raw.LocationBundle"/>
    <jms-queue name="FHIR.Message.Normalised.GroupBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.GroupBundle"/>
    <jms-queue name="FHIR.Message.Normalised.CareTeamBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.CareTeamBundle"/>
    <jms-queue name="FHIR.Message.Normalised.rawPractitionerBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.PractitionerBundle"/>
    <jms-queue name="FHIR.Message.Normalised.rawPractitionerRoleBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.PractitionerRoleBundle"/>
    <jms-queue name="FHIR.Message.Normalised.OrganizationBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.OrganizationBundle"/>
    <jms-queue name="FHIR.Message.Normalised.TaskBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.TaskBundle"/>
    <jms-queue name="FHIR.Message.Normalised.EndPointBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.EndPointBundle"/>
    <jms-queue name="FHIR.Message.Normalised.LocationBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.LocationBundle"/>
    <jms-queue name="FHIR.Message.Normalised.PatientBundle" entries="jms:topic:pegacorn.FHIR.message.normalised.PatientBundleBundle"/>
 */
/**
 *
 * @author Mark A. Hunter (ACT Health)
 */

@ApplicationScoped
public class CommunicateProperties {
    
    @Inject
    PegacornCommonProperties siteCommonProperties;

    // Site Details
    private static String ROOMSERVER_IDENTITY_PREFIX = "CHS";

    private static final String RMSVR2IRIS_ENDPOINT_ON_IRIS_RESOURCE = "/transactions/{id}";
    private static final String RMSVR2IRIS_ENDPOINT_ON_IRIS_PORT = "12001";
    private static final String MSVR2IRIS_ENDPOINT_ON_RMSVR_CONNECTION = "netty-http:http://";
    private static final String MSVR2IRIS_ENDPOINT_ON_RMSVR_PORT = "40101";
    
    private static final String IRIS_COMMUNICATION_BUNDLE_PORT = "30101";
    private static final String IRIS_COMMUNICATION_BUNDLE_RESOURCE = "/fhir/r1/CommunicateBundle/";

    private static final String IRIS_GROUP_BUNDLE_PORT = "30102";
    private static final String IRIS_GROUP_BUNDLE_RESOURCE = "/fhir/r1/GroupBundle/";
    
    private static final String TOPIC_RAW_COMM_TO_CARETEAM      = "jms:topic:pegacorn.FHIR.message.raw.CommunicationToACareTeamBundle";
    private static final String TOPIC_RAW_COMM_TO_ENDPOINT      = "jms:topic:pegacorn.FHIR.message.raw.CommunicationToAEndPointBundle";
    private static final String TOPIC_RAW_COMM_TO_GROUP         = "jms:topic:pegacorn.FHIR.message.raw.CommunicationToAGroupBundle";
    private static final String TOPIC_RAW_COMM_TO_ORGANIZATION  = "jms:topic:pegacorn.FHIR.message.raw.CommunicationToAnOrganizationBundle";
    private static final String TOPIC_RAW_COMM_TO_PRACT         = "jms:topic:pegacorn.FHIR.message.raw.CommunicationToAPractitionerBundle";
    private static final String TOPIC_RAW_COM_TO_PRACTROLE      = "jms:topic:pegacorn.FHIR.message.raw.CommunicationToAPractitionerRoleBundle";

    private static final String TOPIC_RAW_CARETEAM          = "jms:topic:pegacorn.FHIR.message.raw.CareTeamBundle";
    private static final String TOPIC_RAW_COMMUNICATION     = "jms:topic:pegacorn.FHIR.message.raw.CommunicationBundle";
    private static final String TOPIC_RAW_ENDPOINT          = "jms:topic:pegacorn.FHIR.message.raw.EndPointBundle";
    private static final String TOPIC_RAW_GROUP             = "jms:topic:pegacorn.FHIR.message.raw.GroupBundle";
    private static final String TOPIC_RAW_LOCATION          = "jms:topic:pegacorn.FHIR.message.raw.LocationBundle";
    private static final String TOPIC_RAW_ORGANIZATION      = "jms:topic:pegacorn.FHIR.message.raw.OrganizationBundle";
    private static final String TOPIC_RAW_PRACTITIONER      = "jms:topic:pegacorn.FHIR.message.raw.PractitionerBundle";
    private static final String TOPIC_RAW_PRACTITIONERROLE  = "jms:topic:pegacorn.FHIR.message.raw.PractitionerRoleBundle";
    private static final String TOPIC_RAW_TASK              = "jms:topic:pegacorn.FHIR.message.raw.TaskBundle";
    
    private static final String TOPIC_NORMALISED_CARETEAM           = "jms:topic:pegacorn.FHIR.message.normalised.CareTeamBundle";
    private static final String TOPIC_NORMALISED_COMMUNICATION      = "jms:topic:pegacorn.FHIR.message.normalised.CommunicationBundle";
    private static final String TOPIC_NORMALISED_ENDPOINT           = "jms:topic:pegacorn.FHIR.message.normalised.EndPointBundle";
    private static final String TOPIC_NORMALISED_GROUP              = "jms:topic:pegacorn.FHIR.message.normalised.GroupBundle";
    private static final String TOPIC_NORMALISED_LOCATION           = "jms:topic:pegacorn.FHIR.message.normalised.LocationBundle";
    private static final String TOPIC_NORMALISED_ORGANIZATION       = "jms:topic:pegacorn.FHIR.message.normalised.OrganizationBundle";
    private static final String TOPIC_NORMALISED_PRACTITIONER       = "jms:topic:pegacorn.FHIR.message.normalised.PractitionerBundle";
    private static final String TOPIC_NORMALISED_PRACTITIONERROLE   = "jms:topic:pegacorn.FHIR.message.normalised.PractitionerRoleBundle";
    private static final String TOPIC_NORMALISED_TASK               = "jms:topic:pegacorn.FHIR.message.normalised.TaskBundle";


    public String getRoomServerIdentityPrefix() {
        return (ROOMSERVER_IDENTITY_PREFIX);
    }

    public String getIrisEndPointForIncomingCommunicationBundle() {
        return ("netty-http:http://" + siteCommonProperties.getPegacornCommunicateIrisHost() + ":" + IRIS_COMMUNICATION_BUNDLE_PORT + IRIS_COMMUNICATION_BUNDLE_RESOURCE);
    }    
    
    public String getIrisEndPointForIncomingGroupBundle() {
        return ("netty-http:http://" + siteCommonProperties.getPegacornCommunicateIrisHost() + ":" + IRIS_GROUP_BUNDLE_PORT + IRIS_GROUP_BUNDLE_RESOURCE);
    }       
    
    public String getIrisEndPointForRoomServerEvent() {
        return ("netty-http:http://" + siteCommonProperties.getPegacornCommunicateIrisHost() + ":" + RMSVR2IRIS_ENDPOINT_ON_IRIS_PORT + RMSVR2IRIS_ENDPOINT_ON_IRIS_RESOURCE);
    }
    
    public String getRoomServerEndPointForIrisEvent(){
        return(MSVR2IRIS_ENDPOINT_ON_RMSVR_CONNECTION + siteCommonProperties.getPegacornCommunicateRoomServerHost() + ":" + MSVR2IRIS_ENDPOINT_ON_RMSVR_PORT + "/");
    }

    public String getRawCommunication2PractitionersTopic() {
        return (TOPIC_RAW_COMM_TO_PRACT);
    }

    public String getRawCommunication2PractitionerRolesTopic() {
        return (TOPIC_RAW_COM_TO_PRACTROLE);
    }

    public String getRawCommunication2CareTeamsTopic() {
        return (TOPIC_RAW_COMM_TO_CARETEAM);
    }

    public String getRawCommunication2OrganizationsTopic() {
        return (TOPIC_RAW_COMM_TO_ORGANIZATION);
    }

    public String getRawCommunication2GroupsTopic() {
        return (TOPIC_RAW_COMM_TO_GROUP);
    }

    public String getRawCommunicationTopic() {
        return (TOPIC_RAW_COMMUNICATION);
    }

    public String getRawGroupTopic() {
        return (TOPIC_RAW_GROUP);
    }

    public String getRawCareTeamTopic() {
        return (TOPIC_RAW_CARETEAM);
    }

    public String getRawPractitionerTopic() {
        return (TOPIC_RAW_PRACTITIONER);
    }

    public String getRawPractitionerRoleTopic() {
        return (TOPIC_RAW_PRACTITIONERROLE);
    }

    public String getRawOrganizationTopic() {
        return (TOPIC_RAW_ORGANIZATION);
    }

    public String getRawTaskTopic() {
        return (TOPIC_RAW_TASK);
    }

    public String getRawEndPointTopic() {
        return (TOPIC_RAW_ENDPOINT);
    }

    public String getRawLocationTopic() {
        return (TOPIC_RAW_LOCATION);
    }
    
    public String getCommunicationTopic() {
        return (TOPIC_NORMALISED_COMMUNICATION);
    }

    public String getGroupTopic() {
        return (TOPIC_NORMALISED_GROUP);
    }

    public String getCareTeamTopic() {
        return (TOPIC_NORMALISED_CARETEAM);
    }

    public String getPractitionerTopic() {
        return (TOPIC_NORMALISED_PRACTITIONER);
    }

    public String getPractitionerRoleTopic() {
        return (TOPIC_NORMALISED_PRACTITIONERROLE);
    }

    public String getOrganizationTopic() {
        return (TOPIC_NORMALISED_ORGANIZATION);
    }

    public String getTaskTopic() {
        return (TOPIC_NORMALISED_TASK);
    }

    public String getEndPointTopic() {
        return (TOPIC_RAW_ENDPOINT);
    }

    public String getLocationTopic() {
        return (TOPIC_RAW_LOCATION);
    }    
}
