package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PlatformNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(PlatformNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, ProcessingPlantTopologyNode> processingPlants;
    private Integer instanceCount;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public Map<TopologyNodeRDN, ProcessingPlantTopologyNode> getProcessingPlants() {
        return processingPlants;
    }

    public void setProcessingPlants(Map<TopologyNodeRDN, ProcessingPlantTopologyNode> processingPlants) {
        this.processingPlants = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> plantNames = processingPlants.keySet();
        for(TopologyNodeRDN name: plantNames){
            this.processingPlants.putIfAbsent(name, processingPlants.get(name));
        }
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
    }
}
