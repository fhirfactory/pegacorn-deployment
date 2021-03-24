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
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornHTTPIPCPort;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.PegacornStandardIPCPort;

public class HestiaDAMIMPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile {
    //
    // Edge :: Answer (FHIR API Server)
    //
    private PegacornHTTPIPCPort edgeAnswer;
    //
    // Edge :: Receive (FHIR::Media Receivers)
    //
    private PegacornStandardIPCPort edgeReceiveMediaLadonNexusIM;
    private PegacornStandardIPCPort edgeReceiveMediaCommunicateIrisIM;
    private PegacornStandardIPCPort edgeReceiveMediaFHIRBreakLDAPScanner;
    private PegacornStandardIPCPort edgeReceiveMediaFHIRBreakEmailGateway;
    private PegacornStandardIPCPort edgeReceiveMediaMITaFSMSGateway;

    public PegacornHTTPIPCPort getEdgeAnswer() {
        return edgeAnswer;
    }

    public void setEdgeAnswer(PegacornHTTPIPCPort edgeAnswer) {
        this.edgeAnswer = edgeAnswer;
    }

    public PegacornStandardIPCPort getEdgeReceiveMediaLadonNexusIM() {
        return edgeReceiveMediaLadonNexusIM;
    }

    public void setEdgeReceiveMediaLadonNexusIM(PegacornStandardIPCPort edgeReceiveMediaLadonNexusIM) {
        this.edgeReceiveMediaLadonNexusIM = edgeReceiveMediaLadonNexusIM;
    }

    public PegacornStandardIPCPort getEdgeReceiveMediaCommunicateIrisIM() {
        return edgeReceiveMediaCommunicateIrisIM;
    }

    public void setEdgeReceiveMediaCommunicateIrisIM(PegacornStandardIPCPort edgeReceiveMediaCommunicateIrisIM) {
        this.edgeReceiveMediaCommunicateIrisIM = edgeReceiveMediaCommunicateIrisIM;
    }

    public PegacornStandardIPCPort getEdgeReceiveMediaFHIRBreakLDAPScanner() {
        return edgeReceiveMediaFHIRBreakLDAPScanner;
    }

    public void setEdgeReceiveMediaFHIRBreakLDAPScanner(PegacornStandardIPCPort edgeReceiveMediaFHIRBreakLDAPScanner) {
        this.edgeReceiveMediaFHIRBreakLDAPScanner = edgeReceiveMediaFHIRBreakLDAPScanner;
    }

    public PegacornStandardIPCPort getEdgeReceiveMediaFHIRBreakEmailGateway() {
        return edgeReceiveMediaFHIRBreakEmailGateway;
    }

    public void setEdgeReceiveMediaFHIRBreakEmailGateway(PegacornStandardIPCPort edgeReceiveMediaFHIRBreakEmailGateway) {
        this.edgeReceiveMediaFHIRBreakEmailGateway = edgeReceiveMediaFHIRBreakEmailGateway;
    }

    public PegacornStandardIPCPort getEdgeReceiveMediaMITaFSMSGateway() {
        return edgeReceiveMediaMITaFSMSGateway;
    }

    public void setEdgeReceiveMediaMITaFSMSGateway(PegacornStandardIPCPort edgeReceiveMediaMITaFSMSGateway) {
        this.edgeReceiveMediaMITaFSMSGateway = edgeReceiveMediaMITaFSMSGateway;
    }
}
