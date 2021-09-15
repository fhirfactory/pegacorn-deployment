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

import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeFDN;
import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class WorkUnitProcessorTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(WorkUnitProcessorTopologyNode.class);

    private ArrayList<TopologyNodeFDN> wupComponents;
    private ArrayList<TopologyNodeFDN> wupInterchangeComponents;
    private ArrayList<TopologyNodeFDN> endpoints;

    public WorkUnitProcessorTopologyNode(){
        this.wupComponents = new ArrayList<>();
        this.wupInterchangeComponents = new ArrayList<>();
        this.endpoints = new ArrayList<>();
    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public ArrayList<TopologyNodeFDN> getWupComponents() {
        return wupComponents;
    }

    public void setWupComponents(ArrayList<TopologyNodeFDN> wupComponents) {
        this.wupComponents = wupComponents;
    }

    public ArrayList<TopologyNodeFDN> getWupInterchangeComponents() {
        return wupInterchangeComponents;
    }

    public void setWupInterchangeComponents(ArrayList<TopologyNodeFDN> wupInterchangeComponents) {
        this.wupInterchangeComponents = wupInterchangeComponents;
    }

    public ArrayList<TopologyNodeFDN> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(ArrayList<TopologyNodeFDN> endpoints) {
        this.endpoints = endpoints;
    }
}
