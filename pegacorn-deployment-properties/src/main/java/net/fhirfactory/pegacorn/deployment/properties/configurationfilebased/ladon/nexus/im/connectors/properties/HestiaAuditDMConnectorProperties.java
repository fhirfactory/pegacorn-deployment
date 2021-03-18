/*
 * The MIT License
 *
 * Copyright 2020 Mark A. Hunter (ACT Health).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.properties;

import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.properties.common.LadonConnectorProperties;
import net.fhirfactory.pegacorn.deployment.properties.configurationfilebased.ladon.nexus.im.connectors.segments.HestiaAuditDMPropertFileSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HestiaAuditDMConnectorProperties extends LadonConnectorProperties {
    private static Logger LOG = LoggerFactory.getLogger(HestiaAuditDMConnectorProperties.class);
    private static String TARGET_QAULIFIER_STRING = "hestia-audit";

    private HestiaAuditDMPropertFileSegment propertFileSegment;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public HestiaAuditDMConnectorProperties(Integer lowerClusterServicePortBound, HestiaAuditDMPropertFileSegment propertFileSegment)
    {
        super(TARGET_QAULIFIER_STRING, lowerClusterServicePortBound, propertFileSegment);
        this.propertFileSegment = propertFileSegment;
    }
}
