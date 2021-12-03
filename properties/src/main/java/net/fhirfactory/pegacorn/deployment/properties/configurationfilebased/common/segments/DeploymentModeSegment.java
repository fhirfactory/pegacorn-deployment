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
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.datatypes.ParameterNameValuePairType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DeploymentModeSegment {
    private boolean kubernetes;
    private Integer processingPlantReplicationCount;
    private boolean concurrent;
    private boolean usingInternalEncryption;
    private String deploymentConfig;
    private String intraZoneIPCStackConfigFile;
    private String intraZoneTopologyStackConfigFile;
    private String intraZoneMetricsStackConfigFile;
    private String intraZoneInterceptionStackConfigFile;
    private String intraZoneSubscriptionsStackConfigFile;
    private String intraZoneTaskingStackConfigFile;
    private String intraZoneAuditStackConfigFile;
    private String interZoneIPCStackConfigFile;
    private String interZoneTopologyStackConfigFile;
    private String interZoneMetricsStackConfigFile;
    private String interZoneInterceptionStackConfigFile;
    private String interZoneSubscriptionStackConfigFile;
    private String interZoneTaskingStackConfigFile;
    private String interZoneAuditStackConfigFile;
    private String multiZoneInfinispanStackConfigFile;
    private List<ParameterNameValuePairType> otherDeploymentParameters;

    public DeploymentModeSegment(){
        kubernetes = false;
        processingPlantReplicationCount = 0;
        concurrent = false;
        usingInternalEncryption = false;
        deploymentConfig = null;
        this.intraZoneIPCStackConfigFile = null;
        this.intraZoneTopologyStackConfigFile = null;
        this.intraZoneMetricsStackConfigFile = null;
        this.intraZoneInterceptionStackConfigFile = null;
        this.intraZoneSubscriptionsStackConfigFile = null;
        this.intraZoneTaskingStackConfigFile = null;
        this.intraZoneAuditStackConfigFile = null;
        this.interZoneIPCStackConfigFile = null;
        this.interZoneTopologyStackConfigFile = null;
        this.interZoneMetricsStackConfigFile = null;
        this.interZoneInterceptionStackConfigFile = null;
        this.interZoneSubscriptionStackConfigFile = null;
        this.interZoneTaskingStackConfigFile = null;
        this.multiZoneInfinispanStackConfigFile = null;
        this.interZoneAuditStackConfigFile = null;
        this.otherDeploymentParameters = new ArrayList<>();
    }

    public void mergeOverrides(DeploymentModeSegment overrides){
        this.kubernetes = overrides.isKubernetes();
        this.concurrent = overrides.isConcurrent();
        this.usingInternalEncryption = overrides.isUsingInternalEncryption();
        if(overrides.getProcessingPlantReplicationCount() > 0){
            this.processingPlantReplicationCount = overrides.getProcessingPlantReplicationCount();
        }
        if(!StringUtils.isEmpty(overrides.getDeploymentConfig())){
            this.deploymentConfig = overrides.getDeploymentConfig();
        }
        if(!StringUtils.isEmpty(overrides.getInterZoneIPCStackConfigFile())){
            this.setInterZoneIPCStackConfigFile(overrides.getInterZoneIPCStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getMultiZoneInfinispanStackConfigFile())){
            this.setMultiZoneInfinispanStackConfigFile(overrides.getMultiZoneInfinispanStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getInterZoneInterceptionStackConfigFile())){
            this.setInterZoneInterceptionStackConfigFile(overrides.getInterZoneInterceptionStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getInterZoneMetricsStackConfigFile())){
            this.setInterZoneMetricsStackConfigFile(overrides.getInterZoneMetricsStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getInterZoneSubscriptionStackConfigFile())){
            this.setInterZoneSubscriptionStackConfigFile(overrides.getInterZoneSubscriptionStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getInterZoneTaskingStackConfigFile())){
            this.setInterZoneTaskingStackConfigFile(overrides.getInterZoneTaskingStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getInterZoneTopologyStackConfigFile())){
            this.setInterZoneTopologyStackConfigFile(overrides.getInterZoneTopologyStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getInterZoneAuditStackConfigFile())){
            this.setInterZoneAuditStackConfigFile(overrides.getInterZoneAuditStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getIntraZoneIPCStackConfigFile())){
            this.setIntraZoneIPCStackConfigFile(overrides.getIntraZoneIPCStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getIntraZoneInterceptionStackConfigFile())){
            this.setIntraZoneInterceptionStackConfigFile(overrides.getIntraZoneInterceptionStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getIntraZoneMetricsStackConfigFile())){
            this.setIntraZoneMetricsStackConfigFile(overrides.getIntraZoneMetricsStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getIntraZoneSubscriptionsStackConfigFile())){
            this.setIntraZoneSubscriptionsStackConfigFile(overrides.getIntraZoneSubscriptionsStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getIntraZoneTaskingStackConfigFile())){
            this.setIntraZoneTaskingStackConfigFile(overrides.getIntraZoneTaskingStackConfigFile());
        }
        if(!StringUtils.isEmpty(overrides.getIntraZoneAuditStackConfigFile())){
            this.setIntraZoneAuditStackConfigFile(overrides.getIntraZoneAuditStackConfigFile());
        }
    }

    //
    // Getters and Setters
    //

    public List<ParameterNameValuePairType> getOtherDeploymentParameters() {
        return otherDeploymentParameters;
    }

    public void setOtherDeploymentParameters(List<ParameterNameValuePairType> otherDeploymentParameters) {
        this.otherDeploymentParameters = otherDeploymentParameters;
    }

    public String getIntraZoneAuditStackConfigFile() {
        return intraZoneAuditStackConfigFile;
    }

    public void setIntraZoneAuditStackConfigFile(String intraZoneAuditStackConfigFile) {
        this.intraZoneAuditStackConfigFile = intraZoneAuditStackConfigFile;
    }

    public String getInterZoneAuditStackConfigFile() {
        return interZoneAuditStackConfigFile;
    }

    public void setInterZoneAuditStackConfigFile(String interZoneAuditStackConfigFile) {
        this.interZoneAuditStackConfigFile = interZoneAuditStackConfigFile;
    }

    public String getIntraZoneIPCStackConfigFile() {
        return intraZoneIPCStackConfigFile;
    }

    public void setIntraZoneIPCStackConfigFile(String intraZoneIPCStackConfigFile) {
        this.intraZoneIPCStackConfigFile = intraZoneIPCStackConfigFile;
    }

    public String getIntraZoneTopologyStackConfigFile() {
        return intraZoneTopologyStackConfigFile;
    }

    public void setIntraZoneTopologyStackConfigFile(String intraZoneTopologyStackConfigFile) {
        this.intraZoneTopologyStackConfigFile = intraZoneTopologyStackConfigFile;
    }

    public String getIntraZoneMetricsStackConfigFile() {
        return intraZoneMetricsStackConfigFile;
    }

    public void setIntraZoneMetricsStackConfigFile(String intraZoneMetricsStackConfigFile) {
        this.intraZoneMetricsStackConfigFile = intraZoneMetricsStackConfigFile;
    }

    public String getIntraZoneInterceptionStackConfigFile() {
        return intraZoneInterceptionStackConfigFile;
    }

    public void setIntraZoneInterceptionStackConfigFile(String intraZoneInterceptionStackConfigFile) {
        this.intraZoneInterceptionStackConfigFile = intraZoneInterceptionStackConfigFile;
    }

    public String getIntraZoneSubscriptionsStackConfigFile() {
        return intraZoneSubscriptionsStackConfigFile;
    }

    public void setIntraZoneSubscriptionsStackConfigFile(String intraZoneSubscriptionsStackConfigFile) {
        this.intraZoneSubscriptionsStackConfigFile = intraZoneSubscriptionsStackConfigFile;
    }

    public String getIntraZoneTaskingStackConfigFile() {
        return intraZoneTaskingStackConfigFile;
    }

    public void setIntraZoneTaskingStackConfigFile(String intraZoneTaskingStackConfigFile) {
        this.intraZoneTaskingStackConfigFile = intraZoneTaskingStackConfigFile;
    }

    public String getInterZoneIPCStackConfigFile() {
        return interZoneIPCStackConfigFile;
    }

    public void setInterZoneIPCStackConfigFile(String interZoneIPCStackConfigFile) {
        this.interZoneIPCStackConfigFile = interZoneIPCStackConfigFile;
    }

    public String getInterZoneTopologyStackConfigFile() {
        return interZoneTopologyStackConfigFile;
    }

    public void setInterZoneTopologyStackConfigFile(String interZoneTopologyStackConfigFile) {
        this.interZoneTopologyStackConfigFile = interZoneTopologyStackConfigFile;
    }

    public String getInterZoneMetricsStackConfigFile() {
        return interZoneMetricsStackConfigFile;
    }

    public void setInterZoneMetricsStackConfigFile(String interZoneMetricsStackConfigFile) {
        this.interZoneMetricsStackConfigFile = interZoneMetricsStackConfigFile;
    }

    public String getInterZoneInterceptionStackConfigFile() {
        return interZoneInterceptionStackConfigFile;
    }

    public void setInterZoneInterceptionStackConfigFile(String interZoneInterceptionStackConfigFile) {
        this.interZoneInterceptionStackConfigFile = interZoneInterceptionStackConfigFile;
    }

    public String getInterZoneSubscriptionStackConfigFile() {
        return interZoneSubscriptionStackConfigFile;
    }

    public void setInterZoneSubscriptionStackConfigFile(String interZoneSubscriptionStackConfigFile) {
        this.interZoneSubscriptionStackConfigFile = interZoneSubscriptionStackConfigFile;
    }

    public String getInterZoneTaskingStackConfigFile() {
        return interZoneTaskingStackConfigFile;
    }

    public void setInterZoneTaskingStackConfigFile(String interZoneTaskingStackConfigFile) {
        this.interZoneTaskingStackConfigFile = interZoneTaskingStackConfigFile;
    }

    public String getMultiZoneInfinispanStackConfigFile() {
        return multiZoneInfinispanStackConfigFile;
    }

    public void setMultiZoneInfinispanStackConfigFile(String multiZoneInfinispanStackConfigFile) {
        this.multiZoneInfinispanStackConfigFile = multiZoneInfinispanStackConfigFile;
    }

    public String getDeploymentConfig() {
        return deploymentConfig;
    }

    public void setDeploymentConfig(String deploymentConfig) {
        this.deploymentConfig = deploymentConfig;
    }

    public boolean isKubernetes() {
        return kubernetes;
    }

    public void setKubernetes(boolean kubernetes) {
        this.kubernetes = kubernetes;
    }

    public Integer getProcessingPlantReplicationCount() {
        return processingPlantReplicationCount;
    }

    public void setProcessingPlantReplicationCount(Integer processingPlantReplicationCount) {
        this.processingPlantReplicationCount = processingPlantReplicationCount;
    }

    public boolean isConcurrent() {
        return concurrent;
    }

    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

    public boolean isUsingInternalEncryption() {
        return usingInternalEncryption;
    }

    public void setUsingInternalEncryption(boolean usingInternalEncryption) {
        this.usingInternalEncryption = usingInternalEncryption;
    }

    //
    // To String
    //

    @Override
    public String toString() {
        return "DeploymentModeSegment{" +
                "kubernetes=" + kubernetes +
                ", processingPlantReplicationCount=" + processingPlantReplicationCount +
                ", concurrent=" + concurrent +
                ", usingInternalEncryption=" + usingInternalEncryption +
                ", deploymentConfig=" + deploymentConfig +
                ", intraZoneIPCStackConfigFile=" + intraZoneIPCStackConfigFile +
                ", intraZoneTopologyStackConfigFile=" + intraZoneTopologyStackConfigFile +
                ", intraZoneMetricsStackConfigFile=" + intraZoneMetricsStackConfigFile +
                ", intraZoneInterceptionStackConfigFile=" + intraZoneInterceptionStackConfigFile +
                ", intraZoneSubscriptionsStackConfigFile=" + intraZoneSubscriptionsStackConfigFile +
                ", intraZoneTaskingStackConfigFile=" + intraZoneTaskingStackConfigFile +
                ", intraZoneAuditStackConfigFile=" + intraZoneAuditStackConfigFile +
                ", interZoneIPCStackConfigFile=" + interZoneIPCStackConfigFile +
                ", interZoneTopologyStackConfigFile=" + interZoneTopologyStackConfigFile +
                ", interZoneMetricsStackConfigFile=" + interZoneMetricsStackConfigFile +
                ", interZoneInterceptionStackConfigFile=" + interZoneInterceptionStackConfigFile +
                ", interZoneSubscriptionStackConfigFile=" + interZoneSubscriptionStackConfigFile +
                ", interZoneTaskingStackConfigFile=" + interZoneTaskingStackConfigFile +
                ", multiZoneInfinispanStackConfigFile=" + multiZoneInfinispanStackConfigFile +
                ", interZoneAuditStackConfigFile=" + interZoneAuditStackConfigFile +
                '}';
    }
}
