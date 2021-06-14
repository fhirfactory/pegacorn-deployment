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
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.common.StandardPortSegment;


public abstract class HestiaAuditIMPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile {

    private HTTPIPCPortSegment edgeAnswer;
    private StandardPortSegment edgeReceiveAuditEventLadonNexusIM;
    private StandardPortSegment edgeReceiveAuditEventCommunicateIrisIM;
    private StandardPortSegment edgeReceiveAuditEventFHIRBreakLDAPScanner;
    private StandardPortSegment edgeReceiveAuditEventFHIRBreakEmailGateway;
    private StandardPortSegment edgeReceiveAuditEventMITaFSMSGateway;

    public HTTPIPCPortSegment getEdgeAnswer() {
        return edgeAnswer;
    }

    public void setEdgeAnswer(HTTPIPCPortSegment edgeAnswer) {
        this.edgeAnswer = edgeAnswer;
    }

    public StandardPortSegment getEdgeReceiveAuditEventLadonNexusIM() {
        return edgeReceiveAuditEventLadonNexusIM;
    }

    public void setEdgeReceiveAuditEventLadonNexusIM(StandardPortSegment edgeReceiveAuditEventLadonNexusIM) {
        this.edgeReceiveAuditEventLadonNexusIM = edgeReceiveAuditEventLadonNexusIM;
    }

    public StandardPortSegment getEdgeReceiveAuditEventCommunicateIrisIM() {
        return edgeReceiveAuditEventCommunicateIrisIM;
    }

    public void setEdgeReceiveAuditEventCommunicateIrisIM(StandardPortSegment edgeReceiveAuditEventCommunicateIrisIM) {
        this.edgeReceiveAuditEventCommunicateIrisIM = edgeReceiveAuditEventCommunicateIrisIM;
    }

    public StandardPortSegment getEdgeReceiveAuditEventFHIRBreakLDAPScanner() {
        return edgeReceiveAuditEventFHIRBreakLDAPScanner;
    }

    public void setEdgeReceiveAuditEventFHIRBreakLDAPScanner(StandardPortSegment edgeReceiveAuditEventFHIRBreakLDAPScanner) {
        this.edgeReceiveAuditEventFHIRBreakLDAPScanner = edgeReceiveAuditEventFHIRBreakLDAPScanner;
    }

    public StandardPortSegment getEdgeReceiveAuditEventFHIRBreakEmailGateway() {
        return edgeReceiveAuditEventFHIRBreakEmailGateway;
    }

    public void setEdgeReceiveAuditEventFHIRBreakEmailGateway(StandardPortSegment edgeReceiveAuditEventFHIRBreakEmailGateway) {
        this.edgeReceiveAuditEventFHIRBreakEmailGateway = edgeReceiveAuditEventFHIRBreakEmailGateway;
    }

    public StandardPortSegment getEdgeReceiveAuditEventMITaFSMSGateway() {
        return edgeReceiveAuditEventMITaFSMSGateway;
    }

    public void setEdgeReceiveAuditEventMITaFSMSGateway(StandardPortSegment edgeReceiveAuditEventMITaFSMSGateway) {
        this.edgeReceiveAuditEventMITaFSMSGateway = edgeReceiveAuditEventMITaFSMSGateway;
    }
}
