/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deploymentproperties;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Mark A. Hunter (ACT Health)
 */
@ApplicationScoped
public class LadonProperties {

    /*
 * The following (JMS) queues need to be added to the Wildfly configuration 
    <jms-queue name="FHIR.raw.Communication" entries="jms:topic:pegacorn.FHIR.raw.Communication"/>
    <jms-queue name="FHIR.raw.Communication2Practitioner" entries="jms:topic:pegacorn.FHIR.raw.CommunicationToAPractitioner"/>
    <jms-queue name="FHIR.raw.Communication2Group" entries="jms:topic:pegacorn.FHIR.raw.CommunicationToAGroup"/>
    <jms-queue name="FHIR.raw.Communication2PractitionerRole" entries="jms:topic:pegacorn.FHIR.raw.CommunicationToAPractitionerRole"/>
    <jms-queue name="FHIR.raw.Communication2CareTeam" entries="jms:topic:pegacorn.FHIR.raw.CommunicationToACareTeam"/>
    <jms-queue name="FHIR.raw.Communication2Organization" entries="jms:topic:pegacorn.FHIR.raw.CommunicationToAOrganization"/>
    <jms-queue name="Communicate.raw.RoomServerMatrixMessages" entries="pegacorn.Communicate.raw.RoomServerMatrixMessages"/>
    <jms-queue name="FHIR.raw.Group" entries="jms:topic:pegacorn.FHIR.raw.Group"/>
    <jms-queue name="FHIR.raw.CareTeam" entries="jms:topic:pegacorn.FHIR.raw.CareTeam"/>
    <jms-queue name="FHIR.raw.Practitioner" entries="jms:topic:pegacorn.FHIR.raw.Practitioner"/>
    <jms-queue name="FHIR.raw.PractitionerRole" entries="jms:topic:pegacorn.FHIR.raw.PractitionerRole"/>
    <jms-queue name="FHIR.raw.Organization" entries="jms:topic:pegacorn.FHIR.raw.Organization"/>
    <jms-queue name="FHIR.raw.Task" entries="jms:topic:pegacorn.FHIR.raw.Task"/>
    <jms-queue name="FHIR.raw.EndPoint" entries="jms:topic:pegacorn.FHIR.raw.EndPoint"/>
    <jms-queue name="FHIR.raw.Location" entries="jms:topic:pegacorn.FHIR.raw.Location"/>
    <jms-queue name="FHIR.normalised.Group" entries="jms:topic:pegacorn.FHIR.normalised.Group"/>
    <jms-queue name="FHIR.normalised.CareTeam" entries="jms:topic:pegacorn.FHIR.normalised.CareTeam"/>
    <jms-queue name="FHIR.normalised.rawPractitioner" entries="jms:topic:pegacorn.FHIR.normalised.Practitioner"/>
    <jms-queue name="FHIR.normalised.rawPractitionerRole" entries="jms:topic:pegacorn.FHIR.normalised.PractitionerRole"/>
    <jms-queue name="FHIR.normalised.Organization" entries="jms:topic:pegacorn.FHIR.normalised.Organization"/>
    <jms-queue name="FHIR.normalised.Task" entries="jms:topic:pegacorn.FHIR.normalised.Task"/>
    <jms-queue name="FHIR.normalised.EndPoint" entries="jms:topic:pegacorn.FHIR.normalised.EndPoint"/>
    <jms-queue name="FHIR.normalised.Location" entries="jms:topic:pegacorn.FHIR.normalised.Location"/>
    <jms-queue name="FHIR.normalised.Patient" entries="jms:topic:pegacorn.FHIR.normalised.Patient"/>
     */
    private static final String TOPIC_RAW_COMM_TO_PRACT = "jms:topic:pegacorn.FHIR.raw.CommunicationToAPractitioner";
    private static final String TOPIC_RAW_COM_TO_PRACTROLE = "jms:topic:pegacorn.FHIR.raw.CommunicationToAPractitionerRole";
    private static final String TOPIC_RAW_COMM_TO_CARETEAM = "jms:topic:pegacorn.FHIR.raw.CommunicationToACareTeam";
    private static final String TOPIC_RAW_COMM_TO_ORGANIZATION = "jms:topic:pegacorn.FHIR.raw.CommunicationToAnOrganization";
    private static final String TOPIC_RAW_COMM_TO_GROUP = "jms:topic:pegacorn.FHIR.raw.CommunicationToAGroup";
    private static final String TOPIC_RAW_COMM_TO_ENDPOINT = "jms:topic:pegacorn.FHIR.raw.CommunicationToAEndPoint";
    private static final String TOPIC_RAW_COMMUNICATION = "jms:topic:pegacorn.FHIR.raw.Communication";
    private static final String TOPIC_RAW_GROUP = "jms:topic:pegacorn.FHIR.raw.Group";
    private static final String TOPIC_RAW_CARETEAM = "jms:topic:pegacorn.FHIR.raw.CareTeam";
    private static final String TOPIC_RAW_PATIENT = "jms:topic:pegacorn.FHIR.raw.Patient";
    private static final String TOPIC_RAW_PRACTITIONER = "jms:topic:pegacorn.FHIR.raw.Practitioner";
    private static final String TOPIC_RAW_PRACTITIONERROLE = "jms:topic:pegacorn.FHIR.raw.PractitionerRole";
    private static final String TOPIC_RAW_ORGANIZATION = "jms:topic:pegacorn.FHIR.raw.Organization";
    private static final String TOPIC_RAW_TASK = "jms:topic:pegacorn.FHIR.raw.Task";
    private static final String TOPIC_RAW_ENDPOINT = "jms:topic:pegacorn.FHIR.raw.EndPoint";
    private static final String TOPIC_RAW_LOCATION = "jms:topic:pegacorn.FHIR.raw.Location";

    private static final String TOPIC_NORMALISED_COMMUNICATION = "jms:topic:pegacorn.FHIR.normalised.Communication";
    private static final String TOPIC_NORMALISED_GROUP = "jms:topic:pegacorn.FHIR.normalised.Group";
    private static final String TOPIC_NORMALISED_CARETEAM = "jms:topic:pegacorn.FHIR.normalised.CareTeam";
    private static final String TOPIC_NORMALISED_PATIENT = "jms:topic:pegacorn.FHIR.normalised.Patient";
    private static final String TOPIC_NORMALISED_PRACTITIONER = "jms:topic:pegacorn.FHIR.normalised.Practitioner";
    private static final String TOPIC_NORMALISED_PRACTITIONERROLE = "jms:topic:pegacorn.FHIR.normalised.PractitionerRole";
    private static final String TOPIC_NORMALISED_ORGANIZATION = "jms:topic:pegacorn.FHIR.normalised.Organization";
    private static final String TOPIC_NORMALISED_TASK = "jms:topic:pegacorn.FHIR.normalised.Task";
    private static final String TOPIC_NORMALISED_ENDPOINT = "jms:topic:pegacorn.FHIR.normalised.EndPoint";
    private static final String TOPIC_NORMALISED_LOCATION = "jms:topic:pegacorn.FHIR.normalised.Location";

    private static final String ENDPOINT_INGRES_RAW_COMM_TO_PRACT = "netty-http:http://10.111.111.102:50001/pegacorn/ladon/raw/CommToPactitioners";
    private static final String ENDPOINT_INGRES_RAW_COMM_TO_PRACTROLE = "netty-http:http://10.111.111.102:50002/pegacorn/ladon/raw/CommToPactitionerRoles";
    private static final String ENDPOINT_INGRES_RAW_COMM_TO_CARETEAM = "netty-http:http://10.111.111.102:50003/pegacorn/ladon/raw/CommToCareTeams";
    private static final String ENDPOINT_INGRES_RAW_COMM_TO_ORGANIZATION = "netty-http:http://10.111.111.102:50004/pegacorn/ladon/raw/CommToOrganizations";
    private static final String ENDPOINT_INGRES_RAW_COMM_TO_GROUP = "netty-http:http://10.111.111.102:50005/pegacorn/ladon/raw/CommToPactitionerGroups";
    private static final String ENDPOINT_INGRES_RAW_COMM_TO_ENDPOINT = "netty-http:http://10.111.111.102:50005/pegacorn/ladon/raw/CommToEndPoint";

    private static final String ENDPOINT_INGRES_RAW_COMMUNICATION = "netty-http:http://10.111.111.102:50010/pegacorn/ladon/raw/Communication";
    private static final String ENDPOINT_INGRES_RAW_GROUP = "netty-http:http://10.111.111.102:50011/pegacorn/ladon/raw/Group";
    private static final String ENDPOINT_INGRES_RAW_CARETEAM = "netty-http:http://10.111.111.102:50012/pegacorn/ladon/raw/CareTeam";
    private static final String ENDPOINT_INGRES_RAW_PATIENT = "netty-http:http://10.111.111.102:50012/pegacorn/ladon/raw/Patient";
    private static final String ENDPOINT_INGRES_RAW_PRACTITIONER = "netty-http:http://10.111.111.102:50013/pegacorn/ladon/raw/Practitioner";
    private static final String ENDPOINT_INGRES_RAW_PRACTITIONERROLE = "netty-http:http://10.111.111.102:50014/pegacorn/ladon/raw/PractitionerRole";
    private static final String ENDPOINT_INGRES_RAW_ORGANIZATION = "netty-http:http://10.111.111.102:50015/pegacorn/ladon/raw/Organization";
    private static final String ENDPOINT_INGRES_RAW_TASK = "netty-http:http://10.111.111.102:50016/pegacorn/ladon/raw/Task";
    private static final String ENDPOINT_INGRES_RAW_ENDPOINT = "netty-http:http://10.111.111.102:50017/pegacorn/ladon/raw/EndPoint";
    private static final String ENDPOINT_INGRES_RAW_LOCATION = "netty-http:http://10.111.111.102:50018/pegacorn/ladon/raw/Location";

    public String getEndPointRawCommunicationToPractitioner() {
        return (ENDPOINT_INGRES_RAW_COMM_TO_PRACT);
    }

    public String getEndPointRawCommunicationToPractitionerRole() {
        return (ENDPOINT_INGRES_RAW_COMM_TO_PRACTROLE);
    }

    public String getEndPointRawCommunicationToCareTeam() {
        return (ENDPOINT_INGRES_RAW_COMM_TO_CARETEAM);
    }

    public String getEndPointRawCommunicationToEndPoint() {
        return (ENDPOINT_INGRES_RAW_COMM_TO_ENDPOINT);
    }

    public String getEndPointRawCommunicationToOrganization() {
        return (ENDPOINT_INGRES_RAW_COMM_TO_ORGANIZATION);
    }

    public String getEndPointRawCommunicationToGroup() {
        return (ENDPOINT_INGRES_RAW_COMM_TO_GROUP);
    }

    public String getRawCommunication2PractitionersTopic() {
        return (TOPIC_RAW_COMM_TO_PRACT);
    }

    public String getRawCommunication2EndPointTopic() {
        return (TOPIC_RAW_COMM_TO_ENDPOINT);
    }

    public String getEndPointRawCommunication() {
        return (ENDPOINT_INGRES_RAW_COMMUNICATION);
    }

    public String getEndPointRawGroup() {
        return (ENDPOINT_INGRES_RAW_GROUP);
    }

    public String getEndPointRawCareTeam() {
        return (ENDPOINT_INGRES_RAW_CARETEAM);
    }

    public String getEndPointRawPractitioner() {
        return (ENDPOINT_INGRES_RAW_PRACTITIONER);
    }

    public String getEndPointRawPractitionerRole() {
        return (ENDPOINT_INGRES_RAW_PRACTITIONERROLE);
    }

    public String getEndPointRawOrganization() {
        return (ENDPOINT_INGRES_RAW_ORGANIZATION);
    }

    public String getEndPointRawTask() {
        return (ENDPOINT_INGRES_RAW_TASK);
    }

    public String getEndPointRawEndPoint() {
        return (ENDPOINT_INGRES_RAW_ENDPOINT);
    }

    public String getEndPointRawLocation() {
        return (ENDPOINT_INGRES_RAW_LOCATION);
    }

    public String getEndPointRawPatient() {
        return (ENDPOINT_INGRES_RAW_PATIENT);
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

    public String getRawPatientTopic() {
        return (TOPIC_RAW_PATIENT);
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
