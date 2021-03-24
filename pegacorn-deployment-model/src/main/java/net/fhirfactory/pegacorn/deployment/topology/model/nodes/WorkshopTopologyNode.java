package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorkshopTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(WorkshopTopologyNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, WorkUnitProcessorTopologyNode> wupSet;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public Map<TopologyNodeRDN, WorkUnitProcessorTopologyNode> getWupSet() {
        return wupSet;
    }

    public void setWupSet(Map<TopologyNodeRDN, WorkUnitProcessorTopologyNode> incomingWUPSet) {
        this.wupSet = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> wupNames = incomingWUPSet.keySet();
        for(TopologyNodeRDN name: wupNames){
            this.wupSet.putIfAbsent(name, incomingWUPSet.get(name));
        }
    }
}
