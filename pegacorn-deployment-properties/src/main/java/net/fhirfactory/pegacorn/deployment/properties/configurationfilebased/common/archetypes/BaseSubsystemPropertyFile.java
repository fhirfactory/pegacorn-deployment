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

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.*;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.standard.HTTPProcessingPlantPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.HTTPIPCPortSegment;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc.JGroupsIPCPortSegment;
import org.slf4j.Logger;

public class BaseSubsystemPropertyFile {

    private SubsystemInstanceSegment subsystemInstant;
    private DeploymentModeSegment deploymentMode;
    private DeploymentSiteSegment deploymentSites;
    private HTTPProcessingPlantPortSegment kubeReadinessProbe;
    private HTTPProcessingPlantPortSegment kubeLivelinessProbe;
    private HTTPProcessingPlantPortSegment prometheusPort;
    private HTTPProcessingPlantPortSegment jolokiaPort;

    private SubsystemImageSegment subsystemImageProperties;
    private SecurityCredentialSegment trustStorePassword;
    private SecurityCredentialSegment keyPassword;

    public BaseSubsystemPropertyFile() {
        subsystemInstant = new SubsystemInstanceSegment();
        deploymentMode = new DeploymentModeSegment();
        deploymentSites = new DeploymentSiteSegment();
        kubeLivelinessProbe = new HTTPProcessingPlantPortSegment();
        kubeReadinessProbe = new HTTPProcessingPlantPortSegment();
        subsystemImageProperties = new SubsystemImageSegment();
        trustStorePassword = new SecurityCredentialSegment();
        keyPassword = new SecurityCredentialSegment();
        jolokiaPort = new HTTPProcessingPlantPortSegment();
        prometheusPort = new HTTPProcessingPlantPortSegment();
    }

    public HTTPProcessingPlantPortSegment getKubeReadinessProbe() {
        return kubeReadinessProbe;
    }

    public void setKubeReadinessProbe(HTTPProcessingPlantPortSegment kubeReadinessProbe) {
        this.kubeReadinessProbe = kubeReadinessProbe;
    }

    public HTTPProcessingPlantPortSegment getKubeLivelinessProbe() {
        return kubeLivelinessProbe;
    }

    public void setKubeLivelinessProbe(HTTPProcessingPlantPortSegment kubeLivelinessProbe) {
        this.kubeLivelinessProbe = kubeLivelinessProbe;
    }

    public HTTPProcessingPlantPortSegment getPrometheusPort() {
        return prometheusPort;
    }

    public void setPrometheusPort(HTTPProcessingPlantPortSegment prometheusPort) {
        this.prometheusPort = prometheusPort;
    }

    public HTTPProcessingPlantPortSegment getJolokiaPort() {
        return jolokiaPort;
    }

    public void setJolokiaPort(HTTPProcessingPlantPortSegment jolokiaPort) {
        this.jolokiaPort = jolokiaPort;
    }

    public SubsystemInstanceSegment getSubsystemInstant() {
        return subsystemInstant;
    }

    public void setSubsystemInstant(SubsystemInstanceSegment subsystemInstant) {
        this.subsystemInstant = subsystemInstant;
    }

    public DeploymentModeSegment getDeploymentMode() {
        return deploymentMode;
    }

    public void setDeploymentMode(DeploymentModeSegment deploymentMode) {
        this.deploymentMode = deploymentMode;
    }

    public DeploymentSiteSegment getDeploymentSites() {
        return deploymentSites;
    }

    public void setDeploymentSites(DeploymentSiteSegment deploymentSites) {
        this.deploymentSites = deploymentSites;
    }


    public SubsystemImageSegment getSubsystemImageProperties() {
        return subsystemImageProperties;
    }

    public void setSubsystemImageProperties(SubsystemImageSegment subsystemImageProperties) {
        this.subsystemImageProperties = subsystemImageProperties;
    }

    public SecurityCredentialSegment getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(SecurityCredentialSegment trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public SecurityCredentialSegment getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(SecurityCredentialSegment keyPassword) {
        this.keyPassword = keyPassword;
    }

    @Override
    public String toString() {
        return "BaseSubsystemPropertyFile{" +
                "subsystemInstant=" + subsystemInstant +
                ", deploymentMode=" + deploymentMode +
                ", deploymentSites=" + deploymentSites +
                ", kubeReadinessProbe=" + kubeReadinessProbe +
                ", kubeLivelinessProbe=" + kubeLivelinessProbe +
                ", prometheusPort=" + prometheusPort +
                ", jolokiaPort=" + jolokiaPort +
                ", subsystemImageProperties=" + subsystemImageProperties +
                ", trustStorePassword=" + trustStorePassword +
                ", keyPassword=" + keyPassword +
                "," + super.toString() +
                '}';
    }
}
