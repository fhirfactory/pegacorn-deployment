/*
 * Copyright (c) 2020 Mark A. Hunter (ACT Health)
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
package net.fhirfactory.pegacorn.deployment.names;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PegacornCommunicateComponentNames {

    private static final String COMMUNICATE_IRIS_DEFAULT = "Communicate-Iris";
    private static final String COMMUNICATE_IRIS_EXTERNALISED_SERVICE_DEFAULT = "Communicate-Iris";
    private static final String COMMUNICATE_IRIS_SERVICE_DEFAULT = "Communicate-Iris";
    private static final String COMMUNICATE_IRIS_PROCESSING_PLANT_DEFAULT = "Communicate-Iris";
    private static final String IRIS_MATRIX_APPLICATION_SERVICES_RECEIVER_PORT_ENDPOINT_NAME = "Iris-MatrixAppServices-Receiver-Port";
    private static final String COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_ALL_FROM_LADON = "Port:Receive-FHIR-All-From-Ladon";
    private static final String COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_ALL_TO_LADON = "Port:Forward-FHIR-All-To-Ladon";

    private static final String COMMUNICATE_GRPSVR_DEFAULT = "Communicate-GrpSvr";
    private static final String COMMUNICATE_GRPSVR_EXTERNALISED_SERVICE_DEFAULT = "Communicate-GrpSvr";
    private static final String COMMUNICATE_GRPSVR_SERVICE_DEFAULT = "Communicate-GrpSvr";
    private static final String COMMUNICATE_GRPSVR_PROCESSING_PLANT_DEFAULT = "Communicate-GrpSvr";

    private static final String COMMUNICATE_VOIPBRIDGE_DEFAULT = "Communicate-VoIPBridge";
    private static final String COMMUNICATE_VOIPBRIDGE_EXTERNALISED_SERVICE_DEFAULT = "Communicate-VoIPBridge";
    private static final String COMMUNICATE_VOIPBRIDGE_SERVICE_DEFAULT = "Communicate-VoIPBridge";
    private static final String COMMUNICATE_VOIPBRIDGE_PROCESSING_PLANT_DEFAULT = "Communicate-VoIPBridge";

    private static final String COMMUNICATE_AVCONFSVR_DEFAULT = "Communicate-AVConfSvr";
    private static final String COMMUNICATE_AVCONFSVR_EXTERNALISED_SERVICE_DEFAULT = "Communicate-AVConfSvr";
    private static final String COMMUNICATE_AVCONFSVR_SERVICE_DEFAULT = "Communicate-ConfSvr";
    private static final String COMMUNICATE_AVCONFSVR_PROCESSING_PLANT_DEFAULT = "Communicate-ConfSvr";

    public static String getCommunicateIrisDefault() {
        return COMMUNICATE_IRIS_DEFAULT;
    }

    public static String getCommunicateIrisExternalisedServiceDefault() {
        return COMMUNICATE_IRIS_EXTERNALISED_SERVICE_DEFAULT;
    }

    public static String getCommunicateIrisServiceDefault() {
        return COMMUNICATE_IRIS_SERVICE_DEFAULT;
    }

    public static String getCommunicateIrisProcessingPlantDefault() {
        return COMMUNICATE_IRIS_PROCESSING_PLANT_DEFAULT;
    }

    public static String getIrisMatrixApplicationServicesReceiverPortEndpointName() {
        return IRIS_MATRIX_APPLICATION_SERVICES_RECEIVER_PORT_ENDPOINT_NAME;
    }

    public static String getCommunicateIrisEdgeReceiveFhirAllFromLadon() {
        return COMMUNICATE_IRIS_EDGE_RECEIVE_FHIR_ALL_FROM_LADON;
    }

    public static String getCommunicateIrisEdgeForwardFhirAllToLadon() {
        return COMMUNICATE_IRIS_EDGE_FORWARD_FHIR_ALL_TO_LADON;
    }

    public static String getCommunicateGrpsvrDefault() {
        return COMMUNICATE_GRPSVR_DEFAULT;
    }

    public static String getCommunicateGrpsvrExternalisedServiceDefault() {
        return COMMUNICATE_GRPSVR_EXTERNALISED_SERVICE_DEFAULT;
    }

    public static String getCommunicateGrpsvrServiceDefault() {
        return COMMUNICATE_GRPSVR_SERVICE_DEFAULT;
    }

    public static String getCommunicateGrpsvrProcessingPlantDefault() {
        return COMMUNICATE_GRPSVR_PROCESSING_PLANT_DEFAULT;
    }

    public static String getCommunicateVoipbridgeDefault() {
        return COMMUNICATE_VOIPBRIDGE_DEFAULT;
    }

    public static String getCommunicateVoipbridgeExternalisedServiceDefault() {
        return COMMUNICATE_VOIPBRIDGE_EXTERNALISED_SERVICE_DEFAULT;
    }

    public static String getCommunicateVoipbridgeServiceDefault() {
        return COMMUNICATE_VOIPBRIDGE_SERVICE_DEFAULT;
    }

    public static String getCommunicateVoipbridgeProcessingPlantDefault() {
        return COMMUNICATE_VOIPBRIDGE_PROCESSING_PLANT_DEFAULT;
    }

    public static String getCommunicateAvconfsvrDefault() {
        return COMMUNICATE_AVCONFSVR_DEFAULT;
    }

    public static String getCommunicateAvconfsvrExternalisedServiceDefault() {
        return COMMUNICATE_AVCONFSVR_EXTERNALISED_SERVICE_DEFAULT;
    }

    public static String getCommunicateAvconfsvrServiceDefault() {
        return COMMUNICATE_AVCONFSVR_SERVICE_DEFAULT;
    }

    public static String getCommunicateAvconfsvrProcessingPlantDefault() {
        return COMMUNICATE_AVCONFSVR_PROCESSING_PLANT_DEFAULT;
    }
}
