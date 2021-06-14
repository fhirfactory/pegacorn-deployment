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
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.hestia.dam.im;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes.ClusterServiceDeliverySubsystemPropertyFile;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.common.StandardPortSegment;

public class HestiaDAMIMPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile {
    //
    // Edge :: Answer (FHIR API Server)
    //
    private HTTPIPCPortSegment edgeAnswer;
    //
    // Edge :: Receive (FHIR::Media Receivers)
    //
    private StandardPortSegment edgeReceiveMediaLadonNexusIM;
    private StandardPortSegment edgeReceiveMediaCommunicateIrisIM;
    private StandardPortSegment edgeReceiveMediaFHIRBreakLDAPScanner;
    private StandardPortSegment edgeReceiveMediaFHIRBreakEmailGateway;
    private StandardPortSegment edgeReceiveMediaMITaFSMSGateway;

    public HTTPIPCPortSegment getEdgeAnswer() {
        return edgeAnswer;
    }

    public void setEdgeAnswer(HTTPIPCPortSegment edgeAnswer) {
        this.edgeAnswer = edgeAnswer;
    }

    public StandardPortSegment getEdgeReceiveMediaLadonNexusIM() {
        return edgeReceiveMediaLadonNexusIM;
    }

    public void setEdgeReceiveMediaLadonNexusIM(StandardPortSegment edgeReceiveMediaLadonNexusIM) {
        this.edgeReceiveMediaLadonNexusIM = edgeReceiveMediaLadonNexusIM;
    }

    public StandardPortSegment getEdgeReceiveMediaCommunicateIrisIM() {
        return edgeReceiveMediaCommunicateIrisIM;
    }

    public void setEdgeReceiveMediaCommunicateIrisIM(StandardPortSegment edgeReceiveMediaCommunicateIrisIM) {
        this.edgeReceiveMediaCommunicateIrisIM = edgeReceiveMediaCommunicateIrisIM;
    }

    public StandardPortSegment getEdgeReceiveMediaFHIRBreakLDAPScanner() {
        return edgeReceiveMediaFHIRBreakLDAPScanner;
    }

    public void setEdgeReceiveMediaFHIRBreakLDAPScanner(StandardPortSegment edgeReceiveMediaFHIRBreakLDAPScanner) {
        this.edgeReceiveMediaFHIRBreakLDAPScanner = edgeReceiveMediaFHIRBreakLDAPScanner;
    }

    public StandardPortSegment getEdgeReceiveMediaFHIRBreakEmailGateway() {
        return edgeReceiveMediaFHIRBreakEmailGateway;
    }

    public void setEdgeReceiveMediaFHIRBreakEmailGateway(StandardPortSegment edgeReceiveMediaFHIRBreakEmailGateway) {
        this.edgeReceiveMediaFHIRBreakEmailGateway = edgeReceiveMediaFHIRBreakEmailGateway;
    }

    public StandardPortSegment getEdgeReceiveMediaMITaFSMSGateway() {
        return edgeReceiveMediaMITaFSMSGateway;
    }

    public void setEdgeReceiveMediaMITaFSMSGateway(StandardPortSegment edgeReceiveMediaMITaFSMSGateway) {
        this.edgeReceiveMediaMITaFSMSGateway = edgeReceiveMediaMITaFSMSGateway;
    }
}
