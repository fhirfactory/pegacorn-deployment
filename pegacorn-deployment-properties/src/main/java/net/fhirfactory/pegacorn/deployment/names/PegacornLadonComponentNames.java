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
public class PegacornLadonComponentNames {
    private static final String LADON_SUBSYSTEM_DEFAULT = "Ladon";
    private static final String LADON_SUBSYSTEM_VERSION = "1.0.0";
    private static final String LADON_EXTERNALISED_SERVICE_DEFAULT = "Ladon";
    private static final String LADON_SERVICE_DEFAULT = "Ladon-Core";
    private static final String LADON_PROCESSING_PLANT_DEFAULT = "Ladon-Core";
    private static final String LADON_PROCESSING_PLANT_VERSION = "1.0.0";

    private static final String LADON_EDGE_RECEIVE_FHIR_ALL_FROM_IRIS = "Port:Receive-FHIR-All-From-Iris";
    private static final String LADON_EDGE_FORWARD_FHIR_ALL_TO_IRIS = "Port:Forward-FHIR-All-To-Iris";

    private static final String EDGE_ANSWER_ENDPOINT_NAME = "Port:Edge-Answer";

    private static final String PETASOS_WATCHDOG_FINALISATION_SERVICE = "Port:Petasos-Watchdog-Finalisation-Service";
    private static final String PETASOS_WATCHDOG_HEARTBEAT_SERVICE = "Port:Petasos-Watchdog-Heartbeat-Service";

    public String getLadonEdgeReceiveFhirAllFromIris() {
        return LADON_EDGE_RECEIVE_FHIR_ALL_FROM_IRIS;
    }

    public String getLadonEdgeForwardFhirAllToIris() {
        return LADON_EDGE_FORWARD_FHIR_ALL_TO_IRIS;
    }


    public String getPetasosWatchdogFinalisationService() {
        return PETASOS_WATCHDOG_FINALISATION_SERVICE;
    }

    public String getPetasosWatchdogHeartbeatService() {
        return PETASOS_WATCHDOG_HEARTBEAT_SERVICE;
    }

    public String getLadonProcessingPlantVersion() {
        return LADON_PROCESSING_PLANT_VERSION;
    }

    public String getLadonSubsystemDefault() {
        return LADON_SUBSYSTEM_DEFAULT;
    }

    public String getLadonExternalisedServiceDefault() {
        return LADON_EXTERNALISED_SERVICE_DEFAULT;
    }

    public String getLadonServiceDefault() {
        return LADON_SERVICE_DEFAULT;
    }

    public String getLadonProcessingPlantDefault() {
        return LADON_PROCESSING_PLANT_DEFAULT;
    }

    public static String getEdgeAnswerEndpointName() {
        return EDGE_ANSWER_ENDPOINT_NAME;
    }


}
