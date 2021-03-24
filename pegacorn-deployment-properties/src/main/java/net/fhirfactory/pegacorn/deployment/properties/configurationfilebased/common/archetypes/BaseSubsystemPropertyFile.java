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
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.StandardProcessingPlantPort;

public class BaseSubsystemPropertyFile {
    private SubsystemInstanceDetails subsystemInstant;
    private DeploymentModeProperties deploymentMode;
    private DeploymentSiteProperties deploymentSites;
    private StandardProcessingPlantPort kubeReadinessProbe;
    private StandardProcessingPlantPort kubeLivelinessProbe;
    private StandardProcessingPlantPort prometheusPort;
    private StandardProcessingPlantPort jolokiaPort;
    private SubsystemImageProperties subsystemImageProperties;
    private SecurityCredential trustStorePassword;
    private SecurityCredential keyPassword;

    public BaseSubsystemPropertyFile() {
        subsystemInstant = new SubsystemInstanceDetails();
        deploymentMode = new DeploymentModeProperties();
        deploymentSites = new DeploymentSiteProperties();
        kubeLivelinessProbe = new StandardProcessingPlantPort();
        kubeReadinessProbe = new StandardProcessingPlantPort();
        subsystemImageProperties = new SubsystemImageProperties();
        trustStorePassword = new SecurityCredential();
        keyPassword = new SecurityCredential();
        jolokiaPort = new StandardProcessingPlantPort();
        prometheusPort = new StandardProcessingPlantPort();
    }

    public StandardProcessingPlantPort getPrometheusPort() {
        return prometheusPort;
    }

    public void setPrometheusPort(StandardProcessingPlantPort prometheusPort) {
        this.prometheusPort = prometheusPort;
    }

    public StandardProcessingPlantPort getJolokiaPort() {
        return jolokiaPort;
    }

    public void setJolokiaPort(StandardProcessingPlantPort jolokiaPort) {
        this.jolokiaPort = jolokiaPort;
    }

    public SubsystemInstanceDetails getSubsystemInstant() {
        return subsystemInstant;
    }

    public void setSubsystemInstant(SubsystemInstanceDetails subsystemInstant) {
        this.subsystemInstant = subsystemInstant;
    }

    public DeploymentModeProperties getDeploymentMode() {
        return deploymentMode;
    }

    public void setDeploymentMode(DeploymentModeProperties deploymentMode) {
        this.deploymentMode = deploymentMode;
    }

    public DeploymentSiteProperties getDeploymentSites() {
        return deploymentSites;
    }

    public void setDeploymentSites(DeploymentSiteProperties deploymentSites) {
        this.deploymentSites = deploymentSites;
    }

    public StandardProcessingPlantPort getKubeReadinessProbe() {
        return kubeReadinessProbe;
    }

    public void setKubeReadinessProbe(StandardProcessingPlantPort kubeReadinessProbe) {
        this.kubeReadinessProbe = kubeReadinessProbe;
    }

    public StandardProcessingPlantPort getKubeLivelinessProbe() {
        return kubeLivelinessProbe;
    }

    public void setKubeLivelinessProbe(StandardProcessingPlantPort kubeLivelinessProbe) {
        this.kubeLivelinessProbe = kubeLivelinessProbe;
    }

    public SubsystemImageProperties getSubsystemImageProperties() {
        return subsystemImageProperties;
    }

    public void setSubsystemImageProperties(SubsystemImageProperties subsystemImageProperties) {
        this.subsystemImageProperties = subsystemImageProperties;
    }

    public SecurityCredential getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(SecurityCredential trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public SecurityCredential getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(SecurityCredential keyPassword) {
        this.keyPassword = keyPassword;
    }
}
