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

public class SubsystemInstanceSegment {
    private String owningOrganizationName;
    private String solutionName;
    private String subsystemName;
    private String subsystemVersion;
    private String externalisedServiceName;
    private String externalisedServiceDNSName;
    private String externalisedServiceEndpointName;
    private String externalisedServiceHost;
    private String clusterServiceName;
    private String clusterServiceDNSName;
    private String processingPlantName;
    private String processingPlantDNSName;
    private String processingPlantVersion;
    private String solutionGroup;
    private String solutionDescription;

    /* an update */

    public SubsystemInstanceSegment(){
        owningOrganizationName = null;
        solutionName = null;
        subsystemName = null;
        subsystemVersion = null;
        externalisedServiceName = null;
        externalisedServiceEndpointName = null;
        externalisedServiceDNSName = null;
        clusterServiceName = null;
        processingPlantName = null;
        processingPlantVersion = null;
        solutionGroup = null;
        solutionDescription = null;
        externalisedServiceHost = null;
    }

    public String getOwningOrganizationName() {
        return owningOrganizationName;
    }

    public void setOwningOrganizationName(String owningOrganizationName) {
        this.owningOrganizationName = owningOrganizationName;
    }

    public String getSolutionGroup() {
        return solutionGroup;
    }

    public void setSolutionGroup(String solutionGroup) {
        this.solutionGroup = solutionGroup;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public String getSubsystemVersion() {
        return subsystemVersion;
    }

    public void setSubsystemVersion(String subsystemVersion) {
        this.subsystemVersion = subsystemVersion;
    }

    public String getExternalisedServiceName() {
        return externalisedServiceName;
    }

    public void setExternalisedServiceName(String externalisedServiceName) {
        this.externalisedServiceName = externalisedServiceName;
    }

    public String getExternalisedServiceDNSName() {
        return externalisedServiceDNSName;
    }

    public void setExternalisedServiceDNSName(String externalisedServiceDNSName) {
        this.externalisedServiceDNSName = externalisedServiceDNSName;
    }

    public String getExternalisedServiceEndpointName() {
        return externalisedServiceEndpointName;
    }

    public void setExternalisedServiceEndpointName(String externalisedServiceEndpointName) {
        this.externalisedServiceEndpointName = externalisedServiceEndpointName;
    }

    public String getClusterServiceName() {
        return clusterServiceName;
    }

    public void setClusterServiceName(String clusterServiceName) {
        this.clusterServiceName = clusterServiceName;
    }

    public String getClusterServiceDNSName() {
        return clusterServiceDNSName;
    }

    public void setClusterServiceDNSName(String clusterServiceDNSName) {
        this.clusterServiceDNSName = clusterServiceDNSName;
    }

    public String getProcessingPlantName() {
        return processingPlantName;
    }

    public void setProcessingPlantName(String processingPlantName) {
        this.processingPlantName = processingPlantName;
    }

    public String getProcessingPlantDNSName() {
        return processingPlantDNSName;
    }

    public void setProcessingPlantDNSName(String processingPlantDNSName) {
        this.processingPlantDNSName = processingPlantDNSName;
    }

    public String getProcessingPlantVersion() {
        return processingPlantVersion;
    }

    public void setProcessingPlantVersion(String processingPlantVersion) {
        this.processingPlantVersion = processingPlantVersion;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }

    public String getExternalisedServiceHost() {
        return externalisedServiceHost;
    }

    public void setExternalisedServiceHost(String externalisedServiceHost) {
        this.externalisedServiceHost = externalisedServiceHost;
    }

    @Override
    public String toString() {
        return "SubsystemInstanceSegment{" +
                "owningOrganizationName='" + owningOrganizationName + '\'' +
                ", solutionName='" + solutionName + '\'' +
                ", subsystemName='" + subsystemName + '\'' +
                ", subsystemVersion='" + subsystemVersion + '\'' +
                ", externalisedServiceName='" + externalisedServiceName + '\'' +
                ", externalisedServiceDNSName='" + externalisedServiceDNSName + '\'' +
                ", externalisedServiceEndpointName='" + externalisedServiceEndpointName + '\'' +
                ", externalisedServiceHost='" + externalisedServiceHost + '\'' +
                ", clusterServiceName='" + clusterServiceName + '\'' +
                ", clusterServiceDNSName='" + clusterServiceDNSName + '\'' +
                ", processingPlantName='" + processingPlantName + '\'' +
                ", processingPlantDNSName='" + processingPlantDNSName + '\'' +
                ", processingPlantVersion='" + processingPlantVersion + '\'' +
                ", solutionGroup='" + solutionGroup + '\'' +
                ", solutionDescription='" + solutionDescription + '\'' +
                '}';
    }
}
