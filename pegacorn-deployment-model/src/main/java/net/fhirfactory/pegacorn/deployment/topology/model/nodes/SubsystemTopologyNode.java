package net.fhirfactory.pegacorn.deployment.topology.model.nodes;

import net.fhirfactory.pegacorn.deployment.topology.model.common.TopologyNode;
import net.fhirfactory.pegacorn.common.model.componentid.TopologyNodeRDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SubsystemTopologyNode extends TopologyNode {
    private static final Logger LOG = LoggerFactory.getLogger(SubsystemTopologyNode.class);

    private ConcurrentHashMap<TopologyNodeRDN, BusinessServiceTopologyNode> businessServices;
    private Integer siteCount;

    @Override
    protected Logger getLogger() {
        return (LOG);
    }

    public Map<TopologyNodeRDN, BusinessServiceTopologyNode> getBusinessServices() {
        return businessServices;
    }

    public void setBusinessServices(Map<TopologyNodeRDN, BusinessServiceTopologyNode> businessServices) {
        this.businessServices = new ConcurrentHashMap<>();
        Set<TopologyNodeRDN> serviceNames = businessServices.keySet();
        for(TopologyNodeRDN serviceName: serviceNames){
            this.businessServices.putIfAbsent(serviceName, businessServices.get(serviceName));
        }
    }

    public Integer getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(Integer siteCount) {
        this.siteCount = siteCount;
    }
}
