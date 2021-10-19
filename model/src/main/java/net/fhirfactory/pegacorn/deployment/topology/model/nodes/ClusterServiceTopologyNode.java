/*
 * Copyright (c) 2020 Mark A. Hunter (ACT Health)
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
package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.fhirfactory.pegacorn.common.model.componentid.ComponentIdType;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.deployment.topology.model.nodes.common.EndpointProviderInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ClusterServiceTopologyNode extends TopologyNode implements EndpointProviderInterface {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterServiceTopologyNode.class);

    private ArrayList<ComponentIdType> platformNodes;
    private Integer platformNodeCount;
    private ArrayList<ComponentIdType> serviceEndpoints;
    private String defaultDNSName;
    private boolean internalTrafficEncrypted;

    //
    // Constructor(s)
    //

    public ClusterServiceTopologyNode(){
        super();
        this.platformNodes = new ArrayList<>();
        this.serviceEndpoints = new ArrayList<>();
    }

    //
    // Getters and Setters
    //

    @Override @JsonIgnore
    protected Logger getLogger() {
        return (LOG);
    }

    @JsonIgnore
    public boolean hasPlatformNodes(){
        boolean hasValue = this.platformNodes != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getPlatformNodes() {
        return platformNodes;
    }

    public void setPlatformNodes(ArrayList<ComponentIdType> platformNodes) {
        this.platformNodes = platformNodes;
    }

    @JsonIgnore
    public boolean hasServiceEndpoints(){
        boolean hasValue = this.serviceEndpoints != null;
        return(hasValue);
    }

    public ArrayList<ComponentIdType> getServiceEndpoints() {
        return serviceEndpoints;
    }

    public void setServiceEndpoints(ArrayList<ComponentIdType> serviceEndpoints) {
        this.serviceEndpoints = serviceEndpoints;
    }

    @JsonIgnore
    public boolean hasDefaultDNSName(){
        boolean hasValue = this.defaultDNSName != null;
        return(hasValue);
    }

    public String getDefaultDNSName() {
        return defaultDNSName;
    }

    public void setDefaultDNSName(String defaultDNSName) {
        this.defaultDNSName = defaultDNSName;
    }

    public boolean isInternalTrafficEncrypted() {
        return internalTrafficEncrypted;
    }

    public void setInternalTrafficEncrypted(boolean internalTrafficEncrypted) {
        this.internalTrafficEncrypted = internalTrafficEncrypted;
    }

    @JsonIgnore
    public boolean hasPlatformNodeCount(){
        boolean hasValue = this.platformNodeCount != null;
        return(hasValue);
    }

    public Integer getPlatformNodeCount() {
        return platformNodeCount;
    }

    public void setPlatformNodeCount(Integer platformNodeCount) {
        this.platformNodeCount = platformNodeCount;
    }

    @Override
    public void addEndpoint(ComponentIdType endpointId) {
        getLogger().debug(".addEndpoint(): Entry, endpointId->{}", endpointId);
        serviceEndpoints.add(endpointId);
    }
}
