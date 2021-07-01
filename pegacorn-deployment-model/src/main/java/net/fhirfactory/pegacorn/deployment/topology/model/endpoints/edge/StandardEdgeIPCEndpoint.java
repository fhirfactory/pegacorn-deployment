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
package net.fhirfactory.pegacorn.deployment.topology.model.endpoints.edge;

import net.fhirfactory.pegacorn.deployment.topology.model.endpoints.base.IPCServerTopologyEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StandardEdgeIPCEndpoint extends IPCServerTopologyEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(StandardEdgeIPCEndpoint.class);

    private List<InitialHostSpecification> initialHosts;
    private String nameSpace;

    public StandardEdgeIPCEndpoint(){
        super();
        initialHosts = new ArrayList<>();
    }

    public List<InitialHostSpecification> getInitialHosts() {
        return initialHosts;
    }

    public void setInitialHosts(List<InitialHostSpecification> initialHosts) {
        this.initialHosts = initialHosts;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    @Override
    public String toString() {
        return "StandardEdgeIPCEndpoint{" +
                "nodeRDN=" + getNodeRDN() +
                ", nodeFDN=" + getNodeFDN() +
                ", componentType=" + getComponentType() +
                ", containingNodeFDN=" + getContainingNodeFDN() +
                ", nodeKey='" + getNodeKey() + '\'' +
                ", nodeFunctionFDN=" + getNodeFunctionFDN() +
                ", concurrencyMode=" + getConcurrencyMode() +
                ", resilienceMode=" + getResilienceMode() +
                ", securityZone=" + getSecurityZone() +
                ", kubernetesDeployed=" + isKubernetesDeployed() +
                ", supportedInterfaceSet=" + getSupportedInterfaceSet() +
                ", encrypted=" + isEncrypted() +
                ", portValue=" + getPortValue() +
                ", portType='" + getPortType() + '\'' +
                ", aServer=" + getaServer() +
                ", encrypted=" + getEncrypted() +
                ", interfaceDNSName='" + getInterfaceDNSName() + '\'' +
                ", additionalParameters=" + getAdditionalParameters() +
                ", name='" + getName() + '\'' +
                ", endpointType=" + getEndpointType() +
                ", initialHosts=" + initialHosts +
                ", nameSpace='" + nameSpace + '\'' +
                '}';
    }
}
