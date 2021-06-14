package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.fhirpersistence.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.ClusterServiceDeliverySubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCPortSegment;

public abstract class FHIRInformationManagerPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile {

    HTTPIPCPortSegment edgeAnswerConformance;
    HTTPIPCPortSegment edgeAnswerTerminology;
    HTTPIPCPortSegment edgeAnswerSecurity;
    HTTPIPCPortSegment edgeAnswerDocuments;
    HTTPIPCPortSegment edgeAnswerOther;
    HTTPIPCPortSegment edgeAnswerIndividuals;
    HTTPIPCPortSegment edgeAnswerEntities;
    HTTPIPCPortSegment edgeAnswerWorkflow;
    HTTPIPCPortSegment edgeAnswerManagement;
    HTTPIPCPortSegment edgeAnswerSummary;
    HTTPIPCPortSegment edgeAnswerDiagnostics;
    HTTPIPCPortSegment edgeAnswerMedications;
    HTTPIPCPortSegment edgeAnswerCareProvision;
    HTTPIPCPortSegment edgeAnswerRequestAndResponse;
    HTTPIPCPortSegment edgeAnswerSupport;
    HTTPIPCPortSegment edgeAnswerBilling;
    HTTPIPCPortSegment edgeAnswerPayment;
    HTTPIPCPortSegment edgeAnswerGeneral;

    public HTTPIPCPortSegment getEdgeAnswerConformance() {
        return edgeAnswerConformance;
    }

    public void setEdgeAnswerConformance(HTTPIPCPortSegment edgeAnswerConformance) {
        this.edgeAnswerConformance = edgeAnswerConformance;
    }

    public HTTPIPCPortSegment getEdgeAnswerTerminology() {
        return edgeAnswerTerminology;
    }

    public void setEdgeAnswerTerminology(HTTPIPCPortSegment edgeAnswerTerminology) {
        this.edgeAnswerTerminology = edgeAnswerTerminology;
    }

    public HTTPIPCPortSegment getEdgeAnswerSecurity() {
        return edgeAnswerSecurity;
    }

    public void setEdgeAnswerSecurity(HTTPIPCPortSegment edgeAnswerSecurity) {
        this.edgeAnswerSecurity = edgeAnswerSecurity;
    }

    public HTTPIPCPortSegment getEdgeAnswerDocuments() {
        return edgeAnswerDocuments;
    }

    public void setEdgeAnswerDocuments(HTTPIPCPortSegment edgeAnswerDocuments) {
        this.edgeAnswerDocuments = edgeAnswerDocuments;
    }

    public HTTPIPCPortSegment getEdgeAnswerOther() {
        return edgeAnswerOther;
    }

    public void setEdgeAnswerOther(HTTPIPCPortSegment edgeAnswerOther) {
        this.edgeAnswerOther = edgeAnswerOther;
    }

    public HTTPIPCPortSegment getEdgeAnswerIndividuals() {
        return edgeAnswerIndividuals;
    }

    public void setEdgeAnswerIndividuals(HTTPIPCPortSegment edgeAnswerIndividuals) {
        this.edgeAnswerIndividuals = edgeAnswerIndividuals;
    }

    public HTTPIPCPortSegment getEdgeAnswerEntities() {
        return edgeAnswerEntities;
    }

    public void setEdgeAnswerEntities(HTTPIPCPortSegment edgeAnswerEntities) {
        this.edgeAnswerEntities = edgeAnswerEntities;
    }

    public HTTPIPCPortSegment getEdgeAnswerWorkflow() {
        return edgeAnswerWorkflow;
    }

    public void setEdgeAnswerWorkflow(HTTPIPCPortSegment edgeAnswerWorkflow) {
        this.edgeAnswerWorkflow = edgeAnswerWorkflow;
    }

    public HTTPIPCPortSegment getEdgeAnswerManagement() {
        return edgeAnswerManagement;
    }

    public void setEdgeAnswerManagement(HTTPIPCPortSegment edgeAnswerManagement) {
        this.edgeAnswerManagement = edgeAnswerManagement;
    }

    public HTTPIPCPortSegment getEdgeAnswerSummary() {
        return edgeAnswerSummary;
    }

    public void setEdgeAnswerSummary(HTTPIPCPortSegment edgeAnswerSummary) {
        this.edgeAnswerSummary = edgeAnswerSummary;
    }

    public HTTPIPCPortSegment getEdgeAnswerDiagnostics() {
        return edgeAnswerDiagnostics;
    }

    public void setEdgeAnswerDiagnostics(HTTPIPCPortSegment edgeAnswerDiagnostics) {
        this.edgeAnswerDiagnostics = edgeAnswerDiagnostics;
    }

    public HTTPIPCPortSegment getEdgeAnswerMedications() {
        return edgeAnswerMedications;
    }

    public void setEdgeAnswerMedications(HTTPIPCPortSegment edgeAnswerMedications) {
        this.edgeAnswerMedications = edgeAnswerMedications;
    }

    public HTTPIPCPortSegment getEdgeAnswerCareProvision() {
        return edgeAnswerCareProvision;
    }

    public void setEdgeAnswerCareProvision(HTTPIPCPortSegment edgeAnswerCareProvision) {
        this.edgeAnswerCareProvision = edgeAnswerCareProvision;
    }

    public HTTPIPCPortSegment getEdgeAnswerRequestAndResponse() {
        return edgeAnswerRequestAndResponse;
    }

    public void setEdgeAnswerRequestAndResponse(HTTPIPCPortSegment edgeAnswerRequestAndResponse) {
        this.edgeAnswerRequestAndResponse = edgeAnswerRequestAndResponse;
    }

    public HTTPIPCPortSegment getEdgeAnswerSupport() {
        return edgeAnswerSupport;
    }

    public void setEdgeAnswerSupport(HTTPIPCPortSegment edgeAnswerSupport) {
        this.edgeAnswerSupport = edgeAnswerSupport;
    }

    public HTTPIPCPortSegment getEdgeAnswerBilling() {
        return edgeAnswerBilling;
    }

    public void setEdgeAnswerBilling(HTTPIPCPortSegment edgeAnswerBilling) {
        this.edgeAnswerBilling = edgeAnswerBilling;
    }

    public HTTPIPCPortSegment getEdgeAnswerPayment() {
        return edgeAnswerPayment;
    }

    public void setEdgeAnswerPayment(HTTPIPCPortSegment edgeAnswerPayment) {
        this.edgeAnswerPayment = edgeAnswerPayment;
    }

    public HTTPIPCPortSegment getEdgeAnswerGeneral() {
        return edgeAnswerGeneral;
    }

    public void setEdgeAnswerGeneral(HTTPIPCPortSegment edgeAnswerGeneral) {
        this.edgeAnswerGeneral = edgeAnswerGeneral;
    }
}
