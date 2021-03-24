/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.ClusterServiceDeliverySubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornHTTPIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.segments.*;

/**
 *
 * @author Mark A. Hunter (ACT Health)
 */

public abstract class LadonNexusPropertyFileBase extends ClusterServiceDeliverySubsystemPropertyFile {

    //
    // Petasos Replication
    //
    private PegacornStandardIPCPort petasosReplicationServer;
    //
    // Petasos Status
    //
    private PegacornStandardIPCPort petasosStatus;
    //
    // Edge :: Answer (FHIR API Server)
    //
    private PegacornHTTPIPCPort edgeAnswer;
    //
    // Communicate::FHIRBridge Ports
    //
    private CommunicateIrisPropertyFileSegment communicateFHIRBridgeConnection;
    //
    // FHIRBreak::EmailGateway
    //
    private FHIRBreakEmailGatewayPropertyFileSegment fhirBreakEmailGatewayConnection;
    //
    // FHIRBreak::LDAPScanner
    //
    private FHIRBreakLDAPScannerPropertyFileSegment fhirbreakLDAPScannerConnection;
    //
    // MITaF::SMSGateway
    //
    private MITaFSMSGatewayPropertyFileSegment mitafSMSGatewayConnection;
    //
    // Hestia::Audit
    //
    private HestiaAuditDMPropertFileSegment hestiaAuditConnection;
    //
    // Hestia::DAM
    //
    private HestiaDAMDMPropertyFileSegment hestiaDAMConnection;

    //
    //
    // Getters and Setters
    //
    //

    public CommunicateIrisPropertyFileSegment getCommunicateFHIRBridgeConnection() {
        return communicateFHIRBridgeConnection;
    }

    public void setCommunicateFHIRBridgeConnection(CommunicateIrisPropertyFileSegment communicateFHIRBridgeConnection) {
        this.communicateFHIRBridgeConnection = communicateFHIRBridgeConnection;
    }

    public FHIRBreakEmailGatewayPropertyFileSegment getFhirBreakEmailGatewayConnection() {
        return fhirBreakEmailGatewayConnection;
    }

    public void setFhirBreakEmailGatewayConnection(FHIRBreakEmailGatewayPropertyFileSegment fhirBreakEmailGatewayConnection) {
        this.fhirBreakEmailGatewayConnection = fhirBreakEmailGatewayConnection;
    }

    public FHIRBreakLDAPScannerPropertyFileSegment getFhirbreakLDAPScannerConnection() {
        return fhirbreakLDAPScannerConnection;
    }

    public void setFhirbreakLDAPScannerConnection(FHIRBreakLDAPScannerPropertyFileSegment fhirbreakLDAPScannerConnection) {
        this.fhirbreakLDAPScannerConnection = fhirbreakLDAPScannerConnection;
    }

    public MITaFSMSGatewayPropertyFileSegment getMitafSMSGatewayConnection() {
        return mitafSMSGatewayConnection;
    }

    public void setMitafSMSGatewayConnection(MITaFSMSGatewayPropertyFileSegment mitafSMSGatewayConnection) {
        this.mitafSMSGatewayConnection = mitafSMSGatewayConnection;
    }

    public PegacornStandardIPCPort getPetasosReplicationServer() {
        return petasosReplicationServer;
    }

    public void setPetasosReplicationServer(PegacornStandardIPCPort petasosReplicationServer) {
        this.petasosReplicationServer = petasosReplicationServer;
    }

    public HestiaAuditDMPropertFileSegment getHestiaAuditConnection() {
        return hestiaAuditConnection;
    }

    public void setHestiaAuditConnection(HestiaAuditDMPropertFileSegment hestiaAuditConnection) {
        this.hestiaAuditConnection = hestiaAuditConnection;
    }

    public HestiaDAMDMPropertyFileSegment getHestiaDAMConnection() {
        return hestiaDAMConnection;
    }

    public void setHestiaDAMConnection(HestiaDAMDMPropertyFileSegment hestiaDAMConnection) {
        this.hestiaDAMConnection = hestiaDAMConnection;
    }
}
