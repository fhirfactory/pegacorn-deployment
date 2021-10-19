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
package net.fhirfactory.pegacorn.deployment.topology.model.endpoints.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternalFileShareIPCEndpoint extends IPCClusteredServerTopologyEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ExternalFileShareIPCEndpoint.class);
    private String fileShareName;
    private String fileShareProtocol;
    private String fileSharePath;
    private String fileShareServer;

    //
    // Constructor(s)
    //

    public ExternalFileShareIPCEndpoint(){
        super();
        this.fileShareName = null;
        this.fileShareProtocol = null;
        this.fileSharePath = null;
        this.fileShareServer = null;
    }

    //
    // Getters and Setters
    //

    public String getFileShareName() {
        return fileShareName;
    }

    public void setFileShareName(String fileShareName) {
        this.fileShareName = fileShareName;
    }

    public String getFileShareProtocol() {
        return fileShareProtocol;
    }

    public void setFileShareProtocol(String fileShareProtocol) {
        this.fileShareProtocol = fileShareProtocol;
    }

    public String getFileSharePath() {
        return fileSharePath;
    }

    public void setFileSharePath(String fileSharePath) {
        this.fileSharePath = fileSharePath;
    }

    public String getFileShareServer() {
        return fileShareServer;
    }

    public void setFileShareServer(String fileShareServer) {
        this.fileShareServer = fileShareServer;
    }

    @Override @JsonIgnore
    protected Logger getLogger() {
        return (LOG);
    }

    //
    // To String
    //


    @Override
    public String toString() {
        return "ExternalFileShareIPCEndpoint{" +
                "otherConfigParameters=" + getOtherConfigParameters() +
                ", kubernetesDeployed=" + isKubernetesDeployed() +
                ", nodeRDN=" + getNodeRDN() +
                ", componentType=" + getComponentType() +
                ", componentId=" + getComponentId() +
                ", parentNode=" + getParentNode() +
                ", concurrencyMode=" + getConcurrencyMode() +
                ", resilienceMode=" + getResilienceMode() +
                ", securityZone=" + getSecurityZone() +
                ", otherConfigurationParameters=" + getOtherConfigurationParameters() +
                ", actualHostIP='" + getActualHostIP() + '\'' +
                ", actualPodIP='" + getActualPodIP() + '\'' +
                ", fileShareName='" + fileShareName + '\'' +
                ", fileShareProtocol='" + fileShareProtocol + '\'' +
                ", fileSharePath='" + fileSharePath + '\'' +
                ", fileShareServer='" + fileShareServer + '\'' +
                ", supportedInterfaceSet=" + getSupportedInterfaceSet() +
                ", portValue=" + getPortValue() +
                ", portType='" + getPortType() + '\'' +
                ", aServer=" + getaServer() +
                ", hostDNSName='" + getHostDNSName() + '\'' +
                ", implementingWUP=" + getImplementingWUP() +
                ", encrypted=" + isEncrypted() +
                ", additionalParameters=" + getAdditionalParameters() +
                ", connectedSystemName='" + getConnectedSystemName() + '\'' +
                ", name='" + getName() + '\'' +
                ", endpointType=" + getEndpointType() +
                '}';
    }
}
