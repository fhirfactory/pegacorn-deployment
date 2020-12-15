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
public class PegacornLadon2HestiaEndpointNames {
    //
    // Ladon <--> Hestia-Audit Connectivity Endpoint Names
    //
    // FHIR::AuditEvent Reception (from Ladon --> Hestia DAM)
    private static final String ENDPOINT_LADON_EDGE_FORWARD_FHIR_AUDIT_EVENT_TO_HESTIA_AUDIT = "Port:Ladon-Forward-FHIR-AuditEvent-To-Hestia-Audit";
    private static final String ENDPOINT_HESTIA_AUDIT_EDGE_RECEIVE_FHIR_AUDIT_EVENT_FROM_LADON = "Port:Hestia-Audit-Receive-FHIR-AuditEvent-From-Ladon";
    // Petasos Finalisation
    private static final String ENDPOINT_LADON_PETASOS_WATCHDOG_FINALISATION_SERVICE_TO_HESTIA_AUDIT = "Port:Ladon-Petasos-WatchdogFinalisation-To-Hestia-Audit";
    private static final String ENDPOINT_LADON_PETASOS_WATCHDOG_FINALISATION_SERVICE_FROM_HESTIA_AUDIT = "Port:Ladon-Petasos-WatchdogFinalisation-From-Hestia-Audit";
    private static final String ENDPOINT_HESTIA_AUDIT_PETASOS_WATCHDOG_FINALISATION_SERVICE_TO_LADON = "Port:Hestia-Audit-Petasos-WatchdogFinalisation-To-Ladon";
    private static final String ENDPOINT_HESTIA_AUDIT_PETASOS_WATCHDOG_FINALISATION_SERVICE_FROM_LADON = "Port:Hestia-Audit-Petasos-WatchdogFinalisation-From-Ladon";
    // Petasos Heartbeat
    private static final String ENDPOINT_LADON_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_TO_HESTIA_AUDIT = "Port:Ladon-Petasos-WatchdogHeartbeat-To-Hestia-Audit";
    private static final String ENDPOINT_LADON_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_FROM_HESTIA_AUDIT = "Port:Ladon-Petasos-WatchdogHeartbeat-From-Hestia-Audit";
    private static final String ENDPOINT_HESTIA_AUDIT_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_TO_LADON = "Port:Hestia-Audit-Petasos-WatchdogHeartbeat-To-Ladon";
    private static final String ENDPOINT_HESTIA_AUDIT_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_FROM_LADON = "Port:Hestia-Audit-Petasos-WatchdogHeartbeat-From-Ladon";

    public static String getEndpointLadonEdgeForwardFhirAuditEventToHestiaAudit() {
        return ENDPOINT_LADON_EDGE_FORWARD_FHIR_AUDIT_EVENT_TO_HESTIA_AUDIT;
    }

    public static String getEndpointHestiaAuditEdgeReceiveFhirAuditEventFromLadon() {
        return ENDPOINT_HESTIA_AUDIT_EDGE_RECEIVE_FHIR_AUDIT_EVENT_FROM_LADON;
    }

    public static String getEndpointLadonPetasosWatchdogFinalisationServiceToHestiaAudit() {
        return ENDPOINT_LADON_PETASOS_WATCHDOG_FINALISATION_SERVICE_TO_HESTIA_AUDIT;
    }

    public static String getEndpointLadonPetasosWatchdogFinalisationServiceFromHestiaAudit() {
        return ENDPOINT_LADON_PETASOS_WATCHDOG_FINALISATION_SERVICE_FROM_HESTIA_AUDIT;
    }

    public static String getEndpointHestiaAuditPetasosWatchdogFinalisationServiceToLadon() {
        return ENDPOINT_HESTIA_AUDIT_PETASOS_WATCHDOG_FINALISATION_SERVICE_TO_LADON;
    }

    public static String getEndpointHestiaAuditPetasosWatchdogFinalisationServiceFromLadon() {
        return ENDPOINT_HESTIA_AUDIT_PETASOS_WATCHDOG_FINALISATION_SERVICE_FROM_LADON;
    }

    public static String getEndpointLadonPetasosWatchdogHeartbeatServiceToHestiaAudit() {
        return ENDPOINT_LADON_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_TO_HESTIA_AUDIT;
    }

    public static String getEndpointLadonPetasosWatchdogHeartbeatServiceFromHestiaAudit() {
        return ENDPOINT_LADON_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_FROM_HESTIA_AUDIT;
    }

    public static String getEndpointHestiaAuditPetasosWatchdogHeartbeatServiceToLadon() {
        return ENDPOINT_HESTIA_AUDIT_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_TO_LADON;
    }

    public static String getEndpointHestiaAuditPetasosWatchdogHeartbeatServiceFromLadon() {
        return ENDPOINT_HESTIA_AUDIT_PETASOS_WATCHDOG_HEARTBEAT_SERVICE_FROM_LADON;
    }
}
