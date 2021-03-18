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
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.hestia.audit.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.ClusterServiceDeliverySubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornHTTPIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardIPCPort;


public abstract class HestiaAuditIMPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile {

    private PegacornHTTPIPCPort edgeAnswer;
    private PegacornStandardIPCPort edgeReceiveAuditEventLadonNexusIM;
    private PegacornStandardIPCPort edgeReceiveAuditEventCommunicateIrisIM;
    private PegacornStandardIPCPort edgeReceiveAuditEventFHIRBreakLDAPScanner;
    private PegacornStandardIPCPort edgeReceiveAuditEventFHIRBreakEmailGateway;
    private PegacornStandardIPCPort edgeReceiveAuditEventMITaFSMSGateway;

    public PegacornHTTPIPCPort getEdgeAnswer() {
        return edgeAnswer;
    }

    public void setEdgeAnswer(PegacornHTTPIPCPort edgeAnswer) {
        this.edgeAnswer = edgeAnswer;
    }

    public PegacornStandardIPCPort getEdgeReceiveAuditEventLadonNexusIM() {
        return edgeReceiveAuditEventLadonNexusIM;
    }

    public void setEdgeReceiveAuditEventLadonNexusIM(PegacornStandardIPCPort edgeReceiveAuditEventLadonNexusIM) {
        this.edgeReceiveAuditEventLadonNexusIM = edgeReceiveAuditEventLadonNexusIM;
    }

    public PegacornStandardIPCPort getEdgeReceiveAuditEventCommunicateIrisIM() {
        return edgeReceiveAuditEventCommunicateIrisIM;
    }

    public void setEdgeReceiveAuditEventCommunicateIrisIM(PegacornStandardIPCPort edgeReceiveAuditEventCommunicateIrisIM) {
        this.edgeReceiveAuditEventCommunicateIrisIM = edgeReceiveAuditEventCommunicateIrisIM;
    }

    public PegacornStandardIPCPort getEdgeReceiveAuditEventFHIRBreakLDAPScanner() {
        return edgeReceiveAuditEventFHIRBreakLDAPScanner;
    }

    public void setEdgeReceiveAuditEventFHIRBreakLDAPScanner(PegacornStandardIPCPort edgeReceiveAuditEventFHIRBreakLDAPScanner) {
        this.edgeReceiveAuditEventFHIRBreakLDAPScanner = edgeReceiveAuditEventFHIRBreakLDAPScanner;
    }

    public PegacornStandardIPCPort getEdgeReceiveAuditEventFHIRBreakEmailGateway() {
        return edgeReceiveAuditEventFHIRBreakEmailGateway;
    }

    public void setEdgeReceiveAuditEventFHIRBreakEmailGateway(PegacornStandardIPCPort edgeReceiveAuditEventFHIRBreakEmailGateway) {
        this.edgeReceiveAuditEventFHIRBreakEmailGateway = edgeReceiveAuditEventFHIRBreakEmailGateway;
    }

    public PegacornStandardIPCPort getEdgeReceiveAuditEventMITaFSMSGateway() {
        return edgeReceiveAuditEventMITaFSMSGateway;
    }

    public void setEdgeReceiveAuditEventMITaFSMSGateway(PegacornStandardIPCPort edgeReceiveAuditEventMITaFSMSGateway) {
        this.edgeReceiveAuditEventMITaFSMSGateway = edgeReceiveAuditEventMITaFSMSGateway;
    }
}
