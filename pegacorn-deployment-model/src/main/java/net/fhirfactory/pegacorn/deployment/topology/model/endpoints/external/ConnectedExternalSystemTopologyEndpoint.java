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
package net.fhirfactory.pegacorn.deployment.topology.model.endpoints.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectedExternalSystemTopologyEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectedExternalSystemTopologyEndpoint.class);

    private String targetPortType;
    private Boolean encryptionRequired;
    private Integer targetPortValue;
    private String targetPortDNSName;

    public String getTargetPortType() {
        return targetPortType;
    }

    public void setTargetPortType(String targetPortType) {
        this.targetPortType = targetPortType;
    }

    public Boolean getEncryptionRequired() {
        return encryptionRequired;
    }

    public void setEncryptionRequired(Boolean encryptionRequired) {
        this.encryptionRequired = encryptionRequired;
    }

    public Integer getTargetPortValue() {
        return targetPortValue;
    }

    public void setTargetPortValue(Integer targetPortValue) {
        this.targetPortValue = targetPortValue;
    }

    public String getTargetPortDNSName() {
        return targetPortDNSName;
    }

    public void setTargetPortDNSName(String targetPortDNSName) {
        this.targetPortDNSName = targetPortDNSName;
    }
}
