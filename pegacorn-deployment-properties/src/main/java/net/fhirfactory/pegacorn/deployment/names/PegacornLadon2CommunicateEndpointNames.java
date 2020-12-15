/*
 * The MIT License
 *
 * Copyright 2020 Mark A. Hunter (ACT Health).
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
package net.fhirfactory.pegacorn.deployment.names;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PegacornLadon2CommunicateEndpointNames {
    //
    // Ladon <--> Communicate-Iris Connectivity Endpoint Names
    //
    // FHIR::Communication Messages
    private static final String ENDPOINT_LADON_EDGE_RECEIVE_FHIR_COMMUNICATION_FROM_COMMUNICATE_IRIS = "Port:Ladon-Receive-FHIR-Communication-From-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_COMMUNICATION_TO_LADON = "Port:Communicate-Iris-Forward-FHIR-Communication-To-Ladon";
    private static final String ENDPOINT_LADON_EDGE_FORWARD_FHIR_COMMUNICATION_TO_COMMUNICATE_IRIS = "Port:Ladon-Forward-FHIR-Communication-To-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_COMMUNICATION_FROM_LADON = "Port:Communicate-Iris-Receive-FHIR-Communication-From-Ladon";
    // FHIR::Group Messages
    private static final String ENDPOINT_LADON_EDGE_RECEIVE_FHIR_GROUP_FROM_COMMUNICATE_IRIS = "Port:Ladon-Receive-FHIR-Group-From-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_GROUP_TO_LADON = "Port:Communicate-Iris-Forward-FHIR-Group-To-Ladon";
    private static final String ENDPOINT_LADON_EDGE_FORWARD_FHIR_GROUP_TO_COMMUNICATE_IRIS = "Port:Ladon-Forward-FHIR-Group-To-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_GROUP_FROM_LADON = "Port:Communicate-Iris-Receive-FHIR-Group-From-Ladon";
    // FHIR::CareTeam Messages
    private static final String ENDPOINT_LADON_EDGE_RECEIVE_FHIR_CARE_TEAM_FROM_COMMUNICATE_IRIS = "Port:Ladon-Receive-FHIR-CareTeam-From-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_CARE_TEAM_TO_LADON = "Port:Communicate-Iris-Forward-FHIR-CareTeam-To-Ladon";
    private static final String ENDPOINT_LADON_EDGE_FORWARD_FHIR_CARE_TEAM_TO_COMMUNICATE_IRIS = "Port:Ladon-Forward-FHIR-CareTeam-To-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_CARE_TEAM_FROM_LADON = "Port:Communicate-Iris-Receive-FHIR-CareTeam-From-Ladon";
    // FHIR::Practitioner Messages
    private static final String ENDPOINT_LADON_EDGE_RECEIVE_FHIR_PRACTITIONER_FROM_COMMUNICATE_IRIS = "Port:Ladon-Receive-FHIR-Practitioner-From-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_PRACTITIONER_TO_LADON = "Port:Communicate-Iris-Forward-FHIR-Practitioner-To-Ladon";
    private static final String ENDPOINT_LADON_EDGE_FORWARD_FHIR_PRACTITIONER_TO_COMMUNICATE_IRIS = "Port:Ladon-Forward-FHIR-Practitioner-To-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_PRACTITIONER_FROM_LADON = "Port:Communicate-Iris-Receive-FHIR-Practitioner-From-Ladon";
    // FHIR::..other.. Messages
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_OTHER_FROM_LADON = "Port:Communicate-Iris-Receive-FHIR-Other-From-Ladon";
    private static final String ENDPOINT_LADON_EDGE_RECEIVE_FHIR_OTHER_FROM_COMMUNICATE_IRIS = "Port:Ladon-Receive-FHIR-Other-From-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_OTHER_TO_LADON = "Port:Communicate-Iris-Forward-FHIR-Other-To-Ladon";
    private static final String ENDPOINT_LADON_EDGE_FORWARD_FHIR_OTHER_TO_COMMUNICATE_IRIS = "Port:Ladon-Forward-FHIR-Other-To-Communicate-Iris";
    // Petasos Finalisation
    private static final String ENDPOINT_LADON_PETASOS_WATCHDOG_FINALISATION_SERVICE_TO_COMMUNICATE_IRIS = "Port:Ladon-Petasos-WatchdogFinalisation-To-Communicate-Iris";
    private static final String ENDPOINT_LADON_PETASOS_WATCHDOG_FINALISATION_SERVICE_FROM_COMMUNICATE_IRIS = "Port:Ladon-Petasos-WatchdogFinalisation-From-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_PETASOS_WATCHDOG_FINALISATION_SERVICE_TO_LADON = "Port:Communicate-Iris-Petasos-WatchdogFinalisation-To-Ladon";
    private static final String ENDPOINT_COMMUNICATE_IRIS_PETASOS_WATCHDOG_FINALISATION_SERVICE_FROM_LADON = "Port:Communicate-Iris-Petasos-WatchdogFinalisation-From-Ladon";
    // Petasos Heartbeat
    private static final String ENDPOINT_LADON_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_TO_COMMUNICATE_IRIS = "Port:Ladon-Petasos-WatchdogHeartbeat-To-Communicate-Iris";
    private static final String ENDPOINT_LADON_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_FROM_COMMUNICATE_IRIS = "Port:Ladon-Petasos-WatchdogHeartbeat-From-Communicate-Iris";
    private static final String ENDPOINT_COMMUNICATE_IRIS_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_TO_LADON = "Port:Communicate-Iris-Petasos-WatchdogHeartbeat-To-Ladon";
    private static final String ENDPOINT_COMMUNICATE_IRIS_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_FROM_LADON = "Port:Communicate-Iris-Petasos-WatchdogHeartbeat-From-Ladon";

    public static String getEndpointLadonEdgeReceiveFhirCommunicationFromCommunicateIris() {
        return ENDPOINT_LADON_EDGE_RECEIVE_FHIR_COMMUNICATION_FROM_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeForwardFhirCommunicationToLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_COMMUNICATION_TO_LADON;
    }

    public static String getEndpointLadonEdgeForwardFhirCommunicationToCommunicateIris() {
        return ENDPOINT_LADON_EDGE_FORWARD_FHIR_COMMUNICATION_TO_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeReceiveFhirCommunicationFromLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_COMMUNICATION_FROM_LADON;
    }

    public static String getEndpointLadonEdgeReceiveFhirGroupFromCommunicateIris() {
        return ENDPOINT_LADON_EDGE_RECEIVE_FHIR_GROUP_FROM_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeForwardFhirGroupToLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_GROUP_TO_LADON;
    }

    public static String getEndpointLadonEdgeForwardFhirGroupToCommunicateIris() {
        return ENDPOINT_LADON_EDGE_FORWARD_FHIR_GROUP_TO_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeReceiveFhirGroupFromLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_GROUP_FROM_LADON;
    }

    public static String getEndpointLadonEdgeReceiveFhirCareTeamFromCommunicateIris() {
        return ENDPOINT_LADON_EDGE_RECEIVE_FHIR_CARE_TEAM_FROM_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeForwardFhirCareTeamToLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_CARE_TEAM_TO_LADON;
    }

    public static String getEndpointLadonEdgeForwardFhirCareTeamToCommunicateIris() {
        return ENDPOINT_LADON_EDGE_FORWARD_FHIR_CARE_TEAM_TO_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeReceiveFhirCareTeamFromLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_CARE_TEAM_FROM_LADON;
    }

    public static String getEndpointLadonEdgeReceiveFhirPractitionerFromCommunicateIris() {
        return ENDPOINT_LADON_EDGE_RECEIVE_FHIR_PRACTITIONER_FROM_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeForwardFhirPractitionerToLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_PRACTITIONER_TO_LADON;
    }

    public static String getEndpointLadonEdgeForwardFhirPractitionerToCommunicateIris() {
        return ENDPOINT_LADON_EDGE_FORWARD_FHIR_PRACTITIONER_TO_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeReceiveFhirPractitionerFromLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_PRACTITIONER_FROM_LADON;
    }

    public static String getEndpointCommunicateIrisEdgeReceiveFhirOtherFromLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_OTHER_FROM_LADON;
    }

    public static String getEndpointLadonEdgeReceiveFhirOtherFromCommunicateIris() {
        return ENDPOINT_LADON_EDGE_RECEIVE_FHIR_OTHER_FROM_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisEdgeForwardFhirOtherToLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_OTHER_TO_LADON;
    }

    public static String getEndpointLadonEdgeForwardFhirOtherToCommunicateIris() {
        return ENDPOINT_LADON_EDGE_FORWARD_FHIR_OTHER_TO_COMMUNICATE_IRIS;
    }

    public static String getEndpointLadonPetasosWatchdogFinalisationServiceToCommunicateIris() {
        return ENDPOINT_LADON_PETASOS_WATCHDOG_FINALISATION_SERVICE_TO_COMMUNICATE_IRIS;
    }

    public static String getEndpointLadonPetasosWatchdogFinalisationServiceFromCommunicateIris() {
        return ENDPOINT_LADON_PETASOS_WATCHDOG_FINALISATION_SERVICE_FROM_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisPetasosWatchdogFinalisationServiceToLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_PETASOS_WATCHDOG_FINALISATION_SERVICE_TO_LADON;
    }

    public static String getEndpointCommunicateIrisPetasosWatchdogFinalisationServiceFromLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_PETASOS_WATCHDOG_FINALISATION_SERVICE_FROM_LADON;
    }

    public static String getEndpointLadonPetasosWatchdogHeartbeatServiceToCommunicateIris() {
        return ENDPOINT_LADON_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_TO_COMMUNICATE_IRIS;
    }

    public static String getEndpointLadonPetasosWatchdogHeartbeatServiceFromCommunicateIris() {
        return ENDPOINT_LADON_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_FROM_COMMUNICATE_IRIS;
    }

    public static String getEndpointCommunicateIrisPetasosWatchdogHeartbeatServiceToLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_TO_LADON;
    }

    public static String getEndpointCommunicateIrisPetasosWatchdogHeartbeatServiceFromLadon() {
        return ENDPOINT_COMMUNICATE_IRIS_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_FROM_LADON;
    }
}
