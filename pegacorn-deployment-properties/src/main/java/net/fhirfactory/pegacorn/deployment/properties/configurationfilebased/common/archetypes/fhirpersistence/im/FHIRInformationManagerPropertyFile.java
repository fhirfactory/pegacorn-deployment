package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.fhirpersistence.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.ClusterServiceDeliverySubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornHTTPIPCPort;

public abstract class FHIRInformationManagerPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile {

    PegacornHTTPIPCPort edgeAnswerConformance;
    PegacornHTTPIPCPort edgeAnswerTerminology;
    PegacornHTTPIPCPort edgeAnswerSecurity;
    PegacornHTTPIPCPort edgeAnswerDocuments;
    PegacornHTTPIPCPort edgeAnswerOther;
    PegacornHTTPIPCPort edgeAnswerIndividuals;
    PegacornHTTPIPCPort edgeAnswerEntities;
    PegacornHTTPIPCPort edgeAnswerWorkflow;
    PegacornHTTPIPCPort edgeAnswerManagement;
    PegacornHTTPIPCPort edgeAnswerSummary;
    PegacornHTTPIPCPort edgeAnswerDiagnostics;
    PegacornHTTPIPCPort edgeAnswerMedications;
    PegacornHTTPIPCPort edgeAnswerCareProvision;
    PegacornHTTPIPCPort edgeAnswerRequestAndResponse;
    PegacornHTTPIPCPort edgeAnswerSupport;
    PegacornHTTPIPCPort edgeAnswerBilling;
    PegacornHTTPIPCPort edgeAnswerPayment;
    PegacornHTTPIPCPort edgeAnswerGeneral;

    public PegacornHTTPIPCPort getEdgeAnswerConformance() {
        return edgeAnswerConformance;
    }

    public void setEdgeAnswerConformance(PegacornHTTPIPCPort edgeAnswerConformance) {
        this.edgeAnswerConformance = edgeAnswerConformance;
    }

    public PegacornHTTPIPCPort getEdgeAnswerTerminology() {
        return edgeAnswerTerminology;
    }

    public void setEdgeAnswerTerminology(PegacornHTTPIPCPort edgeAnswerTerminology) {
        this.edgeAnswerTerminology = edgeAnswerTerminology;
    }

    public PegacornHTTPIPCPort getEdgeAnswerSecurity() {
        return edgeAnswerSecurity;
    }

    public void setEdgeAnswerSecurity(PegacornHTTPIPCPort edgeAnswerSecurity) {
        this.edgeAnswerSecurity = edgeAnswerSecurity;
    }

    public PegacornHTTPIPCPort getEdgeAnswerDocuments() {
        return edgeAnswerDocuments;
    }

    public void setEdgeAnswerDocuments(PegacornHTTPIPCPort edgeAnswerDocuments) {
        this.edgeAnswerDocuments = edgeAnswerDocuments;
    }

    public PegacornHTTPIPCPort getEdgeAnswerOther() {
        return edgeAnswerOther;
    }

    public void setEdgeAnswerOther(PegacornHTTPIPCPort edgeAnswerOther) {
        this.edgeAnswerOther = edgeAnswerOther;
    }

    public PegacornHTTPIPCPort getEdgeAnswerIndividuals() {
        return edgeAnswerIndividuals;
    }

    public void setEdgeAnswerIndividuals(PegacornHTTPIPCPort edgeAnswerIndividuals) {
        this.edgeAnswerIndividuals = edgeAnswerIndividuals;
    }

    public PegacornHTTPIPCPort getEdgeAnswerEntities() {
        return edgeAnswerEntities;
    }

    public void setEdgeAnswerEntities(PegacornHTTPIPCPort edgeAnswerEntities) {
        this.edgeAnswerEntities = edgeAnswerEntities;
    }

    public PegacornHTTPIPCPort getEdgeAnswerWorkflow() {
        return edgeAnswerWorkflow;
    }

    public void setEdgeAnswerWorkflow(PegacornHTTPIPCPort edgeAnswerWorkflow) {
        this.edgeAnswerWorkflow = edgeAnswerWorkflow;
    }

    public PegacornHTTPIPCPort getEdgeAnswerManagement() {
        return edgeAnswerManagement;
    }

    public void setEdgeAnswerManagement(PegacornHTTPIPCPort edgeAnswerManagement) {
        this.edgeAnswerManagement = edgeAnswerManagement;
    }

    public PegacornHTTPIPCPort getEdgeAnswerSummary() {
        return edgeAnswerSummary;
    }

    public void setEdgeAnswerSummary(PegacornHTTPIPCPort edgeAnswerSummary) {
        this.edgeAnswerSummary = edgeAnswerSummary;
    }

    public PegacornHTTPIPCPort getEdgeAnswerDiagnostics() {
        return edgeAnswerDiagnostics;
    }

    public void setEdgeAnswerDiagnostics(PegacornHTTPIPCPort edgeAnswerDiagnostics) {
        this.edgeAnswerDiagnostics = edgeAnswerDiagnostics;
    }

    public PegacornHTTPIPCPort getEdgeAnswerMedications() {
        return edgeAnswerMedications;
    }

    public void setEdgeAnswerMedications(PegacornHTTPIPCPort edgeAnswerMedications) {
        this.edgeAnswerMedications = edgeAnswerMedications;
    }

    public PegacornHTTPIPCPort getEdgeAnswerCareProvision() {
        return edgeAnswerCareProvision;
    }

    public void setEdgeAnswerCareProvision(PegacornHTTPIPCPort edgeAnswerCareProvision) {
        this.edgeAnswerCareProvision = edgeAnswerCareProvision;
    }

    public PegacornHTTPIPCPort getEdgeAnswerRequestAndResponse() {
        return edgeAnswerRequestAndResponse;
    }

    public void setEdgeAnswerRequestAndResponse(PegacornHTTPIPCPort edgeAnswerRequestAndResponse) {
        this.edgeAnswerRequestAndResponse = edgeAnswerRequestAndResponse;
    }

    public PegacornHTTPIPCPort getEdgeAnswerSupport() {
        return edgeAnswerSupport;
    }

    public void setEdgeAnswerSupport(PegacornHTTPIPCPort edgeAnswerSupport) {
        this.edgeAnswerSupport = edgeAnswerSupport;
    }

    public PegacornHTTPIPCPort getEdgeAnswerBilling() {
        return edgeAnswerBilling;
    }

    public void setEdgeAnswerBilling(PegacornHTTPIPCPort edgeAnswerBilling) {
        this.edgeAnswerBilling = edgeAnswerBilling;
    }

    public PegacornHTTPIPCPort getEdgeAnswerPayment() {
        return edgeAnswerPayment;
    }

    public void setEdgeAnswerPayment(PegacornHTTPIPCPort edgeAnswerPayment) {
        this.edgeAnswerPayment = edgeAnswerPayment;
    }

    public PegacornHTTPIPCPort getEdgeAnswerGeneral() {
        return edgeAnswerGeneral;
    }

    public void setEdgeAnswerGeneral(PegacornHTTPIPCPort edgeAnswerGeneral) {
        this.edgeAnswerGeneral = edgeAnswerGeneral;
    }
}
