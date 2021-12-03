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

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCServerPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsInterZoneRepeaterClientPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsKubernetesPodPortSegment;

public class PetasosEnabledSubsystemPropertyFile extends ClusterServiceDeliverySubsystemPropertyFile{
    private JGroupsInterZoneRepeaterClientPortSegment interZoneIPC;
    private JGroupsKubernetesPodPortSegment intraZoneIPC;
    private JGroupsInterZoneRepeaterClientPortSegment interZoneTopology;
    private JGroupsKubernetesPodPortSegment intraZoneTopology;
    private JGroupsInterZoneRepeaterClientPortSegment interZoneSubscriptions;
    private JGroupsKubernetesPodPortSegment intraZoneSubscriptions;
    private JGroupsInterZoneRepeaterClientPortSegment interZoneAudit;
    private JGroupsKubernetesPodPortSegment intraZoneAudit;
    private JGroupsInterZoneRepeaterClientPortSegment interZoneInterception;
    private JGroupsKubernetesPodPortSegment intraZoneInterception;
    private JGroupsInterZoneRepeaterClientPortSegment interZoneTasking;
    private JGroupsKubernetesPodPortSegment intraZoneTasking;
    private JGroupsInterZoneRepeaterClientPortSegment multiZoneInfinispan;
    private JGroupsKubernetesPodPortSegment intraZoneMetrics;
    private JGroupsInterZoneRepeaterClientPortSegment interZoneMetrics;
    private HTTPIPCServerPortSegment edgeAnswer;
    private HTTPIPCClientPortSegment edgeAsk;

    //
    // Constructor(s)
    //

    public PetasosEnabledSubsystemPropertyFile(){
        super();
        edgeAnswer = new HTTPIPCServerPortSegment();
        edgeAsk = new HTTPIPCClientPortSegment();
        interZoneIPC = new JGroupsInterZoneRepeaterClientPortSegment();
        intraZoneIPC = new JGroupsKubernetesPodPortSegment();
        interZoneTopology = new JGroupsInterZoneRepeaterClientPortSegment();
        intraZoneTopology = new JGroupsKubernetesPodPortSegment();
        interZoneSubscriptions = new JGroupsInterZoneRepeaterClientPortSegment();
        intraZoneSubscriptions = new JGroupsKubernetesPodPortSegment();
        interZoneAudit = new JGroupsInterZoneRepeaterClientPortSegment();
        intraZoneAudit= new JGroupsKubernetesPodPortSegment();
        interZoneInterception = new JGroupsInterZoneRepeaterClientPortSegment();
        intraZoneInterception = new JGroupsKubernetesPodPortSegment();
        interZoneTasking = new JGroupsInterZoneRepeaterClientPortSegment();
        intraZoneTasking = new JGroupsKubernetesPodPortSegment();
        multiZoneInfinispan = new JGroupsInterZoneRepeaterClientPortSegment();
        interZoneMetrics = new JGroupsInterZoneRepeaterClientPortSegment();
        intraZoneMetrics = new JGroupsKubernetesPodPortSegment();
    }

    //
    // Getters and Setters
    //

    public JGroupsInterZoneRepeaterClientPortSegment getInterZoneSubscriptions() {
        return interZoneSubscriptions;
    }

    public void setInterZoneSubscriptions(JGroupsInterZoneRepeaterClientPortSegment interZoneSubscriptions) {
        this.interZoneSubscriptions = interZoneSubscriptions;
    }

    public JGroupsKubernetesPodPortSegment getIntraZoneSubscriptions() {
        return intraZoneSubscriptions;
    }

    public void setIntraZoneSubscriptions(JGroupsKubernetesPodPortSegment intraZoneSubscriptions) {
        this.intraZoneSubscriptions = intraZoneSubscriptions;
    }

    public HTTPIPCClientPortSegment getEdgeAsk() {
        return edgeAsk;
    }

    public void setEdgeAsk(HTTPIPCClientPortSegment edgeAsk) {
        this.edgeAsk = edgeAsk;
    }

    public JGroupsInterZoneRepeaterClientPortSegment getInterZoneAudit() {
        return interZoneAudit;
    }

    public void setInterZoneAudit(JGroupsInterZoneRepeaterClientPortSegment interZoneAudit) {
        this.interZoneAudit = interZoneAudit;
    }

    public JGroupsKubernetesPodPortSegment getIntraZoneAudit() {
        return intraZoneAudit;
    }

    public void setIntraZoneAudit(JGroupsKubernetesPodPortSegment intraZoneAudit) {
        this.intraZoneAudit = intraZoneAudit;
    }

    public JGroupsInterZoneRepeaterClientPortSegment getInterZoneInterception() {
        return interZoneInterception;
    }

    public void setInterZoneInterception(JGroupsInterZoneRepeaterClientPortSegment interZoneInterception) {
        this.interZoneInterception = interZoneInterception;
    }

    public JGroupsKubernetesPodPortSegment getIntraZoneInterception() {
        return intraZoneInterception;
    }

    public void setIntraZoneInterception(JGroupsKubernetesPodPortSegment intraZoneInterception) {
        this.intraZoneInterception = intraZoneInterception;
    }

    public JGroupsInterZoneRepeaterClientPortSegment getInterZoneTasking() {
        return interZoneTasking;
    }

    public void setInterZoneTasking(JGroupsInterZoneRepeaterClientPortSegment interZoneTasking) {
        this.interZoneTasking = interZoneTasking;
    }

    public JGroupsKubernetesPodPortSegment getIntraZoneTasking() {
        return intraZoneTasking;
    }

    public void setIntraZoneTasking(JGroupsKubernetesPodPortSegment intraZoneTasking) {
        this.intraZoneTasking = intraZoneTasking;
    }

    public JGroupsInterZoneRepeaterClientPortSegment getMultiZoneInfinispan() {
        return multiZoneInfinispan;
    }

    public void setMultiZoneInfinispan(JGroupsInterZoneRepeaterClientPortSegment multiZoneInfinispan) {
        this.multiZoneInfinispan = multiZoneInfinispan;
    }

    public JGroupsInterZoneRepeaterClientPortSegment getInterZoneMetrics() {
        return interZoneMetrics;
    }

    public void setInterZoneMetrics(JGroupsInterZoneRepeaterClientPortSegment interZoneMetrics) {
        this.interZoneMetrics = interZoneMetrics;
    }

    public JGroupsKubernetesPodPortSegment getIntraZoneMetrics() {
        return intraZoneMetrics;
    }

    public void setIntraZoneMetrics(JGroupsKubernetesPodPortSegment intraZoneMetrics) {
        this.intraZoneMetrics = intraZoneMetrics;
    }

    public JGroupsInterZoneRepeaterClientPortSegment getInterZoneIPC() {
        return interZoneIPC;
    }

    public void setInterZoneIPC(JGroupsInterZoneRepeaterClientPortSegment interZoneIPC) {
        this.interZoneIPC = interZoneIPC;
    }

    public HTTPIPCServerPortSegment getEdgeAnswer() {
        return edgeAnswer;
    }

    public void setEdgeAnswer(HTTPIPCServerPortSegment edgeAnswer) {
        this.edgeAnswer = edgeAnswer;
    }

    public JGroupsKubernetesPodPortSegment getIntraZoneIPC() {
        return intraZoneIPC;
    }

    public void setIntraZoneIPC(JGroupsKubernetesPodPortSegment intraZoneIPC) {
        this.intraZoneIPC = intraZoneIPC;
    }

    public JGroupsInterZoneRepeaterClientPortSegment getInterZoneTopology() {
        return interZoneTopology;
    }

    public void setInterZoneTopology(JGroupsInterZoneRepeaterClientPortSegment interZoneTopology) {
        this.interZoneTopology = interZoneTopology;
    }

    public JGroupsKubernetesPodPortSegment getIntraZoneTopology() {
        return intraZoneTopology;
    }

    public void setIntraZoneTopology(JGroupsKubernetesPodPortSegment intraZoneTopology) {
        this.intraZoneTopology = intraZoneTopology;
    }

    //
    // To String
    //

    @Override
    public String toString() {
        return "PetasosEnabledSubsystemPropertyFile{" +
                "kubeReadinessProbe=" + getKubeReadinessProbe() +
                ", kubeLivelinessProbe=" + getKubeLivelinessProbe() +
                ", prometheusPort=" + getPrometheusPort() +
                ", jolokiaPort=" + getJolokiaPort() +
                ", subsystemInstant=" + getSubsystemInstant() +
                ", deploymentMode=" + getDeploymentMode() +
                ", deploymentSites=" + getDeploymentSites() +
                ", subsystemImageProperties=" + getSubsystemImageProperties() +
                ", trustStorePassword=" + getTrustStorePassword() +
                ", keyPassword=" + getKeyPassword() +
                ", deploymentZone=" + getDeploymentZone() +
                ", defaultServicePortLowerBound=" + getDefaultServicePortLowerBound() +
                ", loadBalancer=" + getLoadBalancer() +
                ", volumeMounts=" + getVolumeMounts() +
                ", debugProperties=" + getDebugProperties() +
                ", hapiAPIKey=" + getHapiAPIKey() +
                ", javaDeploymentParameters=" + getJavaDeploymentParameters() +
                ", interZoneIPC=" + interZoneIPC +
                ", intraZoneIPC=" + intraZoneIPC +
                ", interZoneTopology=" + interZoneTopology +
                ", intraZoneTopology=" + intraZoneTopology +
                ", interZoneSubscriptions=" + interZoneSubscriptions +
                ", intraZoneSubscriptions=" + intraZoneSubscriptions +
                ", interZoneAudit=" + interZoneAudit +
                ", intraZoneAudit=" + intraZoneAudit +
                ", interZoneInterception=" + interZoneInterception +
                ", intraZoneInterception=" + intraZoneInterception +
                ", interZoneTasks=" + interZoneTasking +
                ", intraZoneTasks=" + intraZoneTasking +
                ", multiZoneInfinispan=" + multiZoneInfinispan +
                ", interZoneMetrics=" + interZoneMetrics +
                ", intraZoneMetrics=" + intraZoneMetrics +
                ", edgeAnswer=" + edgeAnswer +
                ", edgeAsk=" + edgeAsk +
                '}';
    }
}
