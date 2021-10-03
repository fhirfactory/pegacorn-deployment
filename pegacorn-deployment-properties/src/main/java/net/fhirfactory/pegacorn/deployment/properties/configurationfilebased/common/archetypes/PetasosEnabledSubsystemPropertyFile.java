/*
 * Copyright (c) 2021 Mark A. Hunter (ACT Health)
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
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.archetypes;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsIPCServerPortSegment;

public class PetasosEnabledSubsystemPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile{
    private JGroupsIPCServerPortSegment interZoneIPC;
    private JGroupsIPCServerPortSegment intraZoneIPC;
    private JGroupsIPCServerPortSegment interZoneOAM;
    private JGroupsIPCServerPortSegment intraZoneOAM;
    private HTTPIPCServerPortSegment edgeAnswer;

    public PetasosEnabledSubsystemPropertyFile(){
        super();
        edgeAnswer = new HTTPIPCServerPortSegment();
        interZoneIPC = new JGroupsIPCServerPortSegment();
        intraZoneIPC = new JGroupsIPCServerPortSegment();
        interZoneOAM = new JGroupsIPCServerPortSegment();
        intraZoneOAM = new JGroupsIPCServerPortSegment();
    }

    public JGroupsIPCServerPortSegment getInterZoneIPC() {
        return interZoneIPC;
    }

    public void setInterZoneIPC(JGroupsIPCServerPortSegment interZoneIPC) {
        this.interZoneIPC = interZoneIPC;
    }

    public HTTPIPCServerPortSegment getEdgeAnswer() {
        return edgeAnswer;
    }

    public void setEdgeAnswer(HTTPIPCServerPortSegment edgeAnswer) {
        this.edgeAnswer = edgeAnswer;
    }

    public JGroupsIPCServerPortSegment getIntraZoneIPC() {
        return intraZoneIPC;
    }

    public void setIntraZoneIPC(JGroupsIPCServerPortSegment intraZoneIPC) {
        this.intraZoneIPC = intraZoneIPC;
    }

    public JGroupsIPCServerPortSegment getInterZoneOAM() {
        return interZoneOAM;
    }

    public void setInterZoneOAM(JGroupsIPCServerPortSegment interZoneOAM) {
        this.interZoneOAM = interZoneOAM;
    }

    public JGroupsIPCServerPortSegment getIntraZoneOAM() {
        return intraZoneOAM;
    }

    public void setIntraZoneOAM(JGroupsIPCServerPortSegment intraZoneOAM) {
        this.intraZoneOAM = intraZoneOAM;
    }

    @Override
    public String toString() {
        return "PetasosEnabledSubsystemPropertyFile{" +
                "defaultServicePortLowerBound=" + getDefaultServicePortLowerBound() +
                ", loadBalancer=" + getLoadBalancer() +
                ", volumeMounts=" + getVolumeMounts() +
                ", debugProperties=" + getDebugProperties() +
                ", hapiAPIKey=" + getHapiAPIKey() +
                ", javaDeploymentParameters=" + getJavaDeploymentParameters() +
                ", subsystemInstant=" + getSubsystemInstant() +
                ", deploymentMode=" + getDeploymentMode() +
                ", deploymentSites=" + getDeploymentSites() +
                ", kubeReadinessProbe=" + getKubeReadinessProbe() +
                ", kubeLivelinessProbe=" + getKubeLivelinessProbe() +
                ", prometheusPort=" + getPrometheusPort() +
                ", jolokiaPort=" + getJolokiaPort() +
                ", subsystemImageProperties=" + getSubsystemImageProperties() +
                ", trustStorePassword=" + getTrustStorePassword() +
                ", keyPassword=" + getKeyPassword() +
                ", interZoneIPC=" + getInterZoneIPC() +
                ", intraZoneIPC=" + getIntraZoneIPC() +
                ", interZoneOAM=" + getInterZoneOAM() +
                ", intraZoneOAM=" + getIntraZoneOAM() +
                ", edgeAnswer=" + getEdgeAnswer() +
                '}';
    }
}
