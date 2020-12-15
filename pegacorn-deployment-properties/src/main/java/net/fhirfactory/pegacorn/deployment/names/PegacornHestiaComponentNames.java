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
public class PegacornHestiaComponentNames {

    private static final String HESTIA_AUDIT_SUBSYSTEM = "Hestia-Audit";
    private static final String HESTIA_AUDIT_SUBSYSTEM_VERSION = "1.0.0";
    private static final String HESTIA_AUDIT_EXTERNALISED_SERVICE = "Hestia-Audit";
    private static final String HESTIA_AUDIT_SERVICE = "Hestia-Audit";
    private static final String HESTIA_AUDIT_PROCESSING_PLANT = "Hestia-Audit";
    private static final String HESTIA_AUDIT_PROCESSING_PLANT_VERSION = "1.0.0";
    private static final String HESTIA_AUDIT_AUDIT_EVENT_ENDPOINT_FHIR_API = "Pegacorn-Hestia-Audit-AuditEvent-API";

    private static final String HESTIA_DAM_SUBSYSTEM = "Hestia-DAM";
    private static final String HESTIA_DAM_SUBSYSTEM_VERSION = "1.0.0";
    private static final String HESTIA_DAM_EXTERNALISED_SERVICE = "Hestia-DAM";
    private static final String HESTIA_DAM_SERVICE = "Hestia-DAM";
    private static final String HESTIA_DAM_PROCESSING_PLANT = "Hestia-DAM";
    private static final String HESTIA_DAM_PROCESSING_PLANT_VERSION = "1.0.0";
    private static final String HESTIA_DAM_MEDIA_ENDPOINT_FHIR_API = "Pegacorn-Hestia-DAM-Media-API";

    private static final String HESTIA_DATASWAMP_SUBSYSTEM = "Hestia-DataSwamp";
    private static final String HESTIA_DATASWAMP_SUBSYSTEM_VERSION = "1.0.0";
    private static final String HESTIA_DATASWAMP_EXTERNALISED_SERVICE = "Hestia-DataSwamp";
    private static final String HESTIA_DATASWAMP_SERVICE = "Hestia-DataSwamp";
    private static final String HESTIA_DATASWAMP_PROCESSING_PLANT = "Hestia-DataSwamp";
    private static final String HESTIA_DATASWAMP_PROCESSING_PLANT_VERSION = "1.0.0";
    private static final String HESTIA_DATASWAMP_ENDPOINT_FHIR_API = "Pegacorn-Hestia-DataSwamp-General-API";

    public static String getHestiaAuditSubsystemVersion() {
        return HESTIA_AUDIT_SUBSYSTEM_VERSION;
    }

    public static String getHestiaAuditProcessingPlantVersion() {
        return HESTIA_AUDIT_PROCESSING_PLANT_VERSION;
    }

    public static String getHestiaAuditAuditEventEndpointFhirApi() {
        return HESTIA_AUDIT_AUDIT_EVENT_ENDPOINT_FHIR_API;
    }

    public static String getHestiaDamSubsystemVersion() {
        return HESTIA_DAM_SUBSYSTEM_VERSION;
    }

    public static String getHestiaDamProcessingPlantVersion() {
        return HESTIA_DAM_PROCESSING_PLANT_VERSION;
    }

    public static String getHestiaDamMediaEndpointFhirApi() {
        return HESTIA_DAM_MEDIA_ENDPOINT_FHIR_API;
    }

    public static String getHestiaDataswampSubsystemVersion() {
        return HESTIA_DATASWAMP_SUBSYSTEM_VERSION;
    }

    public static String getHestiaDataswampProcessingPlantVersion() {
        return HESTIA_DATASWAMP_PROCESSING_PLANT_VERSION;
    }

    public static String getHestiaDataswampEndpointFhirApi() {
        return HESTIA_DATASWAMP_ENDPOINT_FHIR_API;
    }

    public static String getHestiaAuditSubsystem() {
        return HESTIA_AUDIT_SUBSYSTEM;
    }

    public static String getHestiaAuditExternalisedService() {
        return HESTIA_AUDIT_EXTERNALISED_SERVICE;
    }

    public static String getHestiaAuditService() {
        return HESTIA_AUDIT_SERVICE;
    }

    public static String getHestiaAuditProcessingPlant() {
        return HESTIA_AUDIT_PROCESSING_PLANT;
    }

    public static String getHestiaDamSubsystem() {
        return HESTIA_DAM_SUBSYSTEM;
    }

    public static String getHestiaDamExternalisedService() {
        return HESTIA_DAM_EXTERNALISED_SERVICE;
    }

    public static String getHestiaDamService() {
        return HESTIA_DAM_SERVICE;
    }

    public static String getHestiaDamProcessingPlant() {
        return HESTIA_DAM_PROCESSING_PLANT;
    }

    public static String getHestiaDataswampSubsystem() {
        return HESTIA_DATASWAMP_SUBSYSTEM;
    }

    public static String getHestiaDataswampExternalisedService() {
        return HESTIA_DATASWAMP_EXTERNALISED_SERVICE;
    }

    public static String getHestiaDataswampService() {
        return HESTIA_DATASWAMP_SERVICE;
    }

    public static String getHestiaDataswampProcessingPlant() {
        return HESTIA_DATASWAMP_PROCESSING_PLANT;
    }
}
