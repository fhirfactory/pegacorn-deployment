package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorkUnitProcessorTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(WorkUnitProcessorTopologyNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, WorkUnitProcessorComponentTopologyNode> wupComponents;
    private ConcurrentHashMap<TopologyNodeRDN, WorkUnitProcessorInterchangeComponentTopologyNode> wupInterchangeComponents;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public Map<TopologyNodeRDN, WorkUnitProcessorComponentTopologyNode> getWupComponents() {
        return wupComponents;
    }

    public void setWupComponents(Map<TopologyNodeRDN, WorkUnitProcessorComponentTopologyNode> wupComponents) {
        this.wupComponents = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> componentNames = wupComponents.keySet();
        for(TopologyNodeRDN name: componentNames){
            this.wupComponents.putIfAbsent(name, wupComponents.get(name));
        }
    }

    public Map<TopologyNodeRDN, WorkUnitProcessorInterchangeComponentTopologyNode> getWupInterchangeComponents() {
        return wupInterchangeComponents;
    }

    public void setWupInterchangeComponents(Map<TopologyNodeRDN, WorkUnitProcessorInterchangeComponentTopologyNode> wupInterchangeComponents) {
        this.wupInterchangeComponents = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> componentNames = wupInterchangeComponents.keySet();
        for(TopologyNodeRDN name: componentNames){
            this.wupInterchangeComponents.putIfAbsent(name, wupInterchangeComponents.get(name));
        }
    }
}
