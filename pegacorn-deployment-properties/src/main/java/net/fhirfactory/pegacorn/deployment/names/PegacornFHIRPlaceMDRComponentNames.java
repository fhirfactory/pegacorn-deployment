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
public class PegacornFHIRPlaceMDRComponentNames {
    private static final String PEGACORN_FHIRPLACE_MDR_NAME = "Pegacorn-FHIRPlace-MDR";
    private static final String PEGACORN_FHIRPLACE_MDR_VERSION = "1.0.0";

    public static String getPegacornFHIRPlaceMDRName() {
        return PEGACORN_FHIRPLACE_MDR_NAME;
    }

    public static String getPegacornFHIRPlaceMDRVersion() {
        return PEGACORN_FHIRPLACE_MDR_VERSION;
    }

    private static final String FOUNDATION_CONFORMANCE_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Conformance";
    private static final String FOUNDATION_CONFORMANCE_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FOUNDATION_CONFORMANCE_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Conformance";
    private static final String FOUNDATION_CONFORMANCE_PEGACORN_MDR_SERVICE = "fhirplace-mdr-foundation-conformance";
    private static final String FOUNDATION_CONFORMANCE_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-mdr-foundation-conformance";
    private static final String FOUNDATION_CONFORMANCE_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FOUNDATION_CONFORMANCE_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Foundation-Conformance-API";

    private static final String FOUNDATION_TERMINOLOGY_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Terminololgy";
    private static final String FOUNDATION_TERMINOLOGY_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FOUNDATION_TERMINOLOGY_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Terminololgy";
    private static final String FOUNDATION_TERMINOLOGY_PEGACORN_MDR_SERVICE = "fhirplace-mdr-foundation-terminology";
    private static final String FOUNDATION_TERMINOLOGY_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-mdr-foundation-terminology";
    private static final String FOUNDATION_TERMINOLOGY_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FOUNDATION_TERMINOLOGY_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Foundation-Terminology-API";

    private static final String FOUNDATION_SECURITY_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Security";
    private static final String FOUNDATION_SECURITY_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FOUNDATION_SECURITY_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Security";
    private static final String FOUNDATION_SECURITY_PEGACORN_MDR_SERVICE = "fhirplace-foundation-security";
    private static final String FOUNDATION_SECURITY_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-foundation-security";
    private static final String FOUNDATION_SECURITY_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FOUNDATION_SECURITY_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Foundation-Security-API";

    private static final String FOUNDATION_DOCUMENTS_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Documents";
    private static final String FOUNDATION_DOCUMENTS_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FOUNDATION_DOCUMENTS_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Documents";
    private static final String FOUNDATION_DOCUMENTS_PEGACORN_MDR_SERVICE = "fhirplace-foundation-documents";
    private static final String FOUNDATION_DOCUMENTS_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-foundation-documents";
    private static final String FOUNDATION_DOCUMENTS_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FOUNDATION_DOCUMENTS_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Foundation-Documents-API";

    private static final String FOUNDATION_OTHER_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Documents";
    private static final String FOUNDATION_OTHER_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FOUNDATION_OTHER_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Documents";
    private static final String FOUNDATION_OTHER_PEGACORN_MDR_SERVICE = "fhirplace-foundation-other";
    private static final String FOUNDATION_OTHER_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-foundation-other";
    private static final String FOUNDATION_OTHER_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FOUNDATION_OTHER_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Foundation-Other-API";

    private static final String BASE_INDIVIDUALS_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Individuals";
    private static final String BASE_INDIVIDUALS_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String BASE_INDIVIDUALS_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Individuals";
    private static final String BASE_INDIVIDUALS_PEGACORN_MDR_SERVICE = "fhirplace-base-individuals";
    private static final String BASE_INDIVIDUALS_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-base-individuals";
    private static final String BASE_INDIVIDUALS_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String BASE_INDIVIDUALS_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Base-Individuals-API";

    private static final String BASE_ENTITIES_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Entities";
    private static final String BASE_ENTITIES_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String BASE_ENTITIES_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Entities";
    private static final String BASE_ENTITIES_PEGACORN_MDR_SERVICE = "fhirplace-base-entities";
    private static final String BASE_ENTITIES_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-entities";
    private static final String BASE_ENTITIES_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String BASE_ENTITIES_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Base-Entities-API";

    private static final String BASE_WORKFLOW_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Workflow";
    private static final String BASE_WORKFLOW_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String BASE_WORKFLOW_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Workflow";
    private static final String BASE_WORKFLOW_PEGACORN_MDR_SERVICE = "fhirplace-base-workflow";
    private static final String BASE_WORKFLOW_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-base-workflow";
    private static final String BASE_WORKFLOW_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String BASE_WORKFLOW_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Base-Workflow-API";

    private static final String BASE_MANAGEMENT_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Management";
    private static final String BASE_MANAGEMENT_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String BASE_MANAGEMENT_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Management";
    private static final String BASE_MANAGEMENT_PEGACORN_MDR_SERVICE = "fhirplace-mgt-fhirplace";
    private static final String BASE_MANAGEMENT_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-mgt-fhirplace";
    private static final String BASE_MANAGEMENT_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String BASE_MANAGEMENT_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Base-Management-API";

    private static final String CLINICAL_SUMMARY_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Clinical-Summary";
    private static final String CLINICAL_SUMMARY_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String CLINICAL_SUMMARY_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Clinical-Summary";
    private static final String CLINICAL_SUMMARY_PEGACORN_MDR_SERVICE = "fhirplace-sum-fhirplace";
    private static final String CLINICAL_SUMMARY_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-sum-fhirplace";
    private static final String CLINICAL_SUMMARY_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String CLINICAL_SUMMARY_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Clinical-Summary-API";

    private static final String CLINICAL_DIAGNOSTICS_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Clinical-Diagnostics";
    private static final String CLINICAL_DIAGNOSTICS_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String CLINICAL_DIAGNOSTICS_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Clinical-Diagnostics";
    private static final String CLINICAL_DIAGNOSTICS_PEGACORN_MDR_SERVICE = "fhirplace-diag-fhirplace";
    private static final String CLINICAL_DIAGNOSTICS_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-diag-fhirplace";
    private static final String CLINICAL_DIAGNOSTICS_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String CLINICAL_DIAGNOSTICS_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Clinical-Diagnostics-API";

    private static final String CLINICAL_MEDICATIONS_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Clinical-Medications";
    private static final String CLINICAL_MEDICATIONS_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String CLINICAL_MEDICATIONS_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Clinical-Medications";
    private static final String CLINICAL_MEDICATIONS_PEGACORN_MDR_SERVICE = "fhirplace-med-fhirplace";
    private static final String CLINICAL_MEDICATIONS_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-med-fhirplace";
    private static final String CLINICAL_MEDICATIONS_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String CLINICAL_MEDICATIONS_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Clinical-Medications-API";

    private static final String CLINICAL_CARE_PROVISION_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Clinical-CareProvision";
    private static final String CLINICAL_CARE_PROVISION_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String CLINICAL_CARE_PROVISION_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Clinical-CareProvision";
    private static final String CLINICAL_CARE_PROVISION_PEGACORN_MDR_SERVICE = "fhirplace-clinical-careprovisioning";
    private static final String CLINICAL_CARE_PROVISION_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-clinical-careprovisioning";
    private static final String CLINICAL_CARE_PROVISION_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String CLINICAL_CARE_PROVISION_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Clinical-CareProvision-API";

    private static final String CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Clinical-RequestAndResponse";
    private static final String CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Clinical-RequestAndResponse";
    private static final String CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_SERVICE = "fhirplace-cliical-requestandresponse";
    private static final String CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_PROCESSING_PLANT = "hirplace-cliical-requestandresponse";
    private static final String CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Clinical-RequestAndResponse-API";

    private static final String FINANCIAL_SUPPORT_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Financial-Support";
    private static final String FINANCIAL_SUPPORT_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FINANCIAL_SUPPORT_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Financial-Support";
    private static final String FINANCIAL_SUPPORT_PEGACORN_MDR_SERVICE = "fhirplace-financial-support";
    private static final String FINANCIAL_SUPPORT_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-financial-support";
    private static final String FINANCIAL_SUPPORT_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FINANCIAL_SUPPORT_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Financial-Support-API";

    private static final String FINANCIAL_BILLING_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Financial-Billing";
    private static final String FINANCIAL_BILLING_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FINANCIAL_BILLING_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Financial-Billing";
    private static final String FINANCIAL_BILLING_PEGACORN_MDR_SERVICE = "fhirplace-financial-billing";
    private static final String FINANCIAL_BILLING_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-financial-billing";
    private static final String FINANCIAL_BILLING_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FINANCIAL_BILLING_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Financial-Billing-API";

    private static final String FINANCIAL_PAYMENT_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Financial-Payment";
    private static final String FINANCIAL_PAYMENT_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FINANCIAL_PAYMENT_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Financial-Payment";
    private static final String FINANCIAL_PAYMENT_PEGACORN_MDR_SERVICE = "fhirplace-financial-payment";
    private static final String FINANCIAL_PAYMENT_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-financial-payment";
    private static final String FINANCIAL_PAYMENT_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FINANCIAL_PAYMENT_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Financial-Payment-API";

    private static final String FINANCIAL_GENERAL_PEGACORN_MDR_SUBSYSTEM = "Pegacorn-FHIRPlace-MDR-Financial-General";
    private static final String FINANCIAL_GENERAL_PEGACORN_MDR_SUBSYSTEM_VERSION = "4.0.1";
    private static final String FINANCIAL_GENERAL_PEGACORN_MDR_EXTERNALISED_SERVICE = "Pegacorn-FHIRPlace-MDR-Financial-General";
    private static final String FINANCIAL_GENERAL_PEGACORN_MDR_SERVICE = "fhirplace-financial-general";
    private static final String FINANCIAL_GENERAL_PEGACORN_MDR_PROCESSING_PLANT = "fhirplace-financial-general";
    private static final String FINANCIAL_GENERAL_PEGACORN_MDR_PROCESSING_PLANT_VERSION = "4.0.1";
    private static final String FINANCIAL_GENERAL_PEGACORN_MDR_ENDPOINT_FHIR_API = "Pegacorn-FHIRPlace-MDR-Financial-General-API";


    public static String getFoundationConformancePegacornMDRSubsystem() {
        return FOUNDATION_CONFORMANCE_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFoundationConformancePegacornMDRSubsystemVersion() {
        return FOUNDATION_CONFORMANCE_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFoundationConformancePegacornMDRExternalisedService() {
        return FOUNDATION_CONFORMANCE_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFoundationConformancePegacornMDRService() {
        return FOUNDATION_CONFORMANCE_PEGACORN_MDR_SERVICE;
    }

    public static String getFoundationConformancePegacornMDRProcessingPlant() {
        return FOUNDATION_CONFORMANCE_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFoundationConformancePegacornMDRProcessingPlantVersion() {
        return FOUNDATION_CONFORMANCE_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFoundationConformancePegacornMDREndpointFhirApi() {
        return FOUNDATION_CONFORMANCE_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getFoundationTerminologyPegacornMDRSubsystem() {
        return FOUNDATION_TERMINOLOGY_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFoundationTerminologyPegacornMDRSubsystemVersion() {
        return FOUNDATION_TERMINOLOGY_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFoundationTerminologyPegacornMDRExternalisedService() {
        return FOUNDATION_TERMINOLOGY_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFoundationTerminologyPegacornMDRService() {
        return FOUNDATION_TERMINOLOGY_PEGACORN_MDR_SERVICE;
    }

    public static String getFoundationTerminologyPegacornMDRProcessingPlant() {
        return FOUNDATION_TERMINOLOGY_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFoundationTerminologyPegacornMDRProcessingPlantVersion() {
        return FOUNDATION_TERMINOLOGY_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFoundationTerminologyPegacornMDREndpointFhirApi() {
        return FOUNDATION_TERMINOLOGY_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getFoundationSecurityPegacornMDRSubsystem() {
        return FOUNDATION_SECURITY_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFoundationSecurityPegacornMDRSubsystemVersion() {
        return FOUNDATION_SECURITY_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFoundationSecurityPegacornMDRExternalisedService() {
        return FOUNDATION_SECURITY_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFoundationSecurityPegacornMDRService() {
        return FOUNDATION_SECURITY_PEGACORN_MDR_SERVICE;
    }

    public static String getFoundationSecurityPegacornMDRProcessingPlant() {
        return FOUNDATION_SECURITY_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFoundationSecurityPegacornMDRProcessingPlantVersion() {
        return FOUNDATION_SECURITY_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFoundationSecurityPegacornMDREndpointFhirApi() {
        return FOUNDATION_SECURITY_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getFoundationDocumentsPegacornMDRSubsystem() {
        return FOUNDATION_DOCUMENTS_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFoundationDocumentsPegacornMDRSubsystemVersion() {
        return FOUNDATION_DOCUMENTS_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFoundationDocumentsPegacornMDRExternalisedService() {
        return FOUNDATION_DOCUMENTS_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFoundationDocumentsPegacornMDRService() {
        return FOUNDATION_DOCUMENTS_PEGACORN_MDR_SERVICE;
    }

    public static String getFoundationDocumentsPegacornMDRProcessingPlant() {
        return FOUNDATION_DOCUMENTS_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFoundationDocumentsPegacornMDRProcessingPlantVersion() {
        return FOUNDATION_DOCUMENTS_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFoundationDocumentsPegacornMDREndpointFhirApi() {
        return FOUNDATION_DOCUMENTS_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getFoundationOtherPegacornMDRSubsystem() {
        return FOUNDATION_OTHER_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFoundationOtherPegacornMDRSubsystemVersion() {
        return FOUNDATION_OTHER_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFoundationOtherPegacornMDRExternalisedService() {
        return FOUNDATION_OTHER_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFoundationOtherPegacornMDRService() {
        return FOUNDATION_OTHER_PEGACORN_MDR_SERVICE;
    }

    public static String getFoundationOtherPegacornMDRProcessingPlant() {
        return FOUNDATION_OTHER_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFoundationOtherPegacornMDRProcessingPlantVersion() {
        return FOUNDATION_OTHER_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFoundationOtherPegacornMDREndpointFhirApi() {
        return FOUNDATION_OTHER_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getBaseIndividualsPegacornMDRSubsystem() {
        return BASE_INDIVIDUALS_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getBaseIndividualsPegacornMDRSubsystemVersion() {
        return BASE_INDIVIDUALS_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getBaseIndividualsPegacornMDRExternalisedService() {
        return BASE_INDIVIDUALS_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getBaseIndividualsPegacornMDRService() {
        return BASE_INDIVIDUALS_PEGACORN_MDR_SERVICE;
    }

    public static String getBaseIndividualsPegacornMDRProcessingPlant() {
        return BASE_INDIVIDUALS_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getBaseIndividualsPegacornMDRProcessingPlantVersion() {
        return BASE_INDIVIDUALS_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getBaseIndividualsPegacornMDREndpointFhirApi() {
        return BASE_INDIVIDUALS_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getBaseEntitiesPegacornMDRSubsystem() {
        return BASE_ENTITIES_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getBaseEntitiesPegacornMDRSubsystemVersion() {
        return BASE_ENTITIES_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getBaseEntitiesPegacornMDRExternalisedService() {
        return BASE_ENTITIES_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getBaseEntitiesPegacornMDRService() {
        return BASE_ENTITIES_PEGACORN_MDR_SERVICE;
    }

    public static String getBaseEntitiesPegacornMDRProcessingPlant() {
        return BASE_ENTITIES_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getBaseEntitiesPegacornMDRProcessingPlantVersion() {
        return BASE_ENTITIES_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getBaseEntitiesPegacornMDREndpointFhirApi() {
        return BASE_ENTITIES_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getBaseWorkflowPegacornMDRSubsystem() {
        return BASE_WORKFLOW_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getBaseWorkflowPegacornMDRSubsystemVersion() {
        return BASE_WORKFLOW_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getBaseWorkflowPegacornMDRExternalisedService() {
        return BASE_WORKFLOW_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getBaseWorkflowPegacornMDRService() {
        return BASE_WORKFLOW_PEGACORN_MDR_SERVICE;
    }

    public static String getBaseWorkflowPegacornMDRProcessingPlant() {
        return BASE_WORKFLOW_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getBaseWorkflowPegacornMDRProcessingPlantVersion() {
        return BASE_WORKFLOW_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getBaseWorkflowPegacornMDREndpointFhirApi() {
        return BASE_WORKFLOW_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getBaseManagementPegacornMDRSubsystem() {
        return BASE_MANAGEMENT_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getBaseManagementPegacornMDRSubsystemVersion() {
        return BASE_MANAGEMENT_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getBaseManagementPegacornMDRExternalisedService() {
        return BASE_MANAGEMENT_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getBaseManagementPegacornMDRService() {
        return BASE_MANAGEMENT_PEGACORN_MDR_SERVICE;
    }

    public static String getBaseManagementPegacornMDRProcessingPlant() {
        return BASE_MANAGEMENT_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getBaseManagementPegacornMDRProcessingPlantVersion() {
        return BASE_MANAGEMENT_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getBaseManagementPegacornMDREndpointFhirApi() {
        return BASE_MANAGEMENT_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getClinicalSummaryPegacornMDRSubsystem() {
        return CLINICAL_SUMMARY_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getClinicalSummaryPegacornMDRSubsystemVersion() {
        return CLINICAL_SUMMARY_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getClinicalSummaryPegacornMDRExternalisedService() {
        return CLINICAL_SUMMARY_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getClinicalSummaryPegacornMDRService() {
        return CLINICAL_SUMMARY_PEGACORN_MDR_SERVICE;
    }

    public static String getClinicalSummaryPegacornMDRProcessingPlant() {
        return CLINICAL_SUMMARY_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getClinicalSummaryPegacornMDRProcessingPlantVersion() {
        return CLINICAL_SUMMARY_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getClinicalSummaryPegacornMDREndpointFhirApi() {
        return CLINICAL_SUMMARY_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getClinicalDiagnosticsPegacornMDRSubsystem() {
        return CLINICAL_DIAGNOSTICS_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getClinicalDiagnosticsPegacornMDRSubsystemVersion() {
        return CLINICAL_DIAGNOSTICS_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getClinicalDiagnosticsPegacornMDRExternalisedService() {
        return CLINICAL_DIAGNOSTICS_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getClinicalDiagnosticsPegacornMDRService() {
        return CLINICAL_DIAGNOSTICS_PEGACORN_MDR_SERVICE;
    }

    public static String getClinicalDiagnosticsPegacornMDRProcessingPlant() {
        return CLINICAL_DIAGNOSTICS_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getClinicalDiagnosticsPegacornMDRProcessingPlantVersion() {
        return CLINICAL_DIAGNOSTICS_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getClinicalDiagnosticsPegacornMDREndpointFhirApi() {
        return CLINICAL_DIAGNOSTICS_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getClinicalMedicationsPegacornMDRSubsystem() {
        return CLINICAL_MEDICATIONS_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getClinicalMedicationsPegacornMDRSubsystemVersion() {
        return CLINICAL_MEDICATIONS_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getClinicalMedicationsPegacornMDRExternalisedService() {
        return CLINICAL_MEDICATIONS_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getClinicalMedicationsPegacornMDRService() {
        return CLINICAL_MEDICATIONS_PEGACORN_MDR_SERVICE;
    }

    public static String getClinicalMedicationsPegacornMDRProcessingPlant() {
        return CLINICAL_MEDICATIONS_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getClinicalMedicationsPegacornMDRProcessingPlantVersion() {
        return CLINICAL_MEDICATIONS_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getClinicalMedicationsPegacornMDREndpointFhirApi() {
        return CLINICAL_MEDICATIONS_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getClinicalCareProvisionPegacornMDRSubsystem() {
        return CLINICAL_CARE_PROVISION_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getClinicalCareProvisionPegacornMDRSubsystemVersion() {
        return CLINICAL_CARE_PROVISION_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getClinicalCareProvisionPegacornMDRExternalisedService() {
        return CLINICAL_CARE_PROVISION_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getClinicalCareProvisionPegacornMDRService() {
        return CLINICAL_CARE_PROVISION_PEGACORN_MDR_SERVICE;
    }

    public static String getClinicalCareProvisionPegacornMDRProcessingPlant() {
        return CLINICAL_CARE_PROVISION_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getClinicalCareProvisionPegacornMDRProcessingPlantVersion() {
        return CLINICAL_CARE_PROVISION_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getClinicalCareProvisionPegacornMDREndpointFhirApi() {
        return CLINICAL_CARE_PROVISION_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getClinicalRequestAndResponsePegacornMDRSubsystem() {
        return CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getClinicalRequestAndResponsePegacornMDRSubsystemVersion() {
        return CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getClinicalRequestAndResponsePegacornMDRExternalisedService() {
        return CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getClinicalRequestAndResponsePegacornMDRService() {
        return CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_SERVICE;
    }

    public static String getClinicalRequestAndResponsePegacornMDRProcessingPlant() {
        return CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getClinicalRequestAndResponsePegacornMDRProcessingPlantVersion() {
        return CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getClinicalRequestAndResponsePegacornMDREndpointFhirApi() {
        return CLINICAL_REQUEST_AND_RESPONSE_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getFinancialSupportPegacornMDRSubsystem() {
        return FINANCIAL_SUPPORT_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFinancialSupportPegacornMDRSubsystemVersion() {
        return FINANCIAL_SUPPORT_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFinancialSupportPegacornMDRExternalisedService() {
        return FINANCIAL_SUPPORT_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFinancialSupportPegacornMDRService() {
        return FINANCIAL_SUPPORT_PEGACORN_MDR_SERVICE;
    }

    public static String getFinancialSupportPegacornMDRProcessingPlant() {
        return FINANCIAL_SUPPORT_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFinancialSupportPegacornMDRProcessingPlantVersion() {
        return FINANCIAL_SUPPORT_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFinancialSupportPegacornMDREndpointFhirApi() {
        return FINANCIAL_SUPPORT_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getFinancialBillingPegacornMDRSubsystem() {
        return FINANCIAL_BILLING_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFinancialBillingPegacornMDRSubsystemVersion() {
        return FINANCIAL_BILLING_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFinancialBillingPegacornMDRExternalisedService() {
        return FINANCIAL_BILLING_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFinancialBillingPegacornMDRService() {
        return FINANCIAL_BILLING_PEGACORN_MDR_SERVICE;
    }

    public static String getFinancialBillingPegacornMDRProcessingPlant() {
        return FINANCIAL_BILLING_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFinancialBillingPegacornMDRProcessingPlantVersion() {
        return FINANCIAL_BILLING_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFinancialBillingPegacornMDREndpointFhirApi() {
        return FINANCIAL_BILLING_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getFinancialPaymentPegacornMDRSubsystem() {
        return FINANCIAL_PAYMENT_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFinancialPaymentPegacornMDRSubsystemVersion() {
        return FINANCIAL_PAYMENT_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFinancialPaymentPegacornMDRExternalisedService() {
        return FINANCIAL_PAYMENT_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFinancialPaymentPegacornMDRService() {
        return FINANCIAL_PAYMENT_PEGACORN_MDR_SERVICE;
    }

    public static String getFinancialPaymentPegacornMDRProcessingPlant() {
        return FINANCIAL_PAYMENT_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFinancialPaymentPegacornMDRProcessingPlantVersion() {
        return FINANCIAL_PAYMENT_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFinancialPaymentPegacornMDREndpointFhirApi() {
        return FINANCIAL_PAYMENT_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }

    public static String getFinancialGeneralPegacornMDRSubsystem() {
        return FINANCIAL_GENERAL_PEGACORN_MDR_SUBSYSTEM;
    }

    public static String getFinancialGeneralPegacornMDRSubsystemVersion() {
        return FINANCIAL_GENERAL_PEGACORN_MDR_SUBSYSTEM_VERSION;
    }

    public static String getFinancialGeneralPegacornMDRExternalisedService() {
        return FINANCIAL_GENERAL_PEGACORN_MDR_EXTERNALISED_SERVICE;
    }

    public static String getFinancialGeneralPegacornMDRService() {
        return FINANCIAL_GENERAL_PEGACORN_MDR_SERVICE;
    }

    public static String getFinancialGeneralPegacornMDRProcessingPlant() {
        return FINANCIAL_GENERAL_PEGACORN_MDR_PROCESSING_PLANT;
    }

    public static String getFinancialGeneralPegacornMDRProcessingPlantVersion() {
        return FINANCIAL_GENERAL_PEGACORN_MDR_PROCESSING_PLANT_VERSION;
    }

    public static String getFinancialGeneralPegacornMDREndpointFhirApi() {
        return FINANCIAL_GENERAL_PEGACORN_MDR_ENDPOINT_FHIR_API;
    }


}
