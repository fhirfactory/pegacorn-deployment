/*
 * The MIT License
 *
 * Copyright 2020 Mark A. Hunter.
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
package net.fhirfactory.pegacorn.deployment.topology.map.solution;

import net.fhirfactory.pegacorn.deployment.topology.manager.TopologyIM;
import org.slf4j.Logger;

import javax.inject.Inject;

/**
 *
 * @author Mark A Hunter
 *
 */
public abstract class SolutionTopologyFactory {

    @Inject
    private TopologyIM topologyIM;

    abstract Logger getLogger();

    protected TopologyIM getTopologyIM(){
        return(topologyIM);
    }

    /*
    private void printTopologySummary(){
        getLogger().info(".printTopologySummary(): Entry");
        Set<NodeElement> nodeSet = getTopologyIM().getNodeSet();
        for(NodeElement node: nodeSet){
            getLogger().info("Node: Type -->{}, Name --> {}", node.getNodeArchetype().getNodeElementType(), node.getNodeInstanceID().toString());
            for(EndpointElementIdentifier endpointIdentifier: node.getEndpoints()){
                FDN endpointFDN = new FDN(endpointIdentifier);
                // LOG.info("      Endpoint: Name --> {}", endpointIdentifier);
                EndpointElement endpointElement = getTopologyIM().getEndpoint(endpointIdentifier);
                getLogger().info("      Endpoint: {} --> isServer={}, InternalHostname/Port={}/{}, ExternalHostname/Port={}/{}", endpointFDN.getUnqualifiedRDN().getUnqualifiedValue(), endpointElement.isServer(), endpointElement.getInternalHostname(), endpointElement.getInternalPort(), endpointElement.getExposedHostname(), endpointElement.getExposedPort());
            }
        }
    }
*/
}
