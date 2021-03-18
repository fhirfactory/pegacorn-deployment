package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SolutionTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(SolutionTopologyNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, SubsystemTopologyNode> subsystemList;
    private String systemOwner;

    public SolutionTopologyNode(){
        subsystemList = new ConcurrentHashMap<>();
    }

    public Map<TopologyNodeRDN, SubsystemTopologyNode> getSubsystemList() {
        return subsystemList;
    }

    public void setSubsystemList(Map<TopologyNodeRDN, SubsystemTopologyNode> subsystemList) {
        this.subsystemList = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> serviceNames = subsystemList.keySet();
        for(TopologyNodeRDN serviceName: serviceNames){
            this.subsystemList.putIfAbsent(serviceName, subsystemList.get(serviceName));
        }
    }

    public String getSystemOwner() {
        return systemOwner;
    }

    public void setSystemOwner(String systemOwner) {
        this.systemOwner = systemOwner;

    }

    @Override
    protected Logger getLogger() {
        return (LOG);
    }
}
