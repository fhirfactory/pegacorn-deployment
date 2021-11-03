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
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.ipc;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.common.segments.ports.base.StandardClusterServiceServerPortSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPIPCServerPortSegment extends StandardClusterServiceServerPortSegment {
    private static Logger LOG = LoggerFactory.getLogger(HTTPIPCServerPortSegment.class);

    private  Integer clusterServicePortOffsetValue;
    private String path;

    public HTTPIPCServerPortSegment(){
        super();
    }

    @Override
    protected Logger specifyLogger() {
        return (LOG);
    }

    public Integer getClusterServicePortOffsetValue() {
        return clusterServicePortOffsetValue;
    }

    public void setClusterServicePortOffsetValue(Integer clusterServicePortOffsetValue) {
        this.clusterServicePortOffsetValue = clusterServicePortOffsetValue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "HTTPIPCPortSegment{" +
                "portValue=" + getPortValue() +
                ", portType=" + getPortType() +
                ", isServer=" + isServer() +
                ", isEncrypted=" + isEncrypted() +
                ", hostDNSEntry=" + getHostDNSEntry() +
                ", supportedInterfaceProfiles=" + getSupportedInterfaceProfiles() +
                ", servicePortValue=" + getServicePortValue() +
                ", servicePortName=" + getServicePortName() +
                ", serviceDNSEntry=" + getServiceDNSEntry() +
                ", clusterServicePortOffsetValue=" + clusterServicePortOffsetValue +
                ", clusterServicePortOffsetValue=" + clusterServicePortOffsetValue +
                ", path=" + path +
                '}';
    }
}